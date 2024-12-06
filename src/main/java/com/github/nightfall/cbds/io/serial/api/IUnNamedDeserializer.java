package com.github.nightfall.cbds.io.serial.api;

import com.github.nightfall.cbds.CBDSConstants;
import com.github.nightfall.cbds.io.CompoundObject;
import com.github.nightfall.cbds.io.custom.IUnNamedCustomSerializable;
import com.github.nightfall.cbds.io.serial.impl.NamedBinaryDeserializer;
import com.github.nightfall.cbds.io.serial.impl.UnNamedBinaryDeserializer;
import com.github.nightfall.cbds.io.serial.obj.INamedSerializable;
import com.github.nightfall.cbds.io.serial.obj.IDataStreamSerializable;
import com.github.nightfall.cbds.io.serial.obj.IUnNamedSerializable;
import com.github.nightfall.cbds.util.NativeArrayUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;

public interface IUnNamedDeserializer {

    HashMap<Class<?>, IUnNamedCustomSerializable<?>> UNNAMED_DESERIALIZER_MAP = new HashMap<>();

    static void registerDeserializer(IUnNamedCustomSerializable<?> deserializer) {
        if (hasDeserializer(deserializer.getSerializableType()) && !CBDSConstants.allowDeserializerOverwriting) CBDSConstants.LOGGER.warn("Cannot overwrite pre-existing serializers, try turning \"com.github.nightfall.cbds.CBDSConstants.allowDeserializerOverwriting\" true.");
        if (deserializer.getSerializableType().isArray()) throw new RuntimeException("cannot register deserializer of array type, I recommend registering the component type instead.");
        UNNAMED_DESERIALIZER_MAP.put(deserializer.getSerializableType(), deserializer);
    }

    static <T> IUnNamedCustomSerializable<T> getDeserializer(Class<T> clazz) {
        return (IUnNamedCustomSerializable<T>) UNNAMED_DESERIALIZER_MAP.get(clazz);
    }

    static boolean hasDeserializer(Class<?> clazz) {
        return !UNNAMED_DESERIALIZER_MAP.containsKey(clazz);
    }

    static boolean hasDeserializer(Object obj) {
        return hasDeserializer(obj.getClass());
    }

    static IUnNamedDeserializer createDefault(byte[] bytes, boolean isCompressed) throws IOException {
        if (isCompressed)
            return new UnNamedBinaryDeserializer(new GZIPInputStream(new ByteArrayInputStream(bytes)).readAllBytes());
        return new UnNamedBinaryDeserializer(bytes);
    }

    static IUnNamedDeserializer createDefault(Byte[] bytes, boolean isCompressed) throws IOException {
        return createDefault(NativeArrayUtil.toNativeArray(bytes), isCompressed);
    }

    default IUnNamedDeserializer newInstance(byte[] bytes) throws IOException {
        return newInstance(bytes, false);
    }
    default IUnNamedDeserializer newInstance(Byte[] bytes) throws IOException {
        return newInstance(NativeArrayUtil.toNativeArray(bytes));
    }

    IUnNamedDeserializer newInstance(byte[] bytes, boolean isCompressed) throws IOException;
    default IUnNamedDeserializer newInstance(Byte[] bytes, boolean isCompressed) throws IOException {
        return newInstance(NativeArrayUtil.toNativeArray(bytes), isCompressed);
    }

    byte readByte() throws IOException;
    Byte[] readByteArray() throws IOException;
    default byte[] readByteArrayAsPrimitive() throws IOException {
        return NativeArrayUtil.toNativeArray(readByteArray());
    }

    short readShort() throws IOException;
    Short[] readShortArray() throws IOException;
    default short[] readShortArrayAsPrimitive() throws IOException {
        return NativeArrayUtil.toNativeArray(readShortArray());
    }

    int readInt() throws IOException;
    Integer[] readIntArray() throws IOException;
    default int[] readIntArrayAsPrimitive() throws IOException {
        return NativeArrayUtil.toNativeArray(readIntArray());
    }

    long readLong() throws IOException;
    Long[] readLongArray() throws IOException;
    default long[] readLongArrayAsPrimitive() throws IOException {
        return NativeArrayUtil.toNativeArray(readLongArray());
    }

    float readFloat() throws IOException;
    Float[] readFloatArray() throws IOException;
    default float[] readFloatArrayAsPrimitive() throws IOException {
        return NativeArrayUtil.toNativeArray(readFloatArray());
    }

    double readDouble() throws IOException;
    Double[] readDoubleArray() throws IOException;
    default double[] readDoubleArrayAsPrimitive() throws IOException {
        return NativeArrayUtil.toNativeArray(readDoubleArray());
    }

    boolean readBoolean() throws IOException;
    Boolean[] readBooleanArray() throws IOException;
    default boolean[] readBooleanArrayAsPrimitive() throws IOException {
        return NativeArrayUtil.toNativeArray(readBooleanArray());
    }

    char readChar() throws IOException;
    Character[] readCharArray() throws IOException;
    default char[] readCharArrayAsPrimitive() throws IOException {
        return NativeArrayUtil.toNativeArray(readCharArray());
    }

    String readString() throws IOException;
    String[] readStringArray() throws IOException;

    CompoundObject readCompoundObject() throws IOException;
    CompoundObject[] readCompoundObjectArray() throws IOException;

    <T extends IDataStreamSerializable> T readRawObject(Class<T> type) throws IOException;
    <T extends IDataStreamSerializable> T[] readRawObjectArray(Class<T> type) throws IOException;

    <T extends INamedSerializable> T readNamedObject(Class<T> type) throws IOException;
    <T extends INamedSerializable> T[] readNamedObjectArray(Class<T> type) throws IOException;

    <T extends IUnNamedSerializable> T readUnNamedObject(Class<T> type) throws IOException;
    <T extends IUnNamedSerializable> T[] readUnNamedObjectArray(Class<T> type) throws IOException;

    <T> T readCustomObject(Class<T> type) throws IOException;
    <T> T[] readCustomObjectArray(Class<T> type) throws IOException;

}
