package com.github.nightfall.cbds.io.libgdx.matrix;

import com.badlogic.gdx.math.Matrix4;
import com.github.nightfall.cbds.io.custom.INamedCustomSerializable;
import com.github.nightfall.cbds.io.custom.IUnNamedCustomSerializable;
import com.github.nightfall.cbds.io.serial.api.INamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.INamedSerializer;
import com.github.nightfall.cbds.io.serial.api.IKeylessDeserializer;
import com.github.nightfall.cbds.io.serial.api.IKeylessSerializer;

import java.io.IOException;

public class Matrix4WriterAndReader implements INamedCustomSerializable<Matrix4>, IUnNamedCustomSerializable<Matrix4> {

    @Override
    public void write(INamedSerializer serializer, Matrix4 obj) throws IOException {
        serializer.writeFloatArray("val", obj.val);
    }

    @Override
    public Matrix4 read(INamedDeserializer deserializer) {
        return new Matrix4(deserializer.readFloatArrayAsPrimitive("val"));
    }

    @Override
    public Matrix4 read(IKeylessDeserializer in) throws IOException{
        return new Matrix4(in.readFloatArrayAsPrimitive());
    }

    @Override
    public void write(IKeylessSerializer out, Matrix4 obj) throws IOException {
        out.writeFloatArray(obj.val);
    }

    @Override
    public Class<Matrix4> getSerializableType() {
        return Matrix4.class;
    }

}
