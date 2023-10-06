package no.ntnu.erbj.tds.ui.cli.commands;

import java.util.Scanner;
import no.ntnu.erbj.tds.dao.TrainDAO;
import no.ntnu.erbj.tds.dao.WagonDAO;
import no.ntnu.erbj.tds.model.Train;
import no.ntnu.erbj.tds.model.Wagon;
import no.ntnu.erbj.tds.model.WagonType;
import no.ntnu.erbj.tds.ui.cli.utilities.TdsLogger;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class CreateCommands {

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
      WagonDAO.getInstance().add(wagon);
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
      TrainDAO.getInstance().add(train);
    } catch (Exception e) {
      TdsLogger.getInstance().warn(e.getMessage());
    }
  }

  @ShellMethod(value = "Start sequence to create a departure.", key = "create-departure")
  public void createDeparture() {
    // TODO: Implement, need implementation of train first
  }

  @ShellMethod(value = "Start sequence to create a reservation.", key = "create-reservation")
  public void createReservation() {
    // TODO: Implement, need implementation of departure first
  }
}
