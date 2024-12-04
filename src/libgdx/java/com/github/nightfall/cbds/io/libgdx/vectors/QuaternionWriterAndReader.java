package com.github.nightfall.cbds.io.libgdx.vectors;

import com.badlogic.gdx.math.Quaternion;
import com.github.nightfall.cbds.io.custom.CustomDeserializer;
import com.github.nightfall.cbds.io.custom.CustomSerializer;
import com.github.nightfall.cbds.io.serial.api.IDeserializer;
import com.github.nightfall.cbds.io.serial.api.ISerializer;

import java.io.IOException;

public class QuaternionWriterAndReader implements CustomSerializer<Quaternion>, CustomDeserializer<Quaternion> {

    @Override
    public void write(ISerializer serializer, Quaternion obj) throws IOException {
        serializer.writeFloat("x", obj.x);
        serializer.writeFloat("y", obj.y);
        serializer.writeFloat("z", obj.z);
        serializer.writeFloat("w", obj.w);
    }

    @Override
    public Quaternion read(IDeserializer deserializer) {
        return new Quaternion(deserializer.readFloat("x"), deserializer.readFloat("y"), deserializer.readFloat("z"), deserializer.readFloat("w"));
    }

    @Override
    public Class<Quaternion> getSerializableType() {
        return Quaternion.class;
    }

}
