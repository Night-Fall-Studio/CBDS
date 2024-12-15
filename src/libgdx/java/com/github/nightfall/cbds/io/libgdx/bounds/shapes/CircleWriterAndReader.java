package com.github.nightfall.cbds.io.libgdx.bounds.shapes;

import com.badlogic.gdx.math.Circle;
import com.github.nightfall.cbds.io.custom.INamedCustomSerializable;
import com.github.nightfall.cbds.io.custom.IUnNamedCustomSerializable;
import com.github.nightfall.cbds.io.serial.api.IKeylessDeserializer;
import com.github.nightfall.cbds.io.serial.api.IKeylessSerializer;
import com.github.nightfall.cbds.io.serial.api.INamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.INamedSerializer;

import java.io.IOException;

public class CircleWriterAndReader implements INamedCustomSerializable<Circle>, IUnNamedCustomSerializable<Circle> {

    @Override
    public void write(INamedSerializer serializer, Circle obj) throws IOException {
        serializer.writeFloat("radius", obj.radius);
        serializer.writeFloat("x", obj.x);
        serializer.writeFloat("y", obj.y);
    }

    @Override
    public Circle read(INamedDeserializer deserializer) {
        return new Circle(
                deserializer.readFloat("radius"),
                deserializer.readFloat("x"),
                deserializer.readFloat("y")
        );
    }

    @Override
    public Circle read(IKeylessDeserializer in) throws IOException{
        return new Circle(
                in.readFloat(),
                in.readFloat(),
                in.readFloat()
        );
    }

    @Override
    public void write(IKeylessSerializer out, Circle obj) throws IOException {
        out.writeFloat(obj.radius);
        out.writeFloat(obj.x);
        out.writeFloat(obj.y);
    }

    @Override
    public Class<Circle> getSerializableType() {
        return Circle.class;
    }

}
