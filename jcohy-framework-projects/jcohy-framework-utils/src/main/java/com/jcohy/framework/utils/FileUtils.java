package com.jcohy.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.jcohy.framework.utils.constant.NumberConstant;
import com.jcohy.framework.utils.constant.StringPools;
import com.jcohy.framework.utils.exceptions.SagaException;

/**
 * 描述: .
 *
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 3/9/22:14:30
 * @since 2022.0.1
 */
public class FileUtils extends FileCopyUtils {

    private final String[] UNIT_NAME = new String[] { "PB", "TB", "GB", "MB", "KB", "B" };

    /**
     * FILENAME_PATTERN.
     */
    public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";

    /**
     * 默认文件最大大小 100 MB.
     */
    public static final long DEFAULT_MAX_SIZE = 100 * NumberConstant.ONE_MB;

    /**
     * DEFAULT_ALLOWED_EXTENSION.
     */
    public static final String[] DEFAULT_ALLOWED_EXTENSION = {
            // 图片
            "bmp", "gif", "jpg", "jpeg", "png",
            // word excel powerpoint
            "doc", "docx", "xls", "xlsx", "ppt", "pptx", "html", "htm", "txt",
            // 压缩文件
            "rar", "zip", "gz", "bz2",
            // pdf
            "pdf" };

    /**
     * 获取文件后缀名.
     * @param fullName 文件全名
     * @return {string}
     */
    public static String getFileExtension(String fullName) {
        return StringUtils.getFilenameExtension(fullName);
    }

    /**
     * 获取文件名，去除后缀名.
     * @param file 文件
     * @return {string}
     */
    public static String getNameWithoutExtension(String file) {
        return StringUtils.getFilename(file);
    }

    public static String getNetFileName(String fileUrl) {
        int dotIndex = fileUrl.lastIndexOf(StringPools.SLASH);
        return (dotIndex == -1) ? fileUrl : fileUrl.substring(dotIndex + 1);
    }

    /**
     * inputStream 转 file.
     * @param ins inputStream 输入流
     * @param name 文件名
     * @return /
     * @throws Exception exception
     */
    public static File inputStreamToFile(InputStream ins, String name) throws Exception {
        File file = new File(System.getProperty("java.io.tmpdir") + File.separator + name);
        if (file.exists()) {
            return file;
        }
        OutputStream os = new FileOutputStream(file);
        int bytesRead;
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
        return file;
    }

    /**
     * 下载网络文件.
     * @param url 文件网络地址
     * @return /
     */
    public static String downloadFile(String url) {
        String temp = System.getProperty("java.io.tmpdir");
        return downloadFile(url, temp.substring(0, temp.length() - 1));
    }

    /**
     * 下载网络文件.
     * @param url 文件网络地址
     * @param saveUrl 保存地址，注意，
     * @return /
     */
    public static String downloadFile(String url, String saveUrl) {
        String fileName = getNetFileName(url);
        try {
            Path path = Paths.get(saveUrl);
            if (!Files.isDirectory(path)) {
                throw new IllegalArgumentException("saveUrl must be a directory");
            }
            InputStream inputStream = new URL("https://jcohy-docs.oss-cn-beijing.aliyuncs.com/img/brhtqqzh.jpeg")
                    .openStream();
            Path target = Paths.get(saveUrl + File.separator + fileName);
            Files.copy(inputStream, target, StandardCopyOption.REPLACE_EXISTING);
            return saveUrl + File.separator + fileName;
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 文件名称验证.
     * @param filename 文件名称
     * @return true 正常 false 非法
     */
    public static boolean isValidFilename(String filename) {
        return filename.matches(FILENAME_PATTERN);
    }

    /**
     * 下载文件名重新编码.
     * @param response 响应对象
     * @param realFileName 真实文件名
     * @throws UnsupportedEncodingException /
     */
    public static void setAttachmentResponseHeader(HttpServletResponse response, String realFileName)
            throws UnsupportedEncodingException {
        String percentEncodedFileName = percentEncode(realFileName);

        StringBuilder contentDispositionValue = new StringBuilder();
        contentDispositionValue.append("attachment; filename=").append(percentEncodedFileName).append(";")
                .append("filename*=").append("utf-8''").append(percentEncodedFileName);

        response.setHeader("Content-disposition", contentDispositionValue.toString());
    }

    /**
     * 百分号编码工具方法.
     * @param s 需要百分号编码的字符串
     * @return 百分号编码后的字符串
     * @throws UnsupportedEncodingException /
     */
    public static String percentEncode(String s) throws UnsupportedEncodingException {
        String encode = URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
        return encode.replaceAll("\\+", "%20");
    }

    /**
     * 输出指定文件的byte数组.
     * @param filePath 文件路径
     * @param os 输出流
     * @throws IOException /
     */
    public static void writeBytes(String filePath, OutputStream os) throws IOException {
        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0) {
                os.write(b, 0, length);
            }
        }
        catch (IOException ex) {
            throw ex;
        }
        finally {
            if (os != null) {
                try {
                    os.close();
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 根据文件路径获取文件名.
     * @param filePath 文件路径
     * @return 文件名
     */
    public static String getFileName(String filePath) {
        return filePath.trim().substring(filePath.trim().lastIndexOf(File.separator) + 1);
    }

    /**
     * 删除文件.
     * @param filePath 文件
     * @return /
     */
    public static boolean deleteFile(String filePath) {
        boolean flag = false;
        try {
            flag = Files.deleteIfExists(Paths.get(filePath));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    /**
     * 获取文件后缀.
     * @param file 文件
     * @return 文件后缀
     */
    public static String getSuffix(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename == null) {
            throw new SagaException("未获取到文件");
        }
        return filename.substring(filename.lastIndexOf("."));
    }

    public static String upload(String filePath, MultipartFile file) throws Exception {

        assertAllowedUpload(file);
        createFileDir(filePath);
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return filePath;
    }

    private static void createFileDir(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
        }
    }

    private static void assertAllowedUpload(MultipartFile file) {
        long size = file.getSize();
        if (size > DEFAULT_MAX_SIZE) {
            throw new SagaException("文件大小不能超过50MB");
        }

        String suffix = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!isAllowedSuffix(suffix, DEFAULT_ALLOWED_EXTENSION)) {
            throw new SagaException("此文件不允许上传");
        }

    }

    public static boolean isAllowedSuffix(String suffix, String[] allowedSuffix) {
        for (String str : allowedSuffix) {
            if (str.equalsIgnoreCase(suffix)) {
                return true;
            }
        }
        return false;
    }

}
