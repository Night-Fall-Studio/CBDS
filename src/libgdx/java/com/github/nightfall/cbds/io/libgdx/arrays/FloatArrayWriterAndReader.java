package com.github.nightfall.cbds.io.libgdx.arrays;

import com.badlogic.gdx.utils.FloatArray;
import com.github.nightfall.cbds.io.custom.INamedCustomSerializable;
import com.github.nightfall.cbds.io.custom.IUnNamedCustomSerializable;
import com.github.nightfall.cbds.io.serial.api.IKeylessDeserializer;
import com.github.nightfall.cbds.io.serial.api.IKeylessSerializer;
import com.github.nightfall.cbds.io.serial.api.INamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.INamedSerializer;

import java.io.IOException;

public class FloatArrayWriterAndReader implements INamedCustomSerializable<FloatArray>, IUnNamedCustomSerializable<FloatArray> {

    @Override
    public void write(INamedSerializer serializer, FloatArray obj) throws IOException {
        serializer.writeFloatArray("items", obj.items);
    }

    @Override
    public FloatArray read(INamedDeserializer deserializer) {
        return new FloatArray(deserializer.readFloatArrayAsPrimitive("items"));
    }

    @Override
    public FloatArray read(IKeylessDeserializer in) throws IOException{
        return new FloatArray(in.readFloatArrayAsPrimitive());
    }

    @Override
    public void write(IKeylessSerializer out, FloatArray obj) throws IOException {
        out.writeFloatArray(obj.items);
    }

    @Override
    public Class<FloatArray> getSerializableType() {
        return FloatArray.class;
    }

}
