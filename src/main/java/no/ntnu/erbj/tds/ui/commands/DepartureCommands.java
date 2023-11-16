package no.ntnu.erbj.tds.ui.commands;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import no.ntnu.erbj.tds.dao.DepartureDao;
import no.ntnu.erbj.tds.model.Departure;
import no.ntnu.erbj.tds.ui.utilities.*;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Commands for manipulating departure objects.
 *
 * @author Erik
 * @version 2.0
 */
@ShellComponent
@EnableTransactionManagement
public class DepartureCommands {

  private final DepartureDao departureDao;

  /**
   * DepartureCommands constructor. Uses constructor injection to get the departureDAO object.
   *
   * @param departureDao injects the departureDAO object.
   */
  public DepartureCommands(DepartureDao departureDao) {
    this.departureDao = departureDao;
  }

  /** Stylised table of all departures, sorted by departure time. */
  @ShellMethod(value = "List all departures in a stylised table.", key = "departure table")
  public void listDepartureTable() {
    List<Departure> departures =
        SortUtility.sortBy(
            departureDao.getAll(), Comparator.comparing(Departure::getDepartureTime));

    if (departures.isEmpty()) {
      Printer.printNoDeparturesFound();
      return;
    }

    Printer.printDeparturesInTableFormat(departures);
  }

  /** Set the track of a departure. */
  @ShellMethod(value = "Set the track of a departure.", key = "departure set track")
  public void setTrack() {
    listDepartureTable();

    Scanner scanner = new Scanner(System.in);
    TdsLogger logger = TdsLogger.getInstance();

    logger.info("Enter the train number of the departure you want to set the track of: ");
    Long trainNumber = scanner.nextLong();
    Departure departure = departureDao.getByTrainNumber(trainNumber);

    if (departure == null) {
      Printer.printNoDeparturesWithTrainNumber();
      return;
    }

    logger.info("Enter the track you want to set: ");
    int track = scanner.nextInt();
    departure.setTrack(track);
    departureDao.update(departure);
    logger.info(Colorize.colorizeText(AnsiColors.GREEN, "Track set."));
  }

  /** Set the delay of a departure. */
  @ShellMethod(value = "Set the delay of a departure.", key = "departure set delay")
  public void setDelay() {
    listDepartureTable();

    Scanner scanner = new Scanner(System.in);

    Printer.printEnterTrainNumberForDelay();
    Long trainNumber = scanner.nextLong();
    Departure departure = departureDao.getByTrainNumber(trainNumber);

    if (departure == null) {
      Printer.printNoDeparturesWithTrainNumber();
      return;
    }

    Printer.printEnterDelay();
    String delay = scanner.nextLine();
    try {
      departure.setDelay(delay);
      departureDao.update(departure);
    } catch (IllegalArgumentException e) {
      Printer.printException(e);
      return;
    }
    Printer.printDelaySetSuccessfully();
  }

  /** Search for a departure by train number. */
  @ShellMethod(
      value =
          "Search for a departure by train number. "
              + "Takes an integer as parameter. ex: departure search train 1234",
      key = "departure search train")
  public void searchByTrainNumber(long trainNumber) {
    List<Departure> departures = departureDao.getAll();

    departures =
        departures
            .stream() // filter by departure.train CONTAINS trainNumber, so 1234 will match 12345
            .filter(
                departure ->
                    departure.getTrain().getId().toString().contains(String.valueOf(trainNumber)))
            .toList();

    if (departures.isEmpty()) {
      Printer.printNoDeparturesWithTrainNumber();
    } else {
      Printer.printDeparturesInTableFormat(departures);
    }
  }

  /** Search for a departure by destination. */
  @ShellMethod(
      value =
          "Search for a departure by destination. "
              + "Takes the search string as parameter. ex: departure search destination Oslo",
      key = "departure search destination")
  public void searchByDestination(String destination) {
    List<Departure> departures = departureDao.getAll();

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
      Printer.printNoDeparturesWithDestination();
    } else {
      Printer.printDeparturesInTableFormat(departures);
    }
  }
}
