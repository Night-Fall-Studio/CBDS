package com.github.nightfall.cbds.io.custom;

import com.github.nightfall.cbds.io.serial.api.IDeserializer;

public interface CustomDeserializer<T> {

    T read(IDeserializer deserializer);

    Class<T> getSerializableType();

}
