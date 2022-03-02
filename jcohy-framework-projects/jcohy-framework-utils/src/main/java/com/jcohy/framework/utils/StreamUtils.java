package com.jcohy.framework.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.stream.Streams;

/**
 * 描述: .
 *
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 3/2/22:15:02
 * @since 2022.0.1
 */
public class StreamUtils extends Streams {

    /**
     * 默认 buffer 大小.
     */
    public static final int BUFFER_SIZE = 4096;

    /**
     * 将给定 InputStream 的内容复制到一个字符串中.
     * <p>
     * 完成后保持流打开.
     * @param in 要复制的 InputStream (可能为 {@code null} 或空)
     * @param charset 编码 {@link Charset}
     * @return 已复制到的字符串（可能为空）
     * @throws IOException 在 I/O 错误的情况下
     * @since 2022.0.1
     */
    public static String copyToString(InputStream in, Charset charset) throws IOException {
        if (in == null) {
            return "";
        }

        StringBuilder out = new StringBuilder(BUFFER_SIZE);
        InputStreamReader reader = new InputStreamReader(in, charset);
        char[] buffer = new char[BUFFER_SIZE];
        int charsRead;
        while ((charsRead = reader.read(buffer)) != -1) {
            out.append(buffer, 0, charsRead);
        }
        return out.toString();
    }

    /**
     * 将给定 {@link ByteArrayOutputStream} 的内容复制到一个字符串中。
     * <p>
     * 更有效的等价于 {@code new String(baos.toByteArray(), charset)}.
     * @param baos 要复制到字符串中的 {@code ByteArrayOutputStream}
     * @param charset 编码 {@link Charset}
     * @return 已复制到的字符串（可能为空）
     * @since 2022.0.1
     */
    public static String copyToString(ByteArrayOutputStream baos, Charset charset) {
        Validate.notNull(baos, "No ByteArrayOutputStream specified");
        Validate.notNull(charset, "No Charset specified");
        try {
            // Can be replaced with toString(Charset) call in Java 10+
            return baos.toString(charset.name());
        }
        catch (UnsupportedEncodingException ex) {
            // Should never happen
            throw new IllegalArgumentException("Invalid charset name: " + charset, ex);
        }
    }

}
