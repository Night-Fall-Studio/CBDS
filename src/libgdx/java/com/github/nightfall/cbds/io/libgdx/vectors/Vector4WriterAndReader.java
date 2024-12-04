package com.github.nightfall.cbds.io.libgdx.vectors;

import com.badlogic.gdx.math.Vector4;
import com.github.nightfall.cbds.io.custom.CustomDeserializer;
import com.github.nightfall.cbds.io.custom.CustomSerializer;
import com.github.nightfall.cbds.io.serial.api.IDeserializer;
import com.github.nightfall.cbds.io.serial.api.ISerializer;

import java.io.IOException;

public class Vector4WriterAndReader implements CustomSerializer<Vector4>, CustomDeserializer<Vector4> {

    @Override
    public void write(ISerializer serializer, Vector4 obj) throws IOException {
        serializer.writeFloat("x", obj.x);
        serializer.writeFloat("y", obj.y);
        serializer.writeFloat("z", obj.z);
        serializer.writeFloat("w", obj.w);
    }

    @Override
    public Vector4 read(IDeserializer deserializer) {
        return new Vector4(deserializer.readFloat("x"), deserializer.readFloat("y"), deserializer.readFloat("z"), deserializer.readFloat("w"));
    }

    @Override
    public Class<Vector4> getSerializableType() {
        return Vector4.class;
    }

}
