package com.github.nightfall.cbds;

import com.github.nightfall.cbds.io.custom.CustomDeserializer;
import com.github.nightfall.cbds.io.custom.CustomSerializer;
import com.github.nightfall.cbds.io.serial.api.IDeserializer;
import com.github.nightfall.cbds.io.serial.api.ISerializer;
import com.github.nightfall.cbds.util.LogWrapper;
import com.github.nightfall.cbds.util.VanillaLogWrapper;

import java.util.ServiceLoader;

@SuppressWarnings("rawtypes")
public class CBDSConstants {

    public static LogWrapper LOGGER = new VanillaLogWrapper();

    public static boolean allowSerializerOverwriting = true;
    public static boolean allowDeserializerOverwriting = true;

    public static void init() {
        // Initializing deserializers from other jars
        ServiceLoader<CustomDeserializer> deserializers = ServiceLoader.load(CustomDeserializer.class);
        for (CustomDeserializer<?> deserializer : deserializers) {
            IDeserializer.registerDeserializer(deserializer);
        }

        // Initializing deserializers from this jar
        deserializers = ServiceLoader.loadInstalled(CustomDeserializer.class);
        for (CustomDeserializer<?> deserializer : deserializers) {
            IDeserializer.registerDeserializer(deserializer);
        }

        // Initializing serializers from other jars
        ServiceLoader<CustomSerializer> serializers = ServiceLoader.load(CustomSerializer.class);
        for (CustomSerializer<?> serializer : serializers) {
            ISerializer.registerSerializer(serializer);
        }

        // Initializing serializers from this jar
        serializers = ServiceLoader.loadInstalled(CustomSerializer.class);
        for (CustomSerializer<?> serializer : serializers) {
            ISerializer.registerSerializer(serializer);
        }
    }

}
