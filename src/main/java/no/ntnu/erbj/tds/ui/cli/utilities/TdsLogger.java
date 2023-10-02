package no.ntnu.erbj.tds.ui.cli.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TdsLogger {

  private static final Logger logger = LoggerFactory.getLogger(TdsLogger.class);
  private static final TdsLogger instance = new TdsLogger();

  private TdsLogger() {
    // private constructor to prevent instantiation
  }

  public static TdsLogger getInstance() {
    return instance;
  }

  public void info(String message) {
    logger.info(message);
  }

  public void debug(String message) {
    logger.debug(message);
  }

  public void warn(String message) {
    logger.warn(message);
  }

  public void error(String message) {
    logger.error(message);
  }
}
