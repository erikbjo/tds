package no.ntnu.tds.model;

import jakarta.persistence.*; // Importing 5+ packages from jakarta.persistence, so using * is ok
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Class representing a departure from a station. A departure has a departure time, a line, a
 * destination, a track, a delay and a train. The class also has a builder class, which is used to
 * create a departure object.
 *
 * @version 1.0
 * @see DepartureBuilder
 * @see Train
 * @author Erik Bjørnsen
 */
@Entity
public class Departure {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(cascade = CascadeType.ALL)
  private Train train;

  private LocalTime departureTime;
  private String line;
  private String destination;

  private int track;
  private LocalTime delay;

  /**
   * Constructor for the Departure class.
   *
   * @param builder the builder to build the departure
   */
  public Departure(DepartureBuilder builder) {
    this.departureTime = builder.getDepartureTime();
    this.line = builder.getLine();
    this.destination = builder.getDestination();
    this.track = builder.getTrack();
    this.delay = builder.getDelay();
    this.train = builder.getTrain();
  }

  /** Used by DB. */
  public Departure() {
    // Empty constructor for JPA
  }

  /**
   * Gets the departure time of the departure.
   *
   * @return the departure time of the departure.
   */
  public LocalTime getDepartureTime() {
    return departureTime;
  }

  /**
   * Gets the line of the departure.
   *
   * @return the line of the departure.
   */
  public String getLine() {
    return line;
  }

  /**
   * Gets the destination of the departure.
   *
   * @return the destination of the departure.
   */
  public String getDestination() {
    return destination;
  }

  /**
   * Gets the track of the departure.
   *
   * @return the track of the departure.
   */
  public int getTrack() {
    return track;
  }

  /**
   * Sets the track of the departure.
   *
   * @param track the track of the departure.
   * @throws IllegalArgumentException if the track is less than 1.
   */
  public void setTrack(int track) {
    if (track < 1) {
      throw new IllegalArgumentException("Track cannot be less than 1");
    }
    this.track = track;
  }

  /**
   * Gets the delay of the departure.
   *
   * @return the delay of the departure.
   */
  public LocalTime getDelay() {
    return delay;
  }

  /**
   * Sets the delay of the departure.
   *
   * @param delay the delay of the departure.
   * @throws IllegalArgumentException if the delay is null, empty or blank. Also, if the delay is
   *     not in the format HH:mm.
   */
  public void setDelay(String delay) {
    if (delay == null || delay.isEmpty() || delay.isBlank()) {
      throw new IllegalArgumentException("Delay cannot be null, empty or blank");
    }

    try {
      this.delay = LocalTime.parse(delay, DateTimeFormatter.ofPattern("HH:mm"));
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Invalid delay time format. Please use HH:mm format.", e);
    }
  }

  /**
   * Gets the train of the departure.
   *
   * @return the train of the departure.
   */
  public Train getTrain() {
    return train;
  }

  /**
   * Gets the id of the departure.
   *
   * @return the id of the departure.
   */
  public Long getId() {
    return id;
  }
}
