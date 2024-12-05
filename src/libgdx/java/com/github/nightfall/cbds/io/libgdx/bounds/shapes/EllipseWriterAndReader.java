package com.github.nightfall.cbds.io.libgdx.bounds.shapes;

import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.github.nightfall.cbds.io.custom.INamedCustomSerializable;
import com.github.nightfall.cbds.io.custom.IUnNamedCustomSerializable;
import com.github.nightfall.cbds.io.serial.api.INamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.INamedSerializer;
import com.github.nightfall.cbds.io.serial.api.IUnNamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.IUnNamedSerializer;

import java.io.IOException;

public class EllipseWriterAndReader implements INamedCustomSerializable<Ellipse>, IUnNamedCustomSerializable<Ellipse> {

    @Override
    public void write(INamedSerializer serializer, Ellipse obj) throws IOException {
        serializer.writeFloat("x", obj.x);
        serializer.writeFloat("y", obj.y);
        serializer.writeFloat("width", obj.width);
        serializer.writeFloat("height", obj.height);
    }

    @Override
    public Ellipse read(INamedDeserializer deserializer) {
        return new Ellipse(
                deserializer.readFloat("x"),
                deserializer.readFloat("y"),
                deserializer.readFloat("width"),
                deserializer.readFloat("height")
        );
    }

    @Override
    public Ellipse read(IUnNamedDeserializer in) throws IOException{
        return new Ellipse(
                in.readFloat(),
                in.readFloat(),
                in.readFloat(),
                in.readFloat()
        );
    }

    @Override
    public void write(IUnNamedSerializer out, Ellipse obj) throws IOException {
        out.writeFloat(obj.x);
        out.writeFloat(obj.y);
        out.writeFloat(obj.width);
        out.writeFloat(obj.height);
    }

    @Override
    public Class<Ellipse> getSerializableType() {
        return Ellipse.class;
    }

}
