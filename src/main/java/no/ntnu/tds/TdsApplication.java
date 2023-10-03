package no.ntnu.tds;

import java.util.Scanner;

import no.ntnu.tds.ui.cli.utilities.TdsLogger;
import no.ntnu.tds.ui.gui.GuiLauncher;
import org.jline.terminal.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TdsApplication {

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
      default:
        logger.warn("Invalid choice. \n");
        // Restart the application
        main(args);
    }
  }

  public static void launchGui(String[] args) {
    // Your JavaFX application launch code here
    System.out.println("Launching GUI...");
    // For instance, if you have a separate JavaFX main class:
    // Application.launch(YourJavaFXMainClass.class);
    GuiLauncher.launch(args);
  }
}
