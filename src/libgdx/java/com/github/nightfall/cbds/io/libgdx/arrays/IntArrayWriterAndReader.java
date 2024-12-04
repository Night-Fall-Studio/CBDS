package com.github.nightfall.cbds.io.libgdx.arrays;

import com.badlogic.gdx.utils.IntArray;
import com.github.nightfall.cbds.io.custom.CustomDeserializer;
import com.github.nightfall.cbds.io.custom.CustomSerializer;
import com.github.nightfall.cbds.io.serial.api.IDeserializer;
import com.github.nightfall.cbds.io.serial.api.ISerializer;

import java.io.IOException;

public class IntArrayWriterAndReader implements CustomSerializer<IntArray>, CustomDeserializer<IntArray> {

    @Override
    public void write(ISerializer serializer, IntArray obj) throws IOException {
        serializer.writeIntArray("items", obj.items);
    }

    @Override
    public IntArray read(IDeserializer deserializer) {
        return new IntArray(deserializer.readIntArrayAsPrimitive("items"));
    }

    @Override
    public Class<IntArray> getSerializableType() {
        return IntArray.class;
    }

}
