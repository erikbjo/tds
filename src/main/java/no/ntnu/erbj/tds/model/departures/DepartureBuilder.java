package no.ntnu.erbj.tds.model.departures;

import static no.ntnu.erbj.tds.shared.utilities.StringValidator.validateString;
import static no.ntnu.erbj.tds.shared.utilities.TimeParser.parseTime;

import java.time.LocalTime;
import no.ntnu.erbj.tds.model.Train;

/**
 * A builder class for the departure class. Can have these parameters:
 *
 * <ul>
 *   <li>departureTime
 *   <li>line
 *   <li>destination
 *   <li>track
 *   <li>delay
 *   <li>train
 * </ul>
 *
 * @version 1.0
 * @see Departure
 * @author Erik Bjørnsen
 */
public class DepartureBuilder {
  private LocalTime departureTime;
  private String line;
  private String destination;
  private int track;
  private LocalTime delay;
  private Train train;

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
   * @param departureTimeString the departure time of the departure.
   * @return the departure builder.
   * @throws IllegalArgumentException if the departure time is null, empty or blank. Also, if the
   *     departure time is not in the format HH:mm.
   */
  public DepartureBuilder setDepartureTime(String departureTimeString) {
    validateString(departureTimeString, "Departure time");

    this.departureTime = parseTime(departureTimeString, "Departure time");
    return this;
  }

  /**
   * Sets the departure time of the departure. Uses LocalTime instead of String.
   *
   * @param departureTime the departure time of the departure.
   * @return the departure builder.
   * @throws IllegalArgumentException if the departure time is null.
   */
  public DepartureBuilder setDepartureTimeLocalTime(LocalTime departureTime) {
    if (departureTime == null) {
      throw new IllegalArgumentException("Departure time cannot be null");
    }

    this.departureTime = departureTime;
    return this;
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
   * @return the departure builder.
   * @throws IllegalArgumentException if the line is null, empty or blank.
   */
  public DepartureBuilder setLine(String line) {
    validateString(line, "Line");

    this.line = line;
    return this;
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
   * @return the departure builder.
   * @throws IllegalArgumentException if the destination is null, empty or blank.
   */
  public DepartureBuilder setDestination(String destination) {
    validateString(destination, "Destination");

    this.destination = destination;
    return this;
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
   * @return the departure builder.
   * @throws IllegalArgumentException if the track is less than 1.
   */
  public DepartureBuilder setTrack(int track) {
    if (track < 1) {
      throw new IllegalArgumentException("Track cannot be less than 1");
    }

    this.track = track;
    return this;
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
   * @return the departure builder.
   * @throws IllegalArgumentException if the delay is null, empty or blank. Also, if the delay is
   *     not in the format HH:mm.
   */
  public DepartureBuilder setDelay(String delay) {
    validateString(delay, "Delay");

    this.delay = parseTime(delay, "Delay");
    return this;
  }

  /**
   * Sets the delay of the departure. Uses LocalTime instead of String.
   *
   * @param delay the delay of the departure.
   * @return the departure builder.
   * @throws IllegalArgumentException if the delay is null.
   */
  public DepartureBuilder setDelayLocalTime(LocalTime delay) {
    if (delay == null) {
      throw new IllegalArgumentException("Delay cannot be null");
    }

    this.delay = delay;
    return this;
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
   * @return the departure builder.
   * @throws IllegalArgumentException if the train is null.
   */
  public DepartureBuilder setTrain(Train train) {
    if (train == null) {
      throw new IllegalArgumentException("Train cannot be null");
    }

    this.train = train;
    return this;
  }

  /**
   * Builds a departure object.
   *
   * @return a departure object.
   */
  public Departure build() {
    return new Departure(this);
  }
}
