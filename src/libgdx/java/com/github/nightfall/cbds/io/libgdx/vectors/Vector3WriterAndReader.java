package com.github.nightfall.cbds.io.libgdx.vectors;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Vector4;
import com.github.nightfall.cbds.io.custom.INamedCustomSerializable;
import com.github.nightfall.cbds.io.custom.IUnNamedCustomSerializable;
import com.github.nightfall.cbds.io.serial.api.INamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.INamedSerializer;
import com.github.nightfall.cbds.io.serial.api.IUnNamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.IUnNamedSerializer;

import java.io.IOException;

public class Vector3WriterAndReader implements INamedCustomSerializable<Vector3>, IUnNamedCustomSerializable<Vector3> {

    @Override
    public void write(INamedSerializer serializer, Vector3 obj) throws IOException {
        serializer.writeFloat("x", obj.x);
        serializer.writeFloat("y", obj.y);
        serializer.writeFloat("z", obj.z);
    }

    @Override
    public Vector3 read(INamedDeserializer deserializer) {
        return new Vector3(
                deserializer.readFloat("x"),
                deserializer.readFloat("y"),
                deserializer.readFloat("z")
        );
    }

    @Override
    public Vector3 read(IUnNamedDeserializer in) throws IOException{
        return new Vector3(
                in.readFloat(),
                in.readFloat(),
                in.readFloat()
        );
    }

    @Override
    public void write(IUnNamedSerializer out, Vector3 obj) throws IOException {
        out.writeFloat(obj.x);
        out.writeFloat(obj.y);
        out.writeFloat(obj.z);
    }

    @Override
    public Class<Vector3> getSerializableType() {
        return Vector3.class;
    }

}
