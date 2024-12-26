package com.github.nightfall.odsl.io.libgdx.arrays;

import com.badlogic.gdx.utils.ShortArray;
import com.github.nightfall.odsl.io.custom.INamedCustomSerializable;
import com.github.nightfall.odsl.io.custom.IKeylessCustomSerializable;
import com.github.nightfall.odsl.io.serial.api.IKeylessDeserializer;
import com.github.nightfall.odsl.io.serial.api.IKeylessSerializer;
import com.github.nightfall.odsl.io.serial.api.INamedDeserializer;
import com.github.nightfall.odsl.io.serial.api.INamedSerializer;

import java.io.IOException;

public class ShortArrayWriterAndReader implements INamedCustomSerializable<ShortArray>, IKeylessCustomSerializable<ShortArray> {

    @Override
    public void write(INamedSerializer serializer, ShortArray obj) throws IOException {
        serializer.writeShortArray("items", obj.items);
    }

    @Override
    public ShortArray read(INamedDeserializer deserializer) {
        return new ShortArray(deserializer.readShortArrayAsNative("items"));
    }

    @Override
    public ShortArray read(IKeylessDeserializer in) throws IOException{
        return new ShortArray(in.readShortArrayAsNative());
    }

    @Override
    public void write(IKeylessSerializer out, ShortArray obj) throws IOException {
        out.writeShortArray(obj.items);
    }

    @Override
    public Class<ShortArray> getSerializableType() {
        return ShortArray.class;
    }

}
