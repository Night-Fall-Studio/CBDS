package com.github.nightfall.cbds.io.serial.obj;

import com.github.nightfall.cbds.io.serial.api.IKeylessDeserializer;
import com.github.nightfall.cbds.io.serial.api.IKeylessSerializer;
import com.github.nightfall.cbds.io.serial.api.INamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.INamedSerializer;

import java.io.IOException;

/**
 * An API class for objects impls that allows custom serialization and deserialization.
 *
 * @see IKeylessSerializer
 * @see IKeylessDeserializer
 *
 * @author Mr Zombii
 * @since 1.0.0
 */
public interface IKeylessSerializable {

    /**
     * The method called on the uninitialized object when its being read from a deserializer
     * @param in The parent deserializer that contains all the data for the class being deserialized.
     */
    void read(IKeylessDeserializer in) throws IOException;

    /**
     * The method called on the object when its being serialized.
     * @param out The parent serializer that is being written to.
     */
    void write(IKeylessSerializer out) throws IOException;
}
