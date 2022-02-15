package com.jcohy.framework.configuration.processor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import com.jcohy.framework.configuration.processor.utils.Constants;
import com.jcohy.framework.configuration.processor.utils.Elements;
import com.jcohy.framework.configuration.processor.utils.MultiMapSet;
import com.jcohy.framework.configuration.processor.utils.Types;
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
@SupportedAnnotationTypes({ "*" })
public class SpringFactoriesProcessor extends AbstractConfigureAnnotationProcessor {

    /**
     * key 为注解，value 为注解所需要实现的接口.为了避免在类上的注解和实现的接口不匹配.
     */
    private final Map<String, String> annotations;

    private final Map<String, ValueExtractor> valueExtractors;

    private final MultiMapSet<String, String> factories = new MultiMapSet<>();

    public SpringFactoriesProcessor() {
        Map<String, String> annotations = new LinkedHashMap<>();
        addAnnotations(annotations);
        this.annotations = Collections.unmodifiableMap(annotations);
        Map<String, ValueExtractor> valueExtractors = new LinkedHashMap<>();
        addValueExtractors(valueExtractors);
        this.valueExtractors = Collections.unmodifiableMap(valueExtractors);
    }

    private void addAnnotations(Map<String, String> annotations) {
        annotations.put("com.jcohy.framework.configuration.processor.annotations.JcohyApplicationContextInitializer",
                "org.springframework.context.ApplicationContextInitializer");
        annotations.put("com.jcohy.framework.configuration.processor.annotations.JcohySagaApplicationListener",
                "org.springframework.context.ApplicationListener");
        annotations.put("com.jcohy.framework.configuration.processor.annotations.JcohySagaFailureAnalyzer",
                "org.springframework.boot.diagnostics.JcohyFailureAnalyzer");
        annotations.put("org.springframework.stereotype.Component",
                "org.springframework.boot.autoconfigure.EnableAutoConfiguration");
    }

    private void addValueExtractors(Map<String, ValueExtractor> attributes) {
    }

    @Override
    protected boolean processImpl(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        processAnnotations(annotations, roundEnv);

        if (roundEnv.processingOver()) {
            try {
                writeProperties();
            }
            catch (Exception ex) {
                throw new IllegalStateException("Failed to write metadata", ex);
            }
        }
        log("SpringFactoriesProcessor processor end ");
        return false;
    }

    /**
     * 注解处理.
     * @param annotations 注解
     * @param roundEnv roundEnv
     */
    private void processAnnotations(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // roundEnv.getRootElements() 会返回工程中所有的 Class,在实际应用中需要对各个 Class 先做过滤以提高效率，避免对每个
        // Class 的内容都进行扫描
        Set<TypeElement> typeElements = roundEnv.getRootElements().stream().filter(this::isClassOrInterface)
                .filter((element) -> element instanceof TypeElement).map((element) -> (TypeElement) element)
                .collect(Collectors.toSet());

        // 如果为空直接跳出
        if (typeElements.isEmpty()) {
            log("Annotations elementSet is isEmpty");
            return;
        }

        log("All typeElements: " + typeElements.toString());

        for (TypeElement typeElement : typeElements) {
            for (Map.Entry<String, String> entry : this.annotations.entrySet()) {
                if (checkImplementer(typeElement, typeElement.getInterfaces())
                        && checkAnnotation(typeElement, entry.getKey())) {
                    log("add new spring.factories factoryName：" + typeElement.getQualifiedName().toString());
                    this.factories.put(entry.getValue(), Elements.getBinaryName(typeElement));
                }
            }
        }
    }

    /**
     * 检查元素类型是否是接口或类.
     * @param e 元素
     * @return {@code true} 是接口或类
     */
    private boolean isClassOrInterface(Element e) {
        ElementKind kind = e.getKind();
        return kind == ElementKind.CLASS || kind == ElementKind.INTERFACE;
    }

    /**
     * 注解检查处理，主要是判断是否是组合注解（不包含 java 元注解）.
     * @param annotationTypeElement annotationTypeElement
     * @param annotationName 注解名
     * @return /
     */
    private boolean checkAnnotation(Element annotationTypeElement, String annotationName) {
        List<? extends AnnotationMirror> allAnnotationMirrors = processingEnv.getElementUtils()
                .getAllAnnotationMirrors(annotationTypeElement);
        log("allAnnotationMirrors======" + allAnnotationMirrors.toString());
        for (AnnotationMirror annotation : allAnnotationMirrors) {

            // 如果是普通的单注解，eg：@Component
            if (checkAnnotation(annotationName, annotation)) {
                return true;
            }
            // 处理组合注解
            Element element = annotation.getAnnotationType().asElement();
            // 如果是 java 元注解，不处理，继续循环
            if (element.toString().startsWith("java.lang")) {
                continue;
            }
            // eg: @Configuration
            if (checkAnnotation(element, annotationName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断注解名和注解是不是同一个.
     * @param annotationFullName 注解名
     * @param annotation 注解类型
     * @return /
     */
    private boolean checkAnnotation(String annotationFullName, AnnotationMirror annotation) {
        return annotationFullName.equals(annotation.getAnnotationType().toString());
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
     * 检查被注解的类实现的接口是否和相对应的注解匹配. eg: 使用
     * com.jcohy.framework.configuration.processor.annotations.SagaApplicationContextInitializer
     * 注解的必须实现 org.springframework.context.ApplicationContextInitializer
     * @param annotationTypeElement 被注解的类。
     * @param interfaces 接口
     * @return {@code true} 匹配
     */
    private boolean checkImplementer(TypeElement annotationTypeElement, List<? extends TypeMirror> interfaces) {
        for (AnnotationMirror annotation : annotationTypeElement.getAnnotationMirrors()) {
            String annotationType = annotation.getAnnotationType().toString();

            // 如果为其他注解，注解返回 true，不进行校验
            if (!annotationType.startsWith("com.jcohy.framework")) {
                return true;
            }
            // 如果元素上的注解为 org.springframework 不做接口实现与 AutoService 注解上的接口是否一致
            if (this.annotations.containsKey(annotationType)
                    && Types.contain(interfaces, this.annotations.get(annotationType))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 写出 spring.factories 文件.
     * @throws IOException /
     */
    private void writeProperties() throws IOException {

        if (!this.factories.isEmpty()) {
            Filer filer = this.processingEnv.getFiler();
            FileObject file = filer.createResource(StandardLocation.CLASS_OUTPUT, "",
                    Constants.SPRING_FACTORY_RESOURCE_LOCATION);
            log("Starting write spring.factories file: " + file);
            try (BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(file.openOutputStream(), StandardCharsets.UTF_8))) {
                Set<String> keySet = this.factories.keySet();
                for (String key : keySet) {
                    Set<String> values = this.factories.get(key);
                    if (values == null || values.isEmpty()) {
                        continue;
                    }
                    writer.write(key);
                    writer.write("=\\\n  ");
                    StringJoiner joiner = new StringJoiner(",\\\n  ");
                    for (String value : values) {
                        joiner.add(value);
                    }
                    writer.write(joiner.toString());
                    writer.newLine();
                }
            }
            log("end write spring.factories file: " + file);
        }
    }

}
