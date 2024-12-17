package com.github.nightfall.cbds.io.serial.obj;

import com.github.nightfall.cbds.io.serial.api.INamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.INamedSerializer;

import java.io.IOException;

/**
 * An API class for objects impls that allows custom serialization and deserialization.
 *
 * @see INamedSerializer
 * @see INamedDeserializer
 *
 * @author Mr Zombii
 * @since 1.0.0
 */
public interface INamedSerializable {

    /**
     * The method called on the uninitialized object when its being read from a deserializer.
     * @param in The parent deserializer that contains all the data for the class being deserialized.
     */
    void read(INamedDeserializer in) throws IOException;

    /**
     * The method called on the object when its being serialized.
     * @param out The parent serializer that is being written to.
     */
    void write(INamedSerializer out) throws IOException;

}
