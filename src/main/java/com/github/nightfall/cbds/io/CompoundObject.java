package com.github.nightfall.cbds.io;

import com.github.nightfall.cbds.io.custom.CustomDeserializer;
import com.github.nightfall.cbds.io.serial.api.IDeserializer;
import com.github.nightfall.cbds.io.serial.api.ISerializer;
import com.github.nightfall.cbds.io.obj.BinSerializable;
import com.github.nightfall.cbds.io.obj.RawDataSerializable;
import com.github.nightfall.cbds.io.serial.impl.BinDeserializer;
import com.github.nightfall.cbds.io.serial.impl.BinSerializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class CompoundObject implements BinSerializable, IDeserializer, ISerializer {

    Map<String, Object> OBJECT_MAP = new HashMap<>();

    public CompoundObject() {}

    public CompoundObject(byte[] bytes) throws IOException {
        read(new BinDeserializer(bytes));
    }

    public static CompoundObject fromBytes(byte[] bytes) throws IOException {
        return new CompoundObject(bytes);
    }

    public static CompoundObject fromBytes(Byte[] bytes) throws IOException {
        return fromBytes(readByteArrayAsPrimitive(bytes));
    }

    public static CompoundObject fromCompressedBytes(byte[] bytes) throws IOException {
        return new CompoundObject(new GZIPInputStream(new ByteArrayInputStream(bytes)).readAllBytes());
    }

    public static CompoundObject fromCompressedBytes(Byte[] bytes) throws IOException {
        return fromCompressedBytes(readByteArrayAsPrimitive(bytes));
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
        return (byte) OBJECT_MAP.get(name);
    }

    public Byte[] readByteArray(String name) {
        return (Byte[]) OBJECT_MAP.get(name);
    }

    public byte[] readByteArrayAsPrimitive(String name) {
        Byte[] array = (Byte[]) OBJECT_MAP.get(name);
        byte[] pArray = new byte[array.length];
        for (int i = 0; i < pArray.length; i++) pArray[i] = array[i];
        return pArray;
    }

    public short readShort(String name) {
        return (short) OBJECT_MAP.get(name);
    }

    public Short[] readShortArray(String name) {
        return (Short[]) OBJECT_MAP.get(name);
    }

    public short[] readShortArrayAsPrimitive(String name) {
        Short[] array = (Short[]) OBJECT_MAP.get(name);
        short[] pArray = new short[array.length];
        for (int i = 0; i < pArray.length; i++) pArray[i] = array[i];
        return pArray;
    }

    public int readInt(String name) {
        return (int) OBJECT_MAP.get(name);
    }

    public Integer[] readIntArray(String name) {
        return (Integer[]) OBJECT_MAP.get(name);
    }

    public int[] readIntArrayAsPrimitive(String name) {
        Integer[] array = (Integer[]) OBJECT_MAP.get(name);
        int[] pArray = new int[array.length];
        for (int i = 0; i < pArray.length; i++) pArray[i] = array[i];
        return pArray;
    }

    public long readLong(String name) {
        return (long) OBJECT_MAP.get(name);
    }

    public Long[] readLongArray(String name) {
        return (Long[]) OBJECT_MAP.get(name);
    }

    public long[] readLongArrayAsPrimitive(String name) {
        Long[] array = (Long[]) OBJECT_MAP.get(name);
        long[] pArray = new long[array.length];
        for (int i = 0; i < pArray.length; i++) pArray[i] = array[i];
        return pArray;
    }

    public float readFloat(String name) {
        return (float) OBJECT_MAP.get(name);
    }

    public Float[] readFloatArray(String name) {
        return (Float[]) OBJECT_MAP.get(name);
    }

    public float[] readFloatArrayAsPrimitive(String name) {
        Float[] array = (Float[]) OBJECT_MAP.get(name);
        float[] pArray = new float[array.length];
        for (int i = 0; i < pArray.length; i++) pArray[i] = array[i];
        return pArray;
    }

    public double readDouble(String name) {
        return (double) OBJECT_MAP.get(name);
    }

    public Double[] readDoubleArray(String name) {
        return (Double[]) OBJECT_MAP.get(name);
    }

    public double[] readDoubleArrayAsPrimitive(String name) {
        Double[] array = (Double[]) OBJECT_MAP.get(name);
        double[] pArray = new double[array.length];
        for (int i = 0; i < pArray.length; i++) pArray[i] = array[i];
        return pArray;
    }

    public boolean readBoolean(String name) {
        return (boolean) OBJECT_MAP.get(name);
    }

    public Boolean[] readBooleanArray(String name) {
        return (Boolean[]) OBJECT_MAP.get(name);
    }

    public boolean[] readBooleanArrayAsPrimitive(String name) {
        Boolean[] array = (Boolean[]) OBJECT_MAP.get(name);
        boolean[] pArray = new boolean[array.length];
        for (int i = 0; i < pArray.length; i++) pArray[i] = array[i];
        return pArray;
    }

    public char readChar(String name) {
        return (char) OBJECT_MAP.get(name);
    }

    public Character[] readCharArray(String name) {
        return (Character[]) OBJECT_MAP.get(name);
    }

    public char[] readCharArrayAsPrimitive(String name) {
        Character[] array = (Character[]) OBJECT_MAP.get(name);
        char[] pArray = new char[array.length];
        for (int i = 0; i < pArray.length; i++) pArray[i] = array[i];
        return pArray;
    }

    public String readString(String name) {
        return (String) OBJECT_MAP.get(name);
    }

    public String[] readStringArray(String name) {
        return (String[]) OBJECT_MAP.get(name);
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
        Object o = OBJECT_MAP.get(name);

        if (type.isAssignableFrom(o.getClass())) return (T) o;

        // If object was not already processed run this
        try {
            T obj = type.getDeclaredConstructor().newInstance();
            obj.read(new DataInputStream(new ByteArrayInputStream(readByteArrayAsPrimitive((Byte[]) o))));
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
        Object o = OBJECT_MAP.get(name);

        if (type.arrayType().isAssignableFrom(o.getClass())) return (T[]) o;

        // If object was not already processed run this
        try {
            Byte[][] objs = (Byte[][]) OBJECT_MAP.get(name);
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
        Object o = OBJECT_MAP.get(name);

        if (type.isAssignableFrom(o.getClass())) return (T) o;

        // If object was not already processed run this
        try {
            T obj = type.getDeclaredConstructor().newInstance();
            obj.read((BinDeserializer) o);
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
        Object o = OBJECT_MAP.get(name);

        if (type.arrayType().isAssignableFrom(o.getClass())) return (T[]) o;

        // If object array was not already processed run this
        BinDeserializer[] objs = (BinDeserializer[]) o;
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

            OBJECT_MAP.put(name, t);
            return t;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            return null;
        }
    }

    @Override
    public <T> T readCustomObject(Class<T> type, String name) {
        if (!IDeserializer.hasDeserializer(type)) throw new RuntimeException("cannot deserialize class of type \"" + type.getName() + "\" due to it not having a registered deserializer.");

        Object o = getObject(name);

        if (type.isAssignableFrom(o.getClass())) return (T) o;

        try {
            return IDeserializer.getDeserializer(type).read((IDeserializer) o);
        } catch (
                IllegalArgumentException | SecurityException e
        ) {
            return null;
        }
    }

    @Override
    public <T> T[] readCustomObjectArray(Class<T> type, String name) {
        if (!IDeserializer.hasDeserializer(type)) throw new RuntimeException("cannot deserialize class of type \"" + type.getName() + "\" due to it not having a registered deserializer.");

        Object o = OBJECT_MAP.get(name);

        if (type.arrayType().isAssignableFrom(o.getClass())) return (T[]) o;

        // If object array was not already processed run this
        IDeserializer[] objs = (IDeserializer[]) o;
        CustomDeserializer<T> customDeserializer = IDeserializer.getDeserializer(type);

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
        return OBJECT_MAP.get(name);
    }

    @Override
    public void read(IDeserializer deserializer) throws IOException {
        String[] keys = deserializer.readStringArray("keys");

        IDeserializer miniDeserializer = deserializer.newInstance(deserializer.readByteArrayAsPrimitive("data"));

        for (String key : keys) {
            OBJECT_MAP.put(key, miniDeserializer.getObject(key));
        }

    }

    @Override
    public ISerializer newInstance() throws IOException {
        return new CompoundObject();
    }

    public void writeByte(String name, byte i) {
        OBJECT_MAP.put(name, i);
    }

    public void writeByteArray(String name, byte[] array) {
        OBJECT_MAP.put(name, array);
    }

    public void writeByteArray(String name, Byte[] array) {
        OBJECT_MAP.put(name, array);
    }

    public void writeShort(String name, short i) {
        OBJECT_MAP.put(name, i);
    }

    public void writeShortArray(String name, short[] array) {
        OBJECT_MAP.put(name, array);
    }

    public void writeShortArray(String name, Short[] array) {
        OBJECT_MAP.put(name, array);
    }

    public void writeInt(String name, int i) {
        OBJECT_MAP.put(name, i);
    }

    public void writeIntArray(String name, int[] array) {
        OBJECT_MAP.put(name, array);
    }

    public void writeIntArray(String name, Integer[] array) {
        OBJECT_MAP.put(name, array);
    }

    public void writeLong(String name, long i) {
        OBJECT_MAP.put(name, i);
    }

    public void writeLongArray(String name, long[] array) {
        OBJECT_MAP.put(name, array);
    }

    public void writeLongArray(String name, Long[] array) {
        OBJECT_MAP.put(name, array);
    }

    public void writeFloat(String name, float i) {
        OBJECT_MAP.put(name, i);
    }

    public void writeFloatArray(String name, float[] array) {
        OBJECT_MAP.put(name, array);
    }

    public void writeFloatArray(String name, Float[] array) {
        OBJECT_MAP.put(name, array);
    }

    public void writeDouble(String name, double i) {
        OBJECT_MAP.put(name, i);
    }

    public void writeDoubleArray(String name, double[] array) {
        OBJECT_MAP.put(name, array);
    }

    public void writeDoubleArray(String name, Double[] array) {
        OBJECT_MAP.put(name, array);
    }

    public void writeBoolean(String name, boolean b) {
        OBJECT_MAP.put(name, b);
    }

    public void writeBooleanArray(String name, boolean[] array) {
        OBJECT_MAP.put(name, array);
    }

    public void writeBooleanArray(String name, Boolean[] array) {
        OBJECT_MAP.put(name, array);
    }

    public void writeChar(String name, char c) {
        OBJECT_MAP.put(name, c);
    }

    public void writeCharArray(String name, char[] array) {
        OBJECT_MAP.put(name, array);
    }

    public void writeCharArray(String name, Character[] array) {
        OBJECT_MAP.put(name, array);
    }

    public void writeString(String name, String v) {
        OBJECT_MAP.put(name, v);
    }

    public void writeStringArray(String name, String[] array) {
        OBJECT_MAP.put(name, array);
    }

    public void writeCompoundObject(String name, CompoundObject object) throws IOException {
        writeObject(name, object);
    }

    public void writeCompoundObjectArray(String name, CompoundObject[] array) throws IOException {
        writeObjectArray(name, array);
    }

    public <T extends RawDataSerializable> void writeRawObject(String name, T object) {
        OBJECT_MAP.put(name, object);
    }

    public <T extends RawDataSerializable> void writeRawObjectArray(String name, T[] array) {
        OBJECT_MAP.put(name, array);
    }

    public <T extends BinSerializable> void writeObject(String name, T object) {
        OBJECT_MAP.put(name, object);
    }

    public <T extends BinSerializable> void writeObjectArray(String name, T[] array) {
        OBJECT_MAP.put(name, array);
    }

    @Override
    public <T> void writeCustomObject(String name, T object) throws IOException {

    }

    @Override
    public <T> void writeCustomObjectArray(String name, T[] array) throws IOException {

    }

    public int getObjectCount() {
        return OBJECT_MAP.size();
    }

    public String[] getKeys() {
        return OBJECT_MAP.keySet().toArray(new String[0]);
    }

    @Override
    public void write(ISerializer serializer) throws IOException {

        serializer.writeStringArray("keys", OBJECT_MAP.keySet().toArray(new String[0]));

        ISerializer miniSerializer = serializer.newInstance();

        for (String key : OBJECT_MAP.keySet()) {
            Object o = OBJECT_MAP.get(key);

            /*
            * To whomever reads this, I am very sorry about all the if statements, it was necessary to make this work.
            */

            if (o instanceof Byte) miniSerializer.writeByte(key, ((Byte) o));
            if (o instanceof byte[]) miniSerializer.writeByteArray(key, ((byte[]) o));
            if (o instanceof Byte[]) miniSerializer.writeByteArray(key, ((Byte[]) o));

            if (o instanceof Short) miniSerializer.writeShort(key, ((Short) o));
            if (o instanceof short[]) miniSerializer.writeShortArray(key, ((short[]) o));
            if (o instanceof Short[]) miniSerializer.writeShortArray(key, ((Short[]) o));

            if (o instanceof Long) miniSerializer.writeLong(key, ((Long) o));
            if (o instanceof long[]) miniSerializer.writeLongArray(key, ((long[]) o));
            if (o instanceof Long[]) miniSerializer.writeLongArray(key, ((Long[]) o));

            if (o instanceof Integer) miniSerializer.writeInt(key, ((Integer) o));
            if (o instanceof int[]) miniSerializer.writeIntArray(key, ((int[]) o));
            if (o instanceof Integer[]) miniSerializer.writeIntArray(key, ((Integer[]) o));

            if (o instanceof Long) miniSerializer.writeLong(key, ((Long) o));
            if (o instanceof long[]) miniSerializer.writeLongArray(key, ((long[]) o));
            if (o instanceof Long[]) miniSerializer.writeLongArray(key, ((Long[]) o));

            if (o instanceof Float) miniSerializer.writeFloat(key, ((Float) o));
            if (o instanceof float[]) miniSerializer.writeFloatArray(key, ((float[]) o));
            if (o instanceof Float[]) miniSerializer.writeFloatArray(key, ((Float[]) o));

            if (o instanceof Double) miniSerializer.writeDouble(key, ((Double) o));
            if (o instanceof double[]) miniSerializer.writeDoubleArray(key, ((double[]) o));
            if (o instanceof Double[]) miniSerializer.writeDoubleArray(key, ((Double[]) o));

            if (o instanceof Boolean) miniSerializer.writeBoolean(key, ((Boolean) o));
            if (o instanceof boolean[]) miniSerializer.writeBooleanArray(key, ((boolean[]) o));
            if (o instanceof Boolean[]) miniSerializer.writeBooleanArray(key, ((Boolean[]) o));

            if (o instanceof String) miniSerializer.writeString(key, ((String) o));
            if (o instanceof String[]) miniSerializer.writeStringArray(key, ((String[]) o));

            if (o instanceof BinSerializable) miniSerializer.writeObject(key, (BinSerializable) o);
            if (o instanceof BinSerializable[]) miniSerializer.writeObjectArray(key, (BinSerializable[]) o);
            if (o instanceof RawDataSerializable) miniSerializer.writeRawObject(key, (RawDataSerializable) o);
            if (o instanceof RawDataSerializable[]) miniSerializer.writeRawObjectArray(key, (RawDataSerializable[]) o);
        }

        serializer.writeByteArray("data", miniSerializer.toBytes());
    }

    public byte[] toBytes() throws IOException {
        BinSerializer serializer = new BinSerializer();
        serializer.writeCompoundObject("cbds_compound_object", this);
        return serializer.toBytes();
    }

    public byte[] toCompressedBytes() throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        GZIPOutputStream stream1 = new GZIPOutputStream(stream);
        stream1.write(toBytes());
        stream1.close();
        return stream.toByteArray();
    }

    public byte[] toBase64() throws IOException {
        return Base64.getEncoder().encode(toBytes());
    }

    public byte[] toCompressedBase64() throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        GZIPOutputStream stream1 = new GZIPOutputStream(stream);
        stream1.write(Base64.getEncoder().encode(toCompressedBytes()));
        stream1.close();
        return stream.toByteArray();
    }

    public byte[] toBytes(ISerializer serializer) throws IOException {
        serializer.writeCompoundObject("cbds_compound_object", this);
        return serializer.toBytes();
    }

    public byte[] toCompressedBytes(ISerializer serializer) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        GZIPOutputStream stream1 = new GZIPOutputStream(stream);
        stream1.write(toBytes(serializer));
        stream1.close();
        return stream.toByteArray();
    }

    public byte[] toBase64(ISerializer serializer) throws IOException {
        return Base64.getEncoder().encode(toBytes(serializer));
    }

    public byte[] toCompressedBase64(ISerializer serializer) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        GZIPOutputStream stream1 = new GZIPOutputStream(stream);
        stream1.write(Base64.getEncoder().encode(toCompressedBytes(serializer)));
        stream1.close();
        return stream.toByteArray();
    }

}
