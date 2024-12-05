package com.github.nightfall.cbds.util;

import java.io.IOException;

public interface ThrowableConsumer<T> {

    void consume(T o) throws IOException;

}
