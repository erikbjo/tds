package no.ntnu.erbj.tds.ui.commands;

import java.util.Scanner;
import no.ntnu.erbj.tds.dao.DepartureDao;
import no.ntnu.erbj.tds.dao.TrainDao;
import no.ntnu.erbj.tds.dao.WagonDao;
import no.ntnu.erbj.tds.model.*;
import no.ntnu.erbj.tds.ui.utilities.TdsLogger;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Commands for creating objects.
 *
 * @author Erik
 * @version 1.2
 */
@ShellComponent
@EnableTransactionManagement
public class CreateCommands {
  private static final String EXIT_STRING = "Exiting object creation."; // For SonarLint
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
    logger.info("Enter wagon's type (or 'exit' to stop): ");
    for (WagonType value : WagonType.values()) {
      logger.info(value.toString());
    }

    String wagonType = scanner.nextLine();

    if ("exit".equalsIgnoreCase(wagonType)) {
      TdsLogger.getInstance().info(EXIT_STRING);
      return;
    }

    WagonType safeWagonType;
    try {
      safeWagonType = WagonType.getWagonTypeByString(wagonType);
    } catch (IllegalArgumentException e) {
      TdsLogger.getInstance().warn("Invalid wagon type: " + wagonType);
      return;
    }

    Wagon wagon = new Wagon(safeWagonType);

    try {
      wagonDao.add(wagon);
      logger.info("Successfully added wagon to database.");
    } catch (Exception e) {
      TdsLogger.getInstance().warn(e.getMessage());
    }
  }

  /** Start sequence to create a train. */
  @ShellMethod(value = "Start sequence to create a train.", key = "new train")
  public void createTrain() {
    TdsLogger logger = TdsLogger.getInstance();
    Scanner scanner = new Scanner(System.in);

    Train train = null;
    boolean isTrainNumberValid = false;

    do {
      logger.info("Enter train number: ");
      String trainNumber = scanner.nextLine();

      if (trainNumber.equalsIgnoreCase("exit")) {
        logger.info(EXIT_STRING);
        return;
      }

      try {
        isTrainNumberValid = trainDao.trainNumberIsUnique(trainNumber);
        train = new Train(trainNumber);
      } catch (IllegalArgumentException e) {
        logger.info("Invalid train number. Type 'exit' to exit.");
      }

    } while (!isTrainNumberValid);

    try {
      trainDao.add(train);
      logger.info("Successfully added train to database.");
    } catch (Exception e) {
      logger.warn(e.getMessage());
    }
  }

  /** Start sequence to create a departure. */
  @ShellMethod(value = "Start sequence to create a departure.", key = "new departure")
  public void createDeparture() {
    Scanner scanner = new Scanner(System.in);
    TdsLogger logger = TdsLogger.getInstance();

    if (trainDao.getAllUnoccupiedTrains().isEmpty()) {
      TdsLogger.getInstance()
          .info("No unoccupied trains in database. Please create a train first.");
      return;
    }

    trainCommands.listUnoccupiedTrainsTable();
    DepartureBuilder builder = new DepartureBuilder();
    Train train = null;

    String trainIdString;
    boolean isTrainIdValid;

    do {
      logger.info("Enter train number: ");
      trainIdString = scanner.nextLine();

      try {
        isTrainIdValid = trainDao.trainIsNotOccupied(trainIdString);
        train = trainDao.findByTrainNumber(trainIdString).orElseThrow();
      } catch (IllegalArgumentException e) {
        isTrainIdValid = false;
      }

      if (trainIdString.equalsIgnoreCase("exit")) {
        logger.info(EXIT_STRING);
        return;
      } else if (!isTrainIdValid) {
        logger.info("Invalid train id. Type 'exit' to exit.");
      }

    } while (!isTrainIdValid);

    String departureTimeString;
    boolean isDepartureTimeValid;

    do {
      logger.info("Enter departure time (HH:mm): ");
      departureTimeString = scanner.nextLine();

      try {
        isDepartureTimeValid =
            builder.setDepartureTime(departureTimeString).getDepartureTime() != null;
      } catch (IllegalArgumentException e) {
        isDepartureTimeValid = false;
      }

      if (departureTimeString.equalsIgnoreCase("exit")) {
        logger.info(EXIT_STRING);
        return;
      } else if (!isDepartureTimeValid) {
        logger.info("Invalid departure time. Type 'exit' to exit.");
      }

    } while (!isDepartureTimeValid);

    do {
      logger.info("Enter line: ");
      String line = scanner.nextLine();

      if (line.equalsIgnoreCase("exit")) {
        logger.info(EXIT_STRING);
        return;
      }

      try {
        builder.setLine(line);
      } catch (IllegalArgumentException e) {
        logger.info("Invalid line. Type 'exit' to exit.");
      }
    } while (builder.getLine() == null);

    do {
      logger.info("Enter destination: ");
      String destination = scanner.nextLine();

      if (destination.equalsIgnoreCase("exit")) {
        logger.info(EXIT_STRING);
        return;
      }

      try {
        builder.setDestination(destination);
      } catch (IllegalArgumentException e) {
        logger.info("Invalid destination. Type 'exit' to exit.");
      }
    } while (builder.getDestination() == null);

    String trackString;
    boolean isTrackValid = false;
    do {
      logger.info("Enter track: ");
      trackString = scanner.nextLine();

      if (trackString.equalsIgnoreCase("exit")) {
        logger.info(EXIT_STRING);
        return;
      }

      try {
        builder.setTrack(Integer.parseInt(trackString));
        isTrackValid = true;
      } catch (IllegalArgumentException e) {
        logger.info("Invalid track. Type 'exit' to exit.");
      }
    } while (!isTrackValid);

    // delay
    String departureDelayString;
    boolean isDepartureDelayValid;

    do {
      logger.info("Enter departure delay (HH:mm): ");
      departureDelayString = scanner.nextLine();

      try {
        isDepartureDelayValid = builder.setDelay(departureDelayString).getDelay() != null;
      } catch (IllegalArgumentException e) {
        isDepartureDelayValid = false;
      }

      if (departureTimeString.equalsIgnoreCase("exit")) {
        logger.info(EXIT_STRING);
        return;
      } else if (!isDepartureDelayValid) {
        logger.info("Invalid departure delay. Type 'exit' to exit.");
      }

    } while (!isDepartureDelayValid);

    Departure departure = builder.build();

    try {
      departureDao.add(departure);
      Train managedTrain = trainDao.merge(train);
      departure.setTrain(managedTrain);
      departureDao.update(departure);
      logger.info("Successfully added departure to database.");
    } catch (Exception e) {
      TdsLogger.getInstance().warn(e.getMessage() + e);
    }
  }

  /** Start sequence to create a reservation. Note: This is not implemented yet */
  @ShellMethod(value = "Start sequence to create a reservation.", key = "new reservation")
  public void createReservation() {
    // TODO: Implement, need implementation of departure first
  }
}
