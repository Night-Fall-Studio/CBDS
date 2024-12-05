package com.github.nightfall.cbds.io.serial.obj;

import com.github.nightfall.cbds.io.serial.api.INamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.INamedSerializer;

import java.io.IOException;

public interface INamedSerializable {

    void read(INamedDeserializer in) throws IOException;
    void write(INamedSerializer out) throws IOException;

}
