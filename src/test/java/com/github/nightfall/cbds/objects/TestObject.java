package com.github.nightfall.cbds.objects;

import com.github.nightfall.cbds.io.serial.api.IUnNamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.IUnNamedSerializer;
import com.github.nightfall.cbds.io.serial.impl.NamedBinarySerializer;
import com.github.nightfall.cbds.io.serial.obj.IDataStreamSerializable;
import com.github.nightfall.cbds.io.serial.obj.INamedSerializable;
import com.github.nightfall.cbds.io.serial.api.INamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.INamedSerializer;
import com.github.nightfall.cbds.io.serial.obj.IUnNamedSerializable;
import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import finalforeach.cosmicreach.savelib.crbin.ICRBinSerializable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

public class TestObject implements INamedSerializable, IUnNamedSerializable, IDataStreamSerializable, ICRBinSerializable {

    static final Random random = new Random();
    
    public TestObject() {}

    @Override
    public void read(INamedDeserializer deserializer) throws IOException {}

    @Override
    public void write(INamedSerializer serializer) throws IOException {
        if (serializer instanceof NamedBinarySerializer s) s.doStringArray = true;
        for (int i = 0; i < 200; i++) {
            serializer.writeLong("test_key_" + i, random.nextLong(Long.MIN_VALUE, Long.MAX_VALUE));
            serializer.writeString("test_key_s_" + i, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }
        serializer.writeString("test_key2", "Hello2");
    }

    @Override
    public void read(IUnNamedDeserializer in) throws IOException {}

    @Override
    public void write(IUnNamedSerializer out) throws IOException {
        for (int i = 0; i < 200; i++) {
            out.writeLong(random.nextLong(Long.MIN_VALUE, Long.MAX_VALUE));
            out.writeString(String.valueOf(random.nextLong(Long.MIN_VALUE, Long.MAX_VALUE)));
        }
        out.writeString("Hello2");
    }

    @Override
    public void read(DataInputStream in) throws IOException {}

    @Override
    public void write(DataOutputStream out) throws IOException {
        for (int i = 0; i < 200; i++) {
            out.writeLong(random.nextLong(Long.MIN_VALUE, Long.MAX_VALUE));
            out.writeUTF(String.valueOf(random.nextLong(Long.MIN_VALUE, Long.MAX_VALUE)));
        }
        out.writeUTF("Hello2");
    }

    @Override
    public void read(CRBinDeserializer deserializer) {}

    @Override
    public void write(CRBinSerializer serializer) {
        for (int i = 0; i < 200; i++) {
            serializer.writeLong("test_key_" + i, random.nextLong(Long.MIN_VALUE, Long.MAX_VALUE));
            serializer.writeString("test_key_s_" + i, String.valueOf(random.nextLong(Long.MIN_VALUE, Long.MAX_VALUE)));
        }
        serializer.writeString("test_key2", "Hello2");
    }

}