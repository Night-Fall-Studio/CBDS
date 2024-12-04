package com.github.nightfall.cbds.io.serial.api;

import com.github.nightfall.cbds.CBDSConstants;
import com.github.nightfall.cbds.io.CompoundObject;
import com.github.nightfall.cbds.io.custom.CustomDeserializer;
import com.github.nightfall.cbds.io.obj.BinSerializable;
import com.github.nightfall.cbds.io.obj.RawDataSerializable;
import com.github.nightfall.cbds.util.NativeArrayUtil;

import java.io.IOException;
import java.util.HashMap;

public interface IDeserializer {

    HashMap<Class<?>, CustomDeserializer<?>> DESERIALIZER_MAP = new HashMap<>();

    static void registerDeserializer(CustomDeserializer<?> deserializer) {
        if (hasDeserializer(deserializer.getSerializableType()) && !CBDSConstants.allowDeserializerOverwriting) CBDSConstants.LOGGER.warn("Cannot overwrite pre-existing serializers, try turning \"com.github.nightfall.cbds.CBDSConstants.allowDeserializerOverwriting\" true.");
        if (deserializer.getSerializableType().isArray()) throw new RuntimeException("cannot register deserializer of array type, I recommend registering the component type instead.");
        DESERIALIZER_MAP.put(deserializer.getSerializableType(), deserializer);
    }

    static <T> CustomDeserializer<T> getDeserializer(Class<T> clazz) {
        return (CustomDeserializer<T>) DESERIALIZER_MAP.get(clazz);
    }

    static boolean hasDeserializer(Class<?> clazz) {
        return !DESERIALIZER_MAP.containsKey(clazz);
    }

    static boolean hasDeserializer(Object obj) {
        return hasDeserializer(obj.getClass());
    }

    IDeserializer newInstance(byte[] bytes) throws IOException;
    default IDeserializer newInstance(Byte[] bytes) throws IOException {
        return newInstance(NativeArrayUtil.toNativeArray(bytes));
    }

    IDeserializer newInstanceFromCompressed(byte[] bytes) throws IOException;
    default IDeserializer newInstanceFromCompressed(Byte[] bytes) throws IOException {
        return newInstanceFromCompressed(NativeArrayUtil.toNativeArray(bytes));
    }

    byte readByte(String name);
    Byte[] readByteArray(String name);
    default byte[] readByteArrayAsPrimitive(String name) {
        return NativeArrayUtil.toNativeArray(readByteArray(name));
    }

    short readShort(String name);
    Short[] readShortArray(String name);
    default short[] readShortArrayAsPrimitive(String name) {
        return NativeArrayUtil.toNativeArray(readShortArray(name));
    }

    int readInt(String name);
    Integer[] readIntArray(String name);
    default int[] readIntArrayAsPrimitive(String name) {
        return NativeArrayUtil.toNativeArray(readIntArray(name));
    }

    long readLong(String name);
    Long[] readLongArray(String name);
    default long[] readLongArrayAsPrimitive(String name) {
        return NativeArrayUtil.toNativeArray(readLongArray(name));
    }

    float readFloat(String name);
    Float[] readFloatArray(String name);
    default float[] readFloatArrayAsPrimitive(String name) {
        return NativeArrayUtil.toNativeArray(readFloatArray(name));
    }

    double readDouble(String name);
    Double[] readDoubleArray(String name);
    default double[] readDoubleArrayAsPrimitive(String name) {
        return NativeArrayUtil.toNativeArray(readDoubleArray(name));
    }

    boolean readBoolean(String name);
    Boolean[] readBooleanArray(String name);
    default boolean[] readBooleanArrayAsPrimitive(String name) {
        return NativeArrayUtil.toNativeArray(readBooleanArray(name));
    }

    char readChar(String name);
    Character[] readCharArray(String name);
    default char[] readCharArrayAsPrimitive(String name) {
        return NativeArrayUtil.toNativeArray(readCharArray(name));
    }

    String readString(String name);
    String[] readStringArray(String name);

    CompoundObject readCompoundObject(String name);
    CompoundObject[] readCompoundObjectArray(String name);

    <T extends RawDataSerializable> T readRawObject(Class<T> type, String name) ;
    <T extends RawDataSerializable> T[] readRawObjectArray(Class<T> type, String name);

    <T extends BinSerializable> T readObject(Class<T> type, String name);
    <T extends BinSerializable> T[] readObjectArray(Class<T> type, String name);

    <T> T readCustomObject(Class<T> type, String name);
    <T> T[] readCustomObjectArray(Class<T> type, String name);

    Object getObject(String name);

}
