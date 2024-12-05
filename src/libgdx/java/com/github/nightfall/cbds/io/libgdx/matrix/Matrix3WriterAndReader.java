package com.github.nightfall.cbds.io.libgdx.matrix;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.github.nightfall.cbds.io.custom.INamedCustomSerializable;
import com.github.nightfall.cbds.io.custom.IUnNamedCustomSerializable;
import com.github.nightfall.cbds.io.serial.api.INamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.INamedSerializer;
import com.github.nightfall.cbds.io.serial.api.IUnNamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.IUnNamedSerializer;

import java.io.IOException;

public class Matrix3WriterAndReader implements INamedCustomSerializable<Matrix3>, IUnNamedCustomSerializable<Matrix3> {

    @Override
    public void write(INamedSerializer serializer, Matrix3 obj) throws IOException {
        serializer.writeFloatArray("val", obj.val);
    }

    @Override
    public Matrix3 read(INamedDeserializer deserializer) {
        return new Matrix3(deserializer.readFloatArrayAsPrimitive("val"));
    }

    @Override
    public Matrix3 read(IUnNamedDeserializer in) throws IOException{
        return new Matrix3(in.readFloatArrayAsPrimitive());
    }

    @Override
    public void write(IUnNamedSerializer out, Matrix3 obj) throws IOException {
        out.writeFloatArray(obj.val);
    }

    @Override
    public Class<Matrix3> getSerializableType() {
        return Matrix3.class;
    }

}
