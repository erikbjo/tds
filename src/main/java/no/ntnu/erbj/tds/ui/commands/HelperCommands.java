package no.ntnu.erbj.tds.ui.commands;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import no.ntnu.erbj.tds.dao.DepartureDao;
import no.ntnu.erbj.tds.dao.TrainDao;
import no.ntnu.erbj.tds.dao.WagonDao;
import no.ntnu.erbj.tds.model.Train;
import no.ntnu.erbj.tds.model.Wagon;
import no.ntnu.erbj.tds.shared.utilities.StringValidator;
import no.ntnu.erbj.tds.shared.utilities.TimeParser;
import no.ntnu.erbj.tds.ui.utilities.Printer;
import no.ntnu.erbj.tds.ui.utilities.SortUtility;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class HelperCommands {

  private final DepartureDao departureDao;
  private final WagonDao wagonDao;
  private final TrainDao trainDao;

  /**
   * HelperCommands constructor. Uses constructor injection to get the trainCommands object.
   *
   * @param departureDao injects the departureDao object.
   * @param wagonDao injects the wagonDao object.
   * @param trainDao injects the trainDao object.
   */
  public HelperCommands(DepartureDao departureDao, WagonDao wagonDao, TrainDao trainDao) {
    this.departureDao = departureDao;
    this.wagonDao = wagonDao;
    this.trainDao = trainDao;
  }

  /** Stylised table of all un trains, sorted by number of wagons. */
  public void listUnoccupiedTrainsTable() {
    List<Train> trains =
        SortUtility.sortBy(
            trainDao.getAllUnoccupiedTrains(), Comparator.comparing(Train::getNumberOfWagons));

    Printer.printTrainsInTableFormat(trains);
  }

  /** Stylish table of all wagons, sorted by number of seats. */
  public void listWagonTable() {
    List<Wagon> wagons =
        SortUtility.sortBy(wagonDao.getAll(), Comparator.comparing(Wagon::getNumberOfSeats));

    Printer.printWagonsInTableFormat(wagons);
  }

  /**
   * Private helper method to get a train from the user.<br>
   * Can return an empty optional if the user enters "exit".
   *
   * @param scanner the scanner to use to get input from the user
   * @return an optional train
   */
  public Optional<Train> getTrainFromUser(Scanner scanner) {
    if (trainDao.getAllUnoccupiedTrains().isEmpty()) {
      Printer.printNoUnoccupiedTrains();
      return Optional.empty();
    }

    listUnoccupiedTrainsTable();
    Train train = null;

    String trainIdString;
    boolean isTrainIdValid;

    do {
      Printer.printEnterTrainNumber();
      trainIdString = scanner.nextLine();

      if (trainIdString.equalsIgnoreCase("exit")) {
        return Optional.empty();
      }

      try {
        isTrainIdValid = trainDao.trainIsNotOccupied(trainIdString);
        train = trainDao.findByTrainNumber(trainIdString).orElseThrow();
      } catch (IllegalArgumentException e) {
        isTrainIdValid = false;
      }
    } while (!isTrainIdValid);

    return Optional.of(train);
  }

  /**
   * Private helper method to get a departure time from the user.<br>
   * Can return an empty optional if the user enters "exit".
   *
   * @param scanner the scanner to use to get input from the user
   * @return an optional departure time
   */
  public Optional<LocalTime> getDepartureTimeFromUser(Scanner scanner) {
    Optional<LocalTime> departureTime = Optional.empty();

    do {
      Printer.printEnterDepartureTime();
      String departureTimeString = scanner.nextLine();

      if (departureTimeString.equalsIgnoreCase("exit")) {
        return Optional.empty();
      }

      try {
        StringValidator.validateString(departureTimeString, "Departure time");
        departureTime = Optional.of(TimeParser.parseTime(departureTimeString, "Departure time"));
      } catch (IllegalArgumentException e) {
        Printer.printInvalidInput("departure time");
      }
    } while (departureTime.isEmpty());

    return departureTime;
  }

  /**
   * Private helper method to get a line from the user.<br>
   * Can return an empty optional if the user enters "exit".
   *
   * @param scanner the scanner to use to get input from the user
   * @return an optional line
   */
  public Optional<String> getLineFromUser(Scanner scanner) {
    Optional<String> lineOpt = Optional.empty();

    do {
      Printer.printEnterLine();
      String line = scanner.nextLine();

      if (line.equalsIgnoreCase("exit")) {
        return Optional.empty();
      }

      try {
        StringValidator.validateString(line, "Line");
        lineOpt = Optional.of(line);
      } catch (IllegalArgumentException e) {
        Printer.printInvalidInput("line");
      }
    } while (lineOpt.isEmpty());

    return lineOpt;
  }

  /**
   * Private helper method to get a destination from the user.<br>
   * Can return an empty optional if the user enters "exit".
   *
   * @param scanner the scanner to use to get input from the user
   * @return an optional destination
   */
  public Optional<String> getDestinationFromUser(Scanner scanner) {
    Optional<String> destinationOpt = Optional.empty();

    do {
      Printer.printEnterDestination();
      String destination = scanner.nextLine();

      if (destination.equalsIgnoreCase("exit")) {
        return Optional.empty();
      }

      try {
        StringValidator.validateString(destination, "Destination");
        destinationOpt = Optional.of(destination);
      } catch (IllegalArgumentException e) {
        Printer.printInvalidInput("destination");
      }
    } while (destinationOpt.isEmpty());

    return destinationOpt;
  }

  /**
   * Private helper method to get a track from the user.<br>
   * Can return an empty optional if the user enters "exit".
   *
   * @param scanner the scanner to use to get input from the user
   * @return an optional track
   */
  public Optional<Integer> getTrackFromUser(Scanner scanner) {
    Optional<Integer> trackOpt = Optional.empty();

    do {
      Printer.printEnterTrack();
      String trackString = scanner.nextLine();

      if (trackString.equalsIgnoreCase("exit")) {
        return Optional.empty();
      }

      try {
        int track = Integer.parseInt(trackString);
        trackOpt = Optional.of(track);
      } catch (IllegalArgumentException e) {
        Printer.printInvalidInput("track");
      }
    } while (trackOpt.isEmpty());

    return trackOpt;
  }

  /**
   * Private helper method to get a delay from the user.<br>
   * Can return an empty optional if the user enters "exit".
   *
   * @param scanner the scanner to use to get input from the user
   * @return an optional delay
   */
  public Optional<LocalTime> getDelayFromUser(Scanner scanner) {
    Optional<LocalTime> delayOpt = Optional.empty();

    do {
      Printer.printEnterDelay();
      String delayString = scanner.nextLine();

      if (delayString.equalsIgnoreCase("exit")) {
        return Optional.empty();
      }

      try {
        LocalTime delay = TimeParser.parseTime(delayString, "Delay");
        delayOpt = Optional.of(delay);
      } catch (IllegalArgumentException e) {
        Printer.printInvalidInput("delay");
      }
    } while (delayOpt.isEmpty());

    return delayOpt;
  }
}
