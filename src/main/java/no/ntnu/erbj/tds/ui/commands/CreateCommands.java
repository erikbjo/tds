package no.ntnu.erbj.tds.ui.commands;

import java.util.Scanner;
import no.ntnu.erbj.tds.dao.DepartureDao;
import no.ntnu.erbj.tds.dao.TrainDao;
import no.ntnu.erbj.tds.dao.WagonDao;
import no.ntnu.erbj.tds.model.*;
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

  private final TrainCommands trainCommands;
  private final DepartureDao departureDao;
  private final TrainDao trainDao;
  private final WagonDao wagonDao;

  /**
   * CreateCommands constructor. Uses constructor injection to get the trainCommands object.
   *
   * @param trainCommands injects the trainCommands object.
   */
  public CreateCommands(
      TrainCommands trainCommands,
      DepartureDao departureDao,
      TrainDao trainDao,
      WagonDao wagonDao) {
    this.trainCommands = trainCommands;
    this.departureDao = departureDao;
    this.trainDao = trainDao;
    this.wagonDao = wagonDao;
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
        TdsLogger.getInstance().info("L99: " + trainNumber + " " + isTrainNumberValid);
        train = new Train(trainNumber);
      } catch (IllegalArgumentException e) {
        Printer.printInvalidInput("train number");
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

    if (trainDao.getAllUnoccupiedTrains().isEmpty()) {
      Printer.printNoUnoccupiedTrains();
      return;
    }

    trainCommands.listUnoccupiedTrainsTable();
    DepartureBuilder builder = new DepartureBuilder();
    Train train = null;

    String trainIdString;
    boolean isTrainIdValid;

    do {
      Printer.printEnterTrainNumber();
      trainIdString = scanner.nextLine();

      try {
        isTrainIdValid = trainDao.trainIsNotOccupied(trainIdString);
        train = trainDao.findByTrainNumber(trainIdString).orElseThrow();
      } catch (IllegalArgumentException e) {
        isTrainIdValid = false;
      }

      if (trainIdString.equalsIgnoreCase("exit")) {
        Printer.printExitString();
        return;
      } else if (!isTrainIdValid) {
        Printer.printInvalidInput("train number");
      }

    } while (!isTrainIdValid);

    String departureTimeString;
    boolean isDepartureTimeValid;

    do {
      Printer.printEnterDepartureTime();
      departureTimeString = scanner.nextLine();

      try {
        isDepartureTimeValid =
            builder.setDepartureTime(departureTimeString).getDepartureTime() != null;
      } catch (IllegalArgumentException e) {
        isDepartureTimeValid = false;
      }

      if (departureTimeString.equalsIgnoreCase("exit")) {
        Printer.printExitString();
        return;
      } else if (!isDepartureTimeValid) {
        Printer.printInvalidInput("departure time");
      }

    } while (!isDepartureTimeValid);

    do {
      Printer.printEnterLine();
      String line = scanner.nextLine();

      if (line.equalsIgnoreCase("exit")) {
        Printer.printExitString();
        return;
      }

      try {
        builder.setLine(line);
      } catch (IllegalArgumentException e) {
        Printer.printInvalidInput("line");
      }
    } while (builder.getLine() == null);

    do {
      Printer.printEnterDestination();
      String destination = scanner.nextLine();

      if (destination.equalsIgnoreCase("exit")) {
        Printer.printExitString();
        return;
      }

      try {
        builder.setDestination(destination);
      } catch (IllegalArgumentException e) {
        Printer.printInvalidInput("destination");
      }
    } while (builder.getDestination() == null);

    String trackString;
    boolean isTrackValid = false;
    do {
      Printer.printEnterTrack();
      trackString = scanner.nextLine();

      if (trackString.equalsIgnoreCase("exit")) {
        Printer.printExitString();
        return;
      }

      try {
        builder.setTrack(Integer.parseInt(trackString));
        isTrackValid = true;
      } catch (IllegalArgumentException e) {
        Printer.printInvalidInput("track");
      }
    } while (!isTrackValid);

    // delay
    String departureDelayString;
    boolean isDepartureDelayValid;

    do {
      Printer.printEnterDelay();
      departureDelayString = scanner.nextLine();

      try {
        isDepartureDelayValid = builder.setDelay(departureDelayString).getDelay() != null;
      } catch (IllegalArgumentException e) {
        isDepartureDelayValid = false;
      }

      if (departureTimeString.equalsIgnoreCase("exit")) {
        Printer.printExitString();
        return;
      } else if (!isDepartureDelayValid) {
        Printer.printInvalidInput("departure delay");
      }

    } while (!isDepartureDelayValid);

    Departure departure = builder.build();

    try {
      departureDao.add(departure);
      Train managedTrain = trainDao.merge(train);
      departure.setTrain(managedTrain);
      departureDao.update(departure);
      Printer.printAddedToDatabase();
    } catch (Exception e) {
      Printer.printException(e);
    }
  }

  /** Start sequence to create a reservation. Note: This is not implemented yet */
  @ShellMethod(value = "Start sequence to create a reservation.", key = "new reservation")
  public void createReservation() {
    // TODO: Implement, need implementation of departure first
  }
}
