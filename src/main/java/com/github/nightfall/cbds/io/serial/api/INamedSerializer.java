package com.github.nightfall.cbds.io.serial.api;

import com.github.nightfall.cbds.CBDSConstants;
import com.github.nightfall.cbds.io.CompoundObject;
import com.github.nightfall.cbds.io.custom.IKeylessCustomSerializable;
import com.github.nightfall.cbds.io.custom.INamedCustomSerializable;
import com.github.nightfall.cbds.io.serial.impl.NamedBinarySerializer;
import com.github.nightfall.cbds.io.serial.obj.IDataStreamSerializable;
import com.github.nightfall.cbds.io.serial.obj.IKeylessSerializable;
import com.github.nightfall.cbds.io.serial.obj.INamedSerializable;
import com.github.nightfall.cbds.util.NativeArrayUtil;

import java.io.IOException;
import java.util.HashMap;

/**
 * The API class for creating serializers with named binary objects.
 *
 * @author Mr Zombii
 * @since 1.0.0
 */
public interface INamedSerializer {

    HashMap<Class<?>, INamedCustomSerializable<?>> NAMED_SERIALIZER_MAP = new HashMap<>();

    /**
     * A method for registering serializers for non-user owned objects.
     *
     * @see INamedCustomSerializable
     *
     * @param serializer The serializer being registered.
     */
    static void registerSerializer(INamedCustomSerializable<?> serializer) {
        if (INamedSerializer.hasSerializer(serializer.getSerializableType()) && !CBDSConstants.allowSerializerOverwriting) CBDSConstants.LOGGER.warn("Cannot overwrite pre-existing serializers, try turning \"com.github.nightfall.cbds.CBDSConstants.allowSerializerOverwriting\" true.");
        if (serializer.getSerializableType().isArray()) throw new RuntimeException("cannot register serializer of array type, I recommend registering the component type instead.");
        NAMED_SERIALIZER_MAP.put(serializer.getSerializableType(), serializer);
    }

    /**
     * The method for checking if a custom serializer exist for a custom object.
     *
     * @param clazz the class that may have a custom serializer built for it.
     */
    static <T> INamedCustomSerializable<T> getSerializer(Class<T> clazz) {
        //noinspection unchecked
        return (INamedCustomSerializable<T>) NAMED_SERIALIZER_MAP.get(clazz);
    }

    /**
     * The method for checking if a custom serializer exist for a custom object.
     *
     * @param clazz the class that may have a custom serializer built for it.
     */
    static boolean hasSerializer(Class<?> clazz) {
        return !NAMED_SERIALIZER_MAP.containsKey(clazz);
    }

    /**
     * The method for checking if a custom serializer exist for a custom object.
     *
     * @param obj the object that may have a custom serializer built for it.
     */
    static boolean hasSerializer(Object obj) {
        return hasSerializer(obj.getClass());
    }

    /**
     * A method for instancing the default named serializer implementation.
     */
    static INamedSerializer createDefault() {
        return new NamedBinarySerializer();
    }

    INamedSerializer newInstance();

    void writeByte(String name, byte i) throws IOException;
    void writeByteArray(String name, byte[] array) throws IOException;
    default void writeByteArray(String name, Byte[] array) throws IOException {
        writeByteArray(name, NativeArrayUtil.toNativeArray(array));
    }

    void writeShort(String name, short i) throws IOException;
    void writeShortArray(String name, short[] array) throws IOException;
    default void writeShortArray(String name, Short[] array) throws IOException {
        writeShortArray(name, NativeArrayUtil.toNativeArray(array));
    }

    void writeInt(String name, int i) throws IOException;
    void writeIntArray(String name, int[] array) throws IOException;
    default void writeIntArray(String name, Integer[] array) throws IOException {
        writeIntArray(name, NativeArrayUtil.toNativeArray(array));
    }

    void writeLong(String name, long i) throws IOException;
    void writeLongArray(String name, long[] array) throws IOException;
    default void writeLongArray(String name, Long[] array) throws IOException {
        writeLongArray(name, NativeArrayUtil.toNativeArray(array));
    }

    void writeFloat(String name, float i) throws IOException;
    void writeFloatArray(String name, float[] array) throws IOException;
    default void writeFloatArray(String name, Float[] array) throws IOException {
        writeFloatArray(name, NativeArrayUtil.toNativeArray(array));
    }

    void writeDouble(String name, double i) throws IOException;
    void writeDoubleArray(String name, double[] array) throws IOException;
    default void writeDoubleArray(String name, Double[] array) throws IOException {
        writeDoubleArray(name, NativeArrayUtil.toNativeArray(array));
    }

    void writeBoolean(String name, boolean b) throws IOException;
    void writeBooleanArray(String name, boolean[] array) throws IOException;
    default void writeBooleanArray(String name, Boolean[] array) throws IOException {
        writeBooleanArray(name, NativeArrayUtil.toNativeArray(array));
    }

    void writeChar(String name, char c) throws IOException;
    void writeCharArray(String name, char[] array) throws IOException;
    default void writeCharArray(String name, Character[] array) throws IOException {
        writeCharArray(name, NativeArrayUtil.toNativeArray(array));
    }

    void writeString(String name, String v) throws IOException;
    void writeStringArray(String name, String[] array) throws IOException;

    default void writeCompoundObject(String name, CompoundObject object) throws IOException {
        this.writeNamedObject(name, object);
    }
    default void writeCompoundObjectArray(String name, CompoundObject[] array) throws IOException {
        this.writeNamedObjectArray(name, array);
    }

    <T extends IDataStreamSerializable> void writeRawObject(String name, T object) throws IOException;
    <T extends IDataStreamSerializable> void writeRawObjectArray(String name, T[] array) throws IOException;

    <T extends INamedSerializable> void writeNamedObject(String name, T object) throws IOException;
    <T extends INamedSerializable> void writeNamedObjectArray(String name, T[] array) throws IOException;

    <T extends IKeylessSerializable> void writeUnNamedObject(String name, T object) throws IOException;
    <T extends IKeylessSerializable> void writeUnNamedObjectArray(String name, T[] array) throws IOException;

    <T> void writeCustomObject(String name, T object) throws IOException;
    <T> void writeCustomObjectArray(String name, T[] array) throws IOException;

    byte[] toBytes() throws IOException;
    byte[] toCompressedBytes() throws IOException;

    String toBase64() throws IOException;
    String toCompressedBase64() throws IOException;

}
