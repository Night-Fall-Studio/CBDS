package com.github.nightfall.cbds.io.serial.obj;

import com.github.nightfall.cbds.io.serial.api.IUnNamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.IUnNamedSerializer;

import java.io.IOException;

public interface IUnNamedSerializable {

    void read(IUnNamedDeserializer in) throws IOException;
    void write(IUnNamedSerializer out) throws IOException;

}
