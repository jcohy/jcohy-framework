package com.jcohy.framework.utils.constant;

/**
 * 描述: Char 字符常量池.
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 2/18/22:15:30
 * @since 2022.0.1
 */
public interface CharPools {

    /**
     * 字符常量：空格符 {@code ' '}.
     */
    char SPACE = ' ';

    /**
     * 字符常量：制表符 {@code '\t'}.
     */
    char TAB = '	';

    /**
     * 字符常量：点 {@code '.'}.
     */
    char DOT = '.';

    /**
     * 字符常量：斜杠 {@code '/'}.
     */
    char SLASH = '/';

    /**
     * 字符常量：反斜杠 {@code '\\'}.
     */
    char BACKSLASH = '\\';

    /**
     * 字符常量：回车符 {@code '\r'}.
     */
    char CR = '\r';

    /**
     * 字符常量：换行符 {@code '\n'}.
     */
    char LF = '\n';

    /**
     * 字符常量：减号（连接符） {@code '-'}.
     */
    char DASHED = '-';

    /**
     * 字符常量：下划线 {@code '_'}.
     */
    char UNDERLINE = '_';

    /**
     * 字符常量：逗号 {@code ','}.
     */
    char COMMA = ',';

    /**
     * 字符常量：花括号（左） <code>'{'</code>.
     */
    char DELIM_START = '{';

    /**
     * 字符常量：花括号（右） <code>'}'</code>.
     */
    char DELIM_END = '}';

    /**
     * 字符常量：中括号（左） {@code '['}.
     */
    char BRACKET_START = '[';

    /**
     * 字符常量：中括号（右） {@code ']'}.
     */
    char BRACKET_END = ']';

    /**
     * 字符常量：双引号 {@code '"'}.
     */
    char DOUBLE_QUOTES = '"';

    /**
     * 字符常量：单引号 {@code '\''}.
     */
    char SINGLE_QUOTE = '\'';

    /**
     * 字符常量：与 {@code '&'}.
     */
    char AMP = '&';

    /**
     * 字符常量：冒号 {@code ':'}.
     */
    char COLON = ':';

    /**
     * 字符常量：艾特 {@code '@'}.
     */
    char AT = '@';

    /**
     * 字符常量：文件扩展后缀 {@code '.'}.
     */
    char EXTENSION_SEPARATOR = CharPools.DOT;

    /**
     * 接口应描述一种类型，从而必须拥有方法.
     * @param chaz chaz
     * @return character char
     */
    Character getChar(char chaz);

}
