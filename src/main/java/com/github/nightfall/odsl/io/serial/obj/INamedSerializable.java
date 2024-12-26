package com.github.nightfall.odsl.io.serial.obj;

import com.github.nightfall.odsl.io.serial.api.INamedDeserializer;
import com.github.nightfall.odsl.io.serial.api.INamedSerializer;

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
     * Deserializes data for use inside this serializable.
     * @param in The parent deserializer that contains all the data for the class being deserialized.
     */
    void read(INamedDeserializer in) throws IOException;

    /**
     * Writes the data from this object to a serializer.
     * @param out The parent serializer that is being written to.
     */
    void write(INamedSerializer out) throws IOException;

}
