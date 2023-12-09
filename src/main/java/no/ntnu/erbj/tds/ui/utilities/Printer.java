package no.ntnu.erbj.tds.ui.utilities;

/**
 * A class that provides printing functionality. <br>
 * Contains methods for printing messages to the console, as well as methods for printing lists of
 * objects in a stylized table format. <br>
 * Use this rather than raw usage of TdsLogger, as this class is more specific.
 *
 * @version 2.0
 * @author erik
 */
public class Printer {

  private static final TdsLogger logger = TdsLogger.getInstance();
  private static final String EXIT_STRING = "Exiting object creation."; // For SonarLint

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
  public static void printEnterDelay() {
    logger.info("Enter delay (HH:mm): (nothing for no delay)");
  }

  /** Prints a message to the console asking the user to enter a train number. */
  public static void printEnterTrainNumber() {
    logger.info("Enter train number: (exit to cancel)");
  }

  /** Prints a message to the console asking the user to enter a line. */
  public static void printEnterLine() {
    logger.info("Enter line: ");
  }

  /** Prints a message to the console asking the user to enter a track. */
  public static void printEnterTrack() {
    logger.info("Enter track: (nothing for no track)");
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

  /** Prints a message to the console informing the user that there are no departures in the db. */
  public static void printNoDeparturesFound() {
    logger.info("No departures found. Please create a departure first.");
  }

  /**
   * Prints a message to the console informing the user that there are no departures with the given
   * destination.
   */
  public static void printNoDeparturesWithDestination() {
    logger.info("No departures found with destination.");
  }

  /**
   * Prints a message to the console informing the user that there are no departures with the given
   * train number.
   */
  public static void printNoDeparturesWithTrainNumber() {
    logger.info("No departures found with train number.");
  }

  /**
   * Prints a message to the console asking the user to enter a train number of a departure to set
   * delay.
   */
  public static void printEnterTrainNumberForDelay() {
    logger.info("Enter the train number of the departure you want to set the delay of: ");
  }

  /** Prints a message to the console asking the user to enter the ID of a wagon. */
  public static void printEnterWagonId() {
    logger.info("Enter the ID of the wagon you want to add to the train: ");
  }

  /** Prints a message to the console informing the user that the delay was set successfully. */
  public static void printDelaySetSuccessfully() {
    logger.info(Colorize.colorizeText(AnsiColors.GREEN, "Delay set."));
  }

  /**
   * Prints a message to the console informing the user that there are no unoccupied wagons in the
   * database.
   */
  public static void printNoUnoccupiedWagons() {
    logger.info("No unoccupied wagons in database. Please create a new wagon first.");
  }

  public static void printTrainIsOccupied() {
    logger.info("Train is occupied or invalid. Please try again.");
  }

  /** Prints a message to the console informing the user that the train number is not unique. */
  public static void printTrainNumberNotUnique() {
    logger.info("Train number is not unique. Please try again.");
  }

  /**
   * Prints a message to the console asking the user to enter a train number of a departure to set
   * track.
   */
  public static void printEnterTrainNumberForTrack() {
    logger.info("Enter the train number of the departure you want to set the track of: ");
  }

  /** Prints a message to the console informing the user that the track was set successfully. */
  public static void printTrackSetSuccessfully() {
    logger.info(Colorize.colorizeText(AnsiColors.GREEN, "Track set."));
  }
}
