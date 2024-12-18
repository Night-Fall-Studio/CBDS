package com.github.nightfall.cbds.io.custom;

import com.github.nightfall.cbds.io.serial.api.INamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.INamedSerializer;

import java.io.IOException;

/**
 * An API class for creating custom serialization & deserialization for any class when using any INamedSerializer and INamedDeserializer.
 *
 * @see INamedSerializer
 * @see INamedDeserializer
 *
 * @author Mr Zombii
 * @since 1.0.0
 */
public interface INamedCustomSerializable<T> {

    /**
     * The method used to deserialize classes after being read from a deserializer.
     *
     * @param in The parent serializer that contains all the data for the class being deserialized.
    */
    T read(INamedDeserializer in);

    /**
     * The method called when this class is being serialized.
     *
     * @param out The parent serializer that is being written to.
     * @param obj The object being serialized.
     */
    void write(INamedSerializer out, T obj) throws IOException;

    /**
     * The class that this serializable controls during serialization and deserialization happens.
     */
    Class<T> getSerializableType();

}
