package no.ntnu.erbj.tds.ui.utilities;

import java.util.List;
import no.ntnu.erbj.tds.model.Train;
import no.ntnu.erbj.tds.model.Wagon;
import no.ntnu.erbj.tds.model.departures.Departure;

/**
 * Utility class for printing tables.
 *
 * @version 2.0
 * @author erik
 */
public class TablePrinter {

  private static final TdsLogger logger = TdsLogger.getInstance();
  private static final String DEPARTURE_TABLE_FORMAT = "| %-8s | %-10s | %-10s | %-14s | %-40s |";
  // Above and below are static to follow SonarLint's recommendation
  private static final String DEPARTURE_TABLE_DIVIDER =
      "+----------+------------+------------+----------------"
          + "+------------------------------------------+";
  private static final String BLUE_DEPARTURE_FORMAT =
      Colorize.colorizeText(AnsiColors.BLUE, DEPARTURE_TABLE_FORMAT);
  private static final String BLUE_DEPARTURE_DIVIDER =
      Colorize.colorizeText(AnsiColors.BLUE, DEPARTURE_TABLE_DIVIDER);

  private static final String TRAIN_TABLE_FORMAT = "| %-15s | %-10s | %-40s |";
  // Above and below are static to follow SonarLint's recommendation
  private static final String TRAIN_TABLE_DIVIDER =
      "+-----------------+------------+------------------------------------------+";
  private static final String BLUE_TRAIN_FORMAT =
      Colorize.colorizeText(AnsiColors.BLUE, TRAIN_TABLE_FORMAT);
  private static final String BLUE_TRAIN_DIVIDER =
      Colorize.colorizeText(AnsiColors.BLUE, TRAIN_TABLE_DIVIDER);
  private static final String WAGON_TABLE_FORMAT = "| %-15s | %-40s | %-15s | %-15s |";
  private static final String WAGON_TABLE_DIVIDER =
      "+-----------------+------------------------------------------"
          + "+-----------------+-----------------+";
  private static final String BLUE_WAGON_FORMAT =
      Colorize.colorizeText(AnsiColors.BLUE, WAGON_TABLE_FORMAT);
  private static final String BLUE_WAGON_DIVIDER =
      Colorize.colorizeText(AnsiColors.BLUE, WAGON_TABLE_DIVIDER);

  /**
   * Private constructor to prevent instantiation.
   *
   * @see <a href="https://rules.sonarsource.com/java/RSPEC-1118">SonarLint rule</a>
   */
  private TablePrinter() {
    // private constructor to hide the implicit public one
  }

  /** Prints the table header for departure table. */
  public static void printDepartureTableHeader() {
    logger.info(BLUE_DEPARTURE_DIVIDER);
    logger.info(
        String.format(
            BLUE_DEPARTURE_FORMAT, "Time", "Line", "Track", "Train number", "Destination"));
    logger.info(BLUE_DEPARTURE_DIVIDER);
  }

  /** Prints the table divider for departure table. */
  public static void printDepartureTableDivider() {
    logger.info(DEPARTURE_TABLE_DIVIDER);
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
    printDepartureTableDivider();
  }

  /** Prints the table header for train table. */
  public static void printTrainTableHeader() {
    logger.info(BLUE_TRAIN_DIVIDER);
    logger.info(String.format(BLUE_TRAIN_FORMAT, "Train number", "Seats", "Wagons"));
    logger.info(BLUE_TRAIN_DIVIDER);
  }

  /** Prints the table divider for train table. */
  public static void printTrainTableDivider() {
    logger.info(TRAIN_TABLE_DIVIDER);
  }

  /**
   * Prints a list of trains in a stylized table.
   *
   * @param trains the list of trains to print
   */
  public static void printTrainsInTableFormat(List<Train> trains) {
    printTrainTableHeader();
    for (Train train : trains) {
      logger.info(
          String.format(
              TRAIN_TABLE_FORMAT,
              train.getTrainNumber(),
              train.getNumberOfSeats(),
              train.getNumberOfWagons()));
    }
    printTrainTableDivider();
  }

  /** Prints the table header for wagon table. */
  public static void printWagonTableHeader() {
    logger.info(BLUE_WAGON_DIVIDER);
    logger.info(
        String.format(BLUE_WAGON_FORMAT, "Wagon ID", "Type", "Open seats", "Reserved seats"));
    logger.info(BLUE_WAGON_DIVIDER);
  }

  /** Prints the table divider for wagon table. */
  public static void printWagonTableDivider() {
    logger.info(WAGON_TABLE_DIVIDER);
  }

  /**
   * Prints a list of wagons in a stylized table.
   *
   * @param wagons the list of wagons to print
   */
  public static void printWagonsInTableFormat(List<Wagon> wagons) {
    printWagonTableHeader();
    for (Wagon wagon : wagons) {
      logger.info(
          String.format(
              WAGON_TABLE_FORMAT,
              wagon.getId(),
              wagon.getWagonType(),
              wagon.getOpenSeats(),
              wagon.getReservedSeats()));
    }
    printWagonTableDivider();
  }
}
