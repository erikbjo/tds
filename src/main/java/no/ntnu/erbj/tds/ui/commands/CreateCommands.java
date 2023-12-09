package no.ntnu.erbj.tds.ui.commands;

import java.time.LocalTime;
import java.util.Scanner;
import no.ntnu.erbj.tds.dao.DepartureDao;
import no.ntnu.erbj.tds.dao.TrainDao;
import no.ntnu.erbj.tds.dao.WagonDao;
import no.ntnu.erbj.tds.model.*;
import no.ntnu.erbj.tds.model.departures.Departure;
import no.ntnu.erbj.tds.model.departures.DepartureBuilder;
import no.ntnu.erbj.tds.ui.utilities.Printer;
import no.ntnu.erbj.tds.ui.utilities.TdsLogger;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Commands for creating objects.
 *
 * @author Erik
 * @version 2.0
 */
@ShellComponent
@EnableTransactionManagement
public class CreateCommands {

  private final DepartureDao departureDao;
  private final TrainDao trainDao;
  private final WagonDao wagonDao;
  private final HelperCommands helperCommands;

  /**
   * CreateCommands constructor. Uses constructor injection to get the trainCommands object.
   *
   * @param departureDao injects the departureDao object.
   * @param trainDao injects the trainDao object.
   * @param wagonDao injects the wagonDao object.
   * @param helperCommands injects the helperCommands object.
   */
  public CreateCommands(
      DepartureDao departureDao,
      TrainDao trainDao,
      WagonDao wagonDao,
      HelperCommands helperCommands) {
    this.departureDao = departureDao;
    this.trainDao = trainDao;
    this.wagonDao = wagonDao;
    this.helperCommands = helperCommands;
  }

  /** Start sequence to create a wagon. */
  @ShellMethod(value = "Start sequence to create a wagon.", key = "new wagon")
  public void createWagon() {
    Scanner scanner = new Scanner(System.in);
    TdsLogger logger = TdsLogger.getInstance();
    Printer.printEnterWagonType();
    for (WagonType value : WagonType.values()) {
      logger.info(value.toString());
    }

    String wagonType = scanner.nextLine();

    if ("exit".equalsIgnoreCase(wagonType)) {
      Printer.printExitString();
      return;
    }

    WagonType safeWagonType;
    try {
      safeWagonType = WagonType.getWagonTypeByString(wagonType);
    } catch (IllegalArgumentException e) {
      Printer.printInvalidInput("wagon type");
      return;
    }

    Wagon wagon = new Wagon(safeWagonType);

    try {
      wagonDao.add(wagon);
      Printer.printAddedToDatabase();
    } catch (Exception e) {
      TdsLogger.getInstance().warn(e.getMessage());
    }
  }

  /** Start sequence to create a train. */
  @ShellMethod(value = "Start sequence to create a train.", key = "new train")
  public void createTrain() {
    Scanner scanner = new Scanner(System.in);

    Train train = null;
    boolean isTrainNumberValid = false;

    do {
      Printer.printEnterTrainNumber();
      String trainNumber = scanner.nextLine();

      if (trainNumber.equalsIgnoreCase("exit")) {
        Printer.printExitString();
        return;
      }

      try {
        isTrainNumberValid = trainDao.trainNumberIsUnique(trainNumber);
        train = new Train(trainNumber);
      } catch (IllegalArgumentException e) {
        Printer.printInvalidInput("train number");
      }

      if (!isTrainNumberValid) {
        Printer.printTrainNumberNotUnique();
      }

    } while (!isTrainNumberValid);

    try {
      trainDao.add(train);
      Printer.printAddedToDatabase();
    } catch (Exception e) {
      Printer.printException(e);
    }
  }

  /** Start sequence to create a departure. */
  @ShellMethod(value = "Start sequence to create a departure.", key = "new departure")
  public void createDeparture() {
    Scanner scanner = new Scanner(System.in);
    DepartureBuilder builder = new DepartureBuilder();

    Train train = helperCommands.getUnoccupiedTrainFromUser(scanner).orElse(null);
    if (train == null) {
      Printer.printExitString();
      return;
    }
    builder.setTrain(train);

    LocalTime departureTime = helperCommands.getDepartureTimeFromUser(scanner).orElse(null);
    if (departureTime == null) {
      Printer.printExitString();
      return;
    }
    builder.setDepartureTimeLocalTime(departureTime);

    String line = helperCommands.getLineFromUser(scanner).orElse(null);
    if (line == null) {
      Printer.printExitString();
      return;
    }
    builder.setLine(line);

    String destination = helperCommands.getDestinationFromUser(scanner).orElse(null);
    if (destination == null) {
      Printer.printExitString();
      return;
    }
    builder.setDestination(destination);

    Integer track = helperCommands.getTrackFromUser(scanner).orElse(null);
    if (track == null) {
      Printer.printExitString();
      return;
    }
    builder.setTrack(track);

    LocalTime delay = helperCommands.getDelayFromUser(scanner).orElse(null);
    if (delay == null) {
      Printer.printExitString();
      return;
    }
    builder.setDelayLocalTime(delay);

    Departure departure = builder.build();

    try {
      departureDao.add(departure);
      Printer.printAddedToDatabase();
    } catch (Exception e) {
      Printer.printException(e);
      e.printStackTrace();
    }
  }
}
