package no.ntnu.erbj.tds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.shell.command.annotation.CommandScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * The main class of the application. This class is responsible for starting the application.
 *
 * @version 3.0
 * @author Erik Bjørnsen
 */
@SpringBootApplication
@EnableTransactionManagement
@CommandScan(basePackages = "no.ntnu.erbj.tds.ui.cli.commands")
@EntityScan(basePackages = "no.ntnu.erbj.tds.model")
public class TdsApplication {

  /**
   * The main method of the application. This method is responsible for starting the application.
   *
   * @param args The command line arguments.
   */
  public static void main(String[] args) {
    SpringApplication.run(TdsApplication.class, args);
  }
}
