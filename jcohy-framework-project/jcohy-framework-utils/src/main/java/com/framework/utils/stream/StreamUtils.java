package com.framework.utils.stream;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Copyright: Copyright (c) 2020 <a href="http://www.jcohy.com" target="_blank">jcohy.com</a>
 *
 * <p> Description:
 *
 * @author jiac
 * @version 1.0.0 2021/1/5:16:18
 * @since 1.0.0
 */
public class StreamUtils {

	public static String copyToString(ByteArrayOutputStream baos, Charset charset) {
		checkNotNull(baos, "No ByteArrayOutputStream specified");
		checkNotNull(charset, "No Charset specified");
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
