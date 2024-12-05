package com.github.nightfall.cbds.util;

import java.io.DataOutputStream;

public class NativeArrayUtil {

    public static byte[] toNativeArray(Byte[] array) {
        byte[] pArray = new byte[array.length];
        for (int i = 0; i < pArray.length; i++) pArray[i] = array[i];
        return pArray;
    }

    public static short[] toNativeArray(Short[] array) {
        short[] pArray = new short[array.length];
        for (int i = 0; i < pArray.length; i++) pArray[i] = array[i];
        return pArray;
    }

    public static int[] toNativeArray(Integer[] array) {
        int[] pArray = new int[array.length];
        for (int i = 0; i < pArray.length; i++) pArray[i] = array[i];
        return pArray;
    }

    public static long[] toNativeArray(Long[] array) {
        long[] pArray = new long[array.length];
        for (int i = 0; i < pArray.length; i++) pArray[i] = array[i];
        return pArray;
    }

    public static float[] toNativeArray(Float[] array) {
        float[] pArray = new float[array.length];
        for (int i = 0; i < pArray.length; i++) pArray[i] = array[i];
        return pArray;
    }

    public static double[] toNativeArray(Double[] array) {
        double[] pArray = new double[array.length];
        for (int i = 0; i < pArray.length; i++) pArray[i] = array[i];
        return pArray;
    }

    public static boolean[] toNativeArray(Boolean[] array) {
        boolean[] pArray = new boolean[array.length];
        for (int i = 0; i < pArray.length; i++) pArray[i] = array[i];
        return pArray;
    }

    public static char[] toNativeArray(Character[] array) {
        char[] pArray = new char[array.length];
        for (int i = 0; i < pArray.length; i++) pArray[i] = array[i];
        return pArray;
    }

}
