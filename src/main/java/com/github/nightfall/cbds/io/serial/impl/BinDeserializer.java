package com.github.nightfall.cbds.io.serial.impl;

import com.github.nightfall.cbds.io.CompoundObject;
import com.github.nightfall.cbds.io.serial.SerializationType;
import com.github.nightfall.cbds.io.custom.CustomDeserializer;
import com.github.nightfall.cbds.io.serial.api.IDeserializer;
import com.github.nightfall.cbds.io.obj.BinSerializable;
import com.github.nightfall.cbds.io.obj.RawDataSerializable;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.zip.GZIPInputStream;

public class BinDeserializer implements IDeserializer {

    DataInputStream input;
    ByteArrayInputStream byteStream;

    Map<String, Object> keyToValue;
    Map<String, SerializationType> keyToType;

    public static BinDeserializer fromBytes(byte[] bytes) throws IOException {
        return new BinDeserializer(bytes);
    }

    public static BinDeserializer fromBytes(Byte[] bytes) throws IOException {
        return fromBytes(readByteArrayAsPrimitive(bytes));
    }

    public static BinDeserializer fromCompressedBytes(byte[] bytes) throws IOException {
        return new BinDeserializer(new GZIPInputStream(new ByteArrayInputStream(bytes)).readAllBytes());
    }

    public static BinDeserializer fromCompressedBytes(Byte[] bytes) throws IOException {
        return fromCompressedBytes(readByteArrayAsPrimitive(bytes));
    }

    public BinDeserializer(byte[] bytes) throws IOException {
        byteStream = new ByteArrayInputStream(bytes);
        input = new DataInputStream(byteStream);

        keyToType = new HashMap<>();
        keyToValue = new HashMap<>();

        SerializationType type = null;
        while (true) {
            try {
                type = readType();
            } catch (EOFException ignore) {
                break;
            }
            String name = input.readUTF();

            Object obj = null;

            switch (type) {
                case BYTE -> obj = input.readByte();
                case BYTE_ARRAY -> obj = readArray(Byte[]::new, input::readByte);
                case SHORT -> obj = input.readShort();
                case SHORT_ARRAY -> obj = readArray(Short[]::new, input::readShort);
                case INT -> obj = input.readInt();
                case INT_ARRAY -> obj = readArray(Integer[]::new, input::readInt);
                case LONG -> obj = input.readLong();
                case LONG_ARRAY -> obj = readArray(Long[]::new, input::readLong);
                case FLOAT -> obj = input.readFloat();
                case FLOAT_ARRAY -> obj = readArray(Float[]::new, input::readFloat);
                case DOUBLE -> obj = input.readDouble();
                case DOUBLE_ARRAY -> obj = readArray(Double[]::new, input::readDouble);
                case BOOLEAN -> obj = input.readBoolean();
                case BOOLEAN_ARRAY -> obj = readArray(Boolean[]::new, input::readBoolean);
                case CHAR -> obj = input.readChar();
                case CHAR_ARRAY -> obj = readArray(Character[]::new, input::readChar);
                case STRING -> obj = input.readUTF();
                case STRING_ARRAY -> obj = readArray(String[]::new, input::readUTF);

                case CUSTOM_OBJECT, KEYED_OBJECT -> obj = newInstance(readArray(Byte[]::new, input::readByte));
                case KEYLESS_OBJECT -> obj = readArray(Byte[]::new, input::readByte);

                case CUSTOM_OBJECT_ARRAY, KEYED_OBJECT_ARRAY -> {
                    IDeserializer[] deserializers = new IDeserializer[input.readInt()];

                    for (int i = 0; i < deserializers.length; i++) {
                        readType(); // Gloss Over Element Type
                        int index = Integer.parseInt(input.readUTF()); // Read "Name" to index

                        deserializers[index] = newInstance(readArray(Byte[]::new, input::readByte));
                    }

                    obj = deserializers;
                }

                case KEYLESS_OBJECT_ARRAY -> {
                    Byte[][] deserializers = new Byte[input.readInt()][0];

                    for (int i = 0; i < deserializers.length; i++) {
                        readType(); // Gloss Over Element Type
                        int index = Integer.parseInt(input.readUTF()); // Read "Name" to index

                        deserializers[index] = readArray(Byte[]::new, input::readByte);
                    }

                    obj = deserializers;
                }
            }

            keyToType.put(name, type);
            keyToValue.put(name, obj);
        }
    }

    private <T> T[] readArray(Function<Integer, T[]> arrayCreator, ThrowableSupplier<T> supplier) throws IOException {
        T[] array = arrayCreator.apply(input.readInt());
        for (int i = 0; i < array.length; i++) {
            array[i] = supplier.get();
        }
        return array;
    }

    @Override
    public IDeserializer newInstance(byte[] bytes) throws IOException {
        return fromBytes(bytes);
    }

    @Override
    public IDeserializer newInstance(Byte[] bytes) throws IOException {
        return fromBytes(bytes);
    }

    @Override
    public IDeserializer newInstanceFromCompressed(byte[] bytes) throws IOException {
        return fromCompressedBytes(bytes);
    }

    @Override
    public IDeserializer newInstanceFromCompressed(Byte[] bytes) throws IOException {
        return fromCompressedBytes(bytes);
    }

    public byte readByte(String name) {
        return (byte) keyToValue.get(name);
    }

    public Byte[] readByteArray(String name) {
        return (Byte[]) keyToValue.get(name);
    }

    public byte[] readByteArrayAsPrimitive(String name) {
        Byte[] array = (Byte[]) keyToValue.get(name);
        byte[] pArray = new byte[array.length];
        for (int i = 0; i < pArray.length; i++) pArray[i] = array[i];
        return pArray;
    }

    public short readShort(String name) {
        return (short) keyToValue.get(name);
    }

    public Short[] readShortArray(String name) {
        return (Short[]) keyToValue.get(name);
    }

    public short[] readShortArrayAsPrimitive(String name) {
        Short[] array = (Short[]) keyToValue.get(name);
        short[] pArray = new short[array.length];
        for (int i = 0; i < pArray.length; i++) pArray[i] = array[i];
        return pArray;
    }

    public int readInt(String name) {
        return (int) keyToValue.get(name);
    }

    public Integer[] readIntArray(String name) {
        return (Integer[]) keyToValue.get(name);
    }

    public int[] readIntArrayAsPrimitive(String name) {
        Integer[] array = (Integer[]) keyToValue.get(name);
        int[] pArray = new int[array.length];
        for (int i = 0; i < pArray.length; i++) pArray[i] = array[i];
        return pArray;
    }

    public long readLong(String name) {
        return (long) keyToValue.get(name);
    }

    public Long[] readLongArray(String name) {
        return (Long[]) keyToValue.get(name);
    }

    public long[] readLongArrayAsPrimitive(String name) {
        Long[] array = (Long[]) keyToValue.get(name);
        long[] pArray = new long[array.length];
        for (int i = 0; i < pArray.length; i++) pArray[i] = array[i];
        return pArray;
    }

    public float readFloat(String name) {
        return (float) keyToValue.get(name);
    }

    public Float[] readFloatArray(String name) {
        return (Float[]) keyToValue.get(name);
    }

    public float[] readFloatArrayAsPrimitive(String name) {
        Float[] array = (Float[]) keyToValue.get(name);
        float[] pArray = new float[array.length];
        for (int i = 0; i < pArray.length; i++) pArray[i] = array[i];
        return pArray;
    }

    public double readDouble(String name) {
        return (double) keyToValue.get(name);
    }

    public Double[] readDoubleArray(String name) {
        return (Double[]) keyToValue.get(name);
    }

    public double[] readDoubleArrayAsPrimitive(String name) {
        Double[] array = (Double[]) keyToValue.get(name);
        double[] pArray = new double[array.length];
        for (int i = 0; i < pArray.length; i++) pArray[i] = array[i];
        return pArray;
    }

    public boolean readBoolean(String name) {
        return (boolean) keyToValue.get(name);
    }

    public Boolean[] readBooleanArray(String name) {
        return (Boolean[]) keyToValue.get(name);
    }

    public boolean[] readBooleanArrayAsPrimitive(String name) {
        Boolean[] array = (Boolean[]) keyToValue.get(name);
        boolean[] pArray = new boolean[array.length];
        for (int i = 0; i < pArray.length; i++) pArray[i] = array[i];
        return pArray;
    }

    public char readChar(String name) {
        return (char) keyToValue.get(name);
    }

    public Character[] readCharArray(String name) {
        return (Character[]) keyToValue.get(name);
    }

    public char[] readCharArrayAsPrimitive(String name) {
        Character[] array = (Character[]) keyToValue.get(name);
        char[] pArray = new char[array.length];
        for (int i = 0; i < pArray.length; i++) pArray[i] = array[i];
        return pArray;
    }

    public String readString(String name) {
        return (String) keyToValue.get(name);
    }

    public String[] readStringArray(String name) {
        return (String[]) keyToValue.get(name);
    }

    public CompoundObject readCompoundObject(String name) {
        return readObject(CompoundObject.class, name);
    }

    public CompoundObject[] readCompoundObjectArray(String name) {
        return readObjectArray(CompoundObject.class, name);
    }

    private static byte[] readByteArrayAsPrimitive(Byte[] array) {
        byte[] pArray = new byte[array.length];
        for (int i = 0; i < pArray.length; i++) pArray[i] = array[i];
        return pArray;
    }

    public <T extends RawDataSerializable> T readRawObject(Class<T> type, String name) {
        try {
            T obj = type.getDeclaredConstructor().newInstance();
            obj.read(new DataInputStream(new ByteArrayInputStream(readByteArrayAsPrimitive(name))));
            return obj;
        } catch (
                InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException
                | IOException e
        ) {
            return null;
        }
    }

    public <T extends RawDataSerializable> T[] readRawObjectArray(Class<T> type, String name) {
        try {
            Byte[][] objs = (Byte[][]) keyToValue.get(name);
            T[] t = (T[]) type.arrayType().getConstructor().newInstance(objs.length);
            for (int i = 0; i < t.length; i++) {
                T obj = type.getDeclaredConstructor().newInstance();
                obj.read(new DataInputStream(new ByteArrayInputStream(readByteArrayAsPrimitive(objs[i]))));
                t[i] = obj;
            }
            return t;
        } catch (
                InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException
                | IOException e
        ) {
            return null;
        }
    }

    public <T extends BinSerializable> T readObject(Class<T> type, String name) {
        try {
            T obj = type.getDeclaredConstructor().newInstance();
            obj.read((BinDeserializer) keyToValue.get(name));
            return obj;
        } catch (
                InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException
                | IOException e
        ) {
            return null;
        }
    }

    public <T extends BinSerializable> T[] readObjectArray(Class<T> type, String name) {
        IDeserializer[] objs = (IDeserializer[]) keyToValue.get(name);
        try {
            T[] t = (T[]) type.arrayType().getConstructor().newInstance(objs.length);

            for (int i = 0; i < t.length; i++) {
                T obj = null;
                try {
                    obj = type.getDeclaredConstructor().newInstance();
                    obj.read(objs[i]);
                    t[i] = obj;
                } catch (
                        InstantiationException | IllegalAccessException
                        | IllegalArgumentException | InvocationTargetException
                        | NoSuchMethodException | SecurityException
                        | IOException e
                ) {
                    objs[i] = null;
                }
            }
            return t;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            return null;
        }
    }

    public <T> T readCustomObject(Class<T> type, String name) {
        if (!DESERIALIZER_MAP.containsKey(type)) throw new RuntimeException("cannot deserialize class of type \"" + type.getName() + "\" due to it not having a registered deserializer.");

        try {
            return (T) DESERIALIZER_MAP.get(type).read((IDeserializer) keyToValue.get(name));
        } catch (
                IllegalArgumentException | SecurityException e
        ) {
            return null;
        }
    }

    public <T> T[] readCustomObjectArray(Class<T> type, String name) {
        if (!DESERIALIZER_MAP.containsKey(type)) throw new RuntimeException("cannot deserialize class of type \"" + type.getName() + "\" due to it not having a registered deserializer.");

        IDeserializer[] objs = (IDeserializer[]) keyToValue.get(name);
        CustomDeserializer<T> customDeserializer = (CustomDeserializer<T>) DESERIALIZER_MAP.get(type);

        try {
            T[] t = (T[]) type.arrayType().getConstructor().newInstance(objs.length);

            for (int i = 0; i < t.length; i++) {
                T obj = customDeserializer.read(objs[i]);
                t[i] = obj;
            }
            return t;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            return null;
        }
    }

    @Override
    public Object getObject(String name) {
        return keyToValue.get(name);
    }

    private SerializationType readType() throws IOException {
        return SerializationType.values()[input.readByte()];
    }

    private interface ThrowableSupplier<T> {

        T get() throws IOException;

    }

}
