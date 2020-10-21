package com.payne.jvm;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;

/**
 * 「Week01-Job2」：
 *      自定义一个 Classloader，加载Hello.xlass文件(该文件所有字节（x=255-x）,执行其 hello 方法
 *
 * @author Payne
 * @date 2020/10/18
 */
public class MyClassLoader extends ClassLoader {

    /**
     * 文件路径
     */
    public static final String FILE_PATH = "/Users/payne/project/JavaCourse/src/com/payne/jvm/Hello.xlass";
    /**
     * 要加载的类的类名
     */
    public static final String CLASS_NAME = "Hello";

    /**
     * 方法名
     */
    public static final String METHOD_NAME = "hello";

    public static void main(String[] args) {

        if (!fileIsExists(FILE_PATH)) {
            return;
        }

        MyClassLoader myClassLoader = new MyClassLoader();

        try {
            byte[] bytes = getRightBinaryCode();
            // 加载类
            Class<?> theClass = myClassLoader.findClass(CLASS_NAME, bytes);
            // 执行该类的 hello() 方法
            theClass.getDeclaredMethod(METHOD_NAME).invoke(theClass.newInstance());
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException
                | NoSuchMethodException | IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 将字节数组转换为 className 这个类的一个实例
     * @param className 类名
     *  @param bytes 字节数组
     * @return 该类的一个实例
     */
    public Class<?> findClass(String className, byte[] bytes) {

        // Converts an array of bytes into an instance of class
        return defineClass(className, bytes, 0, bytes.length);
    }


    /**
     * 获取到正确的字节码数组
     * @return 正确的字节码数组
     * @throws IOException IO异常
     */
    public static byte[] getRightBinaryCode() throws IOException {

        byte[] bytes = loadFileByNio(FILE_PATH);
        if (bytes.length == 0) {
            return null;
        }
        // 处理字节数组
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) ((byte) 255 - bytes[i]);
        }
        return bytes;
    }

    /**
     * 通过文件输入流加载文件并输出为 byte[]
     * @param filePath 文件路径
     * @return 字节数组
     * @throws IOException IO异常
     */
    public static byte[] loadFileByInputStream(String filePath)  throws IOException {

        File file = new File(filePath);
        FileInputStream inputStream = new FileInputStream(file);
        int size = inputStream.available();
        byte[] bytes = new byte[size];
        inputStream.read(bytes);
        inputStream.close();
        return bytes;
    }

    /**
     * Reads all the bytes from a file. The method ensures that the file is
     * closed when all bytes have been read or an I/O error, or other runtime
     * exception, is thrown.
     * @param filePath 文件路径
     * @return 字节数组
     * @throws IOException IO异常
     */
    public static byte[] loadFileByNio(String filePath) throws IOException {
        File file = new File(filePath);
        return Files.readAllBytes(file.toPath());
    }

    /**
     * 判断文件是否存在
     * @param filePath 文件路径
     * @return true:存在，false:不存在
     */
    public static boolean fileIsExists(String filePath) {
        return !filePath.isEmpty() && new File(filePath).exists();
    }

}
