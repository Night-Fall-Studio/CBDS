package com.github.nightfall.cbds.util;

import java.io.IOException;

public interface ThrowableSupplier<T> {

    T get() throws IOException;

}
