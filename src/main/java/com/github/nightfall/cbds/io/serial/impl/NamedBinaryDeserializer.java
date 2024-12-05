package com.github.nightfall.cbds.io.serial.impl;

import com.github.nightfall.cbds.io.CompoundObject;
import com.github.nightfall.cbds.io.serial.SerializationType;
import com.github.nightfall.cbds.io.custom.INamedCustomSerializable;
import com.github.nightfall.cbds.io.serial.api.INamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.IUnNamedDeserializer;
import com.github.nightfall.cbds.io.serial.obj.INamedSerializable;
import com.github.nightfall.cbds.io.serial.obj.IDataStreamSerializable;
import com.github.nightfall.cbds.io.serial.obj.IUnNamedSerializable;
import com.github.nightfall.cbds.util.NativeArrayUtil;
import com.github.nightfall.cbds.util.ThrowableSupplier;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;

import static com.github.nightfall.cbds.io.serial.SerializationType.*;

public class NamedBinaryDeserializer implements INamedDeserializer {

    DataInputStream input;
    ByteArrayInputStream byteStream;

    Map<String, Object> keyToValue;

    public static NamedBinaryDeserializer fromBytes(byte[] bytes) throws IOException {
        return new NamedBinaryDeserializer(bytes);
    }

    public static NamedBinaryDeserializer fromBytes(Byte[] bytes) throws IOException {
        return fromBytes(NativeArrayUtil.toNativeArray(bytes));
    }

    public static NamedBinaryDeserializer fromBytes(byte[] bytes, boolean isCompressed) throws IOException {
        if (isCompressed) return new NamedBinaryDeserializer(new GZIPInputStream(new ByteArrayInputStream(bytes)).readAllBytes());
        else return fromBytes(bytes);
    }

    public static NamedBinaryDeserializer fromBytes(Byte[] bytes, boolean isCompressed) throws IOException {
        return fromBytes(NativeArrayUtil.toNativeArray(bytes), isCompressed);
    }

    @SuppressWarnings("ReassignedVariable")
    public NamedBinaryDeserializer(byte[] bytes) throws IOException {
        byteStream = new ByteArrayInputStream(bytes);
        input = new DataInputStream(byteStream);

        keyToValue = new HashMap<>();

        AtomicBoolean isUsingStringSchema = new AtomicBoolean();
        List<String> schema = new ArrayList<>();

        SerializationType type = null;
        while (true) {
            try {
                type = readType();
            } catch (EOFException ignore) {
                break;
            }
            String name = readString();

            Object obj = null;

            switch (type) {
                case STRING_SCHEMA_sBYTE, STRING_SCHEMA_sSHORT, STRING_SCHEMA_sINT -> {
                    isUsingStringSchema.set(true);
                    schema.addAll(List.of(readArray(String[]::new, this::readString, STRING_SCHEMA_sBYTE, type)));
                    continue;
                }
                case BYTE -> obj = input.readByte();
                case BYTE_ARRAY_sBYTE, BYTE_ARRAY_sSHORT, BYTE_ARRAY_sINT -> obj = readArray(Byte[]::new, input::readByte, BYTE_ARRAY_sBYTE, type);
                case SHORT_sBYTE, SHORT_sSHORT -> obj = getNumber(SHORT_sBYTE, type);
                case SHORT_ARRAY_sBYTE, SHORT_ARRAY_sSHORT, SHORT_ARRAY_sINT -> obj = readArray(Short[]::new, input::readShort, STRING_ARRAY_sBYTE, type);
                case INT_sBYTE, INT_sSHORT, INT_sINT -> obj = getNumber(INT_sBYTE, type);
                case INT_ARRAY_sBYTE, INT_ARRAY_sSHORT, INT_ARRAY_sINT -> obj = readArray(Integer[]::new, input::readInt, INT_ARRAY_sBYTE, type);
                case LONG_sBYTE, LONG_sSHORT, LONG_sINT, LONG_sLONG -> obj = getNumber(LONG_sBYTE, type);
                case LONG_ARRAY_sBYTE, LONG_ARRAY_sSHORT, LONG_ARRAY_sINT -> obj = readArray(Long[]::new, input::readLong, LONG_ARRAY_sBYTE, type);
                case FLOAT -> obj = input.readFloat();
                case FLOAT_ARRAY_sBYTE, FLOAT_ARRAY_sSHORT, FLOAT_ARRAY_sINT -> obj = readArray(Float[]::new, input::readFloat, FLOAT_ARRAY_sBYTE, type);
                case DOUBLE -> obj = input.readDouble();
                case DOUBLE_ARRAY_sBYTE, DOUBLE_ARRAY_sSHORT, DOUBLE_ARRAY_sINT -> obj = readArray(Double[]::new, input::readDouble, DOUBLE_ARRAY_sBYTE, type);
                case BOOLEAN -> obj = input.readBoolean();
                case BOOLEAN_ARRAY_sBYTE, BOOLEAN_ARRAY_sSHORT, BOOLEAN_ARRAY_sINT -> obj = readArray(Boolean[]::new, input::readBoolean, BOOLEAN_ARRAY_sBYTE, type);
                case CHAR -> obj = input.readChar();
                case CHAR_ARRAY_sBYTE, CHAR_ARRAY_sSHORT, CHAR_ARRAY_sINT -> obj = readArray(Character[]::new, input::readChar, CHAR_ARRAY_sBYTE, type);
                case STRING_REGULAR -> obj = readString();
                case STRING_sBYTE, STRING_sSHORT, STRING_sINT -> obj = schema.get(getIndex(STRING_sBYTE, type));
                case STRING_ARRAY_sBYTE, STRING_ARRAY_sSHORT, STRING_ARRAY_sINT -> obj = readArray(String[]::new, () -> {
                    if (isUsingStringSchema.get()) return schema.get(input.readInt());
                    else return readString();
                }, STRING_ARRAY_sBYTE, type);

                case RAW_OBJECT_sBYTE, RAW_OBJECT_sSHORT, RAW_OBJECT_sINT -> obj = readArray(Byte[]::new, input::readByte, RAW_OBJECT_sBYTE, type);
                case NAMED_OBJECT_sBYTE, NAMED_OBJECT_sSHORT, NAMED_OBJECT_sINT -> obj = newInstance(readArray(Byte[]::new, input::readByte, NAMED_OBJECT_sBYTE, type));
                case UNNAMED_OBJECT_sBYTE, UNNAMED_OBJECT_sSHORT, UNNAMED_OBJECT_sINT -> obj = IUnNamedDeserializer.createDefault(readArray(Byte[]::new, input::readByte, UNNAMED_OBJECT_sBYTE, type), false);
                case CUSTOM_OBJECT_sBYTE, CUSTOM_OBJECT_sSHORT, CUSTOM_OBJECT_sINT -> obj = newInstance(readArray(Byte[]::new, input::readByte, CUSTOM_OBJECT_sBYTE, type));

                case CUSTOM_OBJECT_ARRAY_sBYTE, CUSTOM_OBJECT_ARRAY_sSHORT, CUSTOM_OBJECT_ARRAY_sINT -> {
                    obj = processObjectArray(CUSTOM_OBJECT_sBYTE, CUSTOM_OBJECT_ARRAY_sBYTE, type);
                }

                case NAMED_OBJECT_ARRAY_sBYTE, NAMED_OBJECT_ARRAY_sSHORT, NAMED_OBJECT_ARRAY_sINT -> {
                    obj = processObjectArray(NAMED_OBJECT_sBYTE, NAMED_OBJECT_ARRAY_sBYTE, type);
                }

                case UNNAMED_OBJECT_ARRAY_sBYTE, UNNAMED_OBJECT_ARRAY_sSHORT, UNNAMED_OBJECT_ARRAY_sINT -> {
                    IUnNamedDeserializer[] deserializers = new IUnNamedDeserializer[getIndex(UNNAMED_OBJECT_ARRAY_sBYTE, type)];

                    for (int i = 0; i < deserializers.length; i++) {
                        SerializationType type0 = readType();

                        deserializers[i] = IUnNamedDeserializer.createDefault(readArray(Byte[]::new, input::readByte, UNNAMED_OBJECT_sBYTE, type0), false);
                    }

                    obj = deserializers;
                }

                case RAW_OBJECT_ARRAY_sBYTE, RAW_OBJECT_ARRAY_sSHORT, RAW_OBJECT_ARRAY_sINT -> {
                    Byte[][] deserializers = new Byte[input.readInt()][0];

                    for (int i = 0; i < deserializers.length; i++) {
                        SerializationType type0 = readType();

                        deserializers[i] = readArray(Byte[]::new, input::readByte, RAW_OBJECT_sBYTE, type0);
                    }

                    obj = deserializers;
                }
            }

            keyToValue.put(name, obj);
        }
    }


    private String readString() throws IOException {
        return input.readUTF();
    }

    private Object processObjectArray(SerializationType objBaseType, SerializationType baseType, SerializationType type) throws IOException {
        INamedDeserializer[] deserializers = new INamedDeserializer[getIndex(baseType, type)];

        for (int i = 0; i < deserializers.length; i++) {
            SerializationType type0 = readType();

            deserializers[i] = newInstance(readArray(Byte[]::new, input::readByte, objBaseType, type0));
        }

        return deserializers;
    }

    private <T> T[] readArray(Function<Integer, T[]> arrayCreator, ThrowableSupplier<T> supplier) throws IOException {
        T[] array = arrayCreator.apply(input.readInt());
        for (int i = 0; i < array.length; i++) {
            array[i] = supplier.get();
        }
        return array;
    }

    private Number getNumber(SerializationType baseType, SerializationType currentType) throws IOException {
        int ordinal = baseType.ordinal();
        int cOrdinal = currentType.ordinal();

        ThrowableSupplier<Number> getIndexMethod = input::readByte;

        switch (ordinal - cOrdinal) {
            case 1 -> getIndexMethod = input::readShort;
            case 2 -> getIndexMethod = input::readInt;
            case 3 -> getIndexMethod = input::readLong;
        }
        return getIndexMethod.get();
    }

    private Integer getIndex(SerializationType baseType, SerializationType currentType) throws IOException {
        int ordinal = baseType.ordinal();
        int cOrdinal = currentType.ordinal();

        ThrowableSupplier<Number> getIndexMethod = input::readByte;

        switch (ordinal - cOrdinal) {
            case 1 -> getIndexMethod = input::readShort;
            case 2 -> getIndexMethod = input::readInt;
            case 3 -> getIndexMethod = input::readLong;
        }
        return getIndexMethod.get().intValue();
    }

    private <T> T[] readArray(Function<Integer, T[]> arrayCreator, ThrowableSupplier<T> supplier, SerializationType baseType, SerializationType currentType) throws IOException {
        int ordinal = baseType.ordinal();
        int cOrdinal = currentType.ordinal();

        ThrowableSupplier<Number> getIndexMethod = input::readByte;

        switch (ordinal - cOrdinal) {
            case 1 -> getIndexMethod = input::readShort;
            case 2 -> getIndexMethod = input::readInt;
            case 3 -> getIndexMethod = input::readLong;
        }

        int arrayLength = getIndexMethod.get().intValue();

        T[] array = arrayCreator.apply(arrayLength);
        for (int i = 0; i < array.length; i++) {
            array[i] = supplier.get();
        }
        return array;
    }

    @Override
    public INamedDeserializer newInstance(byte[] bytes, boolean isCompressed) throws IOException {
        return fromBytes(bytes, isCompressed);
    }

    public byte readByte(String name) {
        return (byte) keyToValue.get(name);
    }
    public Byte[] readByteArray(String name) {
        return (Byte[]) keyToValue.get(name);
    }

    public short readShort(String name) {
        return (short) keyToValue.get(name);
    }
    public Short[] readShortArray(String name) {
        return (Short[]) keyToValue.get(name);
    }

    public int readInt(String name) {
        return (int) keyToValue.get(name);
    }
    public Integer[] readIntArray(String name) {
        return (Integer[]) keyToValue.get(name);
    }

    public long readLong(String name) {
        return (long) keyToValue.get(name);
    }
    public Long[] readLongArray(String name) {
        return (Long[]) keyToValue.get(name);
    }

    public float readFloat(String name) {
        return (float) keyToValue.get(name);
    }
    public Float[] readFloatArray(String name) {
        return (Float[]) keyToValue.get(name);
    }

    public double readDouble(String name) {
        return (double) keyToValue.get(name);
    }
    public Double[] readDoubleArray(String name) {
        return (Double[]) keyToValue.get(name);
    }

    public boolean readBoolean(String name) {
        return (boolean) keyToValue.get(name);
    }
    public Boolean[] readBooleanArray(String name) {
        return (Boolean[]) keyToValue.get(name);
    }

    public char readChar(String name) {
        return (char) keyToValue.get(name);
    }
    public Character[] readCharArray(String name) {
        return (Character[]) keyToValue.get(name);
    }

    public String readString(String name) {
        return (String) keyToValue.get(name);
    }
    public String[] readStringArray(String name) {
        return (String[]) keyToValue.get(name);
    }

    public CompoundObject readCompoundObject(String name) {
        return this.readNamedObject(name, CompoundObject.class);
    }
    public CompoundObject[] readCompoundObjectArray(String name) {
        return this.readNamedObjectArray(name, CompoundObject.class);
    }

    public <T extends IDataStreamSerializable> T readRawObject(String name, Class<T> type) {
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

    public <T extends IDataStreamSerializable> T[] readRawObjectArray(String name, Class<T> type) {
        try {
            Byte[][] objs = (Byte[][]) keyToValue.get(name);
            T[] t = (T[]) Array.newInstance(type, objs.length);
            for (int i = 0; i < t.length; i++) {
                T obj = type.getDeclaredConstructor().newInstance();
                obj.read(new DataInputStream(new ByteArrayInputStream(NativeArrayUtil.toNativeArray(objs[i]))));
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

    public <T extends INamedSerializable> T readNamedObject(String name, Class<T> type) {
        try {
            T obj = type.getDeclaredConstructor().newInstance();
            obj.read((INamedDeserializer) keyToValue.get(name));
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

    public <T extends INamedSerializable> T[] readNamedObjectArray(String name, Class<T> type) {
        INamedDeserializer[] objs = (INamedDeserializer[]) keyToValue.get(name);
        T[] t = (T[]) Array.newInstance(type, objs.length);

        for (int i = 0; i < t.length; i++) {
            T obj;
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
    }

    @Override
    public <T extends IUnNamedSerializable> T readUnNamedObject(String name, Class<T> type) {
        try {
            T obj = type.getDeclaredConstructor().newInstance();
            obj.read((IUnNamedDeserializer) keyToValue.get(name));
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
    public <T extends IUnNamedSerializable> T[] readUnNamedObjectArray(String name, Class<T> type) {
        IUnNamedDeserializer[] objs = (IUnNamedDeserializer[]) keyToValue.get(name);
        T[] t = (T[]) Array.newInstance(type, objs.length);

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
    }

    public <T> T readCustomObject(String name, Class<T> type) {
        if (!NAMED_DESERIALIZER_MAP.containsKey(type)) throw new RuntimeException("cannot deserialize class of type \"" + type.getName() + "\" due to it not having a registered deserializer.");

        try {
            return (T) NAMED_DESERIALIZER_MAP.get(type).read((INamedDeserializer) keyToValue.get(name));
        } catch (
                IllegalArgumentException | SecurityException e
        ) {
            return null;
        }
    }

    public <T> T[] readCustomObjectArray(String name, Class<T> type) {
        if (!NAMED_DESERIALIZER_MAP.containsKey(type)) throw new RuntimeException("cannot deserialize class of type \"" + type.getName() + "\" due to it not having a registered deserializer.");

        INamedDeserializer[] objs = (INamedDeserializer[]) keyToValue.get(name);
        INamedCustomSerializable<T> customDeserializer = (INamedCustomSerializable<T>) NAMED_DESERIALIZER_MAP.get(type);

        T[] t = (T[]) Array.newInstance(type, objs.length);

        for (int i = 0; i < t.length; i++) {
            T obj = customDeserializer.read(objs[i]);
            t[i] = obj;
        }
        return t;
    }

    @Override
    public Object getObject(String name) {
        return keyToValue.get(name);
    }

    @Override
    public int getObjectCount() {
        return keyToValue.size();
    }

    @Override
    public String[] getKeys() {
        return keyToValue.keySet().toArray(new String[0]);
    }

    private SerializationType readType() throws IOException {
        return SerializationType.values()[input.readByte()];
    }

}
