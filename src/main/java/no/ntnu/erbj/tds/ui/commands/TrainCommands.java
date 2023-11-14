package no.ntnu.erbj.tds.ui.commands;

import no.ntnu.erbj.tds.dao.TrainDAO;
import no.ntnu.erbj.tds.model.Departure;
import no.ntnu.erbj.tds.model.Train;
import no.ntnu.erbj.tds.ui.utilities.ANSIColors;
import no.ntnu.erbj.tds.ui.utilities.Colorize;
import no.ntnu.erbj.tds.ui.utilities.SortUtility;
import no.ntnu.erbj.tds.ui.utilities.TdsLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Comparator;
import java.util.List;

/**
 * Commands for manipulating train objects.
 *
 * @author Erik
 * @version 1.0
 */
@ShellComponent
@EnableTransactionManagement
public class TrainCommands {
  private static final String TABLE_FORMAT = "| %-15s | %-10s | %-40s |";
  // Above and below are static to follow SonarLint's recommendation
  private static final String DIVIDER =
      "+-----------------+------------+------------------------------------------+";
  private final String blueFormat = Colorize.colorizeText(ANSIColors.BLUE, TABLE_FORMAT);
  private final String blueDivider = Colorize.colorizeText(ANSIColors.BLUE, DIVIDER);
  @Autowired private TrainDAO trainDAO;

  private void printTableHeader() {
    TdsLogger logger = TdsLogger.getInstance();
    logger.info(blueDivider);
    logger.info(String.format(blueFormat, "Train number", "Seats", "Wagons"));
    logger.info(blueDivider);
  }

  /** List all trains. */
  @ShellMethod(value = "List all trains.", key = "train list")
  public void listTrains() {
    trainDAO.getAll().forEach(train -> TdsLogger.getInstance().info(train.toString()));
  }

  /** Stylised table of all trains, sorted by number of wagons. */
  @ShellMethod(value = "List all trains in a stylised table.", key = "train table")
  public void listTrainTable() {
    TdsLogger logger = TdsLogger.getInstance();

    List<Train> trains =
        SortUtility.sortBy(trainDAO.getAll(), Comparator.comparing(Train::getNumberOfWagons));

    printTableHeader();

    for (Train train : trains) {
      logger.info(
          String.format(
              TABLE_FORMAT,
              train.getTrainNumber(),
              train.getNumberOfSeats(),
              train.getNumberOfWagons()));
    }

    logger.info(DIVIDER);
  }

  /**
   * Stylised table of all un trains, sorted by number of wagons.
   */
  @ShellMethod(value = "List all unoccupied trains in a stylised table.", key = "train unoccupied table")
  public void listUnoccupiedTrainsTable() {
    TdsLogger logger = TdsLogger.getInstance();

    List<Train> trains =
            SortUtility.sortBy(trainDAO.getAllUnoccupiedTrains(), Comparator.comparing(Train::getNumberOfWagons));

    printTableHeader();

    for (Train train : trains) {
      logger.info(
              String.format(
                      TABLE_FORMAT,
                      train.getTrainNumber(),
                      train.getNumberOfSeats(),
                      train.getNumberOfWagons()));
    }

    logger.info(DIVIDER);
  }
}
