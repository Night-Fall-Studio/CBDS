package com.github.nightfall.cbds.io.libgdx.bounds.shapes;

import com.badlogic.gdx.math.Circle;
import com.github.nightfall.cbds.io.custom.CustomDeserializer;
import com.github.nightfall.cbds.io.custom.CustomSerializer;
import com.github.nightfall.cbds.io.serial.api.IDeserializer;
import com.github.nightfall.cbds.io.serial.api.ISerializer;

import java.io.IOException;

public class CircleWriterAndReader implements CustomSerializer<Circle>, CustomDeserializer<Circle> {

    @Override
    public void write(ISerializer serializer, Circle obj) throws IOException {
        serializer.writeFloat("radius", obj.radius);
        serializer.writeFloat("x", obj.x);
        serializer.writeFloat("y", obj.y);
    }

    @Override
    public Circle read(IDeserializer deserializer) {
        return new Circle(
                deserializer.readFloat("radius"),
                deserializer.readFloat("x"),
                deserializer.readFloat("y")
        );
    }

    @Override
    public Class<Circle> getSerializableType() {
        return Circle.class;
    }

}
