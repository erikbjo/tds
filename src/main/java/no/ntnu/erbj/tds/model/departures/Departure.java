package no.ntnu.erbj.tds.model.departures;

import static no.ntnu.erbj.tds.shared.utilities.StringValidator.validateString;
import static no.ntnu.erbj.tds.shared.utilities.TimeParser.parseTime;

import jakarta.persistence.*;
import java.time.LocalTime;
import no.ntnu.erbj.tds.model.Station;
import no.ntnu.erbj.tds.model.Train;

/**
 * Class representing a departure from a station. A departure has a departure time, a line, a
 * destination, a track, a delay and a train. The class also has a builder class, which is used to
 * create a departure object.
 *
 * @version 1.2
 * @see DepartureBuilder
 * @see Train
 * @author Erik Bjørnsen
 */
@Entity
public class Departure {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne()
  @JoinColumn(name = "train_id")
  private Train train;

  @ManyToOne
  @JoinColumn(name = "station_id")
  private Station station;

  private LocalTime departureTime;
  private String line;
  private String destination;

  private int track;
  private LocalTime delay;

  /**
   * Constructor for the Departure class. Package private, so it can only be used by the builder.
   *
   * @param builder the builder to build the departure {@link DepartureBuilder}
   * @throws IllegalArgumentException if the builder is null.
   */
  Departure(DepartureBuilder builder) {
    if (builder == null) {
      throw new IllegalArgumentException("Builder cannot be null");
    }

    if (builder.getDelay() == null) {
      this.delay = LocalTime.of(0, 0);
    } else {
      this.delay = builder.getDelay();
    }

    if (builder.getDepartureTime() == null) {
      this.departureTime = LocalTime.of(0, 0);
    } else {
      this.departureTime = builder.getDepartureTime();
    }

    this.line = builder.getLine();
    this.destination = builder.getDestination();
    this.track = builder.getTrack();
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
   * Sets the departure time of the departure.
   *
   * @param departureTime the departure time of the departure.
   * @throws IllegalArgumentException if the departure time is null, empty or blank. Also, if the
   *     departure time is not in the format HH:mm.
   */
  public void setDepartureTime(String departureTime) {
    validateString(departureTime, "Departure time");

    this.departureTime = parseTime(departureTime, "Departure time");
  }

  /**
   * Sets the departure time of the departure. Uses LocalTime instead of String.
   *
   * @param departureTime the departure time of the departure.
   * @throws IllegalArgumentException if the departure time is null.
   */
  public void setDepartureLocalTime(LocalTime departureTime) {
    if (departureTime == null) {
      throw new IllegalArgumentException("Departure time cannot be null");
    }
    this.departureTime = departureTime;
  }

  /**
   * Sets the delay of the departure. Uses LocalTime instead of String.
   *
   * @param delay the delay of the departure.
   * @throws IllegalArgumentException if the delay is null.
   */
  public void setDelayLocalTime(LocalTime delay) {
    if (delay == null) {
      throw new IllegalArgumentException("Delay cannot be null");
    }
    this.delay = delay;
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
   * Sets the line of the departure.
   *
   * @param line the line of the departure.
   * @throws IllegalArgumentException if the line is null, empty or blank.
   */
  public void setLine(String line) {
    validateString(line, "Line");
    this.line = line;
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
   * Sets the destination of the departure.
   *
   * @param destination the destination of the departure.
   * @throws IllegalArgumentException if the destination is null, empty or blank.
   */
  public void setDestination(String destination) {
    validateString(destination, "Destination");
    this.destination = destination;
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
   * Sets the track of the departure. If the track is -1, it means that the train is not assigned to
   * a track.
   *
   * @param track the track of the departure.
   * @throws IllegalArgumentException if the track is less than 1.
   */
  public void setTrack(int track) {
    if (track < 1 && track != -1) {
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
    validateString(delay, "Delay");
    this.delay = parseTime(delay, "Delay");
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
   * Sets the train of the departure.
   *
   * @param train the train of the departure.
   * @throws IllegalArgumentException if the train is null.
   */
  public void setTrain(Train train) {
    if (train == null) {
      throw new IllegalArgumentException("Train cannot be null");
    }
    this.train = train;
  }

  /**
   * Gets the id of the departure.
   *
   * @return the id of the departure.
   */
  public Long getId() {
    return id;
  }

  /**
   * Gets the estimated arrival time of the departure.
   *
   * @return the estimated arrival time of the departure.
   */
  public LocalTime getEstimatedArrival() {
    return departureTime.plusHours(delay.getHour()).plusMinutes(delay.getMinute());
  }
}
