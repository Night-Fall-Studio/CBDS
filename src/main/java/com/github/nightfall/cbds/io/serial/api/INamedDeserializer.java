package com.github.nightfall.cbds.io.serial.api;

import com.github.nightfall.cbds.CBDSConstants;
import com.github.nightfall.cbds.io.CompoundObject;
import com.github.nightfall.cbds.io.custom.INamedCustomSerializable;
import com.github.nightfall.cbds.io.serial.impl.NamedBinaryDeserializer;
import com.github.nightfall.cbds.io.serial.obj.IDataStreamSerializable;
import com.github.nightfall.cbds.io.serial.obj.IKeylessSerializable;
import com.github.nightfall.cbds.io.serial.obj.INamedSerializable;
import com.github.nightfall.cbds.util.NativeArrayUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;

public interface INamedDeserializer {

    HashMap<Class<?>, INamedCustomSerializable<?>> NAMED_DESERIALIZER_MAP = new HashMap<>();

    static void registerDeserializer(INamedCustomSerializable<?> deserializer) {
        if (hasDeserializer(deserializer.getSerializableType()) && !CBDSConstants.allowDeserializerOverwriting) CBDSConstants.LOGGER.warn("Cannot overwrite pre-existing serializers, try turning \"com.github.nightfall.cbds.CBDSConstants.allowDeserializerOverwriting\" true.");
        if (deserializer.getSerializableType().isArray()) throw new RuntimeException("cannot register deserializer of array type, I recommend registering the component type instead.");
        NAMED_DESERIALIZER_MAP.put(deserializer.getSerializableType(), deserializer);
    }

    static <T> INamedCustomSerializable<T> getDeserializer(Class<T> clazz) {
        return (INamedCustomSerializable<T>) NAMED_DESERIALIZER_MAP.get(clazz);
    }

    static boolean hasDeserializer(Class<?> clazz) {
        return !NAMED_DESERIALIZER_MAP.containsKey(clazz);
    }

    static boolean hasDeserializer(Object obj) {
        return hasDeserializer(obj.getClass());
    }

    static INamedDeserializer createDefault(byte[] bytes, boolean isCompressed) throws IOException {
        if (isCompressed)
            return new NamedBinaryDeserializer(NativeArrayUtil.readNBytes(new GZIPInputStream(new ByteArrayInputStream(bytes)), Integer.MAX_VALUE));
        return new NamedBinaryDeserializer(bytes);
    }

    static INamedDeserializer createDefault(Byte[] bytes, boolean isCompressed) throws IOException {
        return createDefault(NativeArrayUtil.toNativeArray(bytes), isCompressed);
    }

    default INamedDeserializer newInstance(byte[] bytes) throws IOException {
        return newInstance(bytes, false);
    }

    default INamedDeserializer newInstance(Byte[] bytes) throws IOException {
        return newInstance(bytes, false);
    }

    INamedDeserializer newInstance(byte[] bytes, boolean isCompressed) throws IOException;
    default INamedDeserializer newInstance(Byte[] bytes, boolean isCompressed) throws IOException {
        return newInstance(NativeArrayUtil.toNativeArray(bytes), isCompressed);
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

    <T extends IDataStreamSerializable> T readRawObject(String name, Class<T> type) ;
    <T extends IDataStreamSerializable> T[] readRawObjectArray(String name, Class<T> type);

    <T extends INamedSerializable> T readNamedObject(String name, Class<T> type);
    <T extends INamedSerializable> T[] readNamedObjectArray(String name, Class<T> type);

    <T extends IKeylessSerializable> T readUnNamedObject(String name, Class<T> type);
    <T extends IKeylessSerializable> T[] readUnNamedObjectArray(String name, Class<T> type);

    <T> T readCustomObject(String name, Class<T> type);
    <T> T[] readCustomObjectArray(String name, Class<T> type);

    Object getObject(String name);

    int getObjectCount();

    String[] getKeys();
}
