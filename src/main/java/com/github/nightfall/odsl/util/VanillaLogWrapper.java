package com.github.nightfall.odsl.util;

import com.github.nightfall.odsl.ODSLConstants;

import java.util.logging.Logger;

/**
 * The basic logWrapper implementation using the built-in java logger.
 *
 * @author Mr Zombii
 * @since 1.0.0
 */
public class VanillaLogWrapper implements ILogWrapper {

    Logger logger = Logger.getLogger("ODSL");

    public void info(String s) {
        logger.info(s);
    }

    public void warn(String s) {
        logger.warning(s);
    }

    public void error(String s) {
        logger.severe(s);
    }

    public void debug(String s) {
        if (ODSLConstants.isDebug)
            logger.info(s);
    }
}
