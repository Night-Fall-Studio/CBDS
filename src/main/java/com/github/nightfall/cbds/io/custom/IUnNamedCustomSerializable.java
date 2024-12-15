package com.github.nightfall.cbds.io.custom;

import com.github.nightfall.cbds.io.serial.api.IKeylessDeserializer;
import com.github.nightfall.cbds.io.serial.api.IKeylessSerializer;

import java.io.IOException;

public interface IUnNamedCustomSerializable<T> {

    T read(IKeylessDeserializer in) throws IOException;
    void write(IKeylessSerializer out, T obj) throws IOException;

    Class<T> getSerializableType();

}
