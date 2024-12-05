package com.github.nightfall.cbds.io.serial.impl;

import com.github.nightfall.cbds.io.CompoundObject;
import com.github.nightfall.cbds.io.custom.INamedCustomSerializable;
import com.github.nightfall.cbds.io.serial.SerializationType;
import com.github.nightfall.cbds.io.serial.api.INamedSerializer;
import com.github.nightfall.cbds.io.serial.api.IUnNamedSerializer;
import com.github.nightfall.cbds.io.serial.obj.INamedSerializable;
import com.github.nightfall.cbds.io.serial.obj.IDataStreamSerializable;
import com.github.nightfall.cbds.io.serial.obj.IUnNamedSerializable;
import com.github.nightfall.cbds.util.ThrowableSupplier;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.GZIPOutputStream;

public class NamedBinarySerializer implements INamedSerializer {

    DataOutputStream output;
    ByteArrayOutputStream byteStream;
    final List<String> strings = new ArrayList<>();

    public NamedBinarySerializer() {
        byteStream = new ByteArrayOutputStream();
        output = new DataOutputStream(byteStream);
    }

    private void writeType(SerializationType type) throws IOException {
        output.writeByte(type.ordinal());
    }

    private void writeType(SerializationType baseType, int i) throws IOException {
        if (i > Short.MAX_VALUE) {
            output.writeByte(baseType.ordinal() - 2);
            return;
        }
        if (i > Byte.MAX_VALUE) {
            output.writeByte(baseType.ordinal() - 1);
            return;
        }
        output.writeByte(baseType.ordinal());
    }
    
    private void writeDynamicInt(int i) throws IOException {
        if (i > Short.MAX_VALUE) {
            output.writeInt(i);
            return;
        }
        if (i > Byte.MAX_VALUE) {
            output.writeShort((short) i);
            return;
        }
        output.writeByte((byte) i);
    }

    @Override
    public INamedSerializer newInstance() {
        return new NamedBinarySerializer();
    }

    public void writeByte(String name, byte i) throws IOException {
        writeType(SerializationType.BYTE);
        output.writeUTF(name);
        output.writeByte(i);
    }

    public void writeByteArray(String name, byte[] array) throws IOException {
        writeType(SerializationType.BYTE_ARRAY_sBYTE, array.length);
        output.writeUTF(name);
        writeDynamicInt(array.length);
        for (byte i : array) output.writeByte(i);
    }

    public void writeShort(String name, short i) throws IOException {
        writeType(SerializationType.SHORT);
        output.writeUTF(name);
        output.writeShort(i);
    }

    public void writeShortArray(String name, short[] array) throws IOException {
        writeType(SerializationType.SHORT_ARRAY_sBYTE, array.length);
        output.writeUTF(name);
        writeDynamicInt(array.length);
        for (short i : array) output.writeShort(i);
    }

    public void writeInt(String name, int i) throws IOException {
        writeType(SerializationType.INT);
        output.writeUTF(name);
        output.writeInt(i);
    }

    public void writeIntArray(String name, int[] array) throws IOException {
        writeType(SerializationType.INT_ARRAY_sBYTE, array.length);
        output.writeUTF(name);
        writeDynamicInt(array.length);
        for (int i : array) output.writeShort(i);
    }

    public void writeLong(String name, long i) throws IOException {
        writeType(SerializationType.LONG);
        output.writeUTF(name);
        output.writeLong(i);
    }

    public void writeLongArray(String name, long[] array) throws IOException {
        writeType(SerializationType.LONG_ARRAY_sBYTE, array.length);
        output.writeUTF(name);
        writeDynamicInt(array.length);
        for (long i : array) output.writeLong(i);
    }

    public void writeFloat(String name, float i) throws IOException {
        writeType(SerializationType.FLOAT);
        output.writeUTF(name);
        output.writeFloat(i);
    }

    public void writeFloatArray(String name, float[] array) throws IOException {
        writeType(SerializationType.FLOAT_ARRAY_sBYTE, array.length);
        output.writeUTF(name);
        writeDynamicInt(array.length);
        for (float i : array) output.writeFloat(i);
    }

    public void writeDouble(String name, double i) throws IOException {
        writeType(SerializationType.DOUBLE);
        output.writeUTF(name);
        output.writeDouble(i);
    }

    public void writeDoubleArray(String name, double[] array) throws IOException {
        writeType(SerializationType.DOUBLE_ARRAY_sBYTE, array.length);
        output.writeUTF(name);
        writeDynamicInt(array.length);
        for (double i : array) output.writeDouble(i);
    }

    public void writeBoolean(String name, boolean b) throws IOException {
        writeType(SerializationType.BOOLEAN);
        output.writeUTF(name);
        output.writeBoolean(b);
    }

    public void writeBooleanArray(String name, boolean[] array) throws IOException {
        writeType(SerializationType.BOOLEAN_ARRAY_sBYTE, array.length);
        output.writeUTF(name);
        writeDynamicInt(array.length);
        for (boolean b : array) output.writeBoolean(b);
    }

    public void writeChar(String name, char c) throws IOException {
        writeType(SerializationType.CHAR);
        output.writeUTF(name);
        output.writeChar(c);
    }

    public void writeCharArray(String name, char[] array) throws IOException {
        writeType(SerializationType.CHAR_ARRAY_sBYTE, array.length);
        output.writeUTF(name);
        writeDynamicInt(array.length);
        for (char c : array) output.writeChar(c);
    }

    public void writeString(String name, String v) throws IOException {
        writeType(SerializationType.STRING);
        output.writeUTF(name);
        if (doStringArray){
            int index = strings.indexOf(v);
            if (index == -1) {
                strings.add(v);
                index = strings.size() - 1;
            }
            output.writeInt(index);
        } else output.writeUTF(v);
    }

    public void writeStringArray(String name, String[] array) throws IOException {
        writeType(SerializationType.STRING_ARRAY_sBYTE, array.length);
        output.writeUTF(name);
        writeDynamicInt(array.length);
        for (String s : array) output.writeUTF(s);
    }

    public void writeCompoundObject(String name, CompoundObject object) throws IOException {
        this.writeNamedObject(name, object);
    }

    public void writeCompoundObjectArray(String name, CompoundObject[] array) throws IOException {
        this.writeNamedObjectArray(name, array);
    }

    private <T extends IDataStreamSerializable> void _writeRawObj(String name, T object) throws IOException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        DataOutputStream stream = new DataOutputStream(byteOut);
        object.write(stream);

        byte[] bytes = byteOut.toByteArray();

        writeType(SerializationType.RAW_OBJECT_sBYTE, bytes.length);
        if (name != null) output.writeUTF(name);

        writeDynamicInt(bytes.length);
        for (byte i : bytes) output.writeByte(i);
    }

    public <T extends IDataStreamSerializable> void writeRawObject(String name, T object) throws IOException {
        _writeRawObj(name, object);
    }

    public <T extends IDataStreamSerializable> void writeRawObjectArray(String name, T[] array) throws IOException {
        writeType(SerializationType.RAW_OBJECT_ARRAY_sBYTE, array.length);
        output.writeUTF(name);
        writeDynamicInt(array.length);
        for (T obj : array) {
            _writeRawObj(null, obj);
        }
    }

    private <T extends INamedSerializable> void _writeNamedObj(String name, T object) throws IOException {
        INamedSerializer miniSerializer = newInstance();
        object.write(miniSerializer);

        byte[] bytes = miniSerializer.toBytes();

        writeType(SerializationType.NAMED_OBJECT_sBYTE, bytes.length);
        if (name != null) output.writeUTF(name);

        writeDynamicInt(bytes.length);
        for (byte i : bytes) output.writeByte(i);
    }

    public <T extends INamedSerializable> void writeNamedObject(String name, T object) throws IOException {
        _writeNamedObj(name, object);
    }

    public <T extends INamedSerializable> void writeNamedObjectArray(String name, T[] array) throws IOException {
        writeType(SerializationType.NAMED_OBJECT_ARRAY_sBYTE, array.length);
        output.writeUTF(name);
        writeDynamicInt(array.length);
        for (T obj : array) {
            _writeNamedObj(null, obj);
        }
    }

    private <T extends IUnNamedSerializable> void _writeUnNamedObj(String name, T object) throws IOException {
        IUnNamedSerializer miniSerializer = IUnNamedSerializer.createDefault();
        object.write(miniSerializer);

        byte[] bytes = miniSerializer.toBytes();

        writeType(SerializationType.UNNAMED_OBJECT_sBYTE, bytes.length);
        if (name != null) output.writeUTF(name);

        writeDynamicInt(bytes.length);
        for (byte i : bytes) output.writeByte(i);
    }

    @Override
    public <T extends IUnNamedSerializable> void writeUnNamedObject(String name, T object) throws IOException {
        _writeUnNamedObj(name, object);
    }

    @Override
    public <T extends IUnNamedSerializable> void writeUnNamedObjectArray(String name, T[] array) throws IOException {
        writeType(SerializationType.UNNAMED_OBJECT_ARRAY_sBYTE, array.length);
        output.writeUTF(name);
        writeDynamicInt(array.length);
        for (T obj : array) {
            _writeUnNamedObj(null, obj);
        }
    }

    private <T> void _writeCustomObj(String name, T object) throws IOException {
        if (!NAMED_SERIALIZER_MAP.containsKey(object.getClass())) throw new RuntimeException("cannot serialize class of type \"" + object.getClass().getName() + "\" due to it not having a registered serializer.");

        INamedSerializer miniSerializer = newInstance();
        INamedCustomSerializable<T> serializer = (INamedCustomSerializable<T>) INamedSerializer.getSerializer(object.getClass());
        serializer.write(miniSerializer, object);

        byte[] bytes = miniSerializer.toBytes();

        writeType(SerializationType.CUSTOM_OBJECT_sBYTE, bytes.length);
        output.writeUTF(name);

        writeDynamicInt(bytes.length);
        for (byte i : bytes) output.writeByte(i);
    }

    public <T> void writeCustomObject(String name, T object) throws IOException {
        _writeCustomObj(name, object);
    }

    public <T> void writeCustomObjectArray(String name, T[] array) throws IOException {
        writeType(SerializationType.CUSTOM_OBJECT_ARRAY_sBYTE, array.length);
        output.writeUTF(name);
        writeDynamicInt(array.length);
        for (T obj : array) {
            _writeCustomObj(null, obj);
        }
    }

    public boolean doStringArray = true;

    public byte[] toBytes() throws IOException {
        if (doStringArray) {
            byte[] bytes = byteStream.toByteArray();
            byteStream = new ByteArrayOutputStream();
            output = new DataOutputStream(byteStream);

            writeType(SerializationType.STRING_SCHEMA_sBYTE, strings.size());
            output.writeUTF("STRINGS");
            writeDynamicInt(strings.size());
            for (String s : strings) output.writeUTF(s);

            byteStream.write(bytes);

            return byteStream.toByteArray();
        }

        return byteStream.toByteArray();
    }

    public byte[] toCompressedBytes() throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        GZIPOutputStream stream1 = new GZIPOutputStream(stream);
        stream1.write(byteStream.toByteArray());
        stream1.close();
        return stream.toByteArray();
    }

    public String toBase64() throws IOException {
        return Base64.getEncoder().encodeToString(toBytes());
    }

    public String toCompressedBase64() throws IOException {
        return Base64.getEncoder().encodeToString(toCompressedBytes());
    }

}
