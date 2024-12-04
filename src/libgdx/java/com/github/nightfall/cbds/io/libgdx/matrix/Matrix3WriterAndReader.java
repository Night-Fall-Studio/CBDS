package com.github.nightfall.cbds.io.libgdx.matrix;

import com.badlogic.gdx.math.Matrix3;
import com.github.nightfall.cbds.io.custom.CustomDeserializer;
import com.github.nightfall.cbds.io.custom.CustomSerializer;
import com.github.nightfall.cbds.io.serial.api.IDeserializer;
import com.github.nightfall.cbds.io.serial.api.ISerializer;

import java.io.IOException;

public class Matrix3WriterAndReader implements CustomSerializer<Matrix3>, CustomDeserializer<Matrix3> {

    @Override
    public void write(ISerializer serializer, Matrix3 obj) throws IOException {
        serializer.writeFloatArray("val", obj.val);
    }

    @Override
    public Matrix3 read(IDeserializer deserializer) {
        return new Matrix3(deserializer.readFloatArrayAsPrimitive("val"));
    }

    @Override
    public Class<Matrix3> getSerializableType() {
        return Matrix3.class;
    }

}
