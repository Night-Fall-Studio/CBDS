package com.github.nightfall.cbds.io.libgdx.bounds;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.github.nightfall.cbds.io.custom.CustomDeserializer;
import com.github.nightfall.cbds.io.custom.CustomSerializer;
import com.github.nightfall.cbds.io.serial.api.IDeserializer;
import com.github.nightfall.cbds.io.serial.api.ISerializer;

import java.io.IOException;

public class BoundingBoxWriterAndReader implements CustomSerializer<BoundingBox>, CustomDeserializer<BoundingBox> {

    @Override
    public void write(ISerializer serializer, BoundingBox obj) throws IOException {
        serializer.writeCustomObject("min", obj.min);
        serializer.writeCustomObject("max", obj.max);
    }

    @Override
    public BoundingBox read(IDeserializer deserializer) {
        return new BoundingBox(
                deserializer.readCustomObject(Vector3.class, "min"),
                deserializer.readCustomObject(Vector3.class, "max")
        );
    }

    @Override
    public Class<BoundingBox> getSerializableType() {
        return BoundingBox.class;
    }

}
