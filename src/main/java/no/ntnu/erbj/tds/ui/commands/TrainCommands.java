package no.ntnu.erbj.tds.ui.commands;

import java.util.Comparator;
import java.util.List;
import no.ntnu.erbj.tds.dao.TrainDao;
import no.ntnu.erbj.tds.model.Train;
import no.ntnu.erbj.tds.ui.utilities.AnsiColors;
import no.ntnu.erbj.tds.ui.utilities.Colorize;
import no.ntnu.erbj.tds.ui.utilities.SortUtility;
import no.ntnu.erbj.tds.ui.utilities.TdsLogger;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Commands for manipulating train objects.
 *
 * @author Erik
 * @version 1.1
 */
@ShellComponent
@EnableTransactionManagement
public class TrainCommands {
  private static final String TABLE_FORMAT = "| %-15s | %-10s | %-40s |";
  // Above and below are static to follow SonarLint's recommendation
  private static final String DIVIDER =
      "+-----------------+------------+------------------------------------------+";
  private final String blueFormat = Colorize.colorizeText(AnsiColors.BLUE, TABLE_FORMAT);
  private final String blueDivider = Colorize.colorizeText(AnsiColors.BLUE, DIVIDER);
  private final TrainDao trainDao;

  /**
   * TrainCommands constructor. Uses constructor injection to get the trainDAO object.
   *
   * @param trainDao injects the trainDAO object.
   */
  public TrainCommands(TrainDao trainDao) {
    this.trainDao = trainDao;
  }

  private void printTableHeader() {
    TdsLogger logger = TdsLogger.getInstance();
    logger.info(blueDivider);
    logger.info(String.format(blueFormat, "Train number", "Seats", "Wagons"));
    logger.info(blueDivider);
  }

  /** List all trains. */
  @ShellMethod(value = "List all trains.", key = "train list")
  public void listTrains() {
    trainDao.getAll().forEach(train -> TdsLogger.getInstance().info(train.toString()));
  }

  /** Stylised table of all trains, sorted by number of wagons. */
  @ShellMethod(value = "List all trains in a stylised table.", key = "train table")
  public void listTrainTable() {
    TdsLogger logger = TdsLogger.getInstance();

    List<Train> trains =
        SortUtility.sortBy(trainDao.getAll(), Comparator.comparing(Train::getNumberOfWagons));

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

  /** Stylised table of all un trains, sorted by number of wagons. */
  @ShellMethod(
      value = "List all unoccupied trains in a stylised table.",
      key = "train unoccupied table")
  public void listUnoccupiedTrainsTable() {
    TdsLogger logger = TdsLogger.getInstance();

    List<Train> trains =
        SortUtility.sortBy(
            trainDao.getAllUnoccupiedTrains(), Comparator.comparing(Train::getNumberOfWagons));

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
