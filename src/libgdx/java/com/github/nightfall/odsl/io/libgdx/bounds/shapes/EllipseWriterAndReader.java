package com.github.nightfall.odsl.io.libgdx.bounds.shapes;

import com.badlogic.gdx.math.Ellipse;
import com.github.nightfall.odsl.io.custom.INamedCustomSerializable;
import com.github.nightfall.odsl.io.custom.IKeylessCustomSerializable;
import com.github.nightfall.odsl.io.serial.api.IKeylessDeserializer;
import com.github.nightfall.odsl.io.serial.api.IKeylessSerializer;
import com.github.nightfall.odsl.io.serial.api.INamedDeserializer;
import com.github.nightfall.odsl.io.serial.api.INamedSerializer;

import java.io.IOException;

public class EllipseWriterAndReader implements INamedCustomSerializable<Ellipse>, IKeylessCustomSerializable<Ellipse> {

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
    public Ellipse read(IKeylessDeserializer in) throws IOException{
        return new Ellipse(
                in.readFloat(),
                in.readFloat(),
                in.readFloat(),
                in.readFloat()
        );
    }

    @Override
    public void write(IKeylessSerializer out, Ellipse obj) throws IOException {
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
