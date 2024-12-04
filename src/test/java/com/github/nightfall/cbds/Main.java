package com.github.nightfall.cbds;

import com.github.nightfall.cbds.io.serial.impl.BinDeserializer;
import com.github.nightfall.cbds.io.serial.impl.BinSerializer;
import com.github.nightfall.cbds.io.CompoundObject;
import com.github.nightfall.cbds.io.serial.api.IDeserializer;
import com.github.nightfall.cbds.io.serial.api.ISerializer;
import com.github.nightfall.cbds.io.obj.BinSerializable;

import java.io.IOException;
import java.util.Random;

public class Main {

    static long startTime;
    static long endTime;

    public static void main(String[] args) throws IOException {
        CBDSConstants.init();

        ISerializer serializer = new BinSerializer();

        CompoundObject object = new CompoundObject();

        object.writeString("test", "Hello World");
        for (int i = 0; i < 800; i++) {
            object.writeObject("testObj_" + i, new Test());
            serializer.writeObject("testObj_" + i, new Test());
        }
        System.out.println("Before Serialization: " + object.readString("test"));
        System.out.println("Before Serialization key count: " + object.getObjectCount());

        serializer.writeObject("compound", object);

        startTime = System.nanoTime();
        byte[] bytes = serializer.toBytes();
        endTime = System.nanoTime();
        System.out.println("Took " + nanoToMilli(endTime - startTime) + "ms to serialize");
        System.out.println("Size in bytes without compression: " + bytes.length + "\n");
        startTime = System.nanoTime();
        byte[] bytesC = serializer.toCompressedBytes();
        endTime = System.nanoTime();
        System.out.println("Took " + nanoToMilli(endTime - startTime) + "ms to serialize with compression");
        System.out.println("Size in bytes with compression: " + bytesC.length + '\n');
        System.out.println("Size decreased by " + ((100f / bytes.length) * bytesC.length) + "%\n");
        startTime = System.nanoTime();
        IDeserializer deserializer = BinDeserializer.fromCompressedBytes(bytesC);
        object = deserializer.readObject(CompoundObject.class, "compound");
        endTime = System.nanoTime();
        System.out.println("Took " + nanoToMilli(endTime - startTime) + "ms to deserialize");
        System.out.println("After Deserialization: " + object.readString("test"));
        System.out.println("After Deserialization key count: " + object.getObjectCount());
    }

    public static double nanoToMilli(long nano) {
        return nano / 1e+6;
    }

    public static class Test implements BinSerializable {

        public Test() {}

        @Override
        public void read(IDeserializer deserializer) throws IOException {
//            System.out.println(deserializer.readString("test_key"));
//            System.out.println(deserializer.readString("test_key2"));
        }

        @Override
        public void write(ISerializer serializer) throws IOException {
            Random random = new Random();
            for (int i = 0; i < 200; i++) {
                serializer.writeLong("test_key_" + i, random.nextLong(Long.MIN_VALUE, Long.MAX_VALUE));
                serializer.writeString("test_key_s_" + i, String.valueOf(random.nextLong(Long.MIN_VALUE, Long.MAX_VALUE)));
            }
//            serializer.writeString("test_key2", "Hello2");
        }

    }

}
