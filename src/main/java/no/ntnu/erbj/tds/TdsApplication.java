package no.ntnu.erbj.tds;

import java.util.Scanner;
import no.ntnu.erbj.tds.ui.cli.utilities.TdsLogger;
import no.ntnu.erbj.tds.ui.gui.GuiLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class of the application. This class is responsible for starting the application.
 *
 * <p>The application can be started in two different modes: CLI and GUI. The user is prompted to
 * choose which mode to start the application in. The user can also choose to exit the application.
 *
 * @see GuiLauncher
 * @version 1.0
 * @author Erik Bjørnsen
 */
@SpringBootApplication
public class TdsApplication {

  /**
   * The main method of the application. This method is responsible for starting the application.
   * The user is prompted to choose which mode to start the application in. The user can also choose
   * to exit the application.
   *
   * @param args The command line arguments.
   */
  public static void main(String[] args) {
    TdsLogger logger = TdsLogger.getInstance();

    logger.info("Choose mode:");
    logger.info("1. CLI");
    logger.info("2. GUI");
    logger.info("3. Exit");
    logger.info("Enter choice (1/2/3): ");

    Scanner scanner = new Scanner(System.in);
    String choice = scanner.nextLine();

    switch (choice) {
      case "1":
        SpringApplication.run(TdsApplication.class, args);
        break;
      case "2":
        launchGui(args);
        break;
      case "3":
        logger.info("Exiting...");
        System.exit(0);
        break; // Not necessary, but good practice and prevents a warning
      default:
        logger.warn("Invalid choice. \n");
        // Restart the application
        main(args);
    }
  }

  /**
   * Launches the GUI.
   *
   * @see GuiLauncher
   * @param args The command line arguments.
   */
  public static void launchGui(String[] args) {
    TdsLogger.getInstance().info("Launching GUI...");
    GuiLauncher.launch(args);
  }
}
