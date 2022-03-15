package com.jcohy.framework.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;

import com.jcohy.framework.utils.constant.StringPools;

/**
 * 描述: 生成随机字符串.
 * <p>
 * 注意：此类在所依赖的 {@link Random} 实例虽然是线程安全的,但在加密上并不安全
 * </p>
 *
 * <p>
 * RandomUtils 旨在用于简单的用例。 对于更高级的用例，请考虑改用 Apache Commons Text 的 RandomStringGenerator。,
 * 对于具有更严格要求（性能和/或正确性）的应用程序，可以使用 Commons RNG
 * </p>
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/15/22:11:23
 * @since 2022.0.1
 * @see SecureRandom
 * @see org.apache.commons.lang3.RandomUtils
 * @see ThreadLocalRandom
 */
public class RandomUtils extends org.apache.commons.lang3.RandomStringUtils {

    private static final Random random = new SecureRandom();

    /**
     * 默认长度.
     */
    public static final int DEFAULT_SIZE = 6;

    /**
     * 生成随机的字符串文件名 随机 6 位字符串.
     * @return 随机 6 位字符串
     * @since 2022.0.1 生成文件名方法可使用 {@link RandomUtils#letter(int)} 方法替代
     * @see SecureRandom
     */
    @Deprecated
    public static String fileName() {
        return letter(DEFAULT_SIZE);
    }

    /**
     * 生成用户名密码，默认 10 位.
     * @return 用户名
     * @see SecureRandom
     */
    public static String nickName() {
        return numberAndLetter(DEFAULT_SIZE);
    }

    /**
     * 生成随机的字符串文件名. 默认大小为 6
     * @return 返回结果
     * @see SecureRandom
     */
    public static String letter() {
        return RandomUtils.letter(DEFAULT_SIZE);
    }

    /**
     * 生成随机的字符串文件名.
     * @param size 随机数大小
     * @return 返回结果
     * @see SecureRandom
     */
    public static String letter(int size) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size; i++) {
            int intVal = (int) (Math.random() * 26 + 97);
            result.append((char) intVal);
        }
        return result.toString();
    }

    /**
     * 生成随机数字. 默认大小为 6
     * @return 返回结果
     */
    public static String number() {
        return RandomUtils.number(DEFAULT_SIZE);
    }

    /**
     * 生成随机数字.
     * @param size 随机数大小
     * @return 返回结果
     * @see SecureRandom
     */
    public static String number(int size) {
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            int intVal = random.nextInt(10);
            result.append(intVal);
        }
        return result.toString();
    }

    public static String numberAndLetter(int size) {
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            int flag = random.nextInt(2);
            if (flag == 0) {
                int intVal = (int) (Math.random() + 9);
                result.append(intVal);
            }
            else {
                int intVal = (int) (Math.random() * 26 + 97);
                result.append((char) intVal);
            }
        }
        return result.toString();
    }

    /**
     * 获取随机 UUID.
     * @return 随机UUID
     * @see SecureRandom
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取随机 UUID.
     * @return 随机UUID
     * @see SecureRandom
     */
    public static String randomSecurityUUID() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return new UUID(random.nextLong(), random.nextLong()).toString().replace(StringPools.DASH, StringPools.EMPTY);
    }

    /**
     * 简化的 UUID，去掉了横线.
     * @return 简化的 UUID，去掉了横线
     */
    public static String simpleUUID() {
        return randomUUID().replaceAll("-", "");
    }

    /**
     * 输出指定验证码图片流.
     * @param os outputStream
     * @param code code
     * @throws IOException i/o异常
     */
    public static void outputImage(OutputStream os, String code) throws IOException {
        int w = 111;
        int h = 36;
        int verifySize = code.length();
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Random rand = new Random();
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color[] colors = new Color[5];
        Color[] colorSpaces = new Color[] { Color.WHITE, Color.CYAN, Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA,
                Color.ORANGE, Color.PINK, Color.YELLOW };
        float[] fractions = new float[colors.length];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = colorSpaces[rand.nextInt(colorSpaces.length)];
            fractions[i] = rand.nextFloat();
        }
        Arrays.sort(fractions);

        g2.setColor(Color.GRAY); // 设置边框色
        g2.fillRect(0, 0, w, h);

        Color c = getRandColor(200, 250);
        g2.setColor(c); // 设置背景色
        g2.fillRect(0, 2, w, h - 4);

        // 绘制干扰线
        Random random = new Random();
        g2.setColor(getRandColor(160, 200)); // 设置线条的颜色
        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(w - 1);
            int y = random.nextInt(h - 1);
            int xl = random.nextInt(6) + 1;
            int yl = random.nextInt(12) + 1;
            g2.drawLine(x, y, x + xl + 40, y + yl + 20);
        }

        // 添加噪点
        float yawpRate = 0.05f; // 噪声率
        int area = (int) (yawpRate * w * h);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(w);
            int y = random.nextInt(h);
            int rgb = getRandomIntColor();
            image.setRGB(x, y, rgb);
        }

        shear(g2, w, h, c); // 使图片扭曲

        g2.setColor(getRandColor(100, 160));
        int fontSize = h - 4;
        Font font = new Font("Algerian", Font.ITALIC, fontSize);
        g2.setFont(font);
        char[] chars = code.toCharArray();
        for (int i = 0; i < verifySize; i++) {
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(Math.PI / 4 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1),
                    (w / verifySize) * i + fontSize / 2, h / 2);
            g2.setTransform(affine);
            g2.drawChars(chars, i, 1, ((w - 10) / verifySize) * i + 5, h / 2 + fontSize / 2 - 10);
        }

        g2.dispose();
        ImageIO.write(image, "jpg", os);
    }

    private static Color getRandColor(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    private static int getRandomIntColor() {
        int[] rgb = getRandomRgb();
        int color = 0;
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        return color;
    }

    private static int[] getRandomRgb() {
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }

    private static void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }

    private static void shearX(Graphics g, int w1, int h1, Color color) {

        int period = random.nextInt(2);

        boolean borderGap = true;
        int frames = 1;
        int phase = random.nextInt(2);

        for (int i = 0; i < h1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }

    }

    private static void shearY(Graphics g, int w1, int h1, Color color) {

        int period = random.nextInt(40) + 10; // 50;

        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        for (int i = 0; i < w1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }

        }
    }

}
