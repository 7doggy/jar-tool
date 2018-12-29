package io.xjar.jar;

import io.xjar.*;
import io.xjar.key.XKey;
import org.apache.commons.compress.archivers.jar.JarArchiveEntry;
import org.apache.commons.compress.archivers.jar.JarArchiveInputStream;
import org.apache.commons.compress.archivers.jar.JarArchiveOutputStream;

import java.io.*;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.zip.Deflater;

/**
 * 普通JAR包加密器
 *
 * @author Payne 646742615@qq.com
 * 2018/11/22 15:27
 */
public class XJarEncryptor extends XEntryEncryptor<JarArchiveEntry> implements XEncryptor, XConstants {
    private final int level;

    public XJarEncryptor(XEncryptor xEncryptor) {
        this(xEncryptor, null);
    }

    public XJarEncryptor(XEncryptor xEncryptor, XEntryFilter<JarArchiveEntry> filter) {
        this(xEncryptor, Deflater.DEFLATED, filter);
    }

    public XJarEncryptor(XEncryptor xEncryptor, int level) {
        this(xEncryptor, level, null);
    }

    public XJarEncryptor(XEncryptor xEncryptor, int level, XEntryFilter<JarArchiveEntry> filter) {
        super(xEncryptor, filter);
        this.level = level;
    }

    @Override
    public void encrypt(XKey key, File src, File dest) throws IOException {
        try (
                FileInputStream fis = new FileInputStream(src);
                FileOutputStream fos = new FileOutputStream(dest)
        ) {
            encrypt(key, fis, fos);
        }
    }

    @Override
    public void encrypt(XKey key, InputStream in, OutputStream out) throws IOException {
        JarArchiveInputStream zis = null;
        JarArchiveOutputStream zos = null;
        Set<String> indexes = new LinkedHashSet<>();
        try {
            zis = new JarArchiveInputStream(in);
            zos = new JarArchiveOutputStream(out);
            zos.setLevel(level);
            XUnclosedInputStream nis = new XUnclosedInputStream(zis);
            XUnclosedOutputStream nos = new XUnclosedOutputStream(zos);
            JarArchiveEntry entry;
            Manifest manifest = null;
            while ((entry = zis.getNextJarEntry()) != null) {
                if (entry.getName().startsWith(XJAR_SRC_DIR)
                        || entry.getName().endsWith(XJAR_INF_DIR)
                        || entry.getName().endsWith(XJAR_INF_DIR + XJAR_INF_IDX)
                ) {
                    continue;
                }
                if (entry.isDirectory()) {
                    JarArchiveEntry jarArchiveEntry = new JarArchiveEntry(entry.getName());
                    jarArchiveEntry.setTime(entry.getTime());
                    zos.putArchiveEntry(jarArchiveEntry);
                } else if (entry.getName().equals(META_INF_MANIFEST)) {
                    manifest = new Manifest(nis);
                    Attributes attributes = manifest.getMainAttributes();
                    String mainClass = attributes.getValue("Main-Class");
                    if (mainClass != null) {
                        attributes.putValue("Jar-Main-Class", mainClass);
                        attributes.putValue("Main-Class", "io.xjar.jar.XJarLauncher");
                    }
                    JarArchiveEntry jarArchiveEntry = new JarArchiveEntry(entry.getName());
                    jarArchiveEntry.setTime(entry.getTime());
                    zos.putArchiveEntry(jarArchiveEntry);
                    manifest.write(nos);
                } else {
                    JarArchiveEntry jarArchiveEntry = new JarArchiveEntry(entry.getName());
                    jarArchiveEntry.setTime(entry.getTime());
                    zos.putArchiveEntry(jarArchiveEntry);
                    boolean filtered = filtrate(entry);
                    if (filtered) indexes.add(entry.getName());
                    XEncryptor encryptor = filtered ? xEncryptor : xNopEncryptor;
                    try (OutputStream eos = encryptor.encrypt(key, nos)) {
                        XKit.transfer(nis, eos);
                    }
                }
                zos.closeArchiveEntry();
            }

            if (!indexes.isEmpty()) {
                JarArchiveEntry XJAR_INF = new JarArchiveEntry(XJAR_INF_DIR);
                XJAR_INF.setTime(System.currentTimeMillis());
                zos.putArchiveEntry(XJAR_INF);
                zos.closeArchiveEntry();

                JarArchiveEntry IDX = new JarArchiveEntry(XJAR_INF_DIR + XJAR_INF_IDX);
                IDX.setTime(System.currentTimeMillis());
                zos.putArchiveEntry(IDX);
                for (String index : indexes) {
                    zos.write(index.getBytes());
                    zos.write(CRLF.getBytes());
                }
                zos.closeArchiveEntry();

                String mainClass = manifest != null && manifest.getMainAttributes() != null ? manifest.getMainAttributes().getValue("Main-Class") : null;
                if (mainClass != null) {
                    XInjector.inject(zos);
                }
            }

            zos.finish();
        } finally {
            XKit.close(zis);
            XKit.close(zos);
        }
    }

    @Override
    public void encrypt(XKey key, InputStream in, OutputStream out, String password) throws IOException {

    }

    @Override
    public boolean filtrate(JarArchiveEntry entry) {
        return super.filtrate(entry) && !entry.getName().equals(META_INF_MANIFEST);
    }
}
