package com.github.nightfall.cbds.io.custom;

import com.github.nightfall.cbds.io.serial.api.IUnNamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.IUnNamedSerializer;

import java.io.IOException;

public interface IUnNamedCustomSerializable<T> {

    T read(IUnNamedDeserializer in) throws IOException;
    void write(IUnNamedSerializer out, T obj) throws IOException;

    Class<T> getSerializableType();

}
