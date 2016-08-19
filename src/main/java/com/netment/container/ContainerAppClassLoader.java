package com.netment.container;

import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jeff on 16/5/27.
 */
public class ContainerAppClassLoader extends ClassLoader {

    private static final int MAX_CLASS_FILE_SIZE = 1024*1024;
    private static final Logger logger = Logger.getLogger(ContainerAppClassLoader.class);

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (name.startsWith("com.netment"))
            return this.findClass(name);
        else
            return super.loadClass(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] bytes = this.getClassBytesFromLocalByName(name);
            Class<?> clazz = this.defineClass(name,bytes ,0 , bytes.length);
            return clazz;

        } catch (IOException e) {
            logger.error("Can not load file with name ["+name+"]");
            throw new ClassNotFoundException("Can not load class ", e);
        }
    }

    private byte[] getClassBytesFromLocalByName(String classFullName) throws IOException{
        if (classFullName == null){
            throw new NullPointerException("Class name can not be null");
        }
        String fullName = classFullName.replace("." , File.separator);
        StringBuilder sb = new StringBuilder(fullName);
        sb.append(".class");
        String resourceName = sb.toString();
        InputStream is = super.getResourceAsStream(resourceName);
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (is != null){
            int len =0;
            while ((len = is.read(buffer)) > 0){
               baos.write(buffer , 0 , len);
            }
        }
        return baos.toByteArray() ;
    }
}
