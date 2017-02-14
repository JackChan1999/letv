package com.nostra13.universalimageloader.cache.disc.impl;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.core.DefaultConfigurationFactory;
import com.nostra13.universalimageloader.utils.IoUtils;
import com.nostra13.universalimageloader.utils.IoUtils.CopyListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class BaseDiscCache implements DiskCache {
    public static final int DEFAULT_BUFFER_SIZE = 32768;
    public static final CompressFormat DEFAULT_COMPRESS_FORMAT = CompressFormat.PNG;
    public static final int DEFAULT_COMPRESS_QUALITY = 100;
    private static final String ERROR_ARG_NULL = " argument must be not null";
    private static final String TEMP_IMAGE_POSTFIX = ".tmp";
    protected int bufferSize;
    protected final File cacheDir;
    protected CompressFormat compressFormat;
    protected int compressQuality;
    protected final FileNameGenerator fileNameGenerator;
    protected final File reserveCacheDir;

    public BaseDiscCache(File cacheDir) {
        this(cacheDir, null);
    }

    public BaseDiscCache(File cacheDir, File reserveCacheDir) {
        this(cacheDir, reserveCacheDir, DefaultConfigurationFactory.createFileNameGenerator());
    }

    public BaseDiscCache(File cacheDir, File reserveCacheDir, FileNameGenerator fileNameGenerator) {
        this.bufferSize = 32768;
        this.compressFormat = DEFAULT_COMPRESS_FORMAT;
        this.compressQuality = 100;
        if (cacheDir == null) {
            throw new IllegalArgumentException("cacheDir argument must be not null");
        } else if (fileNameGenerator == null) {
            throw new IllegalArgumentException("fileNameGenerator argument must be not null");
        } else {
            this.cacheDir = cacheDir;
            this.reserveCacheDir = reserveCacheDir;
            this.fileNameGenerator = fileNameGenerator;
        }
    }

    public File getDirectory() {
        return this.cacheDir;
    }

    public File get(String imageUri) {
        return getFile(imageUri);
    }

    public boolean save(String imageUri, InputStream imageStream, CopyListener listener) throws IOException {
        OutputStream os;
        File imageFile = getFile(imageUri);
        File tmpFile = new File(imageFile.getAbsolutePath() + TEMP_IMAGE_POSTFIX);
        boolean loaded = false;
        try {
            os = new BufferedOutputStream(new FileOutputStream(tmpFile), this.bufferSize);
            loaded = IoUtils.copyStream(imageStream, os, listener, this.bufferSize);
        } catch (Throwable th) {
        } finally {
            IoUtils.closeSilently(
/*
Method generation error in method: com.nostra13.universalimageloader.cache.disc.impl.BaseDiscCache.save(java.lang.String, java.io.InputStream, com.nostra13.universalimageloader.utils.IoUtils$CopyListener):boolean
jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0049: INVOKE  (wrap: java.io.OutputStream
  ?: MERGE  (r2_2 'os' java.io.OutputStream) = (r2_0 'os' java.io.OutputStream), (r8_0 'imageStream' java.io.InputStream)) com.nostra13.universalimageloader.utils.IoUtils.closeSilently(java.io.Closeable):void type: STATIC in method: com.nostra13.universalimageloader.cache.disc.impl.BaseDiscCache.save(java.lang.String, java.io.InputStream, com.nostra13.universalimageloader.utils.IoUtils$CopyListener):boolean
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:226)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:203)
	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:100)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:50)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:93)
	at jadx.core.codegen.RegionGen.makeTryCatch(RegionGen.java:297)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:183)
	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:328)
	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:265)
	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:228)
	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:118)
	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:83)
	at jadx.core.codegen.CodeGen.visit(CodeGen.java:19)
	at jadx.core.ProcessClass.process(ProcessClass.java:43)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
Caused by: jadx.core.utils.exceptions.CodegenException: Error generate insn: ?: MERGE  (r2_2 'os' java.io.OutputStream) = (r2_0 'os' java.io.OutputStream), (r8_0 'imageStream' java.io.InputStream) in method: com.nostra13.universalimageloader.cache.disc.impl.BaseDiscCache.save(java.lang.String, java.io.InputStream, com.nostra13.universalimageloader.utils.IoUtils$CopyListener):boolean
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:226)
	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:101)
	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:679)
	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:649)
	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:343)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:220)
	... 20 more
Caused by: jadx.core.utils.exceptions.CodegenException: MERGE can be used only in fallback mode
	at jadx.core.codegen.InsnGen.fallbackOnlyInsn(InsnGen.java:530)
	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:514)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:211)
	... 25 more

*/

            public boolean save(String imageUri, Bitmap bitmap) throws IOException {
                File imageFile = getFile(imageUri);
                File tmpFile = new File(imageFile.getAbsolutePath() + TEMP_IMAGE_POSTFIX);
                OutputStream os = new BufferedOutputStream(new FileOutputStream(tmpFile), this.bufferSize);
                boolean savedSuccessfully = false;
                try {
                    savedSuccessfully = bitmap.compress(this.compressFormat, this.compressQuality, os);
                    bitmap.recycle();
                    return savedSuccessfully;
                } finally {
                    IoUtils.closeSilently(os);
                    if (savedSuccessfully && !tmpFile.renameTo(imageFile)) {
                        savedSuccessfully = false;
                    }
                    if (!savedSuccessfully) {
                        tmpFile.delete();
                    }
                }
            }

            public boolean remove(String imageUri) {
                return getFile(imageUri).delete();
            }

            public void close() {
            }

            public void clear() {
                File[] files = this.cacheDir.listFiles();
                if (files != null) {
                    for (File f : files) {
                        f.delete();
                    }
                }
            }

            protected File getFile(String imageUri) {
                String fileName = this.fileNameGenerator.generate(imageUri);
                File dir = this.cacheDir;
                if (!(this.cacheDir.exists() || this.cacheDir.mkdirs() || this.reserveCacheDir == null || (!this.reserveCacheDir.exists() && !this.reserveCacheDir.mkdirs()))) {
                    dir = this.reserveCacheDir;
                }
                return new File(dir, fileName);
            }

            public void setBufferSize(int bufferSize) {
                this.bufferSize = bufferSize;
            }

            public void setCompressFormat(CompressFormat compressFormat) {
                this.compressFormat = compressFormat;
            }

            public void setCompressQuality(int compressQuality) {
                this.compressQuality = compressQuality;
            }
        }
