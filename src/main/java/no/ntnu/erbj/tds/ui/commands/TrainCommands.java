package no.ntnu.erbj.tds.ui.commands;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import no.ntnu.erbj.tds.dao.TrainDao;
import no.ntnu.erbj.tds.dao.WagonDao;
import no.ntnu.erbj.tds.model.Train;
import no.ntnu.erbj.tds.model.Wagon;
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
  private final WagonDao wagonDao;
  private final WagonCommands wagonCommands;
  private final CreateCommands createCommands;
  private final HelperCommands helperCommands;

  /**
   * TrainCommands constructor. Uses constructor injection to get beans.
   *
   * @param trainDao injects the trainDAO object.
   * @param wagonDao injects the wagonDAO object.
   * @param wagonCommands injects the wagonCommands object.
   * @param createCommands injects the createCommands object.
   */
  public TrainCommands(
      TrainDao trainDao,
      WagonDao wagonDao,
      WagonCommands wagonCommands,
      CreateCommands createCommands,
      HelperCommands helperCommands) {
    this.trainDao = trainDao;
    this.wagonDao = wagonDao;
    this.wagonCommands = wagonCommands;
    this.createCommands = createCommands;
    this.helperCommands = helperCommands;
  }

  /** Stylised table of all trains, sorted by number of wagons. */
  @ShellMethod(value = "List all trains in a stylised table.", key = "train table")
  public void listTrainTable() {
    List<Train> trains =
        SortUtility.sortBy(trainDao.getAll(), Comparator.comparing(Train::getNumberOfWagons));

    TablePrinter.printTrainsInTableFormat(trains);
  }

  /** Start sequence to add a wagon to a train. */
  @ShellMethod(value = "Starts sequence to add a wagon to a train.", key = "train add wagon")
  public void addWagonToTrain() {
    if (wagonDao.getAllUnoccupiedWagons().isEmpty()) {
      Printer.printNoUnoccupiedWagons();
      return;
    }

    Scanner scanner = new Scanner(System.in);

    Train train = helperCommands.getTrainFromUser(scanner).orElse(null);
    if (train == null) {
      Printer.printExitString();
      return;
    }

    Wagon wagon = helperCommands.getWagonFromUser(scanner).orElse(null);
    if (wagon == null) {
      Printer.printExitString();
      return;
    }

    Wagon managedWagon = wagonDao.merge(wagon);
    Train managedTrain = trainDao.merge(train);

    if (managedTrain != null && managedWagon != null) {
      managedWagon.setTrain(managedTrain);
      managedTrain.addWagon(managedWagon);
      trainDao.update(managedTrain);
      Printer.printAddedToDatabase();
    } else {
      Printer.printException(new Exception("Could not add wagon to train."));
    }
  }
}
