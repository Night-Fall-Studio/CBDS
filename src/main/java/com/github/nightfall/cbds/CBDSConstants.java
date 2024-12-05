package com.github.nightfall.cbds;

import com.github.nightfall.cbds.io.custom.INamedCustomSerializable;
import com.github.nightfall.cbds.io.custom.IUnNamedCustomSerializable;
import com.github.nightfall.cbds.io.serial.api.INamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.INamedSerializer;
import com.github.nightfall.cbds.io.serial.api.IUnNamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.IUnNamedSerializer;
import com.github.nightfall.cbds.util.LogWrapper;
import com.github.nightfall.cbds.util.VanillaLogWrapper;

import java.util.ServiceLoader;
import java.util.function.Consumer;

public class CBDSConstants {

    public static LogWrapper LOGGER = new VanillaLogWrapper();

    public static boolean allowSerializerOverwriting = true;
    public static boolean allowDeserializerOverwriting = true;

    public static void init() {
        register(INamedCustomSerializable.class, INamedSerializer::registerSerializer);
        register(INamedCustomSerializable.class, INamedDeserializer::registerDeserializer);

        register(IUnNamedCustomSerializable.class, IUnNamedSerializer::registerSerializer);
        register(IUnNamedCustomSerializable.class, IUnNamedDeserializer::registerDeserializer);
    }

    public static <T> void register(Class<T> clazz, Consumer<T> method) {
        // Init from other jars
        ServiceLoader<T> objs = ServiceLoader.load(clazz);
        for (T obj : objs)  method.accept(obj);

        // Init from this jar
        objs = ServiceLoader.loadInstalled(clazz);
        for (T obj : objs) method.accept(obj);
    }

}
