package com.github.nightfall.cbds.io.libgdx.arrays;

import com.badlogic.gdx.utils.ShortArray;
import com.github.nightfall.cbds.io.custom.INamedCustomSerializable;
import com.github.nightfall.cbds.io.custom.IKeylessCustomSerializable;
import com.github.nightfall.cbds.io.serial.api.IKeylessDeserializer;
import com.github.nightfall.cbds.io.serial.api.IKeylessSerializer;
import com.github.nightfall.cbds.io.serial.api.INamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.INamedSerializer;

import java.io.IOException;

public class ShortArrayWriterAndReader implements INamedCustomSerializable<ShortArray>, IKeylessCustomSerializable<ShortArray> {

    @Override
    public void write(INamedSerializer serializer, ShortArray obj) throws IOException {
        serializer.writeShortArray("items", obj.items);
    }

    @Override
    public ShortArray read(INamedDeserializer deserializer) {
        return new ShortArray(deserializer.readShortArrayAsPrimitive("items"));
    }

    @Override
    public ShortArray read(IKeylessDeserializer in) throws IOException{
        return new ShortArray(in.readShortArrayAsPrimitive());
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
