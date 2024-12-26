package com.github.nightfall.odsl.io.libgdx.arrays;

import com.badlogic.gdx.utils.BooleanArray;
import com.github.nightfall.odsl.io.custom.INamedCustomSerializable;
import com.github.nightfall.odsl.io.custom.IKeylessCustomSerializable;
import com.github.nightfall.odsl.io.serial.api.IKeylessDeserializer;
import com.github.nightfall.odsl.io.serial.api.IKeylessSerializer;
import com.github.nightfall.odsl.io.serial.api.INamedDeserializer;
import com.github.nightfall.odsl.io.serial.api.INamedSerializer;

import java.io.IOException;

public class BooleanArrayWriterAndReader implements INamedCustomSerializable<BooleanArray>, IKeylessCustomSerializable<BooleanArray> {

    @Override
    public void write(INamedSerializer serializer, BooleanArray obj) throws IOException {
        serializer.writeBooleanArray("items", obj.items);
    }

    @Override
    public BooleanArray read(INamedDeserializer deserializer) {
        return new BooleanArray(deserializer.readBooleanArrayAsNative("items"));
    }

    @Override
    public BooleanArray read(IKeylessDeserializer in) throws IOException {
        return new BooleanArray(in.readBooleanArrayAsNative());
    }

    @Override
    public void write(IKeylessSerializer out, BooleanArray obj) throws IOException {
        out.writeBooleanArray(obj.items);
    }

    @Override
    public Class<BooleanArray> getSerializableType() {
        return BooleanArray.class;
    }

}
