package com.github.nightfall.odsl.io.custom;

import com.github.nightfall.odsl.io.serial.api.INamedDeserializer;
import com.github.nightfall.odsl.io.serial.api.INamedSerializer;

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
     * Deserializes data and creates a new object of class type it's responsible for.
     *
     * @param in The parent serializer that contains all the data for the class being deserialized.
    */
    T read(INamedDeserializer in);

    /**
     * Serializes data from the provided object.
     *
     * @param out The parent serializer that is being written to.
     * @param obj The object being serialized.
     */
    void write(INamedSerializer out, T obj) throws IOException;

    /**
     * Gets the class this serializable is responsible for.
     */
    Class<T> getSerializableType();

}
