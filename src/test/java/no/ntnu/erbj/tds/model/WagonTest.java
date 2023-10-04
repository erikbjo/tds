package no.ntnu.erbj.tds.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WagonTest {

  private Wagon sleepingWagon;
  private Wagon passengerWagon;
  private Wagon freightWagon;

  @BeforeEach
  void setUp() {
    sleepingWagon = new Wagon(WagonType.SLEEPING);
    passengerWagon = new Wagon(WagonType.PASSENGER);
    freightWagon = new Wagon(WagonType.FREIGHT);
  }

  /** Method under test: {@link Wagon#Wagon(WagonType)} */
  @Test
  void constructorNegative() {
    assertThrows(IllegalArgumentException.class, () -> new Wagon(null));
  }

  /** Method under test: {@link Wagon#getWagonType()} */
  @Test
  void getWagonType() {
    assertEquals(WagonType.SLEEPING, sleepingWagon.getWagonType());
    assertEquals(WagonType.PASSENGER, passengerWagon.getWagonType());
    assertEquals(WagonType.FREIGHT, freightWagon.getWagonType());
  }

  /** Method under test: {@link Wagon#getOpenSeats()} */
  @Test
  void getOpenSeats() {
    assertEquals(WagonType.SLEEPING.getSeats(), sleepingWagon.getOpenSeats());
    assertEquals(WagonType.PASSENGER.getSeats(), passengerWagon.getOpenSeats());
    assertEquals(WagonType.FREIGHT.getSeats(), freightWagon.getOpenSeats());
  }

  /** Method under test: {@link Wagon#getReservedSeats()} */
  @Test
  void getReservedSeats() {
    assertEquals(0, sleepingWagon.getReservedSeats());
    assertEquals(0, passengerWagon.getReservedSeats());
    assertEquals(0, freightWagon.getReservedSeats());
  }

  /** Method under test: {@link Wagon#reserveSeats(int)} */
  @Test
  void reserveSeatsPositive() {
    sleepingWagon.reserveSeats(10);
    passengerWagon.reserveSeats(10);
    freightWagon.reserveSeats(0);
    assertEquals(WagonType.SLEEPING.getSeats() - 10, sleepingWagon.getOpenSeats());
    assertEquals(WagonType.PASSENGER.getSeats() - 10, passengerWagon.getOpenSeats());
    assertEquals(WagonType.FREIGHT.getSeats(), freightWagon.getOpenSeats());
  }

  /** Method under test: {@link Wagon#reserveSeats(int)} */
  @Test
  void reserveSeatsNegative() {
    assertThrows(IllegalArgumentException.class, () -> sleepingWagon.reserveSeats(-1));
    assertThrows(IllegalArgumentException.class, () -> passengerWagon.reserveSeats(-1));
    assertThrows(IllegalArgumentException.class, () -> freightWagon.reserveSeats(-1));
  }

  /** Method under test: {@link Wagon#reserveSeats(int)} */
  @Test
  void reserveSeatsTooMany() {
    assertThrows(
        IllegalArgumentException.class,
        () -> sleepingWagon.reserveSeats(WagonType.SLEEPING.getSeats() + 1));
    assertThrows(
        IllegalArgumentException.class,
        () -> passengerWagon.reserveSeats(WagonType.PASSENGER.getSeats() + 1));
    assertThrows(
        IllegalArgumentException.class,
        () -> freightWagon.reserveSeats(WagonType.FREIGHT.getSeats() + 1));
  }

  /** Method under test: {@link Wagon#cancelReservation(int)} */
  @Test
  void cancelReservation() {
    sleepingWagon.reserveSeats(10);
    passengerWagon.reserveSeats(10);
    freightWagon.reserveSeats(0);
    sleepingWagon.cancelReservation(5);
    passengerWagon.cancelReservation(5);
    freightWagon.cancelReservation(0);
    assertEquals(WagonType.SLEEPING.getSeats() - 5, sleepingWagon.getOpenSeats());
    assertEquals(WagonType.PASSENGER.getSeats() - 5, passengerWagon.getOpenSeats());
    assertEquals(WagonType.FREIGHT.getSeats(), freightWagon.getOpenSeats());
  }

  /** Method under test: {@link Wagon#cancelReservation(int)} */
  @Test
  void cancelReservationNegative() {
    sleepingWagon.reserveSeats(10);
    passengerWagon.reserveSeats(10);
    freightWagon.reserveSeats(0);
    assertThrows(IllegalArgumentException.class, () -> sleepingWagon.cancelReservation(-1));
    assertThrows(IllegalArgumentException.class, () -> passengerWagon.cancelReservation(-1));
    assertThrows(IllegalArgumentException.class, () -> freightWagon.cancelReservation(-1));
  }

  /** Method under test: {@link Wagon#cancelReservation(int)} */
  @Test
  void cancelReservationTooMany() {
    sleepingWagon.reserveSeats(10);
    passengerWagon.reserveSeats(10);
    freightWagon.reserveSeats(0);
    assertThrows(IllegalArgumentException.class, () -> sleepingWagon.cancelReservation(11));
    assertThrows(IllegalArgumentException.class, () -> passengerWagon.cancelReservation(11));
    assertThrows(IllegalArgumentException.class, () -> freightWagon.cancelReservation(1));
  }

  /** Method under test: {@link Wagon#reserveSeats(int)} */
  @Test
  void getReservedSeatsAfterReserving() {
    sleepingWagon.reserveSeats(10);
    passengerWagon.reserveSeats(10);
    assertEquals(10, sleepingWagon.getReservedSeats());
    assertEquals(10, passengerWagon.getReservedSeats());
  }
}
