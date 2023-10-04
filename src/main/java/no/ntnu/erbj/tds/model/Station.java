package no.ntnu.erbj.tds.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a station. A station has a name, a location, a list of departures and a number
 * of platforms. The assignment does specify that only ONE station should be used, but to
 * future-proof the application, the class is made to represent a station, and not THE station.
 *
 * @version 1.0
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
  // TODO: TBD if a builder is needed for this class

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
}
