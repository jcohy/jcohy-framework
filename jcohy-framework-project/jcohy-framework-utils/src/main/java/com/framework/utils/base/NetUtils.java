package com.framework.utils.base;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import com.framework.utils.stream.StreamUtils;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Copyright: Copyright (c) 2020 <a href="http://www.jcohy.com" target="_blank">jcohy.com</a>
 *
 * <p> Description:
 *
 * @author jiac
 * @version 1.0.0 2021/1/5:16:05
 * @since 1.0.0
 */
public class NetUtils {

	/**
	 * uri 解码，有以下规则
	 *
	 * <ul>
	 * <li>字母 {@code "a"} - {@code "z"}, {@code "A"} - {@code "Z"},
	 * 和 数字 {@code "0"} - {@code "9"} 保持不变.</li>
	 * <li>特殊字符 {@code "-"}, {@code "_"}, {@code "."}, 和 {@code "*"} 保持不变.</li>
	 * <li>"{@code %<i>xy</i>}" 被解释为字符的十六进制表示形式.</li>
	 * </ul>
	 *
	 * @param source 编码的字符串
	 * @param charset 字符集
	 * @return 解码值
	 * @throws IllegalArgumentException 当 {@code source}包含无效的编码序列时
	 * @see java.net.URLDecoder#decode(String, String)
	 */
	public static String uriDecode(String source, Charset charset){
		int length = source.length();
		if(length == 0){
			return source;
		}

		checkNotNull(charset,"Charset must not be null");

		ByteArrayOutputStream baos = new ByteArrayOutputStream(length);
		boolean changed = false;
		for( int i = 0; i < length; i++){
			int ch = source.charAt(i);
			if(ch == '%'){
				if( i + 2 < length ){
					char hex1 = source.charAt(i + 1);
					char hex2 = source.charAt(i + 2);
					int u = Character.digit(hex1,16);
					int l = Character.digit(hex2,16);
					if( u == -1 || l == -1){
						throw new IllegalArgumentException("Invalid encoded sequence \"" + source.substring(i) + "\"");
					}
					baos.write((char)((u << 4) + l));
					i += 2;
					changed = true;
				}
				else {
					throw new IllegalArgumentException("Invalid encoded sequence \"" + source.substring(i) + "\"");
				}
			}
			else {
				baos.write(ch);
			}
		}
		return (changed ? StreamUtils.copyToString(baos, charset) : source);
	}
}
