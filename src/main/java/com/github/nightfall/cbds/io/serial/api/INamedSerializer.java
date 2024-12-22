package com.github.nightfall.cbds.io.serial.api;

import com.github.nightfall.cbds.CBDSConstants;
import com.github.nightfall.cbds.io.CompoundObject;
import com.github.nightfall.cbds.io.custom.INamedCustomSerializable;
import com.github.nightfall.cbds.io.serial.impl.NamedBinarySerializer;
import com.github.nightfall.cbds.io.serial.obj.IDataStreamSerializable;
import com.github.nightfall.cbds.io.serial.obj.IKeylessSerializable;
import com.github.nightfall.cbds.io.serial.obj.INamedSerializable;
import com.github.nightfall.cbds.util.NativeArrayUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.zip.GZIPOutputStream;

/**
 * An API class for creating serializers with named binary objects.
 *
 * @author Mr Zombii
 * @since 1.0.0
 */
public interface INamedSerializer {

    HashMap<Class<?>, INamedCustomSerializable<?>> NAMED_SERIALIZER_MAP = new HashMap<>();

    /**
     * Registers a serializer for non-user owned objects.
     *
     * @see INamedCustomSerializable
     *
     * @param serializer The serializer being registered.
     */
    static void registerSerializer(@NotNull INamedCustomSerializable<?> serializer) {
        if (INamedSerializer.hasSerializer(serializer.getSerializableType()) && !CBDSConstants.allowSerializerOverwriting) CBDSConstants.LOGGER.warn("Cannot overwrite pre-existing serializers, try turning \"com.github.nightfall.cbds.CBDSConstants.allowSerializerOverwriting\" true.");
        if (serializer.getSerializableType().isArray()) throw new RuntimeException("cannot register serializer of array type, I recommend registering the component type instead.");
        NAMED_SERIALIZER_MAP.put(serializer.getSerializableType(), serializer);
    }

    /**
     * Gets the serializer for a custom object class.
     *
     * @param clazz the class that may have a custom serializer built for it.
     */
    static <T> INamedCustomSerializable<T> getSerializer(Class<T> clazz) {
        //noinspection unchecked
        return (INamedCustomSerializable<T>) NAMED_SERIALIZER_MAP.get(clazz);
    }

    /**
     * Checks if a custom serializer exist for a custom object class.
     *
     * @param clazz the class that may have a custom serializer built for it.
     */
    static boolean hasSerializer(Class<?> clazz) {
        return !NAMED_SERIALIZER_MAP.containsKey(clazz);
    }

    /**
     * Checks if a custom serializer exist for a custom object.
     *
     * @param obj the object that may have a custom serializer built for it.
     */
    static boolean hasSerializer(@NotNull Object obj) {
        return hasSerializer(obj.getClass());
    }

    /**
     * Instances the default named serializer implementation.
     */
    @Contract(" -> new")
    static @NotNull INamedSerializer createDefault() {
        return new NamedBinarySerializer();
    }

    /**
     * Creates new instances of the parent/current serializer.
     */
    INamedSerializer newInstance();

    /**
     * Writes a byte to the serializer.
     * @param name The name of the byte.
     * @param i The byte to be written.
     */
    void writeByte(String name, byte i) throws IOException;

    /**
     * Writes an array of bytes.
     * @param name The name of the array.
     * @param array The array to be written.
     */
    void writeByteArray(String name, byte[] array) throws IOException;

    /**
     * Writes an array of bytes.
     * @param name The name of the array.
     * @param array The array to be written.
     */
    default void writeByteArray(String name, Byte[] array) throws IOException {
        writeByteArray(name, NativeArrayUtil.toNativeArray(array));
    }

    /**
     * Writes a list of bytes.
     * @param name The name of the list.
     * @param list The list to be written.
     */
    default void writeByteList(String name, @NotNull List<Byte> list) throws IOException {
        writeByteArray(name, NativeArrayUtil.toNativeArray(list.toArray(new Byte[0])));
    }

    /**
     * Writes a short to the serializer.
     * @param name The name of the short.
     * @param i The short to be written.
     */
    void writeShort(String name, short i) throws IOException;

    /**
     * Writes an array of shorts.
     * @param name The name of the array.
     * @param array The array to be written.
     */
    void writeShortArray(String name, short[] array) throws IOException;

    /**
     * Writes an array of shorts.
     * @param name The name of the array.
     * @param array The array to be written.
     */
    default void writeShortArray(String name, Short[] array) throws IOException {
        writeShortArray(name, NativeArrayUtil.toNativeArray(array));
    }

    /**
     * Writes a list of shorts.
     * @param name The name of the list.
     * @param list The list to be written.
     */
    default void writeShortList(String name, @NotNull List<Short> list) throws IOException {
        writeShortArray(name, NativeArrayUtil.toNativeArray(list.toArray(new Short[0])));
    }

    /**
     * Writes an integer to the serializer.
     * @param name The name of the short.
     * @param i The short to be written.
     */
    void writeInt(String name, int i) throws IOException;

    /**
     * Writes an array of integers.
     * @param name The name of the array.
     * @param array The array to be written.
     */
    void writeIntArray(String name, int[] array) throws IOException;

    /**
     * Writes an array of integers.
     * @param name The name of the array.
     * @param array The array to be written.
     */
    default void writeIntArray(String name, Integer[] array) throws IOException {
        writeIntArray(name, NativeArrayUtil.toNativeArray(array));
    }

    /**
     * Writes a list of integers.
     * @param name The name of the list.
     * @param list The list to be written.
     */
    default void writeIntList(String name, @NotNull List<Integer> list) throws IOException {
        writeIntArray(name, NativeArrayUtil.toNativeArray(list.toArray(new Integer[0])));
    }

    /**
     * Writes a long to the serializer.
     * @param name The name of the long.
     * @param i The long to be written.
     */
    void writeLong(String name, long i) throws IOException;

    /**
     * Writes an array of longs.
     * @param name The name of the array.
     * @param array The array to be written.
     */
    void writeLongArray(String name, long[] array) throws IOException;

    /**
     * Writes an array of longs.
     * @param name The name of the array.
     * @param array The array to be written.
     */
    default void writeLongArray(String name, Long[] array) throws IOException {
        writeLongArray(name, NativeArrayUtil.toNativeArray(array));
    }

    /**
     * Writes a list of longs.
     * @param name The name of the list.
     * @param list The list to be written.
     */
    default void writeLongList(String name, @NotNull List<Long> list) throws IOException {
        writeLongArray(name, NativeArrayUtil.toNativeArray(list.toArray(new Long[0])));
    }

    /**
     * Writes a float to the serializer.
     * @param name The name of the float.
     * @param i The float to be written.
     */
    void writeFloat(String name, float i) throws IOException;

    /**
     * Writes an array of floats.
     * @param name The name of the array.
     * @param array The array to be written.
     */
    void writeFloatArray(String name, float[] array) throws IOException;

    /**
     * Writes an array of floats.
     * @param name The name of the array.
     * @param array The array to be written.
     */
    default void writeFloatArray(String name, Float[] array) throws IOException {
        writeFloatArray(name, NativeArrayUtil.toNativeArray(array));
    }

    /**
     * Writes a list of floats.
     * @param name The name of the list.
     * @param list The list to be written.
     */
    default void writeFloatList(String name, @NotNull List<Float> list) throws IOException {
        writeFloatArray(name, NativeArrayUtil.toNativeArray(list.toArray(new Float[0])));
    }

    /**
     * Writes a double to the serializer.
     * @param name The name of the double.
     * @param i The double to be written.
     */
    void writeDouble(String name, double i) throws IOException;

    /**
     * Writes an array of doubles.
     * @param name The name of the array.
     * @param array The array to be written.
     */
    void writeDoubleArray(String name, double[] array) throws IOException;

    /**
     * Writes an array of doubles.
     * @param name The name of the array.
     * @param array The array to be written.
     */
    default void writeDoubleArray(String name, Double[] array) throws IOException {
        writeDoubleArray(name, NativeArrayUtil.toNativeArray(array));
    }

    /**
     * Writes a list of doubles.
     * @param name The name of the list.
     * @param list The list to be written.
     */
    default void writeDoubleList(String name, @NotNull List<Double> list) throws IOException {
        writeDoubleArray(name, NativeArrayUtil.toNativeArray(list.toArray(new Double[0])));
    }

    /**
     * Writes a boolean to the serializer.
     * @param name The name of the boolean.
     * @param b The boolean to be written.
     */
    void writeBoolean(String name, boolean b) throws IOException;

    /**
     * Writes an array of booleans.
     * @param name The name of the array.
     * @param array The array to be written.
     */
    void writeBooleanArray(String name, boolean[] array) throws IOException;

    /**
     * Writes an array of booleans.
     * @param name The name of the array.
     * @param array The array to be written.
     */
    default void writeBooleanArray(String name, Boolean[] array) throws IOException {
        writeBooleanArray(name, NativeArrayUtil.toNativeArray(array));
    }

    /**
     * Writes a list of booleans.
     * @param name The name of the list.
     * @param list The list to be written.
     */
    default void writeBooleanList(String name, @NotNull List<Boolean> list) throws IOException {
        writeBooleanArray(name, NativeArrayUtil.toNativeArray(list.toArray(new Boolean[0])));
    }

    /**
     * Writes a character to the serializer.
     * @param name The name of the character.
     * @param c The character to be written.
     */
    void writeChar(String name, char c) throws IOException;

    /**
     * Writes an array of characters.
     * @param name The name of the array.
     * @param array The array to be written.
     */
    void writeCharArray(String name, char[] array) throws IOException;

    /**
     * Writes an array of characters.
     * @param name The name of the array.
     * @param array The array to be written.
     */
    default void writeCharArray(String name, Character[] array) throws IOException {
        writeCharArray(name, NativeArrayUtil.toNativeArray(array));
    }

    /**
     * Writes a list of characters.
     * @param name The name of the list.
     * @param list The list to be written.
     */
    default void writeCharList(String name, @NotNull List<Character> list) throws IOException {
        writeCharArray(name, NativeArrayUtil.toNativeArray(list.toArray(new Character[0])));
    }

    /**
     * Writes a double to the serializer.
     * @param name The name of the double.
     * @param v The string to be written.
     */
    void writeString(String name, String v) throws IOException;

    /**
     * Writes an array of strings.
     * @param name The name of the array.
     * @param array The array to be written.
     */
    void writeStringArray(String name, String[] array) throws IOException;

    /**
     * Writes a list of strings.
     * @param name The name of the list.
     * @param list The list to be written.
     */
    default void writeStringList(String name, @NotNull List<String> list) throws IOException {
        writeStringArray(name, list.toArray(new String[0]));
    }

    /**
     * Writes a compound object.
     * @param name The name of the object.
     * @param object The object to be written.
     */
    default void writeCompoundObject(String name, CompoundObject object) throws IOException {
        this.writeNamedObject(name, object);
    }

    /**
     * Writes an array of compound objects.
     * @param name The name of the array.
     * @param array The array to be written.
     */
    default void writeCompoundObjectArray(String name, CompoundObject[] array) throws IOException {
        this.writeNamedObjectArray(name, array);
    }

    /**
     * Writes an array of compound objects.
     * @param name The name of the list.
     * @param list The list to be written.
     */
    default void writeCompoundObjectList(String name, @NotNull List<CompoundObject> list) throws IOException {
        writeCompoundObjectArray(name, list.toArray(new CompoundObject[0]));
    }

    /**
     * Writes a raw-object using a pre-registered serializer.
     * @param name The name of the object.
     * @param object The object to be written.
     */
    <T extends IDataStreamSerializable> void writeRawObject(String name, T object) throws IOException;

    /**
     * Writes an array of raw-objects using a pre-registered serializer.
     * @param name The name of the array.
     * @param array The array to be written.
     */
    <T extends IDataStreamSerializable> void writeRawObjectArray(String name, T[] array) throws IOException;

    /**
     * Writes an array of raw-objects using a pre-registered serializer.
     * @param name The name of the list.
     * @param list The list to be written.
     */
    default <T extends IDataStreamSerializable> void writeRawObjectList(String name, @NotNull List<T> list) throws IOException {
        writeRawObjectArray(name, list.toArray(new IDataStreamSerializable[0]));
    }

    /**
     * Writes a named-object using a pre-registered serializer.
     * @param name The name of the object.
     * @param object The object to be written.
     */
    <T extends INamedSerializable> void writeNamedObject(String name, T object) throws IOException;

    /**
     * Writes an array of named-objects using a pre-registered serializer.
     * @param name The name of the array.
     * @param array The array to be written.
     */
    <T extends INamedSerializable> void writeNamedObjectArray(String name, T[] array) throws IOException;

    /**
     * Writes a list of named-objects using a pre-registered serializer.
     * @param name The name of the list.
     * @param list The list to be written.
     */
    default <T extends INamedSerializable> void writeNamedObjectList(String name, @NotNull List<T> list) throws IOException {
        writeNamedObjectArray(name, list.toArray(new INamedSerializable[0]));
    }

    /**
     * Writes a keyless-object using a pre-registered serializer.
     * @param name The name of the object.
     * @param object The object to be written.
     */
    <T extends IKeylessSerializable> void writeKeylessObject(String name, T object) throws IOException;

    /**
     * Writes an array of keyless-objects using a pre-registered serializer.
     * @param name The name of the array.
     * @param array The array to be written.
     */
    <T extends IKeylessSerializable> void writeKeylessObjectArray(String name, T[] array) throws IOException;

    /**
     * Writes a list of keyless-objects using a pre-registered serializer.
     * @param name The name of the list.
     * @param list The list to be written.
     */
    default <T extends IKeylessSerializable> void writeKeylessObjectList(String name, @NotNull List<T> list) throws IOException {
        writeKeylessObjectArray(name, list.toArray(new IKeylessSerializable[0]));
    }

    /**
     * Writes a custom object using a pre-registered serializer.
     * @param name The name of the object.
     * @param object The object to be written.
     */
    <T> void writeCustomObject(String name, T object) throws IOException;

    /**
     * Writes an array of custom-objects using a pre-registered serializer.
     * @param name The name of the array.
     * @param array The array to be written.
     */
    <T> void writeCustomObjectArray(String name, T[] array) throws IOException;

    /**
     * Writes a list of custom-objects using a pre-registered serializer.
     * @param name The name of the list.
     * @param list The list to be written.
     */
    default <T> void writeCustomObjectList(String name, @NotNull List<T> list) throws IOException {
        writeCustomObjectArray(name, list.toArray(new Object[0]));
    }

    /**
     * Creates a primitive byte array from the serialized data.
     */
    byte[] toBytes() throws IOException;

    /**
     * Creates a gzip compressed bytes from the serialized data.
     */
    default byte[] toCompressedBytes() throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        GZIPOutputStream stream1 = new GZIPOutputStream(stream);
        stream1.write(toBytes());
        stream1.close();
        return stream.toByteArray();
    }

    /**
     * Creates a base64 string from uncompressed bytes.
     */
    default String toBase64() throws IOException {
        return Base64.getEncoder().encodeToString(toBytes());
    }

    /**
     * Creates a base64 string from compressed bytes.
     */
    default String toCompressedBase64() throws IOException {
        return Base64.getEncoder().encodeToString(toCompressedBytes());
    }


}
