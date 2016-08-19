package com.netment.util;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

/**
 * Created by jeff on 16/5/28.
 */
public class FileUtils {


    public  static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    public static void copyFileUsingChannel(File source , File dest) throws IOException{
        FileChannel sourceChannel = null;
        FileChannel destChannel = null;
        try {
            sourceChannel = new FileInputStream(source).getChannel();
            destChannel = new FileInputStream(dest).getChannel();
            sourceChannel.transferTo(0 , sourceChannel.size() , destChannel);
        }
        finally {
            sourceChannel.close();
            destChannel.close();
        }
    }
    public static void copyWithJava7File(File source , File dest) throws Exception{
        Files.copy(source.toPath(),dest.toPath());
    }
}
