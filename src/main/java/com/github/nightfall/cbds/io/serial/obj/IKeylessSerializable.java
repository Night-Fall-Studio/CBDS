package com.github.nightfall.cbds.io.serial.obj;

import com.github.nightfall.cbds.io.serial.api.IKeylessDeserializer;
import com.github.nightfall.cbds.io.serial.api.IKeylessSerializer;

import java.io.IOException;

public interface IKeylessSerializable {

    void read(IKeylessDeserializer in) throws IOException;
    void write(IKeylessSerializer out) throws IOException;

}
