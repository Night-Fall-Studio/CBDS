package com.github.nightfall.cbds.io.serial.impl;

import com.github.nightfall.cbds.io.CompoundObject;
import com.github.nightfall.cbds.io.serial.SerializationType;
import com.github.nightfall.cbds.io.custom.CustomSerializer;
import com.github.nightfall.cbds.io.serial.api.ISerializer;
import com.github.nightfall.cbds.io.obj.BinSerializable;
import com.github.nightfall.cbds.io.obj.RawDataSerializable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.GZIPOutputStream;

public class BinSerializer implements ISerializer {

    final DataOutputStream output;
    final ByteArrayOutputStream byteStream;

    public BinSerializer() throws IOException {
        byteStream = new ByteArrayOutputStream();
        output = new DataOutputStream(byteStream);
    }

    public BinSerializer(DataOutputStream outputStream) throws IOException {
        byteStream = null;
        output = outputStream;
    }

    private void writeType(SerializationType type) throws IOException {
        output.writeByte(type.ordinal());
    }

    @Override
    public ISerializer newInstance() throws IOException {
        return new BinSerializer();
    }

    public void writeByte(String name, byte i) throws IOException {
        writeType(SerializationType.BYTE);
        output.writeUTF(name);
        output.writeByte(i);
    }

    public void writeByteArray(String name, byte[] array) throws IOException {
        writeType(SerializationType.BYTE_ARRAY);
        output.writeUTF(name);
        output.writeInt(array.length);
        for (byte i : array) output.writeByte(i);
    }

    public void writeByteArray(String name, Byte[] array) throws IOException {
        writeType(SerializationType.BYTE_ARRAY);
        output.writeUTF(name);
        output.writeInt(array.length);
        for (byte i : array) output.writeByte(i);
    }

    public void writeShort(String name, short i) throws IOException {
        writeType(SerializationType.SHORT);
        output.writeUTF(name);
        output.writeShort(i);
    }

    public void writeShortArray(String name, short[] array) throws IOException {
        writeType(SerializationType.SHORT_ARRAY);
        output.writeUTF(name);
        output.writeInt(array.length);
        for (short i : array) output.writeShort(i);
    }

    public void writeShortArray(String name, Short[] array) throws IOException {
        writeType(SerializationType.SHORT_ARRAY);
        output.writeUTF(name);
        output.writeInt(array.length);
        for (short i : array) output.writeShort(i);
    }

    public void writeInt(String name, int i) throws IOException {
        writeType(SerializationType.INT);
        output.writeUTF(name);
        output.writeInt(i);
    }

    public void writeIntArray(String name, int[] array) throws IOException {
        writeType(SerializationType.INT_ARRAY);
        output.writeUTF(name);
        output.writeInt(array.length);
        for (int i : array) output.writeShort(i);
    }

    public void writeIntArray(String name, Integer[] array) throws IOException {
        writeType(SerializationType.INT_ARRAY);
        output.writeUTF(name);
        output.writeInt(array.length);
        for (int i : array) output.writeShort(i);
    }

    public void writeLong(String name, long i) throws IOException {
        writeType(SerializationType.LONG);
        output.writeUTF(name);
        output.writeLong(i);
    }

    public void writeLongArray(String name, long[] array) throws IOException {
        writeType(SerializationType.LONG_ARRAY);
        output.writeUTF(name);
        output.writeInt(array.length);
        for (long i : array) output.writeLong(i);
    }

    public void writeLongArray(String name, Long[] array) throws IOException {
        writeType(SerializationType.LONG_ARRAY);
        output.writeUTF(name);
        output.writeInt(array.length);
        for (long i : array) output.writeLong(i);
    }

    public void writeFloat(String name, float i) throws IOException {
        writeType(SerializationType.FLOAT);
        output.writeUTF(name);
        output.writeFloat(i);
    }

    public void writeFloatArray(String name, float[] array) throws IOException {
        writeType(SerializationType.FLOAT_ARRAY);
        output.writeUTF(name);
        output.writeInt(array.length);
        for (float i : array) output.writeFloat(i);
    }

    public void writeFloatArray(String name, Float[] array) throws IOException {
        writeType(SerializationType.FLOAT_ARRAY);
        output.writeUTF(name);
        output.writeInt(array.length);
        for (float i : array) output.writeFloat(i);
    }

    public void writeDouble(String name, double i) throws IOException {
        writeType(SerializationType.DOUBLE);
        output.writeUTF(name);
        output.writeDouble(i);
    }

    public void writeDoubleArray(String name, double[] array) throws IOException {
        writeType(SerializationType.DOUBLE_ARRAY);
        output.writeUTF(name);
        output.writeInt(array.length);
        for (double i : array) output.writeDouble(i);
    }

    public void writeDoubleArray(String name, Double[] array) throws IOException {
        writeType(SerializationType.DOUBLE_ARRAY);
        output.writeUTF(name);
        output.writeInt(array.length);
        for (double i : array) output.writeDouble(i);
    }

    public void writeBoolean(String name, boolean b) throws IOException {
        writeType(SerializationType.BOOLEAN);
        output.writeUTF(name);
        output.writeBoolean(b);
    }

    public void writeBooleanArray(String name, boolean[] array) throws IOException {
        writeType(SerializationType.BOOLEAN_ARRAY);
        output.writeUTF(name);
        output.writeInt(array.length);
        for (boolean b : array) output.writeBoolean(b);
    }

    public void writeBooleanArray(String name, Boolean[] array) throws IOException {
        writeType(SerializationType.BOOLEAN_ARRAY);
        output.writeUTF(name);
        output.writeInt(array.length);
        for (boolean b : array) output.writeBoolean(b);
    }

    public void writeChar(String name, char c) throws IOException {
        writeType(SerializationType.CHAR);
        output.writeUTF(name);
        output.writeChar(c);
    }

    public void writeCharArray(String name, char[] array) throws IOException {
        writeType(SerializationType.CHAR_ARRAY);
        output.writeInt(array.length);
        for (char c : array) output.writeChar(c);
    }

    public void writeCharArray(String name, Character[] array) throws IOException {
        writeType(SerializationType.CHAR_ARRAY);
        output.writeInt(array.length);
        for (char c : array) output.writeChar(c);
    }

    public void writeString(String name, String v) throws IOException {
        writeType(SerializationType.STRING);
        output.writeUTF(name);
        output.writeUTF(v);
    }

    public void writeStringArray(String name, String[] array) throws IOException {
        writeType(SerializationType.STRING_ARRAY);
        output.writeUTF(name);
        output.writeInt(array.length);
        for (String s : array) output.writeUTF(s);
    }

    public void writeCompoundObject(String name, CompoundObject object) throws IOException {
        writeObject(name, object);
    }

    public void writeCompoundObjectArray(String name, CompoundObject[] array) throws IOException {
        writeObjectArray(name, array);
    }

    public <T extends RawDataSerializable> void writeRawObject(String name, T object) throws IOException {
        writeType(SerializationType.KEYLESS_OBJECT);
        output.writeUTF(name);

        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        DataOutputStream stream = new DataOutputStream(byteOut);
        object.write(stream);

        byte[] bytes = byteOut.toByteArray();

        output.writeInt(bytes.length);
        for (byte i : bytes) output.writeByte(i);
    }

    public <T extends RawDataSerializable> void writeRawObjectArray(String name, T[] array) throws IOException {
        writeType(SerializationType.KEYLESS_OBJECT_ARRAY);
        output.writeUTF(name);
        output.writeInt(array.length);
        for (int i = 0; i < array.length; i++) {
            T obj = array[i];

            writeRawObject(String.valueOf(i), obj);
        }
    }

    public <T extends BinSerializable> void writeObject(String name, T object) throws IOException {
        writeType(SerializationType.KEYED_OBJECT);
        output.writeUTF(name);

        ISerializer miniSerializer = newInstance();
        object.write(miniSerializer);

        byte[] bytes = miniSerializer.toBytes();

        output.writeInt(bytes.length);
        for (byte i : bytes) output.writeByte(i);
    }

    public <T extends BinSerializable> void writeObjectArray(String name, T[] array) throws IOException {
        writeType(SerializationType.KEYED_OBJECT_ARRAY);
        output.writeUTF(name);
        output.writeInt(array.length);
        for (int i = 0; i < array.length; i++) {
            T obj = array[i];

            writeObject(String.valueOf(i), obj);
        }
    }

    public <T> void writeCustomObject(String name, T object) throws IOException {
        if (!SERIALIZER_MAP.containsKey(object.getClass())) throw new RuntimeException("cannot serialize class of type \"" + object.getClass().getName() + "\" due to it not having a registered serializer.");

        writeType(SerializationType.CUSTOM_OBJECT);
        output.writeUTF(name);

        ISerializer miniSerializer = newInstance();
        CustomSerializer<T> serializer = (CustomSerializer<T>) ISerializer.getSerializer(object.getClass());
        serializer.write(miniSerializer, object);

        byte[] bytes = miniSerializer.toBytes();

        output.writeInt(bytes.length);
        for (byte i : bytes) output.writeByte(i);
    }

    public <T> void writeCustomObjectArray(String name, T[] array) throws IOException {
        writeType(SerializationType.CUSTOM_OBJECT_ARRAY);
        output.writeUTF(name);
        output.writeInt(array.length);
        for (int i = 0; i < array.length; i++) {
            T obj = array[i];

            writeCustomObject(String.valueOf(i), obj);
        }
    }

    public byte[] toBytes() {
        if (byteStream == null) throw new RuntimeException("Cannot convert to bytes due to BinSerializer being made without a ByteArrayOutputStream");
        return byteStream.toByteArray();
    }

    public byte[] toCompressedBytes() throws IOException {
        if (byteStream == null) throw new RuntimeException("Cannot convert to bytes due to BinSerializer being made without a ByteArrayOutputStream");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        GZIPOutputStream stream1 = new GZIPOutputStream(stream);
        stream1.write(byteStream.toByteArray());
        stream1.close();
        return stream.toByteArray();
    }

    public byte[] toBase64() {
        return Base64.getEncoder().encode(toBytes());
    }

    public byte[] toCompressedBase64() throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        GZIPOutputStream stream1 = new GZIPOutputStream(stream);
        stream1.write(Base64.getEncoder().encode(toCompressedBytes()));
        stream1.close();
        return stream.toByteArray();
    }

}
