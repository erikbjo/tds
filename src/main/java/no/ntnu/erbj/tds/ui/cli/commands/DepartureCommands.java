package no.ntnu.erbj.tds.ui.cli.commands;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
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
  private static final String TABLE_FORMAT = "| %-8s | %-10s | %-10s | %-14s | %-40s |";
  // Above and below are static to follow SonarLint's recommendation
  private static final String DIVIDER =
      "+----------+------------+------------+----------------"
          + "+------------------------------------------+";
  private final String blueFormat = Colorize.colorizeText(ANSIColors.BLUE, TABLE_FORMAT);
  private final String blueDivider = Colorize.colorizeText(ANSIColors.BLUE, DIVIDER);
  @Autowired private DepartureDAO departureDAO;

  /** Prints the table header for departure table. */
  private void printTableHeader() {
    TdsLogger logger = TdsLogger.getInstance();
    logger.info(blueDivider);
    logger.info(String.format(blueFormat, "Time", "Line", "Track", "Train number", "Destination"));
    logger.info(blueDivider);
  }

  /** List all departures, sorted by departure time. */
  @ShellMethod(value = "List all departures.", key = "d-list")
  public void listDepartures() {
    SortUtility.sortBy(departureDAO.getAll(), Comparator.comparing(Departure::getDepartureTime))
        .forEach(departure -> TdsLogger.getInstance().info(departure.toString()));
  }

  /** Stylised table of all departures, sorted by departure time. */
  @ShellMethod(value = "List all departures in a stylised table.", key = "d-table")
  public void listDepartureTable() {
    TdsLogger logger = TdsLogger.getInstance();

    List<Departure> departures =
        SortUtility.sortBy(
            departureDAO.getAll(), Comparator.comparing(Departure::getDepartureTime));

    if (departures.isEmpty()) {
      logger.info("No departures found. Please create a departure first.");
      return;
    }

    printTableHeader();

    for (Departure departure : departures) {
      logger.info(
          String.format(
              TABLE_FORMAT,
              departure.getDepartureTime(),
              departure.getLine(),
              departure.getTrack(),
              departure.getTrain().getId(),
              departure.getDestination()));
    }

    logger.info(DIVIDER);
  }

  /** Set the track of a departure. */
  @ShellMethod(value = "Set the track of a departure.", key = "d-set-track")
  public void setTrack() {
    listDepartureTable();

    Scanner scanner = new Scanner(System.in);
    TdsLogger logger = TdsLogger.getInstance();

    logger.info("Enter the train number of the departure you want to set the track of: ");
    Long trainNumber = scanner.nextLong();
    Departure departure = departureDAO.getByTrainNumber(trainNumber);

    if (departure == null) {
      logger.info("No departure found with train number " + trainNumber);
      return;
    } else {
      logger.info("Departure found: " + departure);
    }

    logger.info("Enter the track you want to set: ");
    int track = scanner.nextInt();
    departure.setTrack(track);
    departureDAO.update(departure);
    logger.info(Colorize.colorizeText(ANSIColors.GREEN, "Track set."));
  }

  /** Set the delay of a departure. */
  @ShellMethod(value = "Set the delay of a departure.", key = "d-set-delay")
  public void setDelay() {
    listDepartureTable();

    Scanner scanner = new Scanner(System.in);
    TdsLogger logger = TdsLogger.getInstance();

    logger.info("Enter the train number of the departure you want to set the delay of: ");
    Long trainNumber = scanner.nextLong();
    Departure departure = departureDAO.getByTrainNumber(trainNumber);

    if (departure == null) {
      logger.info("No departure found with train number " + trainNumber);
      return;
    } else {
      logger.info("Departure found: " + departure);
    }

    logger.info("Enter the delay you want to set: ");
    String delay = scanner.nextLine();
    departure.setDelay(delay);
    departureDAO.update(departure);
    logger.info(Colorize.colorizeText(ANSIColors.GREEN, "Delay set."));
  }

  /** Search for a departure by train number. */
  @ShellMethod(value = "Search for a departure by train number.", key = "d-search-train-number")
  public void searchByTrainNumber(long trainNumber) {
    TdsLogger logger = TdsLogger.getInstance();

    List<Departure> departures = departureDAO.getAll();

    departures =
        departures
            .stream() // filter by departure.train CONTAINS trainNumber, so 1234 will match 12345
            .filter(
                departure ->
                    departure.getTrain().getId().toString().contains(String.valueOf(trainNumber)))
            .toList();

    if (departures.isEmpty()) {
      logger.info("No departures found with train number " + trainNumber);
    } else {
      printTableHeader();
      departures.forEach(
          departure ->
              logger.info(
                  String.format(
                      TABLE_FORMAT,
                      departure.getDepartureTime(),
                      departure.getLine(),
                      departure.getTrack(),
                      departure.getTrain().getId(),
                      departure.getDestination())));
      logger.info(DIVIDER);
    }
  }

  /** Search for a departure by destination. */
  @ShellMethod(value = "Search for a departure by destination.", key = "d-search-destination")
  public void searchByDestination(String destination) {
    TdsLogger logger = TdsLogger.getInstance();

    List<Departure> departures = departureDAO.getAll();

    departures =
        departures.stream() // filter by departure.train CONTAINS destination, so os will match Oslo
            .filter(
                departure ->
                    departure
                        .getDestination()
                        .toLowerCase()
                        .contains(String.valueOf(destination).toLowerCase()))
            .toList();

    if (departures.isEmpty()) {
      logger.info("No departures found with destination " + destination);
    } else {
      printTableHeader();
      departures.forEach(
          departure ->
              logger.info(
                  String.format(
                      TABLE_FORMAT,
                      departure.getDepartureTime(),
                      departure.getLine(),
                      departure.getTrack(),
                      departure.getTrain().getId(),
                      departure.getDestination())));
      logger.info(DIVIDER);
    }
  }
}

/*
  X Legge inn en ny togavgang – det skal ikke være mulig å legge inn et tog med tognummer
tilsvarende eksisterende tog i listen.

  X Tildele spor til en togavgang – ved først å søke opp togavgang basert på tognummer, og så
sette spor.

  X Legg inn forsinkelse på en togavgang – ved å først søke etter en gitt togavgang basert på
tognummer, og deretter legge til forsinkelse.

  X Søke etter en togavgang basert på Tognummer

  X Søke etter togavgang basert på destinasjon
 */
