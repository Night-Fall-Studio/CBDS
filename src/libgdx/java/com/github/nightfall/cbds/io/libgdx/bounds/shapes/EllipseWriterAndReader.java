package com.github.nightfall.cbds.io.libgdx.bounds.shapes;

import com.badlogic.gdx.math.Ellipse;
import com.github.nightfall.cbds.io.custom.CustomDeserializer;
import com.github.nightfall.cbds.io.custom.CustomSerializer;
import com.github.nightfall.cbds.io.serial.api.IDeserializer;
import com.github.nightfall.cbds.io.serial.api.ISerializer;

import java.io.IOException;

public class EllipseWriterAndReader implements CustomSerializer<Ellipse>, CustomDeserializer<Ellipse> {

    @Override
    public void write(ISerializer serializer, Ellipse obj) throws IOException {
        serializer.writeFloat("x", obj.x);
        serializer.writeFloat("y", obj.y);
        serializer.writeFloat("width", obj.width);
        serializer.writeFloat("height", obj.height);
    }

    @Override
    public Ellipse read(IDeserializer deserializer) {
        return new Ellipse(
                deserializer.readFloat("x"),
                deserializer.readFloat("y"),
                deserializer.readFloat("width"),
                deserializer.readFloat("height")
        );
    }

    @Override
    public Class<Ellipse> getSerializableType() {
        return Ellipse.class;
    }

}
