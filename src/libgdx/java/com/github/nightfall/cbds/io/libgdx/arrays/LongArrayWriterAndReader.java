package com.github.nightfall.cbds.io.libgdx.arrays;

import com.badlogic.gdx.utils.LongArray;
import com.github.nightfall.cbds.io.custom.CustomDeserializer;
import com.github.nightfall.cbds.io.custom.CustomSerializer;
import com.github.nightfall.cbds.io.serial.api.IDeserializer;
import com.github.nightfall.cbds.io.serial.api.ISerializer;

import java.io.IOException;

public class LongArrayWriterAndReader implements CustomSerializer<LongArray>, CustomDeserializer<LongArray> {

    @Override
    public void write(ISerializer serializer, LongArray obj) throws IOException {
        serializer.writeLongArray("items", obj.items);
    }

    @Override
    public LongArray read(IDeserializer deserializer) {
        return new LongArray(deserializer.readLongArrayAsPrimitive("items"));
    }

    @Override
    public Class<LongArray> getSerializableType() {
        return LongArray.class;
    }

}
