package com.jcohy.framework.utils;

import java.io.File;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 描述: .
 *
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 3/11/22:17:17
 * @since 2022.0.1
 */
public class FileUtilsTest {

    @Test
    void toNetFile() {
        String s = FileUtils.toNetFile("https://www.jcohy.com/images/jcohy.png");
        assertThat(new File(s)).exists();
    }

    @Test
    void toNetFileWithUrl() {
        String s = FileUtils.toNetFile("https://www.jcohy.com/images/jcohy.png", "/Users/jcohy/Downloads/");
        assertThat(new File(s)).exists();
    }

    // @Test
    // void listFiles() throws IOException {
    // FileUtils.listAllFiles("/Users/jcohy/Downloads").forEach(System.out::println);
    // final AccumulatorPathVisitor visitor =
    // AccumulatorPathVisitor.withLongCounters(FileFileFilter.FILE);
    // Walk dir tree
    // Files.walkFileTree(Paths.get("/Users/jcohy/Downloads"), visitor);
    // System.out.println(visitor.getPathCounters());
    // System.out.println(visitor.getDirList());
    // System.out.println(visitor.getFileList());
    // }

}
