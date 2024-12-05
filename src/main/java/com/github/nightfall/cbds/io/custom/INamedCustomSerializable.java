package com.github.nightfall.cbds.io.custom;

import com.github.nightfall.cbds.io.serial.api.INamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.INamedSerializer;

import java.io.IOException;

public interface INamedCustomSerializable<T> {

    T read(INamedDeserializer in);
    void write(INamedSerializer out, T obj) throws IOException;

    Class<T> getSerializableType();

}
