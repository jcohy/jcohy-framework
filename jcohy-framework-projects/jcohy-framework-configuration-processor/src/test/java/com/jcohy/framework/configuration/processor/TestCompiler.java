package com.jcohy.framework.configuration.processor;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.annotation.processing.Processor;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

/**
 * 描述: Wrapper to make the {@link JavaCompiler} easier to use in tests.
 * <p>
 *     Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 * @author jiac
 * @version 2022.0.1 2/14/22:18:23
 * @since 2022.0.1
 */
public class TestCompiler {

    /**
     * The default source directory.
     */
    public static final File SOURCE_DIRECTORY = new File("src/test/java");

    private final JavaCompiler compiler;

    private final StandardJavaFileManager fileManager;

    private final File outputLocation;

    public TestCompiler(File outputLocation) throws IOException {
        this(ToolProvider.getSystemJavaCompiler(), outputLocation);
    }

    public TestCompiler(JavaCompiler compiler, File outputLocation) throws IOException {
        this.compiler = compiler;
        this.fileManager = compiler.getStandardFileManager(null, null, null);
        this.outputLocation = outputLocation;
        this.outputLocation.mkdirs();
        Iterable<? extends File> temp = Collections.singletonList(this.outputLocation);
        this.fileManager.setLocation(StandardLocation.CLASS_OUTPUT, temp);
        this.fileManager.setLocation(StandardLocation.SOURCE_OUTPUT, temp);
    }

    public static String sourcePathFor(Class<?> type) {
        return type.getName().replace('.', '/') + ".java";
    }

    public TestCompilationTask getTask(Collection<File> sourceFiles) {
        Iterable<? extends JavaFileObject> javaFileObjects = this.fileManager.getJavaFileObjectsFromFiles(sourceFiles);
        return getTask(javaFileObjects);
    }

    public TestCompilationTask getTask(Class<?>... types) {
        Iterable<? extends JavaFileObject> javaFileObjects = getJavaFileObjects(types);
        return getTask(javaFileObjects);
    }

    private TestCompilationTask getTask(Iterable<? extends JavaFileObject> javaFileObjects) {
        return new TestCompilationTask(
                this.compiler.getTask(null, this.fileManager, null, null, null, javaFileObjects));
    }

    public File getOutputLocation() {
        return this.outputLocation;
    }

    private Iterable<? extends JavaFileObject> getJavaFileObjects(Class<?>... types) {
        File[] files = new File[types.length];
        for (int i = 0; i < types.length; i++) {
            files[i] = getFile(types[i]);
        }
        return this.fileManager.getJavaFileObjects(files);
    }

    protected File getFile(Class<?> type) {
        return new File(getSourceDirectory(), sourcePathFor(type));
    }

    protected File getSourceDirectory() {
        return SOURCE_DIRECTORY;
    }

    /**
     * A compilation task.
     */
    public static class TestCompilationTask {

        private final CompilationTask task;

        public TestCompilationTask(CompilationTask task) {
            this.task = task;
        }

        public void call(Processor... processors) {
            this.task.setProcessors(Arrays.asList(processors));
            if (!this.task.call()) {
                throw new IllegalStateException("Compilation failed");
            }
        }

    }

}
