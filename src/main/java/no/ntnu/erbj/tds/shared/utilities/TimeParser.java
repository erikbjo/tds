package no.ntnu.erbj.tds.shared.utilities;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * A class containing static methods for parsing time strings. <br>
 * The class cannot be instantiated. <br>
 * Contains the following methods:
 *
 * <ul>
 *   <li>{@link #parseTime(String, String)}
 * </ul>
 *
 * @version 1.0
 * @author erik
 */
public class TimeParser {

  /**
   * Private constructor to prevent instantiation.
   *
   * @see <a href="https://rules.sonarsource.com/java/RSPEC-1118">SonarLint rule</a>
   */
  private TimeParser() {
    // private constructor to hide the implicit public one
  }

  /**
   * Validates a time parameter.<br>
   * The parameter cannot be null, empty or blank.<br>
   * Also, the parameter must be in the format HH:mm.
   *
   * @param timeString the time parameter to validate
   * @param paramName the name of the parameter, used in the exception message
   * @throws IllegalArgumentException if the parameter is null, empty or blank.<br>
   *     Also, if the parameter is not in the format HH:mm.
   */
  public static LocalTime parseTime(String timeString, String paramName) {
    try {
      return LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException(
          "Invalid " + paramName + " format. Please use HH:mm format.", e);
    }
  }
}
