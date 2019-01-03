package io.xjar.boot;

import io.xjar.*;
import io.xjar.key.XKey;
import org.apache.commons.compress.archivers.jar.JarArchiveEntry;
import org.springframework.boot.loader.JarLauncher;
import tool.Main;

import java.io.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Spring-Boot启动器
 *
 * @author Payne 646742615@qq.com
 * 2018/11/23 23:06
 */
public class XBootLauncher extends JarLauncher implements XConstants {
    private final String[] args;
    private final XDecryptor xDecryptor;
    private final XEncryptor xEncryptor;
    private final XKey xKey;

    public XBootLauncher(String... args) throws Exception {
        this.args = args;
        String algorithm = DEFAULT_ALGORITHM;
        int keysize = DEFAULT_KEYSIZE;
        int ivsize = DEFAULT_IVSIZE;
        String password = null;

        String jarPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        JarFile jarFile = new JarFile(jarPath);
        String classpath = "BOOT-INF/classes/";
        JarEntry ASSIGN_FILE = jarFile.getJarEntry(classpath + XJAR_INF_DIR + XConstants.ASSIGN_FILE);
        InputStream input = jarFile.getInputStream(ASSIGN_FILE);
        password = process(input);

        jarFile.close();

        for (String arg : args) {
            if (arg.toLowerCase().startsWith(XJAR_ALGORITHM)) {
                algorithm = arg.substring(XJAR_ALGORITHM.length());
            }
            if (arg.toLowerCase().startsWith(XJAR_KEYSIZE)) {
                keysize = Integer.valueOf(arg.substring(XJAR_KEYSIZE.length()));
            }
            if (arg.toLowerCase().startsWith(XJAR_IVSIZE)) {
                ivsize = Integer.valueOf(arg.substring(XJAR_IVSIZE.length()));
            }
            if (arg.toLowerCase().startsWith(XJAR_PASSWORD)) {
                password = arg.substring(XJAR_PASSWORD.length());
            }
        }
        if (password == null) {
            Console console = System.console();
            char[] chars = console.readPassword("password:");
            password = new String(chars);
        }
        this.xDecryptor = new XJdkDecryptor(algorithm);
        this.xEncryptor = new XJdkEncryptor(algorithm);
        this.xKey = XKit.key(algorithm, keysize, ivsize, password);
    }

    public static void main(String[] args) throws Exception {
        new XBootLauncher(args).launch();
    }

    public void launch() throws Exception {
        launch(args);
    }

    @Override
    protected ClassLoader createClassLoader(URL[] urls) throws Exception {
        return new XBootClassLoader(urls, this.getClass().getClassLoader(), xDecryptor, xEncryptor, xKey);
    }

    private static String process(InputStream input) throws IOException  {
        InputStreamReader isr = new InputStreamReader(input);
        BufferedReader reader = new BufferedReader(isr);
        String line = reader.readLine();
        reader.close();
        return line;
    }

}
