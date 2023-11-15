package no.ntnu.erbj.tds.ui.utilities;

/**
 * A class that colorizes text in the terminal. Uses ANSI escape codes (see enum).
 *
 * @author Erik
 * @version 1.0
 * @see AnsiColors
 * @see <a href="https://en.wikipedia.org/wiki/ANSI_escape_code">ANSI escape code</a>
 */
public class Colorize {

  public static final String RESET = "\u001B[0m";

  /**
   * Private constructor to prevent instantiation.
   *
   * @see <a href="https://rules.sonarsource.com/java/RSPEC-1118">SonarLint rule</a>
   */
  private Colorize() {
    // private constructor to hide the implicit public one
  }

  /**
   * Colorize a message with a text color.
   *
   * @param color The text color.
   * @param message The message to be colorized.
   * @return The colorized message.
   */
  public static String colorizeText(AnsiColors color, String message) {
    return color.getCode() + message + RESET;
  }

  /**
   * Colorize a message with a background color and a text color.
   *
   * @param bgColor The background color.
   * @param textColor The text color.
   * @param message The message to be colorized.
   * @return The colorized message.
   */
  public static String colorizeBackgroundAndText(
          AnsiColors bgColor, AnsiColors textColor, String message) {
    return bgColor.getCode() + textColor.getCode() + message + RESET;
  }

  /**
   * Colorize a message with a background color.
   *
   * @param bgColor The background color.
   * @param message The message to be colorized.
   * @return The colorized message.
   */
  public static String colorizeBackground(AnsiColors bgColor, String message) {
    return bgColor.getCode() + message + RESET;
  }
}
