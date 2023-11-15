package no.ntnu.erbj.tds.shared.utilities;

/**
 * A class containing static methods for validating strings. The class cannot be instantiated. <br>
 * Contains the following methods:
 *
 * <ul>
 *   <li>{@link #validateString(String, String)}
 * </ul>
 *
 * @version 1.0
 * @author erik
 */
public class StringValidator {

  /**
   * Private constructor to prevent instantiation.
   *
   * @see <a href="https://rules.sonarsource.com/java/RSPEC-1118">SonarLint rule</a>
   */
  private StringValidator() {
    // private constructor to hide the implicit public one
  }

  /**
   * Validates a string parameter. The parameter cannot be null, empty or blank.
   *
   * @param param the string parameter to validate
   * @param paramName the name of the parameter, used in the exception message
   * @throws IllegalArgumentException if the parameter is null, empty or blank
   */
  public static void validateString(String param, String paramName) {
    if (param == null || param.isEmpty() || param.isBlank()) {
      throw new IllegalArgumentException(paramName + " cannot be null, empty or blank");
    }
  }
}
