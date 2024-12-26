package com.github.nightfall.odsl.io.serial.api;

import com.github.nightfall.odsl.ODSLConstants;
import com.github.nightfall.odsl.io.CompoundObject;
import com.github.nightfall.odsl.io.custom.INamedCustomSerializable;
import com.github.nightfall.odsl.io.serial.impl.NamedBinaryDeserializer;
import com.github.nightfall.odsl.io.serial.obj.IDataStreamSerializable;
import com.github.nightfall.odsl.io.serial.obj.IKeylessSerializable;
import com.github.nightfall.odsl.io.serial.obj.INamedSerializable;
import com.github.nightfall.odsl.util.NativeArrayUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * An API class for creating deserializers with named binary objects.
 *
 * @author Mr Zombii
 * @since 1.0.0
 */
public interface INamedDeserializer {

    HashMap<Class<?>, INamedCustomSerializable<?>> NAMED_DESERIALIZER_MAP = new HashMap<>();

    /**
     * Registers a deserializer for non-user owned objects.
     *
     * @see INamedCustomSerializable
     *
     * @param deserializer The serializer being registered.
     */
    static void registerDeserializer(INamedCustomSerializable<?> deserializer) {
        if (hasDeserializer(deserializer.getSerializableType()) && !ODSLConstants.allowDeserializerOverwriting) ODSLConstants.LOGGER.warn("Cannot overwrite pre-existing serializers, try turning \"com.github.nightfall.odsl.ODSLConstants.allowDeserializerOverwriting\" true.");
        if (deserializer.getSerializableType().isArray()) throw new RuntimeException("cannot register deserializer of array type, I recommend registering the component type instead.");
        NAMED_DESERIALIZER_MAP.put(deserializer.getSerializableType(), deserializer);
    }

    /**
     * Gets the deserializer for a custom object class.
     *
     * @param clazz the class that may have a custom deserializer built for it.
     */
    static <T> INamedCustomSerializable<T> getDeserializer(Class<T> clazz) {
        //noinspection unchecked
        return (INamedCustomSerializable<T>) NAMED_DESERIALIZER_MAP.get(clazz);
    }

    /**
     * Checks if a custom deserializer exist for a custom object class.
     *
     * @param clazz the class that may have a custom deserializer built for it.
     */
    static boolean hasDeserializer(Class<?> clazz) {
        return !NAMED_DESERIALIZER_MAP.containsKey(clazz);
    }

    /**
     * Checks if a custom deserializer exist for a custom object class.
     *
     * @param obj the object that may have a custom deserializer built for it.
     */
    static boolean hasDeserializer(Object obj) {
        return hasDeserializer(obj.getClass());
    }

    /**
     * Instances the default named deserializer implementation.
     * @param bytes The content bytes to deserialize.
     * @param isCompressed The option to allow decompression to the bytes.
     */
    static INamedDeserializer createDefault(byte[] bytes, boolean isCompressed) throws IOException {
        if (isCompressed)
            return new NamedBinaryDeserializer(NativeArrayUtil.readNBytes(new GZIPInputStream(new ByteArrayInputStream(bytes)), Integer.MAX_VALUE));
        return new NamedBinaryDeserializer(bytes);
    }

    /**
     * Instances the default named deserializer implementation.
     * @param bytes The content bytes to deserialize.
     * @param isCompressed The option to allow decompression to the bytes.
     */
    static INamedDeserializer createDefault(Byte[] bytes, boolean isCompressed) throws IOException {
        return createDefault(NativeArrayUtil.toNativeArray(bytes), isCompressed);
    }

    /**
     * Instances the default named deserializer implementation.
     * @param bytes The content bytes to deserialize.
     */
    default INamedDeserializer newInstance(byte[] bytes) throws IOException {
        return newInstance(bytes, false);
    }

    /**
     * Instances the default named deserializer implementation.
     * @param bytes The content bytes to deserialize.
     */
    default INamedDeserializer newInstance(Byte[] bytes) throws IOException {
        return newInstance(bytes, false);
    }

    /**
     * Creates new instances of the parent/current deserializer.
     * @param bytes The content bytes to deserialize.
     * @param isCompressed The option to allow decompression to the bytes.
     */
    INamedDeserializer newInstance(byte[] bytes, boolean isCompressed) throws IOException;
    default INamedDeserializer newInstance(Byte[] bytes, boolean isCompressed) throws IOException {
        return newInstance(NativeArrayUtil.toNativeArray(bytes), isCompressed);
    }

    /**
     * Reads a single byte.
     * @param name the name of the byte.
     */
    byte readByte(String name);

    /**
     * Reads a byte array.
     * @param name the name of the byte array.
     */
    Byte[] readByteArray(String name);

    /**
     * Reads a native byte array.
     * @param name the name of the native byte array.
     */
    default byte[] readByteArrayAsNative(String name) {
        return NativeArrayUtil.toNativeArray(readByteArray(name));
    }

    /**
     * Reads a list of bytes.
     * @param name the name of the list.
     */
    default List<Byte> readByteArrayAsList(String name) {
        return Arrays.asList(readByteArray(name));
    }

    /**
     * Reads a single short.
     * @param name the name of the byte.
     */
    short readShort(String name);

    /**
     * Reads a short array.
     * @param name the name of the short array.
     */
    Short[] readShortArray(String name);

    /**
     * Reads a native short array.
     * @param name the name of the native short array.
     */
    default short[] readShortArrayAsNative(String name) {
        return NativeArrayUtil.toNativeArray(readShortArray(name));
    }

    /**
     * Reads a list of shorts.
     * @param name the name of the list.
     */
    default List<Short> readShortArrayAsList(String name) {
        return Arrays.asList(readShortArray(name));
    }

    /**
     * Reads a single integer.
     * @param name the name of the byte.
     */
    int readInt(String name);

    /**
     * Reads an integer array.
     * @param name the name of the integer.
     */
    Integer[] readIntArray(String name);

    /**
     * Reads a native integer array.
     * @param name the name of the native integer array.
     */
    default int[] readIntArrayAsNative(String name) {
        return NativeArrayUtil.toNativeArray(readIntArray(name));
    }

    /**
     * Reads a list of integers.
     * @param name the name of the list.
     */
    default List<Integer> readIntArrayAsList(String name) {
        return Arrays.asList(readIntArray(name));
    }

    /**
     * Reads a single long.
     * @param name the name of the byte.
     */
    long readLong(String name);

    /**
     * Reads a long array.
     * @param name the name of the long array.
     */
    Long[] readLongArray(String name);

    /**
     * Reads a native long array.
     * @param name the name of the native long array.
     */
    default long[] readLongArrayAsNative(String name) {
        return NativeArrayUtil.toNativeArray(readLongArray(name));
    }

    /**
     * Reads a list of longs.
     * @param name the name of the list.
     */
    default List<Long> readLongArrayAsList(String name) {
        return Arrays.asList(readLongArray(name));
    }

    /**
     * Reads a single float.
     * @param name the name of the byte.
     */
    float readFloat(String name);

    /**
     * Reads a float array.
     * @param name the name of the float array.
     */
    Float[] readFloatArray(String name);

    /**
     * Reads a native float array.
     * @param name the name of the native float array.
     */
    default float[] readFloatArrayAsNative(String name) {
        return NativeArrayUtil.toNativeArray(readFloatArray(name));
    }

    /**
     * Reads a list of floats.
     * @param name the name of the list.
     */
    default List<Float> readFloatArrayAsList(String name) {
        return Arrays.asList(readFloatArray(name));
    }

    /**
     * Reads a single double.
     * @param name the name of the byte.
     */
    double readDouble(String name);

    /**
     * Reads a double array.
     * @param name the name of the double array.
     */
    Double[] readDoubleArray(String name);

    /**
     * Reads a native double array.
     * @param name the name of the double byte array.
     */
    default double[] readDoubleArrayAsNative(String name) {
        return NativeArrayUtil.toNativeArray(readDoubleArray(name));
    }

    /**
     * Reads a list of doubles.
     * @param name the name of the list.
     */
    default List<Double> readDoubleArrayAsList(String name) {
        return Arrays.asList(readDoubleArray(name));
    }

    /**
     * Reads a single boolean.
     * @param name the name of the byte.
     */
    boolean readBoolean(String name);

    /**
     * Reads a boolean array.
     * @param name the name of the boolean array.
     */
    Boolean[] readBooleanArray(String name);

    /**
     * Reads a native boolean array.
     * @param name the name of the native boolean array.
     */
    default boolean[] readBooleanArrayAsNative(String name) {
        return NativeArrayUtil.toNativeArray(readBooleanArray(name));
    }

    /**
     * Reads a list of booleans.
     * @param name the name of the list.
     */
    default List<Boolean> readBooleanArrayAsList(String name) {
        return Arrays.asList(readBooleanArray(name));
    }

    /**
     * Reads a single character.
     * @param name the name of the byte.
     */
    char readChar(String name);

    /**
     * Reads a character array.
     * @param name the name of the character array.
     */
    Character[] readCharArray(String name);

    /**
     * Reads a native character array.
     * @param name the name of the native character array.
     */
    default char[] readCharArrayAsNative(String name) {
        return NativeArrayUtil.toNativeArray(readCharArray(name));
    }

    /**
     * Reads a list of characters.
     * @param name the name of the list.
     */
    default List<Character> readCharArrayAsList(String name) {
        return Arrays.asList(readCharArray(name));
    }

    /**
     * Reads a single string.
     * @param name the name of the byte.
     */
    String readString(String name);

    /**
     * Reads a string array.
     * @param name the name of the string array.
     */
    String[] readStringArray(String name);

    /**
     * Reads a list of strings.
     * @param name the name of the list.
     */
    default List<String> readStringArrayAsList(String name) {
        return Arrays.asList(readStringArray(name));
    }

    /**
     * Reads a single compound object.
     * @param name the name of the byte.
     */
    CompoundObject readCompoundObject(String name);

    /**
     * Reads a compound object array.
     * @param name the name of the compound object array.
     */
    CompoundObject[] readCompoundObjectArray(String name);

    /**
     * Reads a list of compound objects.
     * @param name the name of the list.
     */
    default List<CompoundObject> readCompoundObjectArrayAsList(String name) {
        return Arrays.asList(readCompoundObjectArray(name));
    }

    /**
     * Reads a single raw-object.
     * @param name the name of the byte.
     */
    <T extends IDataStreamSerializable> T readRawObject(String name, Class<T> type) ;

    /**
     * Reads a raw-object array.
     * @param name the name of the raw-object array.
     */
    <T extends IDataStreamSerializable> T[] readRawObjectArray(String name, Class<T> type);

    /**
     * Reads a list of raw-objects.
     * @param name the name of the list.
     */
    default <T extends IDataStreamSerializable> List<T> readRawObjectArrayAsList(String name, Class<T> type) {
        return Arrays.asList(readRawObjectArray(name, type));
    }

    /**
     * Reads a single named-object.
     * @param name the name of the byte.
     */
    <T extends INamedSerializable> T readNamedObject(String name, Class<T> type);

    /**
     * Reads a named-object array.
     * @param name the name of the named-object array.
     */
    <T extends INamedSerializable> T[] readNamedObjectArray(String name, Class<T> type);

    /**
     * Reads a list of named-objects.
     * @param name the name of the list.
     */
    default <T extends INamedSerializable> List<T> readNamedObjectArrayAsList(String name, Class<T> type) {
        return Arrays.asList(readNamedObjectArray(name, type));
    }

    /**
     * Reads a single keyless-object.
     * @param name the name of the byte.
     */
    <T extends IKeylessSerializable> T readKeylessObject(String name, Class<T> type);

    /**
     * Reads a keyless-object array.
     * @param name the name of the keyless-object array.
     */
    <T extends IKeylessSerializable> T[] readKeylessObjectArray(String name, Class<T> type);

    /**
     * Reads a list of keyless-objects.
     * @param name the name of the list.
     */
    default <T extends IKeylessSerializable> List<T> readKeylessObjectArrayAsList(String name, Class<T> type) {
        return Arrays.asList(readKeylessObjectArray(name, type));
    }

    /**
     * Reads a single custom-object.
     * @param name the name of the byte.
     */
    <T> T readCustomObject(String name, Class<T> type);

    /**
     * Reads a custom-object array.
     * @param name the name of the custom-object array.
     */
    <T> T[] readCustomObjectArray(String name, Class<T> type);

    /**
     * Reads a list of custom-objects.
     * @param name the name of the list.
     */
    default <T> List<T> readCustomObjectArrayAsList(String name, Class<T> type) {
        return Arrays.asList(readCustomObjectArray(name, type));
    }

    /**
     * Gets an object that was read.
     * @param name The name of an object you want to get.
     */
    Object getObject(String name);

    /**
     * Gets the key count.
     */
    int getObjectCount();

    /**
     * Gets the list of keys.
     */
    String[] getKeys();
}
