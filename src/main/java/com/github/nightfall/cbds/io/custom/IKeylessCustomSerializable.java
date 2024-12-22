package com.github.nightfall.cbds.io.custom;

import com.github.nightfall.cbds.io.serial.api.IKeylessDeserializer;
import com.github.nightfall.cbds.io.serial.api.IKeylessSerializer;
import com.github.nightfall.cbds.io.serial.api.INamedDeserializer;

import java.io.IOException;

/**
 * An API class for creating custom serialization & deserialization for any class when using any IKeylessSerializer and IKeylessDeserializer.
 *
 * @see IKeylessSerializer
 * @see IKeylessDeserializer
 *
 * @author Mr Zombii
 * @since 1.0.0
 */
public interface IKeylessCustomSerializable<T> {


    /**
     * Deserializes data and creates a new object of class type it's responsible for.
     *
     * @param in The parent serializer that contains all the data for the class being deserialized.
     */
    T read(IKeylessDeserializer in) throws IOException;

    /**
     * Serializes data from the provided object.
     *
     * @param out The parent serializer that is being written to.
     * @param obj The object being serialized.
     */
    void write(IKeylessSerializer out, T obj) throws IOException;

    /**
     * Gets the class this serializable is responsible for.
     */
    Class<T> getSerializableType();

}
