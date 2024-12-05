package com.github.nightfall.cbds.io.libgdx.arrays;

import com.badlogic.gdx.utils.CharArray;
import com.github.nightfall.cbds.io.custom.INamedCustomSerializable;
import com.github.nightfall.cbds.io.custom.IUnNamedCustomSerializable;
import com.github.nightfall.cbds.io.serial.api.INamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.INamedSerializer;
import com.github.nightfall.cbds.io.serial.api.IUnNamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.IUnNamedSerializer;

import java.io.IOException;

public class CharArrayWriterAndReader implements INamedCustomSerializable<CharArray>, IUnNamedCustomSerializable<CharArray> {

    @Override
    public void write(INamedSerializer serializer, CharArray obj) throws IOException {
        serializer.writeCharArray("items", obj.items);
    }

    @Override
    public CharArray read(INamedDeserializer deserializer) {
        return new CharArray(deserializer.readCharArrayAsPrimitive("items"));
    }

    @Override
    public CharArray read(IUnNamedDeserializer in) throws IOException{
        return new CharArray(in.readCharArrayAsPrimitive());
    }

    @Override
    public void write(IUnNamedSerializer out, CharArray obj) throws IOException {
        out.writeCharArray(obj.items);
    }

    @Override
    public Class<CharArray> getSerializableType() {
        return CharArray.class;
    }

}
