package com.github.nightfall.cbds.objects;

import com.github.nightfall.cbds.io.serial.impl.NamedBinarySerializer;
import com.github.nightfall.cbds.io.serial.obj.INamedSerializable;
import com.github.nightfall.cbds.io.serial.api.INamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.INamedSerializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import finalforeach.cosmicreach.savelib.crbin.ICRBinSerializable;

import java.io.IOException;
import java.util.Random;

public class TestObject implements INamedSerializable, ICRBinSerializable {

    static final Random random = new Random();
    static final long[] longs = new long[200];
    static {
        for (int i = 0; i < longs.length; i++) longs[i] = random.nextLong(Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    public TestObject() {}

    @Override
    public void read(INamedDeserializer deserializer) throws IOException {
        System.out.println(deserializer.readString("test_key2"));
    }

    @Override
    public void write(INamedSerializer serializer) throws IOException {
        if (serializer instanceof NamedBinarySerializer s) s.doStringArray = false;
//        for (int i = 0; i < 200; i++) {
//            serializer.writeLong("test_key_" + i, longs[i]);
//            serializer.writeString("test_key_s_" + i, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        }
        serializer.writeString("test_key2", "Hello2");
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        System.out.println(deserializer.readString("test_key2"));
    }

    @Override
    public void write(CRBinSerializer serializer) {
//        for (int i = 0; i < 200; i++) {
//            serializer.writeLong("test_key_" + i, longs[i]);
//            serializer.writeString("test_key_s_" + i, String.valueOf(random.nextLong(Long.MIN_VALUE, Long.MAX_VALUE)));
//        }
        serializer.writeString("test_key2", "Hello2");
    }

}