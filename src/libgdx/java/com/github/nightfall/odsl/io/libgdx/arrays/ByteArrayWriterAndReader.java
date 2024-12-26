package com.github.nightfall.odsl.io.libgdx.arrays;

import com.badlogic.gdx.utils.ByteArray;
import com.github.nightfall.odsl.io.custom.INamedCustomSerializable;
import com.github.nightfall.odsl.io.custom.IKeylessCustomSerializable;
import com.github.nightfall.odsl.io.serial.api.IKeylessDeserializer;
import com.github.nightfall.odsl.io.serial.api.IKeylessSerializer;
import com.github.nightfall.odsl.io.serial.api.INamedDeserializer;
import com.github.nightfall.odsl.io.serial.api.INamedSerializer;

import java.io.IOException;

public class ByteArrayWriterAndReader implements INamedCustomSerializable<ByteArray>, IKeylessCustomSerializable<ByteArray> {

    @Override
    public void write(INamedSerializer serializer, ByteArray obj) throws IOException {
        serializer.writeByteArray("items", obj.items);
    }

    @Override
    public ByteArray read(INamedDeserializer deserializer) {
        return new ByteArray(deserializer.readByteArrayAsNative("items"));
    }

    @Override
    public ByteArray read(IKeylessDeserializer in) throws IOException{
        return new ByteArray(in.readByteArrayAsNative());
    }

    @Override
    public void write(IKeylessSerializer out, ByteArray obj) throws IOException {
        out.writeByteArray(obj.items);
    }

    @Override
    public Class<ByteArray> getSerializableType() {
        return ByteArray.class;
    }

}
