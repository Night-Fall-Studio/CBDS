package com.github.nightfall.odsl;

import com.github.nightfall.odsl.io.custom.INamedCustomSerializable;
import com.github.nightfall.odsl.io.custom.IKeylessCustomSerializable;
import com.github.nightfall.odsl.io.serial.api.IKeylessDeserializer;
import com.github.nightfall.odsl.io.serial.api.IKeylessSerializer;
import com.github.nightfall.odsl.io.serial.api.INamedDeserializer;
import com.github.nightfall.odsl.io.serial.api.INamedSerializer;
import com.github.nightfall.odsl.util.ILogWrapper;
import com.github.nightfall.odsl.util.VanillaLogWrapper;

import java.lang.management.ManagementFactory;
import java.util.ServiceLoader;
import java.util.function.Consumer;

/**
 * A settings class that allows *some* customization and initialization of ODSL.
 *
 * @author Mr Zombii
 * @since 1.0.0
 */
public class ODSLConstants {

    public static ILogWrapper LOGGER = new VanillaLogWrapper();
    public static final boolean isDebug = ManagementFactory.getRuntimeMXBean()
            .getInputArguments()
            .toString()
            .contains("-agentlib:jdwp");

    public static boolean allowSerializerOverwriting = true;
    public static boolean allowDeserializerOverwriting = true;

    /**
     * The initialization method that must be called at the start of the program that,
     * allows the registration of custom serializers & deserializers of UnNamed and Named variants.
     */
    public static void init() {
        register(INamedCustomSerializable.class, INamedSerializer::registerSerializer);
        register(INamedCustomSerializable.class, INamedDeserializer::registerDeserializer);

        register(IKeylessCustomSerializable.class, IKeylessSerializer::registerSerializer);
        register(IKeylessCustomSerializable.class, IKeylessDeserializer::registerDeserializer);
    }

    /**
     * A utility method for loading Service Classes using one class reference and one consuming method.
     *
     * @param clazz The class being used as a service.
     * @param method The consumer call the process each service object found.
     */
    public static <T> void register(Class<T> clazz, Consumer<T> method) {
        // Init from other jars
        ServiceLoader<T> objs = ServiceLoader.load(clazz);
        for (T obj : objs)  method.accept(obj);

        // Init from this jar
        objs = ServiceLoader.loadInstalled(clazz);
        for (T obj : objs) method.accept(obj);
    }

}
