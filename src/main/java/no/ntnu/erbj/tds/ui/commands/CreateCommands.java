package no.ntnu.erbj.tds.ui.commands;

import java.util.Scanner;
import no.ntnu.erbj.tds.dao.DepartureDAO;
import no.ntnu.erbj.tds.dao.TrainDAO;
import no.ntnu.erbj.tds.dao.WagonDAO;
import no.ntnu.erbj.tds.model.*;
import no.ntnu.erbj.tds.ui.utilities.TdsLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Commands for creating objects.
 *
 * @author Erik
 * @version 1.1
 */
@ShellComponent
@EnableTransactionManagement
public class CreateCommands {
  @Autowired private DepartureDAO departureDAO;

  @Autowired private TrainDAO trainDAO;

  @Autowired private WagonDAO wagonDAO;
  private String exitString = "Exiting object creation."; // For SonarLint

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
      TdsLogger.getInstance().info(exitString);
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
    TdsLogger.getInstance().info("Wagon created: " + wagon);

    TdsLogger.getInstance().info("Trying to enter into DB");
    try {
      wagonDAO.add(wagon);
    } catch (Exception e) {
      TdsLogger.getInstance().warn(e.getMessage());
    }
  }

  /** Start sequence to create a train. */
  @ShellMethod(value = "Start sequence to create a train.", key = "new train")
  public void createTrain() {
    TdsLogger logger = TdsLogger.getInstance();
    //    Scanner scanner = new Scanner(System.in);
    //    logger.info("Do you want to create train? (y/exit): ");
    //    String answer = scanner.nextLine();
    //
    //    if ("exit".equalsIgnoreCase(answer)) {
    //      TdsLogger.getInstance().info("Exiting object creation.");
    //      return;
    //    } else if (!"y".equalsIgnoreCase(answer)) {
    //      TdsLogger.getInstance().info("Invalid input.");
    //      createTrain();
    //    }

    Train train = new Train();
    logger.info("Train created: " + train);

    logger.info("Trying to enter into DB");
    try {
      trainDAO.add(train);
    } catch (Exception e) {
      logger.warn(e.getMessage());
    }
  }

  /** Start sequence to create a departure. */
  @ShellMethod(value = "Start sequence to create a departure.", key = "new departure")
  public void createDeparture() {
    Scanner scanner = new Scanner(System.in);
    TdsLogger logger = TdsLogger.getInstance();
    logger.info("Do you want to create departure? (y/exit): ");
    String answer = scanner.nextLine();

    if ("exit".equalsIgnoreCase(answer)) {
      TdsLogger.getInstance().info(exitString);
    } else if (!"y".equalsIgnoreCase(answer)) {
      TdsLogger.getInstance().info("Invalid input.");
      createDeparture();
    }

    if (trainDAO.getAll().isEmpty()) {
      TdsLogger.getInstance().info("No trains in database. Please create a train first.");
      return;
    }

    String trainIdString;
    boolean isTrainIdValid;

    trainDAO.printAllUnoccupiedTrains();
    do {
      logger.info("Enter train id: ");
      trainIdString = scanner.nextLine();

      try {
        isTrainIdValid =
            trainDAO.find(Long.parseLong(trainIdString)).isPresent()
                && trainDAO.trainIsValid(Long.parseLong(trainIdString));
      } catch (NumberFormatException e) {
        isTrainIdValid = false;
      }

      if (trainIdString.equalsIgnoreCase("exit") || trainIdString.isEmpty()) {
        logger.info(exitString);
        return;
      } else if (!isTrainIdValid) {
        logger.info("Invalid train id.");
      }

    } while (!isTrainIdValid);

    DepartureBuilder builder = new DepartureBuilder();
    logger.info("Enter departure time (HH:mm): ");
    String departureTime = scanner.nextLine();
    builder.setDepartureTime(departureTime);

    logger.info("Enter line: ");
    String line = scanner.nextLine();
    builder.setLine(line);

    logger.info("Enter destination: ");
    String destination = scanner.nextLine();
    builder.setDestination(destination);

    logger.info("Enter track: ");
    int track = scanner.nextInt();
    builder.setTrack(track);

    scanner.nextLine();
    logger.info("Enter delay time (HH:mm): ");
    String delay = scanner.nextLine();
    builder.setDelay(delay);

    Departure departure = builder.build();

    try {
      departureDAO.add(departure);

      trainDAO
          .find(Long.parseLong(trainIdString))
          .ifPresent(
              train -> {
                Train managedTrain = trainDAO.merge(train);
                departure.setTrain(managedTrain);
                departureDAO.update(departure);
              });
    } catch (Exception e) {
      TdsLogger.getInstance().warn(e.getMessage());
    }
  }

  /** Start sequence to create a reservation. Note: This is not implemented yet */
  @ShellMethod(value = "Start sequence to create a reservation.", key = "new reservation")
  public void createReservation() {
    // TODO: Implement, need implementation of departure first
  }
}
