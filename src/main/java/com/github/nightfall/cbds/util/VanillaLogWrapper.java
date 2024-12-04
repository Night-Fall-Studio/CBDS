package com.github.nightfall.cbds.util;

import java.util.logging.Logger;

public class VanillaLogWrapper implements LogWrapper {

    Logger logger = Logger.getLogger("CBDS");

    @Override
    public void info(String s) {
        logger.info(s);
    }

    @Override
    public void warn(String s) {
        logger.warning(s);
    }

    @Override
    public void error(String s) {
        logger.severe(s);
    }
}
