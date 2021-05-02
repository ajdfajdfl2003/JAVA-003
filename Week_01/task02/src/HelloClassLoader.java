package Week_01.task02.src;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;

public class HelloClassLoader extends ClassLoader {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<?> hello = new HelloClassLoader().findClass("Hello");
        Method method = hello.getMethod("hello");
        method.invoke(hello.newInstance());
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        String xlassPath = this.getClass()
                .getResource("").getPath() + "Hello.xlass";

        byte[] bytes;

        try {
            bytes = Files.readAllBytes(new File(xlassPath).toPath());
        } catch (IOException e) {
            throw new ClassNotFoundException();
        }

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (255 - bytes[i]);
        }

        return super.defineClass(className, bytes, 0, bytes.length);
    }
}
