package no.ntnu.erbj.tds.model;

import jakarta.persistence.*;

/**
 * A wagon is a part of a train. It has a wagon number and a wagon type. The wagon type determines
 * how many seats the wagon has. Seats can be reserved in a wagon, and the number of available seats
 * is updated.
 *
 * @see WagonType
 * @see Train
 * @author Erik Bjørnsen
 */
@Entity
public class Wagon {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Train train;

  private WagonType wagonType;
  private int openSeats;
  private int reservedSeats;

  /**
   * Constructor for the Wagon class.
   *
   * @param wagonType the wagon type
   * @throws IllegalArgumentException if a wagon type is null
   */
  public Wagon(WagonType wagonType) {
    setWagonType(wagonType);
    setOpenSeats(wagonType.getSeats());
    setReservedSeats(0);
  }

  /** Used by DB. */
  public Wagon() {}

  /**
   * Gets the train the wagon is a part of. Used by DB.
   *
   * @return the train the wagon is a part of
   */
  public Train getTrain() {
    return train;
  }

  /**
   * Sets the train the wagon is a part of. Used by DB.
   *
   * @param train the train the wagon is a part of
   */
  public void setTrain(Train train) {
    this.train = train;
  }

  /**
   * Gets the id of the wagon.
   *
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the id of the wagon. Used by DB.
   *
   * @param id the id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the wagon type.
   *
   * @return the wagon type
   */
  public WagonType getWagonType() {
    return wagonType;
  }

  /**
   * Sets the wagon type.
   *
   * @param wagonType the wagon type
   * @throws IllegalArgumentException if the wagon type is null
   */
  public void setWagonType(WagonType wagonType) {
    if (wagonType == null) {
      throw new IllegalArgumentException("Wagon type cannot be null");
    }
    this.wagonType = wagonType;
  }

  /**
   * Gets the number of open seats in the wagon.
   *
   * @return the number of open seats
   */
  public int getOpenSeats() {
    return openSeats;
  }

  /**
   * Sets the number of open seats in the wagon.
   *
   * @param openSeats the number of open seats
   * @throws IllegalArgumentException if the number of open seats is less than 0, or if the number
   *     of open seats is greater than the number of seats in the wagon
   */
  public void setOpenSeats(int openSeats) {
    if (openSeats < 0) {
      throw new IllegalArgumentException("Number of open seats cannot be less than 0");
    }
    if (openSeats > wagonType.getSeats()) {
      throw new IllegalArgumentException(
          "Number of open seats cannot be greater than the number of seats in the wagon");
    }
    this.openSeats = openSeats;
  }

  /**
   * Gets the number of reserved seats in the wagon.
   *
   * @return the number of reserved seats
   */
  public int getReservedSeats() {
    return reservedSeats;
  }

  /**
   * Sets the number of reserved seats in the wagon.
   *
   * @param reservedSeats the number of reserved seats
   * @throws IllegalArgumentException if the number of reserved seats is less than 0, or if the
   *     number of reserved seats is greater than the number of seats in the wagon
   */
  public void setReservedSeats(int reservedSeats) {
    if (reservedSeats < 0) {
      throw new IllegalArgumentException("Number of reserved seats cannot be less than 0");
    }
    if (reservedSeats > wagonType.getSeats()) {
      throw new IllegalArgumentException(
          "Number of reserved seats cannot be greater than the number of seats in the wagon");
    }
    this.reservedSeats = reservedSeats;
  }

  /**
   * Reserves a number of seats in the wagon.
   *
   * @param seats the number of seats to reserve
   * @throws IllegalArgumentException if the number of seats to reserve is greater than the number
   *     of open seats, or if the number of seats to reserve is less than 0
   */
  public void reserveSeats(int seats) {
    if (seats < 0) {
      throw new IllegalArgumentException("Number of seats cannot be less than 0");
    }
    if (seats > openSeats) {
      throw new IllegalArgumentException("Not enough seats available");
    }
    setOpenSeats(openSeats - seats);
    setReservedSeats(reservedSeats + seats);
  }

  /**
   * Cancels a reservation of seats.
   *
   * @param seats the number of seats to cancel
   * @throws IllegalArgumentException if the number of seats to cancel is greater than the number of
   *     reserved seats, or if the number of seats to cancel is less than 0
   */
  public void cancelReservation(int seats) {
    if (seats < 0) {
      throw new IllegalArgumentException("Number of seats cannot be less than 0");
    }
    if (seats > reservedSeats) {
      throw new IllegalArgumentException("Not enough seats reserved");
    }

    setOpenSeats(openSeats + seats);
    setReservedSeats(reservedSeats - seats);
  }
}
