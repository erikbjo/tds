package no.ntnu.erbj.tds.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Class representing a station. A station has a name, a location, a list of departures and a number
 * of platforms. The assignment does specify that only ONE station should be used, but to
 * future-proof the application, the class is made to represent a station, and not THE station. <br>
 * <br>
 * The task description asks for a "register" class; this is the class used to represent that
 * register.
 *
 * @version 1.2
 * @see Departure
 * @author Erik Bjørnsen
 */
@Entity
public class Station {
  @Id @GeneratedValue private Long id;
  private String name;
  private String location;
  @OneToMany private List<Departure> departures;
  private int
      platforms; // Number of platforms at the station, to be remade into a list of platforms

  // TODO: Add a list of platforms

  /**
   * Constructor for the Station class. <br>
   * Overloaded constructor w/o departures: {@link Station#Station(String, String, int)}
   *
   * @param name the name of the station
   * @param location the location of the station
   * @param departures the departures from the station
   * @param platforms the number of platforms at the station
   * @throws IllegalArgumentException if the name or location is null, empty or blank. Also, if the
   *     number of platforms is less than 1.
   */
  public Station(String name, String location, List<Departure> departures, int platforms) {
    if (name == null || name.isEmpty() || name.isBlank()) {
      throw new IllegalArgumentException("Name cannot be null, empty or blank");
    }
    if (location == null || location.isEmpty() || location.isBlank()) {
      throw new IllegalArgumentException("Location cannot be null, empty or blank");
    }
    if (departures == null) {
      throw new IllegalArgumentException("Departures cannot be null");
    }
    if (platforms < 1) {
      throw new IllegalArgumentException("Number of platforms cannot be less than 1");
    }

    this.name = name;
    this.location = location;
    this.departures = departures;
    this.platforms = platforms;
  }

  /**
   * Overloaded constructor for the Station class. <br>
   * See: {@link Station#Station(String, String, java.util.List, int)}
   *
   * @param name the name of the station
   * @param location the location of the station
   * @param platforms the number of platforms at the station
   * @throws IllegalArgumentException if the name or location is null, empty or blank. Also, if the
   *     number of platforms is less than 1.
   */
  public Station(String name, String location, int platforms) {
    if (name == null || name.isEmpty() || name.isBlank()) {
      throw new IllegalArgumentException("Name cannot be null, empty or blank");
    }
    if (location == null || location.isEmpty() || location.isBlank()) {
      throw new IllegalArgumentException("Location cannot be null, empty or blank");
    }
    if (platforms < 1) {
      throw new IllegalArgumentException("Number of platforms cannot be less than 1");
    }

    this.name = name;
    this.location = location;
    this.departures = new ArrayList<>();
    this.platforms = platforms;
  }

  /**
   * Used by DB. <br>
   * See: {@link Station#Station(String, String, java.util.List, int)}
   */
  public Station() {
    // Empty constructor for JPA
  }

  /**
   * Gets the id of the station.
   *
   * @return the id of the station.
   */
  public Long getId() {
    return id;
  }

  /**
   * Gets the name of the station.
   *
   * @return the name of the station.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the location of the station.
   *
   * @return the location of the station.
   */
  public String getLocation() {
    return location;
  }

  /**
   * Gets the departures from the station.
   *
   * @return the departures from the station.
   */
  public List<Departure> getDepartures() {
    return departures;
  }

  /**
   * Gets the number of platforms at the station.
   *
   * @return the number of platforms at the station.
   */
  public int getPlatforms() {
    return platforms;
  }

  /**
   * Adds a departure to the station. If there already is a departure with the same train number,
   * the departure is not added. <br>
   * This check is only done on the local departures of the object, not on the departures in the
   * database.
   *
   * @param departure the departure to add
   * @throws IllegalArgumentException if the departure already exists, or if the train already has a
   *     departure
   */
  public void addDeparture(Departure departure) {
    if (this.departures.contains(departure)) {
      throw new IllegalArgumentException("Departure already exists");
    }
    if (this.departures.stream().anyMatch(dep -> dep.getTrain().equals(departure.getTrain()))) {
      throw new IllegalArgumentException(
          "Train already in use by another departure with this station");
    }
    this.departures.add(departure);
  }

  /**
   * Removes a departure from the station.
   *
   * @param departure the departure to remove
   * @throws IllegalArgumentException if the departure does not exist
   */
  public void removeDeparture(Departure departure) {
    if (!this.departures.contains(departure)) {
      throw new IllegalArgumentException("Departure does not exist");
    }
    this.departures.remove(departure);
  }

  /**
   * Search for a departure by train number. <br>
   * <br>
   * Since there can only be one departure per train, this method returns the first departure with
   * the given train number. <br>
   * <br>
   * If no departure with the given train number exists, null is returned.
   *
   * @param trainNumber the train number to search for
   * @return the departure with the given train number, or null if no such departure exists
   */
  public Departure getDepartureByTrainNumber(String trainNumber) {
    return this.departures.stream()
        .filter(
            dep ->
                dep.getTrain()
                    .getTrainNumber()
                    .toLowerCase()
                    .contains(trainNumber.toLowerCase().trim()))
        .findFirst()
        .orElse(null);
  }

  /**
   * Search for a departure(s) by destination. <br>
   * <br>
   * Since there can be multiple departures with the same destination, this method returns a list of
   * departures with the given destination. <br>
   * <br>
   * If no departure with the given destination exists, an empty list is returned.
   *
   * @param destination the destination to search for
   * @return a list of departure(s) with the given destination, or an empty list if no such
   *     departures
   */
  public List<Departure> getDeparturesByDestination(String destination) {
    return this.departures.stream()
        .filter(
            dep -> dep.getDestination().toLowerCase().contains(destination.toLowerCase().trim()))
        .toList();
  }

  /**
   * Removes departure(s) that has departure time before the given time. <br>
   * Also takes delay into account. <br>
   * <br>
   * Please note that the parameter is a LocalTime object, and not a String.
   *
   * @param time the time to remove departures before
   * @throws IllegalArgumentException if the time is null
   */
  public void removeDeparturesBeforeTime(LocalTime time) {
    if (time == null) {
      throw new IllegalArgumentException("Time cannot be null");
    }

    this.departures.removeIf(
        dep ->
            dep.getDepartureTime()
                .plusHours(dep.getDelay().getHour())
                .plusMinutes(dep.getDelay().getMinute())
                .isBefore(time));
  }

  /**
   * Returns a list of all departures, sorted by departure time. Does not take delay into account.
   * Might return an empty list if there are no departures.
   *
   * @return a list of all departures, sorted by departure time
   */
  public List<Departure> getAllDeparturesSortedByDepartureTime() {
    return this.departures.stream()
        .sorted(Comparator.comparing(Departure::getDepartureTime))
        .toList();
  }
}
