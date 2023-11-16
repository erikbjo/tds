package no.ntnu.erbj.tds.ui.commands;

import java.time.LocalTime;
import java.util.Optional;
import java.util.Scanner;
import no.ntnu.erbj.tds.dao.DepartureDao;
import no.ntnu.erbj.tds.dao.TrainDao;
import no.ntnu.erbj.tds.dao.WagonDao;
import no.ntnu.erbj.tds.model.*;
import no.ntnu.erbj.tds.model.departures.Departure;
import no.ntnu.erbj.tds.model.departures.DepartureBuilder;
import no.ntnu.erbj.tds.shared.utilities.StringValidator;
import no.ntnu.erbj.tds.shared.utilities.TimeParser;
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
    DepartureBuilder builder = new DepartureBuilder();

    Train train = getTrainFromUser(scanner).orElse(null);
    if (train == null) {
      Printer.printExitString();
      return;
    }

    LocalTime departureTime = getDepartureTimeFromUser(scanner).orElse(null);
    if (departureTime == null) {
      Printer.printExitString();
      return;
    }

    String line = getLineFromUser(scanner).orElse(null);
    if (line == null) {
      Printer.printExitString();
      return;
    }

    String destination = getDestinationFromUser(scanner).orElse(null);
    if (destination == null) {
      Printer.printExitString();
      return;
    }

    Integer track = getTrackFromUser(scanner).orElse(null);
    if (track == null) {
      Printer.printExitString();
      return;
    }

    LocalTime delay = getDelayFromUser(scanner).orElse(null);
    if (delay == null) {
      Printer.printExitString();
      return;
    }

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

  /**
   * Private helper method to get a train from the user.<br>
   * Can return an empty optional if the user enters "exit".
   *
   * @param scanner the scanner to use to get input from the user
   * @return an optional train
   */
  private Optional<Train> getTrainFromUser(Scanner scanner) {
    if (trainDao.getAllUnoccupiedTrains().isEmpty()) {
      Printer.printNoUnoccupiedTrains();
      return Optional.empty();
    }

    trainCommands.listUnoccupiedTrainsTable();
    Train train = null;

    String trainIdString;
    boolean isTrainIdValid;

    do {
      Printer.printEnterTrainNumber();
      trainIdString = scanner.nextLine();

      if (trainIdString.equalsIgnoreCase("exit")) {
        return Optional.empty();
      }

      try {
        isTrainIdValid = trainDao.trainIsNotOccupied(trainIdString);
        train = trainDao.findByTrainNumber(trainIdString).orElseThrow();
      } catch (IllegalArgumentException e) {
        isTrainIdValid = false;
      }
    } while (!isTrainIdValid);

    return Optional.of(train);
  }

  /**
   * Private helper method to get a departure time from the user.<br>
   * Can return an empty optional if the user enters "exit".
   *
   * @param scanner the scanner to use to get input from the user
   * @return an optional departure time
   */
  private Optional<LocalTime> getDepartureTimeFromUser(Scanner scanner) {
    Optional<LocalTime> departureTime = Optional.empty();

    do {
      Printer.printEnterDepartureTime();
      String departureTimeString = scanner.nextLine();

      if (departureTimeString.equalsIgnoreCase("exit")) {
        return Optional.empty();
      }

      try {
        StringValidator.validateString(departureTimeString, "Departure time");
        departureTime = Optional.of(TimeParser.parseTime(departureTimeString, "Departure time"));
      } catch (IllegalArgumentException e) {
        Printer.printInvalidInput("departure time");
      }
    } while (departureTime.isEmpty());

    return departureTime;
  }

  /**
   * Private helper method to get a line from the user.<br>
   * Can return an empty optional if the user enters "exit".
   *
   * @param scanner the scanner to use to get input from the user
   * @return an optional line
   */
  private Optional<String> getLineFromUser(Scanner scanner) {
    Optional<String> lineOpt = Optional.empty();

    do {
      Printer.printEnterLine();
      String line = scanner.nextLine();

      if (line.equalsIgnoreCase("exit")) {
        return Optional.empty();
      }

      try {
        StringValidator.validateString(line, "Line");
        lineOpt = Optional.of(line);
      } catch (IllegalArgumentException e) {
        Printer.printInvalidInput("line");
      }
    } while (lineOpt.isEmpty());

    return lineOpt;
  }

  /**
   * Private helper method to get a destination from the user.<br>
   * Can return an empty optional if the user enters "exit".
   *
   * @param scanner the scanner to use to get input from the user
   * @return an optional destination
   */
  private Optional<String> getDestinationFromUser(Scanner scanner) {
    Optional<String> destinationOpt = Optional.empty();

    do {
      Printer.printEnterDestination();
      String destination = scanner.nextLine();

      if (destination.equalsIgnoreCase("exit")) {
        return Optional.empty();
      }

      try {
        StringValidator.validateString(destination, "Destination");
        destinationOpt = Optional.of(destination);
      } catch (IllegalArgumentException e) {
        Printer.printInvalidInput("destination");
      }
    } while (destinationOpt.isEmpty());

    return destinationOpt;
  }

  /**
   * Private helper method to get a track from the user.<br>
   * Can return an empty optional if the user enters "exit".
   *
   * @param scanner the scanner to use to get input from the user
   * @return an optional track
   */
  private Optional<Integer> getTrackFromUser(Scanner scanner) {
    Optional<Integer> trackOpt = Optional.empty();

    do {
      Printer.printEnterTrack();
      String trackString = scanner.nextLine();

      if (trackString.equalsIgnoreCase("exit")) {
        return Optional.empty();
      }

      try {
        int track = Integer.parseInt(trackString);
        trackOpt = Optional.of(track);
      } catch (IllegalArgumentException e) {
        Printer.printInvalidInput("track");
      }
    } while (trackOpt.isEmpty());

    return trackOpt;
  }

  /**
   * Private helper method to get a delay from the user.<br>
   * Can return an empty optional if the user enters "exit".
   *
   * @param scanner the scanner to use to get input from the user
   * @return an optional delay
   */
  private Optional<LocalTime> getDelayFromUser(Scanner scanner) {
    Optional<LocalTime> delayOpt = Optional.empty();

    do {
      Printer.printEnterDelay();
      String delayString = scanner.nextLine();

      if (delayString.equalsIgnoreCase("exit")) {
        return Optional.empty();
      }

      try {
        LocalTime delay = TimeParser.parseTime(delayString, "Delay");
        delayOpt = Optional.of(delay);
      } catch (IllegalArgumentException e) {
        Printer.printInvalidInput("delay");
      }
    } while (delayOpt.isEmpty());

    return delayOpt;
  }
}
