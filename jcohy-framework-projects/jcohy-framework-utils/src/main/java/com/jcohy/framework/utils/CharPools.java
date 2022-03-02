package com.jcohy.framework.utils;

/**
 * 描述: Char 字符常量池.
 *
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 2/18/22:15:30
 * @since 2022.0.1
 */
public interface CharPools {

    /**
     * UPPER_A.
     */
    char UPPER_A = 'A';

    /**
     * LOWER_A.
     */
    char LOWER_A = 'a';

    /**
     * UPPER_Z.
     */
    char UPPER_Z = 'Z';

    /**
     * LOWER_Z.
     */
    char LOWER_Z = 'z';

    /**
     * COLON.
     */
    char COLON = ':';

    /**
     * BACK_SLASH.
     */
    char BACK_SLASH = '\\';

    /**
     * LF.
     */
    char LF = '\n';

    /**
     * CR.
     */
    char CR = '\r';

    /**
     * TAB.
     */
    char TAB = '\t';

    /**
     * 逗号.
     */
    char COMMA = ',';

    /**
     * 逗号.
     */
    char DOT = '.';

    /**
     * 逗号.
     */
    char EXTENSION_SEPARATOR = CharPools.DOT;

    /**
     * 接口应描述一种类型，从而必须拥有方法.
     * @param chaz chaz
     * @return character char
     */
    Character getChar(char chaz);

}
