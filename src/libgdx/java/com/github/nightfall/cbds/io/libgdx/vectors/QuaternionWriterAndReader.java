package com.github.nightfall.cbds.io.libgdx.vectors;

import com.badlogic.gdx.math.Quaternion;
import com.github.nightfall.cbds.io.custom.INamedCustomSerializable;
import com.github.nightfall.cbds.io.custom.IUnNamedCustomSerializable;
import com.github.nightfall.cbds.io.serial.api.IKeylessDeserializer;
import com.github.nightfall.cbds.io.serial.api.IKeylessSerializer;
import com.github.nightfall.cbds.io.serial.api.INamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.INamedSerializer;

import java.io.IOException;

public class QuaternionWriterAndReader implements INamedCustomSerializable<Quaternion>, IUnNamedCustomSerializable<Quaternion> {

    @Override
    public void write(INamedSerializer serializer, Quaternion obj) throws IOException {
        serializer.writeFloat("x", obj.x);
        serializer.writeFloat("y", obj.y);
        serializer.writeFloat("z", obj.z);
        serializer.writeFloat("w", obj.w);
    }

    @Override
    public Quaternion read(INamedDeserializer deserializer) {
        return new Quaternion(
                deserializer.readFloat("x"),
                deserializer.readFloat("y"),
                deserializer.readFloat("z"),
                deserializer.readFloat("w")
        );
    }

    @Override
    public Quaternion read(IKeylessDeserializer in) throws IOException{
        return new Quaternion(
                in.readFloat(),
                in.readFloat(),
                in.readFloat(),
                in.readFloat()
        );
    }

    @Override
    public void write(IKeylessSerializer out, Quaternion obj) throws IOException {
        out.writeFloat(obj.x);
        out.writeFloat(obj.y);
        out.writeFloat(obj.z);
        out.writeFloat(obj.w);
    }

    @Override
    public Class<Quaternion> getSerializableType() {
        return Quaternion.class;
    }

}
