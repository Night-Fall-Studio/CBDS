package com.github.nightfall.cbds.io.libgdx.arrays;

import com.badlogic.gdx.utils.ShortArray;
import com.github.nightfall.cbds.io.custom.CustomDeserializer;
import com.github.nightfall.cbds.io.custom.CustomSerializer;
import com.github.nightfall.cbds.io.serial.api.IDeserializer;
import com.github.nightfall.cbds.io.serial.api.ISerializer;

import java.io.IOException;

public class ShortArrayWriterAndReader implements CustomSerializer<ShortArray>, CustomDeserializer<ShortArray> {

    @Override
    public void write(ISerializer serializer, ShortArray obj) throws IOException {
        serializer.writeShortArray("items", obj.items);
    }

    @Override
    public ShortArray read(IDeserializer deserializer) {
        return new ShortArray(deserializer.readShortArrayAsPrimitive("items"));
    }

    @Override
    public Class<ShortArray> getSerializableType() {
        return ShortArray.class;
    }

}
