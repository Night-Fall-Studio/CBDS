package com.github.nightfall.cbds.io.libgdx.arrays;

import com.badlogic.gdx.utils.ByteArray;
import com.github.nightfall.cbds.io.custom.INamedCustomSerializable;
import com.github.nightfall.cbds.io.custom.IUnNamedCustomSerializable;
import com.github.nightfall.cbds.io.serial.api.INamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.INamedSerializer;
import com.github.nightfall.cbds.io.serial.api.IUnNamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.IUnNamedSerializer;

import java.io.IOException;

public class ByteArrayWriterAndReader implements INamedCustomSerializable<ByteArray>, IUnNamedCustomSerializable<ByteArray> {

    @Override
    public void write(INamedSerializer serializer, ByteArray obj) throws IOException {
        serializer.writeByteArray("items", obj.items);
    }

    @Override
    public ByteArray read(INamedDeserializer deserializer) {
        return new ByteArray(deserializer.readByteArrayAsPrimitive("items"));
    }

    @Override
    public ByteArray read(IUnNamedDeserializer in) throws IOException{
        return new ByteArray(in.readByteArrayAsPrimitive());
    }

    @Override
    public void write(IUnNamedSerializer out, ByteArray obj) throws IOException {
        out.writeByteArray(obj.items);
    }

    @Override
    public Class<ByteArray> getSerializableType() {
        return ByteArray.class;
    }

}
