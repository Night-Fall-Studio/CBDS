package com.github.nightfall.cbds.io.libgdx.bounds.shapes;

import com.badlogic.gdx.math.Rectangle;
import com.github.nightfall.cbds.io.custom.INamedCustomSerializable;
import com.github.nightfall.cbds.io.custom.IKeylessCustomSerializable;
import com.github.nightfall.cbds.io.serial.api.IKeylessDeserializer;
import com.github.nightfall.cbds.io.serial.api.IKeylessSerializer;
import com.github.nightfall.cbds.io.serial.api.INamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.INamedSerializer;

import java.io.IOException;

public class RectangleWriterAndReader implements INamedCustomSerializable<Rectangle>, IKeylessCustomSerializable<Rectangle> {

    @Override
    public void write(INamedSerializer serializer, Rectangle obj) throws IOException {
        serializer.writeFloat("x", obj.x);
        serializer.writeFloat("y", obj.y);
        serializer.writeFloat("width", obj.width);
        serializer.writeFloat("height", obj.height);
    }

    @Override
    public Rectangle read(INamedDeserializer deserializer) {
        return new Rectangle(
                deserializer.readFloat("x"),
                deserializer.readFloat("y"),
                deserializer.readFloat("width"),
                deserializer.readFloat("height")
        );
    }

    @Override
    public Rectangle read(IKeylessDeserializer in) throws IOException{
        return new Rectangle(
                in.readFloat(),
                in.readFloat(),
                in.readFloat(),
                in.readFloat()
        );
    }

    @Override
    public void write(IKeylessSerializer out, Rectangle obj) throws IOException {
        out.writeFloat(obj.x);
        out.writeFloat(obj.y);
        out.writeFloat(obj.width);
        out.writeFloat(obj.height);
    }

    @Override
    public Class<Rectangle> getSerializableType() {
        return Rectangle.class;
    }

}
