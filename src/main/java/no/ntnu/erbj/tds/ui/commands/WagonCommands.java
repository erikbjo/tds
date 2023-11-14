package no.ntnu.erbj.tds.ui.commands;

import no.ntnu.erbj.tds.dao.WagonDAO;
import no.ntnu.erbj.tds.ui.utilities.TdsLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Commands for manipulating wagon objects.
 *
 * @author Erik
 * @version 1.0
 */
@ShellComponent
@EnableTransactionManagement
public class WagonCommands {
  @Autowired private WagonDAO wagonDAO;

  /** List all wagons. */
  @ShellMethod(value = "List all wagons.", key = "wagon list")
  public void listWagons() {
    wagonDAO.getAll().forEach(wagon -> TdsLogger.getInstance().info(wagon.toString()));
  }
}
