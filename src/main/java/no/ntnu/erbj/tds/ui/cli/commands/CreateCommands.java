package no.ntnu.erbj.tds.ui.cli.commands;

import java.util.Scanner;
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
  }
}
