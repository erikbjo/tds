package no.ntnu.erbj.tds.ui.cli.commands;

import java.util.Comparator;
import java.util.List;
import no.ntnu.erbj.tds.dao.DepartureDAO;
import no.ntnu.erbj.tds.model.Departure;
import no.ntnu.erbj.tds.ui.cli.utilities.ANSIColors;
import no.ntnu.erbj.tds.ui.cli.utilities.Colorize;
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

  /** Stylised table of all departures, sorted by departure time. */
  @ShellMethod(value = "List all departures in a stylised table.", key = "departure-list-table")
  public void listDeparturesTable() {
    List<Departure> departures =
        SortUtility.sortBy(
            departureDAO.getAll(), Comparator.comparing(Departure::getDepartureTime));

    if (departures.isEmpty()) {
      TdsLogger.getInstance().info("No departures found. Please create a departure first.");
      return;
    }

    TdsLogger logger = TdsLogger.getInstance();

    String tableFormat = "| %-8s | %-10s | %-10s | %-14s | %-40s |";
    String divider =
        "+----------+------------+------------+----------------"
            + "+------------------------------------------+";


    String coloredFormat = Colorize.colorizeText(ANSIColors.BLUE, tableFormat);
    String coloredDivider = Colorize.colorizeText(ANSIColors.BLUE, divider);

    logger.info(Colorize.colorizeBackground(ANSIColors.BG_BLUE, "Departures"));
    logger.info(coloredDivider);
    logger.info(
        String.format(coloredFormat, "Time", "Line", "Track", "Train number", "Destination"));
    logger.info(coloredDivider);

    for (Departure departure : departures) {
      TdsLogger.getInstance()
          .info(
              String.format(
                  tableFormat,
                  departure.getDepartureTime(),
                  departure.getLine(),
                  departure.getTrack(),
                  departure.getTrain().getId(),
                  departure.getDestination()));
    }

    logger.info(divider);
  }
}
