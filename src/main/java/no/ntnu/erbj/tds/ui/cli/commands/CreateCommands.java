package no.ntnu.erbj.tds.ui.cli.commands;

import java.util.Scanner;
import no.ntnu.erbj.tds.dao.DepartureDAO;
import no.ntnu.erbj.tds.dao.TrainDAO;
import no.ntnu.erbj.tds.dao.WagonDAO;
import no.ntnu.erbj.tds.model.*;
import no.ntnu.erbj.tds.ui.cli.utilities.TdsLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ShellComponent
@EnableTransactionManagement
public class CreateCommands {
  @Autowired
  private DepartureDAO departureDAO;

  @Autowired
  private TrainDAO trainDAO;

  @Autowired
  private WagonDAO wagonDAO;

  @ShellMethod(value = "Start sequence to create a wagon.", key = "create-wagon")
  public void createWagon() {
    Scanner scanner = new Scanner(System.in);
    TdsLogger logger = TdsLogger.getInstance();
    logger.info("Enter wagon's type (or 'exit' to stop): ");
    for (WagonType value : WagonType.values()) {
      logger.info(value.toString());
    }

    String wagonType = scanner.nextLine();

    if ("exit".equalsIgnoreCase(wagonType)) {
      TdsLogger.getInstance().info("Exiting object creation.");
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

  @ShellMethod(value = "Start sequence to create a train.", key = "create-train")
  public void createTrain() {
    Scanner scanner = new Scanner(System.in);
    TdsLogger logger = TdsLogger.getInstance();
    logger.info("Do you want to create train? (y/exit): ");
    String answer = scanner.nextLine();

    if ("exit".equalsIgnoreCase(answer)) {
      TdsLogger.getInstance().info("Exiting object creation.");
      return;
    } else if (!"y".equalsIgnoreCase(answer)) {
      TdsLogger.getInstance().info("Invalid input.");
      createTrain();
    }

    Train train = new Train();
    TdsLogger.getInstance().info("Train created: " + train);

    TdsLogger.getInstance().info("Trying to enter into DB");
    try {
      trainDAO.add(train);
    } catch (Exception e) {
      TdsLogger.getInstance().warn(e.getMessage());
    }
  }

  @ShellMethod(value = "Start sequence to create a departure.", key = "create-departure")
  public void createDeparture() {
    Scanner scanner = new Scanner(System.in);
    TdsLogger logger = TdsLogger.getInstance();
    logger.info("Do you want to create departure? (y/exit): ");
    String answer = scanner.nextLine();

    if ("exit".equalsIgnoreCase(answer)) {
      TdsLogger.getInstance().info("Exiting object creation.");
    } else if (!"y".equalsIgnoreCase(answer)) {
      TdsLogger.getInstance().info("Invalid input.");
      createDeparture();
    }

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

    logger.info("Enter delay time (HH:mm): ");
    String delay = scanner.nextLine();
    builder.setDelay(delay);

    logger.info("Enter train id: ");
    trainDAO.printAllDetails();
    Long trainId = scanner.nextLong();
    builder.setTrain(trainDAO.find(trainId).get());

    Departure departure = builder.build();
    try {
      departureDAO.add(departure);
    } catch (Exception e) {
      TdsLogger.getInstance().warn(e.getMessage());
    }
  }

  @ShellMethod(value = "Start sequence to create a reservation.", key = "create-reservation")
  public void createReservation() {
    // TODO: Implement, need implementation of departure first
  }
}
