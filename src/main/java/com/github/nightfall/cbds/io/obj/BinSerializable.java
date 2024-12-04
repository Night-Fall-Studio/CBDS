package com.github.nightfall.cbds.io.obj;

import com.github.nightfall.cbds.io.serial.api.IDeserializer;
import com.github.nightfall.cbds.io.serial.api.ISerializer;

import java.io.IOException;

public interface BinSerializable {

    void read(IDeserializer deserializer) throws IOException;
    void write(ISerializer serializer) throws IOException;

}
