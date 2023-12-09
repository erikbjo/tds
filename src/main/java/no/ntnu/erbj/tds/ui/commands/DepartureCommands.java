package no.ntnu.erbj.tds.ui.commands;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import no.ntnu.erbj.tds.dao.DepartureDao;
import no.ntnu.erbj.tds.model.departures.Departure;
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
  private final HelperCommands helperCommands;

  /**
   * DepartureCommands constructor. Uses constructor injection to get the departureDAO object.
   *
   * @param departureDao injects the departureDAO object.
   */
  public DepartureCommands(DepartureDao departureDao, HelperCommands helperCommands) {
    this.departureDao = departureDao;
    this.helperCommands = helperCommands;
  }

  /**
   * Stylised table of all departures, sorted by departure time. Uses {@link
   * no.ntnu.erbj.tds.ui.utilities.TablePrinter#printDeparturesInTableFormat(List)}
   */
  @ShellMethod(value = "List all departures in a stylised table.", key = "departure table")
  public void listDepartureTable() {
    List<Departure> departures =
        SortUtility.sortBy(
            departureDao.getAll(), Comparator.comparing(Departure::getDepartureTime));

    if (departures.isEmpty()) {
      Printer.printNoDeparturesFound();
      return;
    }

    TablePrinter.printDeparturesInTableFormat(departures);
  }

  /** Set the track of a departure. */
  @ShellMethod(value = "Set the track of a departure.", key = "departure set track")
  public void setTrack() {
    listDepartureTable();

    Scanner scanner = new Scanner(System.in);

    Optional<Departure> departure = helperCommands.getDepartureFromUser(scanner);
    if (departure.isEmpty()) {
      Printer.printExitString();
      return;
    }

    Optional<Integer> track = helperCommands.getTrackFromUser(scanner);
    if (track.isEmpty()) {
      Printer.printExitString();
      return;
    }

    try {
      departure.get().setTrack(track.get());
      departureDao.update(departure.get());
      Printer.printTrackSetSuccessfully();
    } catch (IllegalArgumentException e) {
      Printer.printException(e); // Unexpected exception
    }
  }

  /** Set the delay of a departure. */
  @ShellMethod(value = "Set the delay of a departure.", key = "departure set delay")
  public void setDelay() {
    listDepartureTable();

    Scanner scanner = new Scanner(System.in);

    Optional<Departure> departure = helperCommands.getDepartureFromUser(scanner);
    if (departure.isEmpty()) {
      Printer.printNoDeparturesWithTrainNumber();
      return;
    }

    Optional<LocalTime> delay = helperCommands.getDelayFromUser(scanner);
    if (delay.isEmpty()) {
      Printer.printExitString();
      return;
    }

    try {
      departure.get().setDelayLocalTime(delay.get());
      departureDao.update(departure.get());
      Printer.printDelaySetSuccessfully();
    } catch (IllegalArgumentException e) {
      Printer.printException(e); // Unexpected exception
    }
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
      TablePrinter.printDeparturesInTableFormat(departures);
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
      TablePrinter.printDeparturesInTableFormat(departures);
    }
  }
}
