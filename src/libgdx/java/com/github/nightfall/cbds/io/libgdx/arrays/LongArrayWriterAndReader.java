package com.github.nightfall.cbds.io.libgdx.arrays;

import com.badlogic.gdx.utils.LongArray;
import com.github.nightfall.cbds.io.custom.INamedCustomSerializable;
import com.github.nightfall.cbds.io.custom.IUnNamedCustomSerializable;
import com.github.nightfall.cbds.io.serial.api.INamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.INamedSerializer;
import com.github.nightfall.cbds.io.serial.api.IUnNamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.IUnNamedSerializer;

import java.io.IOException;

public class LongArrayWriterAndReader implements INamedCustomSerializable<LongArray>, IUnNamedCustomSerializable<LongArray> {

    @Override
    public void write(INamedSerializer serializer, LongArray obj) throws IOException {
        serializer.writeLongArray("items", obj.items);
    }

    @Override
    public LongArray read(INamedDeserializer deserializer) {
        return new LongArray(deserializer.readLongArrayAsPrimitive("items"));
    }

    @Override
    public LongArray read(IUnNamedDeserializer in) throws IOException{
        return new LongArray(in.readLongArrayAsPrimitive());
    }

    @Override
    public void write(IUnNamedSerializer out, LongArray obj) throws IOException {
        out.writeLongArray(obj.items);
    }

    @Override
    public Class<LongArray> getSerializableType() {
        return LongArray.class;
    }

}
