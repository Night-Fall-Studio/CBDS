package com.github.nightfall.odsl.io.libgdx.matrix;

import com.badlogic.gdx.math.Matrix3;
import com.github.nightfall.odsl.io.custom.INamedCustomSerializable;
import com.github.nightfall.odsl.io.custom.IKeylessCustomSerializable;
import com.github.nightfall.odsl.io.serial.api.IKeylessDeserializer;
import com.github.nightfall.odsl.io.serial.api.IKeylessSerializer;
import com.github.nightfall.odsl.io.serial.api.INamedDeserializer;
import com.github.nightfall.odsl.io.serial.api.INamedSerializer;

import java.io.IOException;

public class Matrix3WriterAndReader implements INamedCustomSerializable<Matrix3>, IKeylessCustomSerializable<Matrix3> {

    @Override
    public void write(INamedSerializer serializer, Matrix3 obj) throws IOException {
        serializer.writeFloatArray("val", obj.val);
    }

    @Override
    public Matrix3 read(INamedDeserializer deserializer) {
        return new Matrix3(deserializer.readFloatArrayAsNative("val"));
    }

    @Override
    public Matrix3 read(IKeylessDeserializer in) throws IOException{
        return new Matrix3(in.readFloatArrayAsNative());
    }

    @Override
    public void write(IKeylessSerializer out, Matrix3 obj) throws IOException {
        out.writeFloatArray(obj.val);
    }

    @Override
    public Class<Matrix3> getSerializableType() {
        return Matrix3.class;
    }

}
