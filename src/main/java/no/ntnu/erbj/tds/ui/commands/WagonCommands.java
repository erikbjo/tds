package no.ntnu.erbj.tds.ui.commands;

import no.ntnu.erbj.tds.dao.WagonDao;
import no.ntnu.erbj.tds.ui.utilities.TablePrinter;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Commands for manipulating wagon objects.
 *
 * @author Erik
 * @version 1.1
 */
@ShellComponent
@EnableTransactionManagement
public class WagonCommands {
  private final WagonDao wagonDao;

  /**
   * WagonCommands constructor. Uses constructor injection to get the wagonDAO object.
   *
   * @param wagonDao injects the wagonDAO object.
   */
  public WagonCommands(WagonDao wagonDao) {
    this.wagonDao = wagonDao;
  }

  /** Stylised table of all wagons. */
  @ShellMethod(value = "List all wagons in a stylised table.", key = "wagon table")
  public void listWagonTable() {
    TablePrinter.printWagonsInTableFormat(wagonDao.getAll());
  }
}
