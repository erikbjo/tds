package no.ntnu.erbj.tds.ui.commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Commands for manipulating wagon objects.
 *
 * @author Erik
 * @version 3.0
 */
@ShellComponent
@EnableTransactionManagement
public class WagonCommands {
  private final HelperCommands helperCommands;

  /**
   * WagonCommands constructor. Uses constructor injection to get the helperCommands object.
   *
   * @param helperCommands injects the helperCommands object.
   */
  public WagonCommands(HelperCommands helperCommands) {
    this.helperCommands = helperCommands;
  }

  /** Stylised table of all wagons. */
  @ShellMethod(value = "List all wagons in a stylised table.", key = "wagon table")
  public void listWagonTable() {
    helperCommands.listAllWagonTable();
  }
}
