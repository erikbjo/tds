package no.ntnu.erbj.tds.ui.utilities;

/**
 * An enum that contains ANSI escape codes for colors. Used for colorizing text in the terminal.
 *
 * @see Colorize
 * @see <a href="https://en.wikipedia.org/wiki/ANSI_escape_code">ANSI escape code</a>
 * @version 1.0
 * @author Erik
 */
public enum AnsiColors {
  RESET("\u001B[0m"),
  BLACK("\u001B[30m"),
  RED("\u001B[31m"),
  GREEN("\u001B[32m"),
  YELLOW("\u001B[33m"),
  BLUE("\u001B[34m"),
  PURPLE("\u001B[35m"),
  CYAN("\u001B[36m"),
  WHITE("\u001B[37m"),
  BG_BLACK("\u001B[40m"),
  BG_RED("\u001B[41m"),
  BG_GREEN("\u001B[42m"),
  BG_YELLOW("\u001B[43m"),
  BG_BLUE("\u001B[44m"),
  BG_PURPLE("\u001B[45m"),
  BG_CYAN("\u001B[46m"),
  BG_WHITE("\u001B[47m");

  private final String code;

  /**
   * Create a new ANSI escape code.
   *
   * @param code The ANSI escape code.
   */
  AnsiColors(String code) {
    this.code = code;
  }

  /**
   * Get the ANSI escape code for the color.
   *
   * @return The ANSI escape code for the color.
   */
  public String getCode() {
    return code;
  }
}
