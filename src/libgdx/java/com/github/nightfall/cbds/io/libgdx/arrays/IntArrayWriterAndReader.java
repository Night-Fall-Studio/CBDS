package com.github.nightfall.cbds.io.libgdx.arrays;

import com.badlogic.gdx.utils.IntArray;
import com.github.nightfall.cbds.io.custom.INamedCustomSerializable;
import com.github.nightfall.cbds.io.custom.IUnNamedCustomSerializable;
import com.github.nightfall.cbds.io.serial.api.IKeylessDeserializer;
import com.github.nightfall.cbds.io.serial.api.IKeylessSerializer;
import com.github.nightfall.cbds.io.serial.api.INamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.INamedSerializer;

import java.io.IOException;

public class IntArrayWriterAndReader implements INamedCustomSerializable<IntArray>, IUnNamedCustomSerializable<IntArray> {

    @Override
    public void write(INamedSerializer serializer, IntArray obj) throws IOException {
        serializer.writeIntArray("items", obj.items);
    }

    @Override
    public IntArray read(INamedDeserializer deserializer) {
        return new IntArray(deserializer.readIntArrayAsPrimitive("items"));
    }

    @Override
    public IntArray read(IKeylessDeserializer in) throws IOException{
        return new IntArray(in.readIntArrayAsPrimitive());
    }

    @Override
    public void write(IKeylessSerializer out, IntArray obj) throws IOException {
        out.writeIntArray(obj.items);
    }

    @Override
    public Class<IntArray> getSerializableType() {
        return IntArray.class;
    }

}
