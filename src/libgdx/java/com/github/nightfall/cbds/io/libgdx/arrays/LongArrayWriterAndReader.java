package com.github.nightfall.cbds.io.libgdx.arrays;

import com.badlogic.gdx.utils.LongArray;
import com.github.nightfall.cbds.io.custom.INamedCustomSerializable;
import com.github.nightfall.cbds.io.custom.IKeylessCustomSerializable;
import com.github.nightfall.cbds.io.serial.api.IKeylessDeserializer;
import com.github.nightfall.cbds.io.serial.api.IKeylessSerializer;
import com.github.nightfall.cbds.io.serial.api.INamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.INamedSerializer;

import java.io.IOException;

public class LongArrayWriterAndReader implements INamedCustomSerializable<LongArray>, IKeylessCustomSerializable<LongArray> {

    @Override
    public void write(INamedSerializer serializer, LongArray obj) throws IOException {
        serializer.writeLongArray("items", obj.items);
    }

    @Override
    public LongArray read(INamedDeserializer deserializer) {
        return new LongArray(deserializer.readLongArrayAsNative("items"));
    }

    @Override
    public LongArray read(IKeylessDeserializer in) throws IOException{
        return new LongArray(in.readLongArrayAsNative());
    }

    @Override
    public void write(IKeylessSerializer out, LongArray obj) throws IOException {
        out.writeLongArray(obj.items);
    }

    @Override
    public Class<LongArray> getSerializableType() {
        return LongArray.class;
    }

}
