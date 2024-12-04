package com.github.nightfall.cbds.io.libgdx.arrays;

import com.badlogic.gdx.utils.ByteArray;
import com.github.nightfall.cbds.io.custom.CustomDeserializer;
import com.github.nightfall.cbds.io.custom.CustomSerializer;
import com.github.nightfall.cbds.io.serial.api.IDeserializer;
import com.github.nightfall.cbds.io.serial.api.ISerializer;

import java.io.IOException;

public class ByteArrayWriterAndReader implements CustomSerializer<ByteArray>, CustomDeserializer<ByteArray> {

    @Override
    public void write(ISerializer serializer, ByteArray obj) throws IOException {
        serializer.writeByteArray("items", obj.items);
    }

    @Override
    public ByteArray read(IDeserializer deserializer) {
        return new ByteArray(deserializer.readByteArrayAsPrimitive("items"));
    }

    @Override
    public Class<ByteArray> getSerializableType() {
        return ByteArray.class;
    }

}
