package com.github.nightfall.cbds.io.libgdx.vectors;

import com.badlogic.gdx.math.Vector2;
import com.github.nightfall.cbds.io.custom.CustomDeserializer;
import com.github.nightfall.cbds.io.custom.CustomSerializer;
import com.github.nightfall.cbds.io.serial.api.IDeserializer;
import com.github.nightfall.cbds.io.serial.api.ISerializer;

import java.io.IOException;

public class Vector2WriterAndReader implements CustomSerializer<Vector2>, CustomDeserializer<Vector2> {

    @Override
    public void write(ISerializer serializer, Vector2 obj) throws IOException {
        serializer.writeFloat("x", obj.x);
        serializer.writeFloat("y", obj.y);
    }

    @Override
    public Vector2 read(IDeserializer deserializer) {
        return new Vector2(deserializer.readFloat("x"), deserializer.readFloat("y"));
    }

    @Override
    public Class<Vector2> getSerializableType() {
        return Vector2.class;
    }

}
