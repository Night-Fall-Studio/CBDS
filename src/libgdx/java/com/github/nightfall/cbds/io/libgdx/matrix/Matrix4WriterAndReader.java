package com.github.nightfall.cbds.io.libgdx.matrix;

import com.badlogic.gdx.math.Matrix4;
import com.github.nightfall.cbds.io.custom.CustomDeserializer;
import com.github.nightfall.cbds.io.custom.CustomSerializer;
import com.github.nightfall.cbds.io.serial.api.IDeserializer;
import com.github.nightfall.cbds.io.serial.api.ISerializer;

import java.io.IOException;

public class Matrix4WriterAndReader implements CustomSerializer<Matrix4>, CustomDeserializer<Matrix4> {

    @Override
    public void write(ISerializer serializer, Matrix4 obj) throws IOException {
        serializer.writeFloatArray("val", obj.val);
    }

    @Override
    public Matrix4 read(IDeserializer deserializer) {
        return new Matrix4(deserializer.readFloatArrayAsPrimitive("val"));
    }

    @Override
    public Class<Matrix4> getSerializableType() {
        return Matrix4.class;
    }

}
