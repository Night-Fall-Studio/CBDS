package com.github.nightfall.cbds.io.libgdx.arrays;

import com.badlogic.gdx.utils.FloatArray;
import com.github.nightfall.cbds.io.custom.CustomDeserializer;
import com.github.nightfall.cbds.io.custom.CustomSerializer;
import com.github.nightfall.cbds.io.serial.api.IDeserializer;
import com.github.nightfall.cbds.io.serial.api.ISerializer;

import java.io.IOException;

public class FloatArrayWriterAndReader implements CustomSerializer<FloatArray>, CustomDeserializer<FloatArray> {

    @Override
    public void write(ISerializer serializer, FloatArray obj) throws IOException {
        serializer.writeFloatArray("items", obj.items);
    }

    @Override
    public FloatArray read(IDeserializer deserializer) {
        return new FloatArray(deserializer.readFloatArrayAsPrimitive("items"));
    }

    @Override
    public Class<FloatArray> getSerializableType() {
        return FloatArray.class;
    }

}
