package no.ntnu.erbj.tds.model;

/**
 * Enum for the different types of wagons. Each wagon type has a number of seats.
 *
 * @see Wagon
 * @author Erik Bjørnsen
 */
public enum WagonType {
  PASSENGER(64),
  SILENT_PASSENGER(64),
  FREIGHT(0),
  FAMILY(48),
  SLEEPING(32),
  CAFETERIA(0);

  private final int seats;

  /**
   * Constructor for the WagonType enum.
   *
   * @param seats the number of seats for the wagon type
   */
  WagonType(int seats) {
    this.seats = seats;
  }

  /**
   * Gets the number of seats for the wagon type.
   *
   * @return the number of seats
   */
  public int getSeats() {
    return seats;
  }
}
