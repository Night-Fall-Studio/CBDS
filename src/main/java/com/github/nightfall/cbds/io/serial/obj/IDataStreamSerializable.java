package com.github.nightfall.cbds.io.serial.obj;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface IDataStreamSerializable {

    void read(DataInputStream in) throws IOException;
    void write(DataOutputStream out) throws IOException;

}
