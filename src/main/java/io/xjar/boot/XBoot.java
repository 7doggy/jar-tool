package io.xjar.boot;

import io.xjar.*;
import io.xjar.key.XKey;
import org.apache.commons.compress.archivers.jar.JarArchiveEntry;

import java.io.*;

/**
 * Spring-Boot JAR包加解密工具类，在不提供过滤器的情况下会加密BOOT-INF/下的所有资源，及包括项目本身的资源和依赖jar资源。
 *
 * @author Payne 646742615@qq.com
 * 2018/11/26 11:11
 */
public class XBoot implements XConstants {

    /**
     * 加密 Spring-Boot JAR 包
     *
     * @param src      原文包
     * @param dest     加密包
     * @param password 密码
     * @throws Exception 加密异常
     */
    public static void encrypt(String src, String dest, String password) throws Exception {
        encrypt(src, dest, password, DEFAULT_ALGORITHM);
    }

    /**
     * 加密 Spring-Boot JAR 包
     *
     * @param src       原文包
     * @param dest      加密包
     * @param password  密码
     * @param algorithm 加密算法
     * @throws Exception 加密异常
     */
    public static void encrypt(String src, String dest, String password, String algorithm) throws Exception {
        encrypt(src, dest, password, algorithm, DEFAULT_KEYSIZE);
    }

    /**
     * 加密 Spring-Boot JAR 包
     *
     * @param src       原文包
     * @param dest      加密包
     * @param password  密码
     * @param algorithm 加密算法
     * @param keysize   密钥长度
     * @throws Exception 加密异常
     */
    public static void encrypt(String src, String dest, String password, String algorithm, int keysize) throws Exception {
        encrypt(src, dest, password, algorithm, keysize, DEFAULT_IVSIZE);
    }

    /**
     * 加密 Spring-Boot JAR 包
     *
     * @param src       原文包
     * @param dest      加密包
     * @param password  密码
     * @param algorithm 加密算法
     * @param keysize   密钥长度
     * @param ivsize    向量长度
     * @throws Exception 加密异常
     */
    public static void encrypt(String src, String dest, String password, String algorithm, int keysize, int ivsize) throws Exception {
        encrypt(new File(src), new File(dest), password, algorithm, keysize, ivsize);
    }

    /**
     * 加密 Spring-Boot JAR 包
     *
     * @param src      原文包
     * @param dest     加密包
     * @param password 密码
     * @throws Exception 加密异常
     */
    public static void encrypt(File src, File dest, String password) throws Exception {
        encrypt(src, dest, password, DEFAULT_ALGORITHM);
    }

    /**
     * 加密 Spring-Boot JAR 包
     *
     * @param src       原文包
     * @param dest      加密包
     * @param password  密码
     * @param algorithm 加密算法
     * @throws Exception 加密异常
     */
    public static void encrypt(File src, File dest, String password, String algorithm) throws Exception {
        encrypt(src, dest, password, algorithm, DEFAULT_KEYSIZE);
    }

    /**
     * 加密 Spring-Boot JAR 包
     *
     * @param src       原文包
     * @param dest      加密包
     * @param password  密码
     * @param algorithm 加密算法
     * @param keysize   密钥长度
     * @throws Exception 加密异常
     */
    public static void encrypt(File src, File dest, String password, String algorithm, int keysize) throws Exception {
        encrypt(src, dest, password, algorithm, keysize, DEFAULT_IVSIZE);
    }

    /**
     * 加密 Spring-Boot JAR 包
     *
     * @param src       原文包
     * @param dest      加密包
     * @param password  密码
     * @param algorithm 加密算法
     * @param keysize   密钥长度
     * @param ivsize    向量长度
     * @throws Exception 加密异常
     */
    public static void encrypt(File src, File dest, String password, String algorithm, int keysize, int ivsize) throws Exception {
        try (
                InputStream in = new FileInputStream(src);
                OutputStream out = new FileOutputStream(dest)
        ) {
            encrypt(in, out, password, algorithm, keysize, ivsize);
        }
    }

    /**
     * 加密 Spring-Boot JAR 包
     *
     * @param in       原文包输入流
     * @param out      加密包输出流
     * @param password 密码
     * @throws Exception 加密异常
     */
    public static void encrypt(InputStream in, OutputStream out, String password) throws Exception {
        encrypt(in, out, password, DEFAULT_ALGORITHM);
    }

    /**
     * 加密 Spring-Boot JAR 包
     *
     * @param in        原文包输入流
     * @param out       加密包输出流
     * @param password  密码
     * @param algorithm 加密算法
     * @throws Exception 加密异常
     */
    public static void encrypt(InputStream in, OutputStream out, String password, String algorithm) throws Exception {
        encrypt(in, out, password, algorithm, DEFAULT_KEYSIZE);
    }

    /**
     * 加密 Spring-Boot JAR 包
     *
     * @param in        原文包输入流
     * @param out       加密包输出流
     * @param password  密码
     * @param algorithm 加密算法
     * @param keysize   密钥长度
     * @throws Exception 加密异常
     */
    public static void encrypt(InputStream in, OutputStream out, String password, String algorithm, int keysize) throws Exception {
        encrypt(in, out, password, algorithm, keysize, DEFAULT_IVSIZE);
    }

    /**
     * 加密 Spring-Boot JAR 包
     *
     * @param in        原文包输入流
     * @param out       加密包输出流
     * @param password  密码
     * @param algorithm 加密算法
     * @param keysize   密钥长度
     * @param ivsize    向量长度
     * @throws Exception 加密异常
     */
    public static void encrypt(InputStream in, OutputStream out, String password, String algorithm, int keysize, int ivsize) throws Exception {
        XBootEncryptor xBootEncryptor = new XBootEncryptor(new XJdkEncryptor(algorithm));
        XKey xKey = XKit.key(algorithm, keysize, ivsize, password);
        xBootEncryptor.encrypt(xKey, in, out);
    }

    /**
     * 解密 Spring-Boot JAR 包
     *
     * @param src      加密包
     * @param dest     解密包
     * @param password 密码
     * @throws Exception 解密异常
     */
    public static void decrypt(String src, String dest, String password) throws Exception {
        decrypt(src, dest, password, DEFAULT_ALGORITHM);
    }

    /**
     * 解密 Spring-Boot JAR 包
     *
     * @param src       加密包
     * @param dest      解密包
     * @param password  密码
     * @param algorithm 加密算法
     * @throws Exception 解密异常
     */
    public static void decrypt(String src, String dest, String password, String algorithm) throws Exception {
        decrypt(src, dest, password, algorithm, DEFAULT_KEYSIZE);
    }

    /**
     * 解密 Spring-Boot JAR 包
     *
     * @param src       加密包
     * @param dest      解密包
     * @param password  密码
     * @param algorithm 加密算法
     * @param keysize   密钥长度
     * @throws Exception 解密异常
     */
    public static void decrypt(String src, String dest, String password, String algorithm, int keysize) throws Exception {
        decrypt(src, dest, password, algorithm, keysize, DEFAULT_IVSIZE);
    }

    /**
     * 解密 Spring-Boot JAR 包
     *
     * @param src       加密包
     * @param dest      解密包
     * @param password  密码
     * @param algorithm 加密算法
     * @param keysize   密钥长度
     * @param ivsize    向量长度
     * @throws Exception 解密异常
     */
    public static void decrypt(String src, String dest, String password, String algorithm, int keysize, int ivsize) throws Exception {
        decrypt(new File(src), new File(dest), password, algorithm, keysize, ivsize);
    }

    /**
     * 解密 Spring-Boot JAR 包
     *
     * @param src      加密包
     * @param dest     解密包
     * @param password 密码
     * @throws Exception 解密异常
     */
    public static void decrypt(File src, File dest, String password) throws Exception {
        decrypt(src, dest, password, DEFAULT_ALGORITHM);
    }

    /**
     * 解密 Spring-Boot JAR 包
     *
     * @param src       加密包
     * @param dest      解密包
     * @param password  密码
     * @param algorithm 加密算法
     * @throws Exception 解密异常
     */
    public static void decrypt(File src, File dest, String password, String algorithm) throws Exception {
        decrypt(src, dest, password, algorithm, DEFAULT_KEYSIZE);
    }

    /**
     * 解密 Spring-Boot JAR 包
     *
     * @param src       加密包
     * @param dest      解密包
     * @param password  密码
     * @param algorithm 加密算法
     * @param keysize   密钥长度
     * @throws Exception 解密异常
     */
    public static void decrypt(File src, File dest, String password, String algorithm, int keysize) throws Exception {
        decrypt(src, dest, password, algorithm, keysize, DEFAULT_IVSIZE);
    }

    /**
     * 解密 Spring-Boot JAR 包
     *
     * @param src       加密包
     * @param dest      解密包
     * @param password  密码
     * @param algorithm 加密算法
     * @param keysize   密钥长度
     * @param ivsize    向量长度
     * @throws Exception 解密异常
     */
    public static void decrypt(File src, File dest, String password, String algorithm, int keysize, int ivsize) throws Exception {
        try (
                InputStream in = new FileInputStream(src);
                OutputStream out = new FileOutputStream(dest)
        ) {
            decrypt(in, out, password, algorithm, keysize, ivsize);
        }
    }

    /**
     * 解密 Spring-Boot JAR 包
     *
     * @param in       加密包输入流
     * @param out      解密包输出流
     * @param password 密码
     * @throws Exception 解密异常
     */
    public static void decrypt(InputStream in, OutputStream out, String password) throws Exception {
        decrypt(in, out, password, DEFAULT_ALGORITHM);
    }

    /**
     * 解密 Spring-Boot JAR 包
     *
     * @param in        加密包输入流
     * @param out       解密包输出流
     * @param password  密码
     * @param algorithm 加密算法
     * @throws Exception 解密异常
     */
    public static void decrypt(InputStream in, OutputStream out, String password, String algorithm) throws Exception {
        decrypt(in, out, password, algorithm, DEFAULT_KEYSIZE);
    }

    /**
     * 解密 Spring-Boot JAR 包
     *
     * @param in        加密包输入流
     * @param out       解密包输出流
     * @param password  密码
     * @param algorithm 加密算法
     * @param keysize   密钥长度
     * @throws Exception 解密异常
     */
    public static void decrypt(InputStream in, OutputStream out, String password, String algorithm, int keysize) throws Exception {
        decrypt(in, out, password, algorithm, keysize, DEFAULT_IVSIZE);
    }

    /**
     * 解密 Spring-Boot JAR 包
     *
     * @param in        加密包输入流
     * @param out       解密包输出流
     * @param password  密码
     * @param algorithm 加密算法
     * @param keysize   密钥长度
     * @param ivsize    向量长度
     * @throws Exception 解密异常
     */
    public static void decrypt(InputStream in, OutputStream out, String password, String algorithm, int keysize, int ivsize) throws Exception {
        XBootDecryptor xBootDecryptor = new XBootDecryptor(new XJdkDecryptor(algorithm));
        XKey xKey = XKit.key(algorithm, keysize, ivsize, password);
        xBootDecryptor.decrypt(xKey, in, out);
    }

    /**
     * 加密 Spring-Boot JAR 包
     *
     * @param src      原文包
     * @param dest     加密包
     * @param password 密码
     * @param filter   过滤器
     * @throws Exception 加密异常
     */
    public static void encrypt(String src, String dest, String password, XEntryFilter<JarArchiveEntry> filter) throws Exception {
        encrypt(src, dest, password, DEFAULT_ALGORITHM, filter);
    }

    /**
     * 加密 Spring-Boot JAR 包
     *
     * @param src       原文包
     * @param dest      加密包
     * @param password  密码
     * @param algorithm 加密算法
     * @param filter    过滤器
     * @throws Exception 加密异常
     */
    public static void encrypt(String src, String dest, String password, String algorithm, XEntryFilter<JarArchiveEntry> filter) throws Exception {
        encrypt(src, dest, password, algorithm, DEFAULT_KEYSIZE, filter);
    }

    /**
     * 加密 Spring-Boot JAR 包
     *
     * @param src       原文包
     * @param dest      加密包
     * @param password  密码
     * @param algorithm 加密算法
     * @param keysize   密钥长度
     * @param filter    过滤器
     * @throws Exception 加密异常
     */
    public static void encrypt(String src, String dest, String password, String algorithm, int keysize, XEntryFilter<JarArchiveEntry> filter) throws Exception {
        encrypt(src, dest, password, algorithm, keysize, DEFAULT_IVSIZE, filter);
    }

    /**
     * 加密 Spring-Boot JAR 包
     *
     * @param src       原文包
     * @param dest      加密包
     * @param password  密码
     * @param algorithm 加密算法
     * @param keysize   密钥长度
     * @param ivsize    向量长度
     * @param filter    过滤器
     * @throws Exception 加密异常
     */
    public static void encrypt(String src, String dest, String password, String algorithm, int keysize, int ivsize, XEntryFilter<JarArchiveEntry> filter) throws Exception {
        encrypt(new File(src), new File(dest), password, algorithm, keysize, ivsize, filter);
    }

    /**
     * 加密 Spring-Boot JAR 包
     *
     * @param src      原文包
     * @param dest     加密包
     * @param password 密码
     * @param filter   过滤器
     * @throws Exception 加密异常
     */
    public static void encrypt(File src, File dest, String password, XEntryFilter<JarArchiveEntry> filter) throws Exception {
        encrypt(src, dest, password, DEFAULT_ALGORITHM, filter);
    }

    /**
     * 加密 Spring-Boot JAR 包
     *
     * @param src       原文包
     * @param dest      加密包
     * @param password  密码
     * @param algorithm 加密算法
     * @param filter    过滤器
     * @throws Exception 加密异常
     */
    public static void encrypt(File src, File dest, String password, String algorithm, XEntryFilter<JarArchiveEntry> filter) throws Exception {
        encrypt(src, dest, password, algorithm, DEFAULT_KEYSIZE, filter);
    }

    /**
     * 加密 Spring-Boot JAR 包
     *
     * @param src       原文包
     * @param dest      加密包
     * @param password  密码
     * @param algorithm 加密算法
     * @param keysize   密钥长度
     * @param filter    过滤器
     * @throws Exception 加密异常
     */
    public static void encrypt(File src, File dest, String password, String algorithm, int keysize, XEntryFilter<JarArchiveEntry> filter) throws Exception {
        encrypt(src, dest, password, algorithm, keysize, DEFAULT_IVSIZE, filter);
    }

    /**
     * 加密 Spring-Boot JAR 包
     *
     * @param src       原文包
     * @param dest      加密包
     * @param password  密码
     * @param algorithm 加密算法
     * @param keysize   密钥长度
     * @param ivsize    向量长度
     * @param filter    过滤器
     * @throws Exception 加密异常
     */
    public static void encrypt(File src, File dest, String password, String algorithm, int keysize, int ivsize, XEntryFilter<JarArchiveEntry> filter) throws Exception {
        try (
                InputStream in = new FileInputStream(src);
                OutputStream out = new FileOutputStream(dest)
        ) {
            encrypt(in, out, password, algorithm, keysize, ivsize, filter);
        }
    }

    /**
     * 加密 Spring-Boot JAR 包
     *
     * @param in       原文包输入流
     * @param out      加密包输出流
     * @param password 密码
     * @param filter   过滤器
     * @throws Exception 加密异常
     */
    public static void encrypt(InputStream in, OutputStream out, String password, XEntryFilter<JarArchiveEntry> filter) throws Exception {
        encrypt(in, out, password, DEFAULT_ALGORITHM, filter);
    }

    /**
     * 加密 Spring-Boot JAR 包
     *
     * @param in        原文包输入流
     * @param out       加密包输出流
     * @param password  密码
     * @param algorithm 加密算法
     * @param filter    过滤器
     * @throws Exception 加密异常
     */
    public static void encrypt(InputStream in, OutputStream out, String password, String algorithm, XEntryFilter<JarArchiveEntry> filter) throws Exception {
        encrypt(in, out, password, algorithm, DEFAULT_KEYSIZE, filter);
    }

    /**
     * 加密 Spring-Boot JAR 包
     *
     * @param in        原文包输入流
     * @param out       加密包输出流
     * @param password  密码
     * @param algorithm 加密算法
     * @param keysize   密钥长度
     * @param filter    过滤器
     * @throws Exception 加密异常
     */
    public static void encrypt(InputStream in, OutputStream out, String password, String algorithm, int keysize, XEntryFilter<JarArchiveEntry> filter) throws Exception {
        encrypt(in, out, password, algorithm, keysize, DEFAULT_IVSIZE, filter);
    }

    /**
     * 加密 Spring-Boot JAR 包
     *
     * @param in        原文包输入流
     * @param out       加密包输出流
     * @param password  密码
     * @param algorithm 加密算法
     * @param keysize   密钥长度
     * @param ivsize    向量长度
     * @param filter    过滤器
     * @throws Exception 加密异常
     */
    public static void encrypt(InputStream in, OutputStream out, String password, String algorithm, int keysize, int ivsize, XEntryFilter<JarArchiveEntry> filter) throws Exception {
        XBootEncryptor xBootEncryptor = new XBootEncryptor(new XJdkEncryptor(algorithm), filter);
        XKey xKey = XKit.key(algorithm, keysize, ivsize, password);
        xBootEncryptor.encrypt(xKey, in, out, password);
    }

    /**
     * 解密 Spring-Boot JAR 包
     *
     * @param src      加密包
     * @param dest     解密包
     * @param password 密码
     * @param filter   过滤器
     * @throws Exception 解密异常
     */
    public static void decrypt(String src, String dest, String password, XEntryFilter<JarArchiveEntry> filter) throws Exception {
        decrypt(src, dest, password, DEFAULT_ALGORITHM, filter);
    }

    /**
     * 解密 Spring-Boot JAR 包
     *
     * @param src       加密包
     * @param dest      解密包
     * @param password  密码
     * @param algorithm 加密算法
     * @param filter    过滤器
     * @throws Exception 解密异常
     */
    public static void decrypt(String src, String dest, String password, String algorithm, XEntryFilter<JarArchiveEntry> filter) throws Exception {
        decrypt(src, dest, password, algorithm, DEFAULT_KEYSIZE, filter);
    }

    /**
     * 解密 Spring-Boot JAR 包
     *
     * @param src       加密包
     * @param dest      解密包
     * @param password  密码
     * @param algorithm 加密算法
     * @param keysize   密钥长度
     * @param filter    过滤器
     * @throws Exception 解密异常
     */
    public static void decrypt(String src, String dest, String password, String algorithm, int keysize, XEntryFilter<JarArchiveEntry> filter) throws Exception {
        decrypt(src, dest, password, algorithm, keysize, DEFAULT_IVSIZE, filter);
    }

    /**
     * 解密 Spring-Boot JAR 包
     *
     * @param src       加密包
     * @param dest      解密包
     * @param password  密码
     * @param algorithm 加密算法
     * @param keysize   密钥长度
     * @param ivsize    向量长度
     * @param filter    过滤器
     * @throws Exception 解密异常
     */
    public static void decrypt(String src, String dest, String password, String algorithm, int keysize, int ivsize, XEntryFilter<JarArchiveEntry> filter) throws Exception {
        decrypt(new File(src), new File(dest), password, algorithm, keysize, ivsize, filter);
    }

    /**
     * 解密 Spring-Boot JAR 包
     *
     * @param src      加密包
     * @param dest     解密包
     * @param password 密码
     * @param filter   过滤器
     * @throws Exception 解密异常
     */
    public static void decrypt(File src, File dest, String password, XEntryFilter<JarArchiveEntry> filter) throws Exception {
        decrypt(src, dest, password, DEFAULT_ALGORITHM, filter);
    }

    /**
     * 解密 Spring-Boot JAR 包
     *
     * @param src       加密包
     * @param dest      解密包
     * @param password  密码
     * @param algorithm 加密算法
     * @param filter    过滤器
     * @throws Exception 解密异常
     */
    public static void decrypt(File src, File dest, String password, String algorithm, XEntryFilter<JarArchiveEntry> filter) throws Exception {
        decrypt(src, dest, password, algorithm, DEFAULT_KEYSIZE, filter);
    }

    /**
     * 解密 Spring-Boot JAR 包
     *
     * @param src       加密包
     * @param dest      解密包
     * @param password  密码
     * @param algorithm 加密算法
     * @param keysize   密钥长度
     * @param filter    过滤器
     * @throws Exception 解密异常
     */
    public static void decrypt(File src, File dest, String password, String algorithm, int keysize, XEntryFilter<JarArchiveEntry> filter) throws Exception {
        decrypt(src, dest, password, algorithm, keysize, DEFAULT_IVSIZE, filter);
    }

    /**
     * 解密 Spring-Boot JAR 包
     *
     * @param src       加密包
     * @param dest      解密包
     * @param password  密码
     * @param algorithm 加密算法
     * @param keysize   密钥长度
     * @param ivsize    向量长度
     * @param filter    过滤器
     * @throws Exception 解密异常
     */
    public static void decrypt(File src, File dest, String password, String algorithm, int keysize, int ivsize, XEntryFilter<JarArchiveEntry> filter) throws Exception {
        try (
                InputStream in = new FileInputStream(src);
                OutputStream out = new FileOutputStream(dest)
        ) {
            decrypt(in, out, password, algorithm, keysize, ivsize, filter);
        }
    }

    /**
     * 解密 Spring-Boot JAR 包
     *
     * @param in       加密包输入流
     * @param out      解密包输出流
     * @param password 密码
     * @param filter   过滤器
     * @throws Exception 解密异常
     */
    public static void decrypt(InputStream in, OutputStream out, String password, XEntryFilter<JarArchiveEntry> filter) throws Exception {
        decrypt(in, out, password, DEFAULT_ALGORITHM, filter);
    }

    /**
     * 解密 Spring-Boot JAR 包
     *
     * @param in        加密包输入流
     * @param out       解密包输出流
     * @param password  密码
     * @param algorithm 加密算法
     * @param filter    过滤器
     * @throws Exception 解密异常
     */
    public static void decrypt(InputStream in, OutputStream out, String password, String algorithm, XEntryFilter<JarArchiveEntry> filter) throws Exception {
        decrypt(in, out, password, algorithm, DEFAULT_KEYSIZE, filter);
    }

    /**
     * 解密 Spring-Boot JAR 包
     *
     * @param in        加密包输入流
     * @param out       解密包输出流
     * @param password  密码
     * @param algorithm 加密算法
     * @param keysize   密钥长度
     * @param filter    过滤器
     * @throws Exception 解密异常
     */
    public static void decrypt(InputStream in, OutputStream out, String password, String algorithm, int keysize, XEntryFilter<JarArchiveEntry> filter) throws Exception {
        decrypt(in, out, password, algorithm, keysize, DEFAULT_IVSIZE, filter);
    }

    /**
     * 解密 Spring-Boot JAR 包
     *
     * @param in        加密包输入流
     * @param out       解密包输出流
     * @param password  密码
     * @param algorithm 加密算法
     * @param keysize   密钥长度
     * @param ivsize    向量长度
     * @param filter    过滤器
     * @throws Exception 解密异常
     */
    public static void decrypt(InputStream in, OutputStream out, String password, String algorithm, int keysize, int ivsize, XEntryFilter<JarArchiveEntry> filter) throws Exception {
        XBootDecryptor xBootDecryptor = new XBootDecryptor(new XJdkDecryptor(algorithm), filter);
        XKey xKey = XKit.key(algorithm, keysize, ivsize, password);
        xBootDecryptor.decrypt(xKey, in, out);
    }

}
