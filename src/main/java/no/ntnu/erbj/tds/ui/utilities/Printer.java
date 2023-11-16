package no.ntnu.erbj.tds.ui.utilities;

import java.util.List;
import no.ntnu.erbj.tds.model.Departure;

/**
 * A class that provides printing functionality. <br>
 * Contains the following methods:
 *
 * <ul>
 *   <li>{@link #printInvalidInput(String)}
 * </ul>
 *
 * Use this rather than raw usage of TdsLogger, as this class is more specific.
 *
 * @version 1.1
 * @author erik
 */
public class Printer {

  private static final TdsLogger logger = TdsLogger.getInstance();
  private static final String EXIT_STRING = "Exiting object creation."; // For SonarLint
  private static final String DEPARTURE_TABLE_FORMAT = "| %-8s | %-10s | %-10s | %-14s | %-40s |";
  // Above and below are static to follow SonarLint's recommendation
  private static final String DEPARTURE_TABLE_DIVIDER =
      "+----------+------------+------------+----------------"
          + "+------------------------------------------+";
  private static final String BLUE_DEPARTURE_FORMAT =
      Colorize.colorizeText(AnsiColors.BLUE, DEPARTURE_TABLE_FORMAT);
  private static final String BLUE_DEPARTURE_DIVIDER =
      Colorize.colorizeText(AnsiColors.BLUE, DEPARTURE_TABLE_DIVIDER);

  /**
   * Private constructor to prevent instantiation.
   *
   * @see <a href="https://rules.sonarsource.com/java/RSPEC-1118">SonarLint rule</a>
   */
  private Printer() {
    // private constructor to hide the implicit public one
  }

  /**
   * Prints a message to the console informing the user that the object creation has been canceled.
   */
  public static void printExitString() {
    logger.info(EXIT_STRING);
  }

  /**
   * Prints a message to the console informing the user that the input was invalid.
   *
   * @param name the name of the input
   */
  public static void printInvalidInput(String name) {
    logger.info("Invalid " + name + ". Please try again, or type 'exit' to cancel.");
  }

  /**
   * Prints a message to the console informing the user that there are no unoccupied trains in the
   * database.
   */
  public static void printNoUnoccupiedTrains() {
    logger.info("No unoccupied trains in database. Please create a train first.");
  }

  /** Prints a message to the console asking the user to enter a departure time. */
  public static void printEnterDepartureTime() {
    logger.info("Enter departure time (HH:mm): ");
  }

  /** Prints a message to the console asking the user to enter a departure delay. */
  public static void printEnterDepartureDelay() {
    logger.info("Enter departure delay (HH:mm): ");
  }

  /** Prints a message to the console asking the user to enter a train number. */
  public static void printEnterTrainNumber() {
    logger.info("Enter train number: ");
  }

  /** Prints a message to the console asking the user to enter a line. */
  public static void printEnterLine() {
    logger.info("Enter line: ");
  }

  /** Prints a message to the console asking the user to enter a track. */
  public static void printEnterTrack() {
    logger.info("Enter track: ");
  }

  /** Prints a message to the console asking the user to enter a destination. */
  public static void printEnterDestination() {
    logger.info("Enter destination: ");
  }

  /**
   * Prints a message to the console informing the user that the object was added to the database.
   */
  public static void printAddedToDatabase() {
    logger.info("Successfully added to database.");
  }

  /** Prints a message to the console asking the user to enter a wagon type. */
  public static void printEnterWagonType() {
    logger.info("Enter wagon's type, or 'exit' to stop: ");
  }

  /**
   * Prints an exception to the console.
   *
   * @param exception the exception to print
   */
  public static void printException(Exception exception) {
    logger.warn(exception.getMessage());
  }

  /** Prints the table header for departure table. */
  public static void printDepartureTableHeader() {
    logger.info(BLUE_DEPARTURE_DIVIDER);
    logger.info(
        String.format(
            BLUE_DEPARTURE_FORMAT, "Time", "Line", "Track", "Train number", "Destination"));
    logger.info(BLUE_DEPARTURE_DIVIDER);
  }

  /**
   * Prints a list of departures in a stylized table.
   *
   * @param departures the list of departures to print
   */
  public static void printDeparturesInTableFormat(List<Departure> departures) {
    printDepartureTableHeader();
    for (Departure departure : departures) {
      logger.info(
          String.format(
              DEPARTURE_TABLE_FORMAT,
              departure.getDepartureTime(),
              departure.getLine(),
              departure.getTrack(),
              departure.getTrain().getTrainNumber(),
              departure.getDestination()));
    }
    printDepartureTableHeader();
  }
}
