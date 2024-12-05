package com.github.nightfall.cbds.io.serial.impl;

import com.github.nightfall.cbds.io.CompoundObject;
import com.github.nightfall.cbds.io.serial.api.INamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.IUnNamedDeserializer;
import com.github.nightfall.cbds.io.serial.obj.IDataStreamSerializable;
import com.github.nightfall.cbds.io.serial.obj.INamedSerializable;
import com.github.nightfall.cbds.io.serial.obj.IUnNamedSerializable;
import com.github.nightfall.cbds.util.NativeArrayUtil;
import com.github.nightfall.cbds.util.ThrowableSupplier;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.function.Function;
import java.util.zip.GZIPInputStream;

public class UnNamedBinaryDeserializer implements IUnNamedDeserializer {

    DataInputStream input;
    ByteArrayInputStream byteStream;

    Map<String, Object> keyToValue;

    public static UnNamedBinaryDeserializer fromBytes(byte[] bytes) throws IOException {
        return new UnNamedBinaryDeserializer(bytes);
    }

    public static UnNamedBinaryDeserializer fromBytes(Byte[] bytes) throws IOException {
        return fromBytes(NativeArrayUtil.toNativeArray(bytes));
    }

    public static UnNamedBinaryDeserializer fromBytes(byte[] bytes, boolean isCompressed) throws IOException {
        if (isCompressed) return new UnNamedBinaryDeserializer(new GZIPInputStream(new ByteArrayInputStream(bytes)).readAllBytes());
        else return fromBytes(bytes);
    }

    public static UnNamedBinaryDeserializer fromBytes(Byte[] bytes, boolean isCompressed) throws IOException {
        return fromBytes(NativeArrayUtil.toNativeArray(bytes), isCompressed);
    }

    public UnNamedBinaryDeserializer(byte[] bytes) throws IOException {
        byteStream = new ByteArrayInputStream(bytes);
        input = new DataInputStream(byteStream);
    }

    @Override
    public IUnNamedDeserializer newInstance(byte[] bytes, boolean isCompressed) throws IOException {
        return fromBytes(bytes, isCompressed);
    }

    private <T> T[] readArray(Function<Integer, T[]> arrayCreator, ThrowableSupplier<T> supplier) throws IOException {
        T[] array = arrayCreator.apply(input.readInt());
        for (int i = 0; i < array.length; i++) {
            array[i] = supplier.get();
        }
        return array;
    }

    @Override
    public byte readByte() throws IOException {
        return input.readByte();
    }

    @Override
    public Byte[] readByteArray() throws IOException {
        return readArray(Byte[]::new, input::readByte);
    }

    @Override
    public short readShort() throws IOException {
        return input.readShort();
    }

    @Override
    public Short[] readShortArray() throws IOException {
        return readArray(Short[]::new, input::readShort);
    }

    @Override
    public int readInt() throws IOException {
        return input.readInt();
    }

    @Override
    public Integer[] readIntArray() throws IOException {
        return readArray(Integer[]::new, input::readInt);
    }

    @Override
    public long readLong() throws IOException {
        return input.readLong();
    }

    @Override
    public Long[] readLongArray() throws IOException {
        return readArray(Long[]::new, input::readLong);
    }

    @Override
    public float readFloat() throws IOException {
        return input.readFloat();
    }

    @Override
    public Float[] readFloatArray() throws IOException {
        return readArray(Float[]::new, input::readFloat);
    }

    @Override
    public double readDouble() throws IOException {
        return input.readDouble();
    }

    @Override
    public Double[] readDoubleArray() throws IOException {
        return readArray(Double[]::new, input::readDouble);
    }

    @Override
    public boolean readBoolean() throws IOException {
        return input.readBoolean();
    }

    @Override
    public Boolean[] readBooleanArray() throws IOException {
        return readArray(Boolean[]::new, input::readBoolean);
    }

    @Override
    public char readChar() throws IOException {
        return input.readChar();
    }

    @Override
    public Character[] readCharArray() throws IOException {
        return readArray(Character[]::new, input::readChar);
    }

    @Override
    public String readString() throws IOException {
        return input.readUTF();
    }

    @Override
    public String[] readStringArray() throws IOException {
        return readArray(String[]::new, input::readUTF);
    }

    @Override
    public CompoundObject readCompoundObject() throws IOException {
        return readNamedObject(CompoundObject.class);
    }

    @Override
    public CompoundObject[] readCompoundObjectArray() throws IOException {
        return readNamedObjectArray(CompoundObject.class);
    }

    @Override
    public <T extends IDataStreamSerializable> T readRawObject(Class<T> type) throws IOException {
        try {
            T obj = type.getDeclaredConstructor().newInstance();
            obj.read(new DataInputStream(new ByteArrayInputStream(readByteArrayAsPrimitive())));
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

    @Override
    public <T extends IDataStreamSerializable> T[] readRawObjectArray(Class<T> type) throws IOException {
        int length = readInt();
        T[] t = (T[]) Array.newInstance(type, length);
        for (int i = 0; i < t.length; i++) {
            t[i] = readRawObject(type);
        }
        return t;
    }

    @Override
    public <T extends INamedSerializable> T readNamedObject(Class<T> type) throws IOException {
        try {
            T obj = type.getDeclaredConstructor().newInstance();
            obj.read(INamedDeserializer.createDefault(readByteArrayAsPrimitive(), false));
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

    @Override
    public <T extends INamedSerializable> T[] readNamedObjectArray(Class<T> type) throws IOException {
        int length = readInt();
        T[] t = (T[]) Array.newInstance(type, length);
        for (int i = 0; i < t.length; i++) {
            t[i] = readNamedObject(type);
        }
        return t;
    }

    @Override
    public <T extends IUnNamedSerializable> T readUnNamedObject(Class<T> type) throws IOException {
        try {
            T obj = type.getDeclaredConstructor().newInstance();
            obj.read(newInstance(readByteArrayAsPrimitive(), false));
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

    @Override
    public <T extends IUnNamedSerializable> T[] readUnNamedObjectArray(Class<T> type) throws IOException {
        int length = readInt();
        T[] t = (T[]) Array.newInstance(type, length);
        for (int i = 0; i < t.length; i++) {
            t[i] = readUnNamedObject(type);
        }
        return t;
    }

    @Override
    public <T> T readCustomObject(Class<T> type) throws IOException {
        if (!UNNAMED_DESERIALIZER_MAP.containsKey(type)) throw new RuntimeException("cannot deserialize class of type \"" + type.getName() + "\" due to it not having a registered deserializer.");

        try {
            return (T) UNNAMED_DESERIALIZER_MAP.get(type).read(newInstance(readByteArrayAsPrimitive()));
        } catch (
                IllegalArgumentException | SecurityException e
        ) {
            return null;
        }
    }

    @Override
    public <T> T[] readCustomObjectArray(Class<T> type) throws IOException {
        if (!UNNAMED_DESERIALIZER_MAP.containsKey(type)) throw new RuntimeException("cannot deserialize class of type \"" + type.getName() + "\" due to it not having a registered deserializer.");

        int length = readInt();
        T[] t = (T[]) Array.newInstance(type, length);
        for (int i = 0; i < t.length; i++) {
            t[i] = readCustomObject(type);
        }
        return t;
    }

}
