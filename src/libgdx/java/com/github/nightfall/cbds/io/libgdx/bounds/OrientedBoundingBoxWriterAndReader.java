package com.github.nightfall.cbds.io.libgdx.bounds;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.OrientedBoundingBox;
import com.github.nightfall.cbds.io.custom.INamedCustomSerializable;
import com.github.nightfall.cbds.io.custom.IUnNamedCustomSerializable;
import com.github.nightfall.cbds.io.serial.api.IKeylessDeserializer;
import com.github.nightfall.cbds.io.serial.api.IKeylessSerializer;
import com.github.nightfall.cbds.io.serial.api.INamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.INamedSerializer;

import java.io.IOException;

public class OrientedBoundingBoxWriterAndReader implements INamedCustomSerializable<OrientedBoundingBox>, IUnNamedCustomSerializable<OrientedBoundingBox> {

    @Override
    public void write(INamedSerializer serializer, OrientedBoundingBox obj) throws IOException {
        serializer.writeCustomObject("bounds", obj.getBounds());
        serializer.writeCustomObject("transform", obj.transform);
    }

    @Override
    public OrientedBoundingBox read(INamedDeserializer deserializer) {
        return new OrientedBoundingBox(
                deserializer.readCustomObject("bounds", BoundingBox.class),
                deserializer.readCustomObject("transform", Matrix4.class)
        );
    }

    @Override
    public OrientedBoundingBox read(IKeylessDeserializer in) throws IOException {
        return new OrientedBoundingBox(
                in.readCustomObject(BoundingBox.class),
                in.readCustomObject(Matrix4.class)
        );
    }

    @Override
    public void write(IKeylessSerializer out, OrientedBoundingBox obj) throws IOException {
        out.writeCustomObject(obj.getBounds());
        out.writeCustomObject(obj.transform);
    }

    @Override
    public Class<OrientedBoundingBox> getSerializableType() {
        return OrientedBoundingBox.class;
    }

}
