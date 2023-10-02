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

  WagonType(int seats) {
    this.seats = seats;
  }

  public int getSeats() {
    return seats;
  }
}
