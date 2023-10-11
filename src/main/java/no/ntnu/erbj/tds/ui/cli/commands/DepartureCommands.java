package no.ntnu.erbj.tds.ui.cli.commands;

import java.util.Comparator;
import no.ntnu.erbj.tds.dao.DepartureDAO;
import no.ntnu.erbj.tds.model.Departure;
import no.ntnu.erbj.tds.ui.cli.utilities.TdsLogger;
import no.ntnu.erbj.tds.ui.shared.SortUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Commands for manipulating departure objects.
 *
 * @author Erik
 * @version 1.0
 */
@ShellComponent
@EnableTransactionManagement
public class DepartureCommands {
  @Autowired private DepartureDAO departureDAO;

  /** List all departures, sorted by departure time. */
  @ShellMethod(value = "List all departures.", key = "departure-list")
  public void listDepartures() {
    SortUtility.sortBy(departureDAO.getAll(), Comparator.comparing(Departure::getDepartureTime))
        .forEach(departure -> TdsLogger.getInstance().info(departure.toString()));
  }
}
