package com.github.nightfall.cbds.io.serial.obj;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * An API class for objects impls that allows custom serialization and deserialization to and from streams.
 *
 * @see DataOutputStream
 * @see DataInputStream
 *
 * @author Mr Zombii
 * @since 1.0.0
 */
public interface IDataStreamSerializable {

    /**
     * The method called on the uninitialized object when its being read from a stream.
     * @param in The parent stream that contains all the data for the class being read.
     */
    void read(DataInputStream in) throws IOException;

    /**
     * The method called on the object when its being written to the stream.
     * @param out The parent stream that is being written to.
     */
    void write(DataOutputStream out) throws IOException;
}
