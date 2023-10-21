package no.ntnu.erbj.tds.ui.cli.utilities;

/**
 * A class that provides ANSI escape codes for colorizing text in the terminal.
 *
 * <p>ANSI escape codes are used to colorize text in the terminal. This class provides a set of
 * constants that can be used to colorize text in the terminal.
 *
 * @author Erik
 * @version 1.0
 * @see <a href="https://en.wikipedia.org/wiki/ANSI_escape_code">ANSI escape code</a>
 */
public class ANSIColors {

  public static final String RESET = "\u001B[0m";
  public static final String BLACK = "\u001B[30m";
  public static final String RED = "\u001B[31m";
  public static final String GREEN = "\u001B[32m";
  public static final String YELLOW = "\u001B[33m";
  public static final String BLUE = "\u001B[34m";
  public static final String PURPLE = "\u001B[35m";
  public static final String CYAN = "\u001B[36m";
  public static final String WHITE = "\u001B[37m";
  public static final String BG_BLACK = "\u001B[40m";
  public static final String BG_RED = "\u001B[41m";
  public static final String BG_GREEN = "\u001B[42m";
  public static final String BG_YELLOW = "\u001B[43m";
  public static final String BG_BLUE = "\u001B[44m";
  public static final String BG_PURPLE = "\u001B[45m";
  public static final String BG_CYAN = "\u001B[46m";
  public static final String BG_WHITE = "\u001B[47m";

  /**
   * Colorize a message with a text color.
   *
   * @param color The text color.
   * @param message The message to be colorized.
   * @return The colorized message.
   */
  public static String colorizeText(String color, String message) {
    return color + message + RESET;
  }

  /**
   * Colorize a message with a background color and a text color.
   *
   * @param bgColor The background color.
   * @param textColor The text color.
   * @param message The message to be colorized.
   * @return The colorized message.
   */
  public static String colorizeBackgroundAndText(String bgColor, String textColor, String message) {
    return bgColor + textColor + message + RESET;
  }

  /**
   * Colorize a message with a background color.
   *
   * @param bgColor The background color.
   * @param message The message to be colorized.
   * @return The colorized message.
   */
  public static String colorizeBackground(String bgColor, String message) {
    return bgColor + message + RESET;
  }
}
