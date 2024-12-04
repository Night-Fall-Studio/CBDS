package com.github.nightfall.cbds.io.libgdx.vectors;

import com.badlogic.gdx.math.Vector3;
import com.github.nightfall.cbds.io.custom.CustomDeserializer;
import com.github.nightfall.cbds.io.custom.CustomSerializer;
import com.github.nightfall.cbds.io.serial.api.IDeserializer;
import com.github.nightfall.cbds.io.serial.api.ISerializer;

import java.io.IOException;

public class Vector3WriterAndReader implements CustomSerializer<Vector3>, CustomDeserializer<Vector3> {

    @Override
    public void write(ISerializer serializer, Vector3 obj) throws IOException {
        serializer.writeFloat("x", obj.x);
        serializer.writeFloat("y", obj.y);
        serializer.writeFloat("z", obj.z);
    }

    @Override
    public Vector3 read(IDeserializer deserializer) {
        return new Vector3(deserializer.readFloat("x"), deserializer.readFloat("y"), deserializer.readFloat("z"));
    }

    @Override
    public Class<Vector3> getSerializableType() {
        return Vector3.class;
    }

}
