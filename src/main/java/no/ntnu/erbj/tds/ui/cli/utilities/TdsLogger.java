package no.ntnu.erbj.tds.ui.cli.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A singleton class that provides logging functionality.
 *
 * @author Erik Bjørnsen
 * @version 1.0
 */
public class TdsLogger {
  private static final Logger logger = LoggerFactory.getLogger(TdsLogger.class);
  private static final TdsLogger instance = new TdsLogger();

  private TdsLogger() {
    // private constructor to prevent instantiation
  }

  /**
   * Get the singleton instance of the TdsLogger class.
   *
   * @return The singleton instance of the TdsLogger class.
   */
  public static TdsLogger getInstance() {
    return instance;
  }

  /**
   * Log a message with the INFO level.
   *
   * @param message The message to be logged.
   */
  public void info(String message) {
    logger.info(message);
  }

  /**
   * Log a message with the DEBUG level.
   *
   * @param message The message to be logged.
   */
  public void debug(String message) {
    logger.debug(message);
  }

  /**
   * Log a message with the WARN level.
   *
   * @param message The message to be logged.
   */
  public void warn(String message) {
    logger.warn(message);
  }

  /**
   * Log a message with the ERROR level.
   *
   * @param message The message to be logged.
   */
  public void error(String message) {
    logger.error(message);
  }
}
