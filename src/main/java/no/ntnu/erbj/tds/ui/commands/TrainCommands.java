package no.ntnu.erbj.tds.ui.commands;

import no.ntnu.erbj.tds.dao.TrainDAO;
import no.ntnu.erbj.tds.ui.utilities.TdsLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Commands for manipulating train objects.
 *
 * @author Erik
 * @version 1.0
 */
@ShellComponent
@EnableTransactionManagement
public class TrainCommands {
  @Autowired private TrainDAO trainDAO;

  /** List all trains. */
  @ShellMethod(value = "List all trains.", key = "train list")
  public void listTrains() {
    trainDAO
        .getAll()
        .forEach(
            train -> {
              TdsLogger.getInstance().info(train.toString());
            });
  }
}
