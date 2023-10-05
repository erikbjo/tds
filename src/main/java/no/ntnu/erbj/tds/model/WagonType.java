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

  /**
   * Gets the wagon type from a string.
   *
   * @param wagonType the wagon type as a string
   * @return the wagon type
   * @throws IllegalArgumentException if the wagon type is invalid
   */
  public static WagonType getWagonTypeByString(String wagonType) {
    return switch (wagonType.trim().toUpperCase()) {
      case "PASSENGER" -> PASSENGER;
      case "SILENT_PASSENGER" -> SILENT_PASSENGER;
      case "FREIGHT" -> FREIGHT;
      case "FAMILY" -> FAMILY;
      case "SLEEPING" -> SLEEPING;
      case "CAFETERIA" -> CAFETERIA;
      default -> throw new IllegalArgumentException("Invalid wagon type: " + wagonType);
    };
  }
}
