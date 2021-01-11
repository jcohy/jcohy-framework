package com.framework.utils.base;

import java.io.File;

/**
 * Copyright: Copyright (c) 2021 <a href="http://www.jcohy.com" target="_blank">jcohy.com</a>
 *
 * <p> Description:
 *
 * @author jiac
 * @version 1.0.0 2021/1/11:22:59
 * @since 1.0.0
 */
public class SystemUtils {

    // 系统属性常量
    // -----------------------------------------------------------------------

    /**
     * The prefix String for all Windows OS.
     */
    private static final String OS_NAME_WINDOWS_PREFIX = "Windows";

    /**
     * 用户家目录
     */
    private static final String USER_HOME_KEY = "user.home";

    /**
     * 用户名
     */
    private static final String USER_NAME_KEY = "user.name";

    /**
     * 用户目录
     */
    private static final String USER_DIR_KEY = "user.dir";

    /**
     * 临时目录
     */
    private static final String JAVA_IO_TMPDIR_KEY = "java.io.tmpdir";

    /**
     * Java home 目录.
     */
    private static final String JAVA_HOME_KEY = "java.home";

    /**
     * <p> {@code awt.toolkit} 系统属性. </p>
     * <p> 在 Windows XP 上为 {@code sun.awt.windows.WToolkit}.</p>
     * <p><b> 在没有 GUI 的平台上，此值为 {@code null}.</b></p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String AWT_TOOLKIT = getSystemProperty("awt.toolkit");

    /**
     * <p> {@code file.encoding} 系统属性.</p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String FILE_ENCODING = getSystemProperty("file.encoding");

    /**
     * <p> {@code file.separator} 系统属性.文件分隔符如下: </p>
     * <ul>
     * <li>UNIX : {@code "/"}  </li>
     * <li>Windows: {@code "\"} .</li>
     * </ul>
     *
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     *
     * @deprecated 建议使用 {@link java.io.File#separator}
     */
    @Deprecated
    public static final String FILE_SEPARATOR = getSystemProperty("file.separator");

    /**
     * <p> {@code java.awt.fonts} 系统属性. </p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String JAVA_AWT_FONTS = getSystemProperty("java.awt.fonts");

    /**
     * <p> {@code java.awt.graphicsenv} 系统属性. </p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String JAVA_AWT_GRAPHICSENV = getSystemProperty("java.awt.graphicsenv");

    /**
     * <p> {@code java.awt.headless} 系统属性. 此属性的值为 String 类型的 {@code "true"} 或 {@code "false"}.</p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     *
     * @see #isJavaAwtHeadless()
     * @since 2.1
     * @since Java 1.4
     */
    public static final String JAVA_AWT_HEADLESS = getSystemProperty("java.awt.headless");

    /**
     * <p> {@code java.awt.printerjob} 系统属性. </p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String JAVA_AWT_PRINTERJOB = getSystemProperty("java.awt.printerjob");

    /**
     * <p> {@code java.class.path} 系统属性. </p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String JAVA_CLASS_PATH = getSystemProperty("java.class.path");

    /**
     * <p> {@code java.class.version} 系统属性. </p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String JAVA_CLASS_VERSION = getSystemProperty("java.class.version");

    /**
     * <p> {@code java.compiler} 系统属性. 要使用的JIT编译器的名称。 在JDK 1.2版中首次出现。 在 Sun JDK 1.2 之后的版本中不再使用.</p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String JAVA_COMPILER = getSystemProperty("java.compiler");

    /**
     * <p> {@code java.endorsed.dirs} 系统属性. </p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String JAVA_ENDORSED_DIRS = getSystemProperty("java.endorsed.dirs");

    /**
     * <p> {@code java.ext.dirs} 系统属性. </p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String JAVA_EXT_DIRS = getSystemProperty("java.ext.dirs");

    /**
     * <p> {@code java.home} 系统属性. </p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String JAVA_HOME = getSystemProperty(JAVA_HOME_KEY);

    /**
     * <p> {@code java.io.tmpdir} 系统属性. </p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String JAVA_IO_TMPDIR = getSystemProperty(JAVA_IO_TMPDIR_KEY);

    /**
     * <p> {@code java.library.path} 系统属性.加载库时搜索的路径列表 </p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String JAVA_LIBRARY_PATH = getSystemProperty("java.library.path");

    /**
     * <p> {@code java.runtime.name} 系统属性. </p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String JAVA_RUNTIME_NAME = getSystemProperty("java.runtime.name");

    /**
     * <p> {@code java.runtime.version} 系统属性. Java 运行环境版本 </p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String JAVA_RUNTIME_VERSION = getSystemProperty("java.runtime.version");

    /**
     * <p> {@code java.specification.name} 系统属性.Java 运行时环境规范名称。 </p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String JAVA_SPECIFICATION_NAME = getSystemProperty("java.specification.name");

    /**
     * <p> {@code java.specification.vendor} 系统属性. Java 运行时环境规范供应商 </p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String JAVA_SPECIFICATION_VENDOR = getSystemProperty("java.specification.vendor");

    /**
     * <p> {@code java.specification.version} 系统属性.Java 运行时环境规范版本 </p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String JAVA_SPECIFICATION_VERSION = getSystemProperty("java.specification.version");
    private static final JavaVersion JAVA_SPECIFICATION_VERSION_AS_ENUM = JavaVersion.get(JAVA_SPECIFICATION_VERSION);

    /**
     * <p> {@code java.util.prefs.PreferencesFactory} 系统属性.Java 运行时环境规范版本 </p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String JAVA_UTIL_PREFS_PREFERENCES_FACTORY =
            getSystemProperty("java.util.prefs.PreferencesFactory");

    /**
     * <p>
     * The  System Property. Java vendor-specific string.
     * </p>
     * <p> {@code java.vendor} 系统属性. Java 运行时环境供应商 </p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String JAVA_VENDOR = getSystemProperty("java.vendor");

    /**
     * <p>
     * <p> {@code java.vendor.url} 系统属性. Java 供应商的 URL </p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String JAVA_VENDOR_URL = getSystemProperty("java.vendor.url");

    /**
     * <p> {@code java.version} 系统属性. Java 运行时环境版本</p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String JAVA_VERSION = getSystemProperty("java.version");

    /**
     * <p> {@code java.vm.info} 系统属性. Java 虚拟机实现详情</p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String JAVA_VM_INFO = getSystemProperty("java.vm.info");

    /**
     * <p> {@code java.vm.name} 系统属性. Java 虚拟机实现名称.</p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String JAVA_VM_NAME = getSystemProperty("java.vm.name");

    /**
     * <p> {@code java.vm.specification.name} 系统属性. Java 虚拟机规范名称</p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String JAVA_VM_SPECIFICATION_NAME = getSystemProperty("java.vm.specification.name");

    /**
     * <p> {@code java.vm.specification.vendor} 系统属性. Java 虚拟机规范供应商</p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String JAVA_VM_SPECIFICATION_VENDOR = getSystemProperty("java.vm.specification.vendor");

    /**
     * <p> {@code java.vm.specification.version} 系统属性. Java 虚拟机规范版本</p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String JAVA_VM_SPECIFICATION_VERSION = getSystemProperty("java.vm.specification.version");

    /**
     * <p> {@code java.vm.vendor} 系统属性. Java 虚拟机实现供应商</p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String JAVA_VM_VENDOR = getSystemProperty("java.vm.vendor");

    /**
     * <p> {@code java.vm.version} 系统属性. Java 虚拟机实现版本</p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String JAVA_VM_VERSION = getSystemProperty("java.vm.version");

    /**
     * <p> {@code line.separator} 系统属性. Line separator ({@code &quot;\n&quot;} on UNIX).</p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     *
     * @deprecated 推荐使用 {@link System#lineSeparator}
     */
    @Deprecated
    public static final String LINE_SEPARATOR = getSystemProperty("line.separator");

    /**
     * <p> {@code os.arch} 系统属性. 操作系统架构.</p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String OS_ARCH = getSystemProperty("os.arch");

    /**
     * <p> {@code os.name} 系统属性. 操作系统名称.</p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String OS_NAME = getSystemProperty("os.name");

    /**
     * <p> {@code os.version} 系统属性. 操作系统版本.</p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String OS_VERSION = getSystemProperty("os.version");

    /**
     * <p> {@code path.separator} 系统属性. Path separator ({@code &quot;:&quot;} on UNIX).</p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     *
     * @deprecated 推荐使用 {@link java.io.File#pathSeparator}
     */
    @Deprecated
    public static final String PATH_SEPARATOR = getSystemProperty("path.separator");

    /**
     * <p> {@code user.country} 或 {@code user.region} 系统属性. 用户的国家码，例如 {@code GB}. Java 1.2 中使用 {@code user.region}, 1.4 后重命名为 {@code user.country}.</p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String USER_COUNTRY = getSystemProperty("user.country") == null ?
            getSystemProperty("user.region") : getSystemProperty("user.country");

    /**
     * <p> {@code user.dir} 系统属性. 用户当前工作目录.</p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String USER_DIR = getSystemProperty(USER_DIR_KEY);

    /**
     * <p> {@code user.home} 系统属性. 用户的主目录.</p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String USER_HOME = getSystemProperty(USER_HOME_KEY);

    /**
     * <p> {@code user.language} 系统属性. 用户的语言码,例如 {@code "en"}.</p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String USER_LANGUAGE = getSystemProperty("user.language");

    /**
     * <p> {@code user.name} 系统属性. 用户名.</p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String USER_NAME = getSystemProperty(USER_NAME_KEY);

    /**
     * <p> {@code user.timezone} 系统属性. For example: {@code "America/Los_Angeles"}..</p>
     * <p> 如果运行时没有读取此属性的权限，或者该属性不存在，则默认为 {@code null}</p>
     * <p> 此值在类加载时初始化，如果在加载后调用 {@link System#setProperty(String,String)} 或 {@link System#setProperties(java.util.Properties)}。将会更改系统属性.</p>
     */
    public static final String USER_TIMEZONE = getSystemProperty("user.timezone");

    // Java 版本检查
    // -----------------------------------------------------------------------

    /**
     * <p> {@code true} Java 版本未 1.1  (包括 1.1.x 版本). </p>
     * <p> 如果 {@link #JAVA_VERSION} 为 {@code null} , 则返回 {@code false}. </p>
     */
    public static final boolean IS_JAVA_1_1 = getJavaVersionMatches("1.1");

    /**
     * <p> {@code true} Java 版本未 1.2  (包括 1.2.x 版本). </p>
     * <p> 如果 {@link #JAVA_VERSION} 为 {@code null} , 则返回 {@code false}. </p>
     */
    public static final boolean IS_JAVA_1_2 = getJavaVersionMatches("1.2");

    /**
     * <p> {@code true} Java 版本未 1.3 (包括 1.3.x 版本). </p>
     * <p> 如果 {@link #JAVA_VERSION} 为 {@code null} , 则返回 {@code false}. </p>
     */
    public static final boolean IS_JAVA_1_3 = getJavaVersionMatches("1.3");

    /**
     * <p> {@code true} Java 版本未 1.4  (包括 1.4.x 版本). </p>
     * <p> 如果 {@link #JAVA_VERSION} 为 {@code null} , 则返回 {@code false}. </p>
     */
    public static final boolean IS_JAVA_1_4 = getJavaVersionMatches("1.4");

    /**
     * <p> {@code true} Java 版本未 1.5  (包括 1.5.x 版本). </p>
     * <p> 如果 {@link #JAVA_VERSION} 为 {@code null} , 则返回 {@code false}. </p>
     */
    public static final boolean IS_JAVA_1_5 = getJavaVersionMatches("1.5");

    /**
     * <p> {@code true} Java 版本未 1.6  (包括 1.6.x 版本). </p>
     * <p> 如果 {@link #JAVA_VERSION} 为 {@code null} , 则返回 {@code false}. </p>
     */
    public static final boolean IS_JAVA_1_6 = getJavaVersionMatches("1.6");

    /**
     * <p> {@code true} Java 版本未 1.7  (包括 1.7.x 版本). </p>
     * <p> 如果 {@link #JAVA_VERSION} 为 {@code null} , 则返回 {@code false}. </p>
     */
    public static final boolean IS_JAVA_1_7 = getJavaVersionMatches("1.7");

    /**
     * <p> {@code true} Java 版本未 1.8  (包括 1.8.x 版本). </p>
     * <p> 如果 {@link #JAVA_VERSION} 为 {@code null} , 则返回 {@code false}. </p>
     */
    public static final boolean IS_JAVA_1_8 = getJavaVersionMatches("1.8");


    /**
     * <p> {@code true} Java 版本未 9  (包括 9.x 版本). </p>
     * <p> 如果 {@link #JAVA_VERSION} 为 {@code null} , 则返回 {@code false}. </p>
     */
    public static final boolean IS_JAVA_9 = getJavaVersionMatches("9");

    /**
     * <p> {@code true} Java 版本未 10  (包括 10.x 版本). </p>
     * <p> 如果 {@link #JAVA_VERSION} 为 {@code null} , 则返回 {@code false}. </p>
     */
    public static final boolean IS_JAVA_10 = getJavaVersionMatches("10");

    /**
     * <p> {@code true} Java 版本未 11  (包括 11.x 版本). </p>
     * <p> 如果 {@link #JAVA_VERSION} 为 {@code null} , 则返回 {@code false}. </p>
     */
    public static final boolean IS_JAVA_11 = getJavaVersionMatches("11");

    /**
     * <p> {@code true} Java 版本未 12  (包括 12.x 版本). </p>
     * <p> 如果 {@link #JAVA_VERSION} 为 {@code null} , 则返回 {@code false}. </p>
     */
    public static final boolean IS_JAVA_12 = getJavaVersionMatches("12");

    /**
     * <p> {@code true} Java 版本未 13  (包括 13.x 版本). </p>
     * <p> 如果 {@link #JAVA_VERSION} 为 {@code null} , 则返回 {@code false}. </p>
     */
    public static final boolean IS_JAVA_13 = getJavaVersionMatches("13");

    /**
     * <p> {@code true} Java 版本未 14  (包括 14.x 版本). </p>
     * <p> 如果 {@link #JAVA_VERSION} 为 {@code null} , 则返回 {@code false}. </p>
     */
    public static final boolean IS_JAVA_14 = getJavaVersionMatches("14");

    /**
     * <p> {@code true} Java 版本未 15  (包括 15.x 版本). </p>
     * <p> 如果 {@link #JAVA_VERSION} 为 {@code null} , 则返回 {@code false}. </p>
     */
    public static final boolean IS_JAVA_15 = getJavaVersionMatches("15");

    // 操作系统检查
    // -----------------------------------------------------------------------

    /**
     * <p> {@code true} 操作系统为 AIX.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_AIX = getOsMatchesName("AIX");

    /**
     * <p> {@code true} 操作系统为 HP-UX.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_HP_UX = getOsMatchesName("HP-UX");

    /**
     * <p> {@code true} 操作系统为 IBM OS/400.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_400 = getOsMatchesName("OS/400");

    /**
     * <p> {@code true} 操作系统为 Irix.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_IRIX = getOsMatchesName("Irix");

    /**
     * <p> {@code true} 操作系统为 Linux.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_LINUX = getOsMatchesName("Linux") || getOsMatchesName("LINUX");

    /**
     * <p> {@code true} 操作系统为 Mac.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_MAC = getOsMatchesName("Mac");

    /**
     * <p> {@code true} 操作系统为 Mac OS X.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_MAC_OSX = getOsMatchesName("Mac OS X");

    /**
     * <p> {@code true} 操作系统为 Mac OS X Cheetah.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_MAC_OSX_CHEETAH = getOsMatches("Mac OS X", "10.0");

    /**
     * <p> {@code true} 操作系统为 Mac OS X Puma.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_MAC_OSX_PUMA = getOsMatches("Mac OS X", "10.1");

    /**
     * <p> {@code true} 操作系统为 Mac OS X Jaguar.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_MAC_OSX_JAGUAR = getOsMatches("Mac OS X", "10.2");

    /**
     * <p> {@code true} 操作系统为 Mac OS X Panther.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_MAC_OSX_PANTHER = getOsMatches("Mac OS X", "10.3");

    /**
     * <p> {@code true} 操作系统为 Mac OS X Tiger.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_MAC_OSX_TIGER = getOsMatches("Mac OS X", "10.4");

    /**
     * <p> {@code true} 操作系统为 Mac OS X Leopard.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_MAC_OSX_LEOPARD = getOsMatches("Mac OS X", "10.5");

    /**
     * <p> {@code true} 操作系统为 Mac OS X Snow Leopard.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_MAC_OSX_SNOW_LEOPARD = getOsMatches("Mac OS X", "10.6");

    /**
     * <p> {@code true} 操作系统为 Mac OS X Lion.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_MAC_OSX_LION = getOsMatches("Mac OS X", "10.7");

    /**
     * <p> {@code true} 操作系统为 Mac OS X Mountain Lion.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_MAC_OSX_MOUNTAIN_LION = getOsMatches("Mac OS X", "10.8");

    /**
     * <p> {@code true} 操作系统为 Mac OS X Mavericks.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_MAC_OSX_MAVERICKS = getOsMatches("Mac OS X", "10.9");

    /**
     * <p> {@code true} 操作系统为 Mac OS X Yosemite.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_MAC_OSX_YOSEMITE = getOsMatches("Mac OS X", "10.10");

    /**
     * <p> {@code true} 操作系统为 Mac OS X El Capitan.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_MAC_OSX_EL_CAPITAN = getOsMatches("Mac OS X", "10.11");

    /**
     * <p> {@code true} 操作系统为 FreeBSD.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_FREE_BSD = getOsMatchesName("FreeBSD");

    /**
     * <p> {@code true} 操作系统为 OpenBSD.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_OPEN_BSD = getOsMatchesName("OpenBSD");

    /**
     * <p> {@code true} 操作系统为 NetBSD.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_NET_BSD = getOsMatchesName("NetBSD");

    /**
     * <p> {@code true} 操作系统为 OS/2.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_OS2 = getOsMatchesName("OS/2");

    /**
     * <p> {@code true} 操作系统为 Solaris.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_SOLARIS = getOsMatchesName("Solaris");

    /**
     * <p> {@code true} 操作系统为 SunOS.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_SUN_OS = getOsMatchesName("SunOS");

    /**
     * <p> {@code true} 操作系统为 UNIX. 例如  AIX, HP-UX, Irix, Linux, MacOSX, Solaris 或 SUN OS. </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_UNIX = IS_OS_AIX || IS_OS_HP_UX || IS_OS_IRIX || IS_OS_LINUX || IS_OS_MAC_OSX
            || IS_OS_SOLARIS || IS_OS_SUN_OS || IS_OS_FREE_BSD || IS_OS_OPEN_BSD || IS_OS_NET_BSD;

    /**
     * <p> {@code true} 操作系统为 Windows.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_WINDOWS = getOsMatchesName(OS_NAME_WINDOWS_PREFIX);

    /**
     * <p> {@code true} 操作系统为 Windows 2000.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_WINDOWS_2000 = getOsMatchesName(OS_NAME_WINDOWS_PREFIX + " 2000");

    /**
     * <p> {@code true} 操作系统为 Windows 2003.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_WINDOWS_2003 = getOsMatchesName(OS_NAME_WINDOWS_PREFIX + " 2003");

    /**
     * <p> {@code true} 操作系统为 Windows Server 2008.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_WINDOWS_2008 = getOsMatchesName(OS_NAME_WINDOWS_PREFIX + " Server 2008");

    /**
     * <p> {@code true} 操作系统为 Windows Server 2012.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_WINDOWS_2012 = getOsMatchesName(OS_NAME_WINDOWS_PREFIX + " Server 2012");

    /**
     * <p> {@code true} 操作系统为 Windows 95.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_WINDOWS_95 = getOsMatchesName(OS_NAME_WINDOWS_PREFIX + " 95");

    /**
     * <p> {@code true} 操作系统为 Windows 98.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_WINDOWS_98 = getOsMatchesName(OS_NAME_WINDOWS_PREFIX + " 98");

    /**
     * <p> {@code true} 操作系统为 Windows ME.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_WINDOWS_ME = getOsMatchesName(OS_NAME_WINDOWS_PREFIX + " Me");

    /**
     * <p> {@code true} 操作系统为 Windows NT.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_WINDOWS_NT = getOsMatchesName(OS_NAME_WINDOWS_PREFIX + " NT");

    /**
     * <p> {@code true} 操作系统为 Windows XP.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_WINDOWS_XP = getOsMatchesName(OS_NAME_WINDOWS_PREFIX + " XP");

    // -----------------------------------------------------------------------
    /**
     * <p> {@code true} 操作系统为 Windows Vista.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_WINDOWS_VISTA = getOsMatchesName(OS_NAME_WINDOWS_PREFIX + " Vista");

    /**
     * <p> {@code true} 操作系统为 Windows 7.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_WINDOWS_7 = getOsMatchesName(OS_NAME_WINDOWS_PREFIX + " 7");

    /**
     * <p> {@code true} 操作系统为 Windows 8.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_WINDOWS_8 = getOsMatchesName(OS_NAME_WINDOWS_PREFIX + " 8");

    /**
     * <p> {@code true} 操作系统为 Windows 10.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    public static final boolean IS_OS_WINDOWS_10 = getOsMatchesName(OS_NAME_WINDOWS_PREFIX + " 10");

    /**
     * <p> {@code true} 操作系统为 z/OS.  </p>
     * <p> 如果 {@code OS_NAME} 为 {@code null},则返回 {@code false}. </p>
     */
    // Values on a z/OS system I tested (Gary Gregory - 2016-03-12)
    // os.arch = s390x
    // os.encoding = ISO8859_1
    // os.name = z/OS
    // os.version = 02.02.00
    public static final boolean IS_OS_ZOS = getOsMatchesName("z/OS");

    /**
     * <p> 获取 Java Home 目录 {@code File}. </p>
     *
     * @return 返回一个目录
     * @throws SecurityException 如果存在安全管理器并且 {@code checkPropertyAccess} 方法不允许访问指定属性
     * @see System#getProperty(String)
     */
    public static File getJavaHome() {
        return new File(System.getProperty(JAVA_HOME_KEY));
    }

    /**
     * 从环境变量中获取主机名 (Windows 为 COMPUTERNAME, 其他为 HOSTNAME ).
     * <p> 如果需要获取网络主机名, 请使用 {@code InetAddress.getLocalHost().getHostName()}. </p>
     *
     * @return 主机名. 如果没有声明则为  {@code null}
     */
    public static String getHostName() {
        return IS_OS_WINDOWS ? System.getenv("COMPUTERNAME") : System.getenv("HOSTNAME");
    }

    /**
     * <p> 获取 Java 临时目录 {@code File}.</p>
     *
     * @return 返回一个目录
     * @throws SecurityException 如果存在安全管理器并且 {@code checkPropertyAccess} 方法不允许访问指定属性
     * @see System#getProperty(String)
     */
    public static File getJavaIoTmpDir() {
        return new File(System.getProperty(JAVA_IO_TMPDIR_KEY));
    }

    /**
     * <p> 判断 Java 版本是否匹配. </p>
     *
     * @param versionPrefix 版本前缀
     * @return 如果匹配,则为 {@code true}. 否则为 {@code false}
     */
    private static boolean getJavaVersionMatches(final String versionPrefix) {
        return isJavaVersionMatch(JAVA_SPECIFICATION_VERSION, versionPrefix);
    }

    /**
     * 判断操作系统是否匹配。
     *
     * @param osNamePrefix OS 名称前缀
     * @param osVersionPrefix 版本前缀
     * @return 如果匹配,则为 {@code true}. 否则为 {@code false}
     */
    private static boolean getOsMatches(final String osNamePrefix, final String osVersionPrefix) {
        return isOSMatch(OS_NAME, OS_VERSION, osNamePrefix, osVersionPrefix);
    }

    /**
     * 判断操作系统是否匹配
     *
     * @param osNamePrefix OS 名称前缀
     * @return 如果匹配,则为 {@code true}. 否则为 {@code false}
     */
    private static boolean getOsMatchesName(final String osNamePrefix) {
        return isOSNameMatch(OS_NAME, osNamePrefix);
    }

    // -----------------------------------------------------------------------
    /**
     * <p> 获取系统属性, 如果不存在则为 {@code null} </p>
     * <p> 如果捕获到 {@code SecurityException} 异常,则返回值为 {@code null} 并将一条消息写入 {@code System.err}. </p>
     *
     * @param property 系统属性名
     * @return 系统属性值；如果发生安全问题，则为 {@code null}
     */
    private static String getSystemProperty(final String property) {
        try {
            return System.getProperty(property);
        } catch (final SecurityException ex) {
            return null;
        }
    }

    /**
     * <p> 获取环境变量, 如果不存在则返回 {@code defaultValue}. </p>
     * <p> 如果捕获到 {@code SecurityException} 异常,则返回值为 {@code null} 并将一条消息写入 {@code System.err}. </p>
     *
     * @param name 环境变量名
     * @param defaultValue 默认值
     * @return 环境变量值；如果发生安全问题，则为 {@code defaultValue}
     */
    public static String getEnvironmentVariable(final String name, final String defaultValue) {
        try {
            final String value = System.getenv(name);
            return value == null ? defaultValue : value;
        } catch (final SecurityException ex) {
            return defaultValue;
        }
    }

    /**
     * <p> 用户的当前工作目录 {@code File}. </p>
     *
     * @return 返回一个目录
     * @throws SecurityException 如果存在安全管理器并且 {@code checkPropertyAccess} 方法不允许访问指定属性
     * @see System#getProperty(String)
     */
    public static File getUserDir() {
        return new File(System.getProperty(USER_DIR_KEY));
    }

    /**
     * <p> 用户主目录 {@code File}. </p>
     *
     * @return 返回一个目录
     * @throws SecurityException 如果存在安全管理器并且 {@code checkPropertyAccess} 方法不允许访问指定属性
     */
    public static File getUserHome() {
        return new File(System.getProperty(USER_HOME_KEY));
    }

    /**
     * <p> 获取用户名. </p>
     *
     * @return 用户名
     * @throws SecurityException 如果存在安全管理器并且 {@code checkPropertyAccess} 方法不允许访问指定属性
     * @see System#getProperty(String)
     */
    public static String getUserName() {
        return System.getProperty(USER_NAME_KEY);
    }

    /**
     * <p> 获取用户名 </p>
     * @param defaultValue 默认值
     * @return 用户名
     * @throws SecurityException 如果存在安全管理器并且 {@code checkPropertyAccess} 方法不允许访问指定属性
     * @see System#getProperty(String)
     */
    public static String getUserName(final String defaultValue) {
        return System.getProperty(USER_NAME_KEY, defaultValue);
    }

    /**
     * 返回 {@link #JAVA_AWT_HEADLESS} 值是否为 {@code true}.
     *
     * @return {@code true} 如果 {@code JAVA_AWT_HEADLESS} 为 {@code "true"}, 否则 {@code false}.
     * @see #JAVA_AWT_HEADLESS
     */
    public static boolean isJavaAwtHeadless() {
        return Boolean.TRUE.toString().equals(JAVA_AWT_HEADLESS);
    }

    /**
     * <p> 当前版本是否大于等于指定版本 </p>
     *
     * @param requiredVersion the required version, for example 1.31f
     * @return {@code true} 如果实际版本等于或大于所需版本
     */
    public static boolean isJavaVersionAtLeast(final JavaVersion requiredVersion) {
        return JAVA_SPECIFICATION_VERSION_AS_ENUM.atLeast(requiredVersion);
    }

    /**
     * <p> 当前版本是否小于等于指定版本 </p>
     *
     * @param requiredVersion the required version, for example 1.31f
     * @return {@code true} 如果实际版本等于或大于所需版本
     */
    public static boolean isJavaVersionAtMost(final JavaVersion requiredVersion) {
        return JAVA_SPECIFICATION_VERSION_AS_ENUM.atMost(requiredVersion);
    }

    /**
     * <p> 判断 Java 版本是否匹配 </p>
     *
     * @param version Java version
     * @param versionPrefix Java version 前缀
     * @return 如果匹配,则返回 {@code true},否则返回 {@code false}
     */
    static boolean isJavaVersionMatch(final String version, final String versionPrefix) {
        if (version == null) {
            return false;
        }
        return version.startsWith(versionPrefix);
    }

    /**
     * 判断操作系统是否匹配。
     *
     * @param osName OS name
     * @param osVersion OS version
     * @param osNamePrefix OS name 前缀
     * @param osVersionPrefix OS version 前缀
     * @return 如果匹配,则返回 {@code true},否则返回 {@code false}
     */
    static boolean isOSMatch(final String osName, final String osVersion, final String osNamePrefix, final String osVersionPrefix) {
        if (osName == null || osVersion == null) {
            return false;
        }
        return isOSNameMatch(osName, osNamePrefix) && isOSVersionMatch(osVersion, osVersionPrefix);
    }

    /**
     * 判断操作系统名称是否匹配
     *
     * @param osName OS name
     * @param osNamePrefix OS name 前缀
     * @return 如果匹配,则返回 {@code true},否则返回 {@code false}
     */
    static boolean isOSNameMatch(final String osName, final String osNamePrefix) {
        if (osName == null) {
            return false;
        }
        return osName.startsWith(osNamePrefix);
    }

    /**
     * 判断操作系统版本是否匹配
     *
     * @param osVersion OS version
     * @param osVersionPrefix OS version 前缀
     * @return 如果匹配,则返回 {@code true},否则返回 {@code false}
     */
    static boolean isOSVersionMatch(final String osVersion, final String osVersionPrefix) {
        if (StringUtils.isEmpty(osVersion)) {
            return false;
        }
        // Compare parts of the version string instead of using String.startsWith(String) because otherwise
        // osVersionPrefix 10.1 would also match osVersion 10.10
        final String[] versionPrefixParts = osVersionPrefix.split("\\.");
        final String[] versionParts = osVersion.split("\\.");
        for (int i = 0; i < Math.min(versionPrefixParts.length, versionParts.length); i++) {
            if (!versionPrefixParts[i].equals(versionParts[i])) {
                return false;
            }
        }
        return true;
    }

    // -----------------------------------------------------------------------
    /**
     * <p>
     * 在写代码的时候不需要创建 {@code SystemUtils} 实例. Instead, 使用 {@code SystemUtils.FILE_SEPARATOR} 类似操作即可.
     * </p>
     */
    public SystemUtils() {
        super();
    }
}
