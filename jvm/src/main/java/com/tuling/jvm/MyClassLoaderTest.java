package com.tuling.jvm;

import java.io.FileInputStream;
import java.lang.reflect.Method;

public class MyClassLoaderTest {
    static class MyClassLoader extends ClassLoader {
        private String classpath;

        public MyClassLoader(String classpath) {
            this.classpath = classpath;
        }

        private byte[] loadByte(String name) throws Exception {
            name = name.replaceAll("\\.", "/");
            FileInputStream fis = new FileInputStream(classpath + "/" + name + ".class");
            int len = fis.available();
            byte[] data = new byte[len];
            fis.read(data);
            fis.close();
            return data;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                byte[] data = loadByte(name);
                //defineClass将一个字节数组转为Class对象，这个字节数组是class文件读取后最终的字节 数组。
                return defineClass(name, data, 0, data.length);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ClassNotFoundException();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // 初始化自定义类加载器，会先初始化父类ClassLoader，其中会把自定义类加载器的父加载
        // 器设置为应用程序类加载器AppClassLoader
        MyClassLoader myClassLoader = new MyClassLoader("/Users/gavin/temp/abc");
        // 上面的classpath目录下 创建 /com/tuling/jvm 几级目录，将User类的复制类User1.class丢入该目录
        //   此时，仍然是 AppClassLoader，因为是双亲委派机制，AppClassLoader先装载了
        //   删除本工程下的User1，即，只保留拷贝出去的 User1.class，此时输出才会是 MyClassLoader
        Class<?> clazz = myClassLoader.loadClass("com.tuling.jvm.User1");
        Object obj = clazz.newInstance();
        Method method = clazz.getDeclaredMethod("sout", null);
        method.invoke(obj, null);
        System.out.println(clazz.getClassLoader().getClass().getName());
    }
}
