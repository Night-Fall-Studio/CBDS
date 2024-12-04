package com.github.nightfall.cbds.io.libgdx.bounds;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.OrientedBoundingBox;
import com.github.nightfall.cbds.io.custom.CustomDeserializer;
import com.github.nightfall.cbds.io.custom.CustomSerializer;
import com.github.nightfall.cbds.io.serial.api.IDeserializer;
import com.github.nightfall.cbds.io.serial.api.ISerializer;

import java.io.IOException;

public class OrientedBoundingBoxWriterAndReader implements CustomSerializer<OrientedBoundingBox>, CustomDeserializer<OrientedBoundingBox> {

    @Override
    public void write(ISerializer serializer, OrientedBoundingBox obj) throws IOException {
        serializer.writeCustomObject("bounds", obj.getBounds());
        serializer.writeCustomObject("transform", obj.transform);
    }

    @Override
    public OrientedBoundingBox read(IDeserializer deserializer) {
        return new OrientedBoundingBox(
                deserializer.readCustomObject(BoundingBox.class, "bounds"),
                deserializer.readCustomObject(Matrix4.class, "transform")
        );
    }

    @Override
    public Class<OrientedBoundingBox> getSerializableType() {
        return OrientedBoundingBox.class;
    }

}
