package com.github.nightfall.cbds.io.serial.api;

import com.github.nightfall.cbds.CBDSConstants;
import com.github.nightfall.cbds.io.CompoundObject;
import com.github.nightfall.cbds.io.custom.IUnNamedCustomSerializable;
import com.github.nightfall.cbds.io.serial.impl.UnNamedBinarySerializer;
import com.github.nightfall.cbds.io.serial.obj.IDataStreamSerializable;
import com.github.nightfall.cbds.io.serial.obj.INamedSerializable;
import com.github.nightfall.cbds.io.serial.obj.IKeylessSerializable;
import com.github.nightfall.cbds.util.NativeArrayUtil;

import java.io.IOException;
import java.util.HashMap;

/**
 * The API class for the Keyless Serializers.
 *
 * @author Mr Zombii
 * @since 1.0.0
 */
public interface IKeylessSerializer {

    HashMap<Class<?>, IUnNamedCustomSerializable<?>> KEYLESS_SERIALIZER_MAP = new HashMap<>();

    static void registerSerializer(IUnNamedCustomSerializable<?> serializer) {
        if (INamedSerializer.hasSerializer(serializer.getSerializableType()) && !CBDSConstants.allowSerializerOverwriting) CBDSConstants.LOGGER.warn("Cannot overwrite pre-existing serializers, try turning \"com.github.nightfall.cbds.CBDSConstants.allowSerializerOverwriting\" true.");
        if (serializer.getSerializableType().isArray()) throw new RuntimeException("cannot register serializer of array type, I recommend registering the component type instead.");
        KEYLESS_SERIALIZER_MAP.put(serializer.getSerializableType(), serializer);
    }

    static <T> IUnNamedCustomSerializable<T> getSerializer(Class<T> clazz) {
        return (IUnNamedCustomSerializable<T>) KEYLESS_SERIALIZER_MAP.get(clazz);
    }

    static boolean hasSerializer(Class<?> clazz) {
        return !KEYLESS_SERIALIZER_MAP.containsKey(clazz);
    }

    static boolean hasSerializer(Object obj) {
        return hasSerializer(obj.getClass());
    }

    static IKeylessSerializer createDefault() {
        return new UnNamedBinarySerializer();
    }

    IKeylessSerializer newInstance();

    void writeByte(byte i) throws IOException;
    void writeByteArray(byte[] array) throws IOException;
    default void writeByteArray(Byte[] array) throws IOException {
        writeByteArray(NativeArrayUtil.toNativeArray(array));
    }

    void writeShort(short i) throws IOException;
    void writeShortArray(short[] array) throws IOException;
    default void writeShortArray(Short[] array) throws IOException {
        writeShortArray(NativeArrayUtil.toNativeArray(array));
    }

    void writeInt(int i) throws IOException;
    void writeIntArray(int[] array) throws IOException;
    default void writeIntArray(Integer[] array) throws IOException {
        writeIntArray(NativeArrayUtil.toNativeArray(array));
    }

    void writeLong(long i) throws IOException;
    void writeLongArray(long[] array) throws IOException;
    default void writeLongArray(Long[] array) throws IOException {
        writeLongArray(NativeArrayUtil.toNativeArray(array));
    }

    void writeFloat(float i) throws IOException;
    void writeFloatArray(float[] array) throws IOException;
    default void writeFloatArray(Float[] array) throws IOException {
        writeFloatArray(NativeArrayUtil.toNativeArray(array));
    }

    void writeDouble(double i) throws IOException;
    void writeDoubleArray(double[] array) throws IOException;
    default void writeDoubleArray(Double[] array) throws IOException {
        writeDoubleArray(NativeArrayUtil.toNativeArray(array));
    }

    void writeBoolean(boolean b) throws IOException;
    void writeBooleanArray(boolean[] array) throws IOException;
    default void writeBooleanArray(Boolean[] array) throws IOException {
        writeBooleanArray(NativeArrayUtil.toNativeArray(array));
    }

    void writeChar(char c) throws IOException;
    void writeCharArray(char[] array) throws IOException;
    default void writeCharArray(Character[] array) throws IOException {
        writeCharArray(NativeArrayUtil.toNativeArray(array));
    }

    void writeString(String v) throws IOException;
    void writeStringArray(String[] array) throws IOException;

    default void writeCompoundObject(CompoundObject object) throws IOException {
        writeUnNamedObject(object);
    }
    default void writeCompoundObjectArray(CompoundObject[] array) throws IOException {
        writeUnNamedObjectArray(array);
    }

    <T extends IDataStreamSerializable> void writeRawObject(T object) throws IOException;
    <T extends IDataStreamSerializable> void writeRawObjectArray(T[] array) throws IOException;

    <T extends INamedSerializable> void writeNamedObject(T object) throws IOException;
    <T extends INamedSerializable> void writeNamedObjectArray(T[] array) throws IOException;

    <T extends IKeylessSerializable> void writeUnNamedObject(T object) throws IOException;
    <T extends IKeylessSerializable> void writeUnNamedObjectArray(T[] array) throws IOException;

    <T> void writeCustomObject(T object) throws IOException;
    <T> void writeCustomObjectArray(T[] array) throws IOException;

    byte[] toBytes() throws IOException;
    byte[] toCompressedBytes() throws IOException;

    String toBase64() throws IOException;
    String toCompressedBase64() throws IOException;

}
