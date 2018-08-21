package com.wzluo.util;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;
import sun.misc.Unsafe;

public class HackUtil {

    /**
     * 反射获取Unsafe实例
     * @return
     */
    public static Unsafe getHacked(){
        try {
            Field field = AtomicInteger.class.getDeclaredField("unsafe");
            field.setAccessible(true);
            return (Unsafe)(field.get(null));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args) {
        Unsafe unsafe = getHacked();
        System.out.println(unsafe);
    }

}
