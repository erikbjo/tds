package no.ntnu.erbj.tds.ui.commands;

import java.util.Comparator;
import java.util.List;
import no.ntnu.erbj.tds.dao.TrainDao;
import no.ntnu.erbj.tds.model.Train;
import no.ntnu.erbj.tds.ui.utilities.*;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Commands for manipulating train objects.
 *
 * @author Erik
 * @version 2.0
 */
@ShellComponent
@EnableTransactionManagement
public class TrainCommands {
  private final TrainDao trainDao;

  /**
   * TrainCommands constructor. Uses constructor injection to get the trainDAO object.
   *
   * @param trainDao injects the trainDAO object.
   */
  public TrainCommands(TrainDao trainDao) {
    this.trainDao = trainDao;
  }

  /** List all trains. */
  @ShellMethod(value = "List all trains.", key = "train list")
  public void listTrains() {
    trainDao.getAll().forEach(train -> TdsLogger.getInstance().info(train.toString()));
  }

  /** Stylised table of all trains, sorted by number of wagons. */
  @ShellMethod(value = "List all trains in a stylised table.", key = "train table")
  public void listTrainTable() {
    List<Train> trains =
        SortUtility.sortBy(trainDao.getAll(), Comparator.comparing(Train::getNumberOfWagons));

    Printer.printTrainsInTableFormat(trains);
  }

  /** Stylised table of all un trains, sorted by number of wagons. */
  @ShellMethod(
      value = "List all unoccupied trains in a stylised table.",
      key = "train unoccupied table")
  public void listUnoccupiedTrainsTable() {
    List<Train> trains =
        SortUtility.sortBy(
            trainDao.getAllUnoccupiedTrains(), Comparator.comparing(Train::getNumberOfWagons));

    Printer.printTrainsInTableFormat(trains);
  }
}
