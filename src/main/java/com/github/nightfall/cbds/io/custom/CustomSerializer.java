package com.github.nightfall.cbds.io.custom;

import com.github.nightfall.cbds.io.serial.api.ISerializer;

import java.io.IOException;

public interface CustomSerializer<T> {

    void write(ISerializer serializer, T obj) throws IOException;

    Class<T> getSerializableType();

}
