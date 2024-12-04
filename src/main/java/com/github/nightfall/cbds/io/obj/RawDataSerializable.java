package com.github.nightfall.cbds.io.obj;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface RawDataSerializable {

    void read(DataInputStream in) throws IOException;
    void write(DataOutputStream out) throws IOException;

}
