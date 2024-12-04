package com.github.nightfall.cbds.io.libgdx.arrays;

import com.badlogic.gdx.utils.BooleanArray;
import com.github.nightfall.cbds.io.custom.CustomDeserializer;
import com.github.nightfall.cbds.io.custom.CustomSerializer;
import com.github.nightfall.cbds.io.serial.api.IDeserializer;
import com.github.nightfall.cbds.io.serial.api.ISerializer;

import java.io.IOException;

public class BooleanArrayWriterAndReader implements CustomSerializer<BooleanArray>, CustomDeserializer<BooleanArray> {

    @Override
    public void write(ISerializer serializer, BooleanArray obj) throws IOException {
        serializer.writeBooleanArray("items", obj.items);
    }

    @Override
    public BooleanArray read(IDeserializer deserializer) {
        return new BooleanArray(deserializer.readBooleanArrayAsPrimitive("items"));
    }

    @Override
    public Class<BooleanArray> getSerializableType() {
        return BooleanArray.class;
    }

}
