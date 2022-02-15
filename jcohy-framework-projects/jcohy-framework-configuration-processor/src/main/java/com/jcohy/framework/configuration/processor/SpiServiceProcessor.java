package com.jcohy.framework.configuration.processor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import com.jcohy.framework.configuration.processor.utils.Constants;
import com.jcohy.framework.configuration.processor.utils.Elements;
import com.jcohy.framework.configuration.processor.utils.MultiMapSet;
import com.jcohy.framework.configuration.processor.value.ValueExtractor;

/**
 * 描述: .
 *
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 2022/2/11:11:24
 * @since 2022.0.1
 */
@SupportedAnnotationTypes({ "com.jcohy.framework.configuration.processor.annotations.JcohySpiService" })
public class SpiServiceProcessor extends AbstractConfigureAnnotationProcessor {

    private final Map<String, String> annotations;

    private final Map<String, ValueExtractor> valueExtractors;

    /**
     * spi 服务集合，key 接口 -> value 实现列表.
     */
    private final MultiMapSet<String, String> providers = new MultiMapSet<>();

    public SpiServiceProcessor() {
        Map<String, String> annotations = new LinkedHashMap<>();
        addAnnotations(annotations);
        this.annotations = Collections.unmodifiableMap(annotations);
        Map<String, ValueExtractor> valueExtractors = new LinkedHashMap<>();
        addValueExtractors(valueExtractors);
        this.valueExtractors = Collections.unmodifiableMap(valueExtractors);
    }

    private void addAnnotations(Map<String, String> annotations) {
        annotations.put("JcohySpiService", "com.jcohy.framework.configuration.processor.annotations.JcohySpiService");
    }

    private void addValueExtractors(Map<String, ValueExtractor> attributes) {
        attributes.put("JcohySpiService", ValueExtractor.allFrom("value"));
    }

    @Override
    protected boolean processImpl(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Map.Entry<String, String> entry : this.annotations.entrySet()) {
            processImpl(roundEnv, entry.getKey(), entry.getValue());
        }
        if (roundEnv.processingOver()) {
            try {
                writeProperties();
            }
            catch (Exception ex) {
                throw new IllegalStateException("Failed to write metadata", ex);
            }
        }
        return false;
    }

    private void processImpl(RoundEnvironment roundEnv, String propertyKey, String annotationName) {
        TypeElement annotationType = this.processingEnv.getElementUtils().getTypeElement(annotationName);
        if (annotationType != null) {
            for (Element element : roundEnv.getElementsAnnotatedWith(annotationType)) {
                processElement(element, propertyKey, annotationName);
            }
        }
    }

    private void processElement(Element element, String propertyKey, String annotationName) {
        try {
            String qualifiedName = Elements.getQualifiedName(element);
            AnnotationMirror annotation = getAnnotation(element, annotationName);
            if (qualifiedName != null && annotation != null) {
                List<Object> interfaces = getValues(propertyKey, annotation);
                log("provider interface: " + interfaces);
                log("provider implementer: " + qualifiedName);
                this.providers.put(interfaces.get(0).toString(), qualifiedName);
            }
        }
        catch (Exception ex) {
            throw new IllegalStateException("Error processing configuration meta-data on " + element, ex);
        }
    }

    private AnnotationMirror getAnnotation(Element element, String type) {
        if (element != null) {
            for (AnnotationMirror annotation : element.getAnnotationMirrors()) {
                if (type.equals(annotation.getAnnotationType().toString())) {
                    return annotation;
                }
            }
        }
        return null;
    }

    private List<Object> getValues(String propertyKey, AnnotationMirror annotation) {
        ValueExtractor extractor = this.valueExtractors.get(propertyKey);
        if (extractor == null) {
            return Collections.emptyList();
        }
        return extractor.getValues(annotation);
    }

    /**
     * 输出文件.
     * @throws IOException /
     */
    private void writeProperties() throws IOException {
        Filer filer = processingEnv.getFiler();
        for (String providerInterface : this.providers.keySet()) {
            String resourceFile = Constants.SERVICE_RESOURCE_LOCATION + providerInterface;
            log("Working on resource file: " + resourceFile);
            try {
                SortedSet<String> allServices = new TreeSet<>();
                try {
                    FileObject existingFile = filer.getResource(StandardLocation.CLASS_OUTPUT, "", resourceFile);
                    log("Looking for existing resource file at " + existingFile.toUri());
                    Set<String> oldServices = readServiceFile(existingFile.openInputStream());
                    log("Existing service entries: " + oldServices);
                    allServices.addAll(oldServices);
                }
                catch (IOException ex) {
                    log("Resource file did not already exist.");
                }

                Set<String> newServices = new HashSet<>(this.providers.get(providerInterface));
                if (allServices.containsAll(newServices)) {
                    log("No new service entries being added.");
                    return;
                }

                allServices.addAll(newServices);
                log("New service file contents: " + allServices);
                FileObject fileObject = filer.createResource(StandardLocation.CLASS_OUTPUT, "", resourceFile);
                OutputStream out = fileObject.openOutputStream();
                writeServiceFile(allServices, out);
                out.close();
                log("Wrote to: " + fileObject.toUri());
            }
            catch (IOException ex) {
                fatalError("Unable to create " + resourceFile + ", " + ex);
                return;
            }

        }
    }

    /**
     * 从服务文件中读取 服务类.
     * @param input not {@code null}. Closed after use.
     * @return a not {@code null Set} of service class names.
     * @throws IOException ex
     */
    public static Set<String> readServiceFile(InputStream input) throws IOException {
        HashSet<String> serviceClasses = new HashSet<String>();
        try (InputStreamReader isr = new InputStreamReader(input, StandardCharsets.UTF_8);
                BufferedReader r = new BufferedReader(isr)) {
            String line;
            while ((line = r.readLine()) != null) {
                int commentStart = line.indexOf('#');
                if (commentStart >= 0) {
                    line = line.substring(0, commentStart);
                }
                line = line.trim();
                if (!line.isEmpty()) {
                    serviceClasses.add(line);
                }
            }
            return serviceClasses;
        }
    }

    /**
     * 将服务类编写进服务文件中.
     * @param output not {@code null}. Not closed after use.
     * @param services a not {@code null Collection} of service class names.
     * @throws IOException ex
     */
    public static void writeServiceFile(Collection<String> services, OutputStream output) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
        for (String service : services) {
            writer.write(service);
            writer.newLine();
        }
        writer.flush();
    }

}
