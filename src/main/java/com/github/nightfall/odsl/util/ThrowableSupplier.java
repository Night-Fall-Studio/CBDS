package com.github.nightfall.odsl.util;

import java.io.IOException;

/**
 * The basic interface for a supplier or method reference that wants to throw an IOException.
 *
 * @author Mr Zombii
 * @since 1.0.0
 */
public interface ThrowableSupplier<T> {

    T get() throws IOException;

}
