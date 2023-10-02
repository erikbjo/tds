package no.ntnu.tds.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
  private WagonType wagonType;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private int openSeats;
  private int reservedSeats;

  /**
   * Constructor for the Wagon class.
   *
   * @param wagonType the wagon type
   * @throws IllegalArgumentException if a wagon type is null
   */
  public Wagon(WagonType wagonType) {
    if (wagonType == null) {
      throw new IllegalArgumentException("Wagon type cannot be null");
    }
    this.wagonType = wagonType;
    this.openSeats = wagonType.getSeats();
    this.reservedSeats = 0;
  }

  /** Used by DB. */
  public Wagon() {}

  /**
   * Gets the id of the wagon.
   *
   * @return the id
   */
  public Long getId() {
    return id;
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
   * Gets the number of open seats in the wagon.
   *
   * @return the number of open seats
   */
  public int getOpenSeats() {
    return openSeats;
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
   * Reserves a number of seats in the wagon.
   *
   * @param seats the number of seats to reserve
   * @throws IllegalArgumentException if the number of seats to reserve is greater than the number
   *     of open seats
   */
  public void reserveSeats(int seats) {
    if (seats > openSeats) {
      throw new IllegalArgumentException("Not enough seats available");
    }
    openSeats -= seats;
    reservedSeats += seats;
  }

  /**
   * Cancels a reservation of seats.
   *
   * @param seats the number of seats to cancel
   * @throws IllegalArgumentException if the number of seats to cancel is greater than the number of
   *     reserved seats
   */
  public void cancelReservation(int seats) {
    if (seats > reservedSeats) {
      throw new IllegalArgumentException("Not enough seats reserved");
    }
    openSeats += seats;
    reservedSeats -= seats;
  }
}
