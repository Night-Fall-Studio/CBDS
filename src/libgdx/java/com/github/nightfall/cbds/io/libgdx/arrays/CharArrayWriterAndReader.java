package com.github.nightfall.cbds.io.libgdx.arrays;

import com.badlogic.gdx.utils.CharArray;
import com.github.nightfall.cbds.io.custom.CustomDeserializer;
import com.github.nightfall.cbds.io.custom.CustomSerializer;
import com.github.nightfall.cbds.io.serial.api.IDeserializer;
import com.github.nightfall.cbds.io.serial.api.ISerializer;

import java.io.IOException;

public class CharArrayWriterAndReader implements CustomSerializer<CharArray>, CustomDeserializer<CharArray> {

    @Override
    public void write(ISerializer serializer, CharArray obj) throws IOException {
        serializer.writeCharArray("items", obj.items);
    }

    @Override
    public CharArray read(IDeserializer deserializer) {
        return new CharArray(deserializer.readCharArrayAsPrimitive("items"));
    }

    @Override
    public Class<CharArray> getSerializableType() {
        return CharArray.class;
    }

}
