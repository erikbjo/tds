package no.ntnu.erbj.tds.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainTest {
  private Train train1;
  private Train train2;
  private Wagon wagon1;
  private Wagon wagon2;
  private Wagon wagon3;

  @BeforeEach
  void setUp() {
    train1 = new Train("T1");
    train2 = new Train("T2");

    wagon1 = new Wagon(WagonType.PASSENGER);
    wagon2 = new Wagon(WagonType.SLEEPING);
    wagon3 = new Wagon(WagonType.CAFETERIA);

    train1.addWagon(wagon1);
    train1.addWagon(wagon2);
    train1.addWagon(wagon3);
  }

  /** Method under test: {@link Train#makeReservation(WagonType, int)} */
  @Test
  void makeReservationPos() {
    assertEquals(
        WagonType.PASSENGER.getSeats() + WagonType.SLEEPING.getSeats(), train1.getNumberOfSeats());
    assertDoesNotThrow(() -> train1.makeReservation(WagonType.PASSENGER, 6));
    assertEquals(
        WagonType.PASSENGER.getSeats() + WagonType.SLEEPING.getSeats() - 6,
        train1.getNumberOfSeats());
  }

  /** Method under test: {@link Train#makeReservation(WagonType, int)} */
  @Test
  void makeReservationNeg() {
    assertEquals(
        WagonType.PASSENGER.getSeats() + WagonType.SLEEPING.getSeats(), train1.getNumberOfSeats());
    assertThrows(
        IllegalArgumentException.class, () -> train1.makeReservation(WagonType.PASSENGER, 100));
    assertEquals(
        WagonType.PASSENGER.getSeats() + WagonType.SLEEPING.getSeats(), train1.getNumberOfSeats());
  }

  /** Method under test: {@link Train#makeReservation(WagonType, int)} */
  @Test
  void makeReservationNeg2() {
    assertThrows(IllegalArgumentException.class, () -> train1.makeReservation(null, 100));
    assertThrows(
        IllegalArgumentException.class, () -> train1.makeReservation(WagonType.PASSENGER, -1));
  }

  /** Method under test: {@link Train#addWagon(Wagon)} */
  @Test
  void addWagonPos() {
    assertEquals(0, train2.getNumberOfWagons());
    train2.addWagon(wagon1);
    assertEquals(1, train2.getNumberOfWagons());
  }

  /** Method under test: {@link Train#addWagon(Wagon)} */
  @Test
  void addWagonNeg() {
    assertEquals(0, train2.getNumberOfWagons());
    assertThrows(IllegalArgumentException.class, () -> train2.addWagon(null));
    assertEquals(0, train2.getNumberOfWagons());
  }

  /** Method under test: {@link Train#addWagon(Wagon)} */
  @Test
  void addWagonNeg2() {
    assertEquals(0, train2.getNumberOfWagons());
    train2.addWagon(wagon1);
    assertEquals(1, train2.getNumberOfWagons());
    assertThrows(IllegalArgumentException.class, () -> train2.addWagon(wagon1));
    assertEquals(1, train2.getNumberOfWagons());
  }

  /** Method under test: {@link Train#removeWagon(Wagon)} */
  @Test
  void removeWagonPos() {
    assertEquals(3, train1.getNumberOfWagons());
    train1.removeWagon(wagon1);
    assertEquals(2, train1.getNumberOfWagons());
  }

  /** Method under test: {@link Train#Train(String)} */
  @Test
  void getWagons() {
    List<Wagon> wagons = train1.getWagons();
    assertEquals(3, wagons.size());
    assertTrue(wagons.contains(wagon1));
    assertTrue(wagons.contains(wagon2));
    assertTrue(wagons.contains(wagon3));
  }

  /** Method under test: {@link Train#setWagons(List)} */
  @Test
  void setWagonsPos() {
    assertEquals(3, train1.getNumberOfWagons());
    train1.setWagons(new ArrayList<>());
    assertEquals(0, train1.getNumberOfWagons());
  }

  /** Method under test: {@link Train#setWagons(List)} */
  @Test
  void setWagons2() {
    assertEquals(3, train1.getNumberOfWagons());
    assertDoesNotThrow(() -> train1.setWagons(null)); // Creates a new list
    assertEquals(0, train1.getNumberOfWagons());
  }

  /** Method under test: {@link Train#getNumberOfWagons()} */
  @Test
  void getNumberOfWagons() {
    assertEquals(3, train1.getNumberOfWagons());
  }

  /** Method under test: {@link Train#getTrainNumber()} */
  @Test
  void getTrainNumber() {
    assertEquals("T1", train1.getTrainNumber());
  }

  // TODO: Add tests for unique train number
  /** Method under test: {@link Train#setTrainNumber(String)} */
  @Test
  void setTrainNumberPos() {
    assertEquals("T1", train1.getTrainNumber());
    train1.setTrainNumber("xyz");
    assertEquals("xyz", train1.getTrainNumber());
  }

  /** Method under test: {@link Train#setTrainNumber(String)} */
  @Test
  void setTrainNumberNeg() {
    assertEquals("T1", train1.getTrainNumber());
    assertThrows(IllegalArgumentException.class, () -> train1.setTrainNumber(null));
    assertThrows(IllegalArgumentException.class, () -> train1.setTrainNumber(""));
    assertThrows(IllegalArgumentException.class, () -> train1.setTrainNumber(" "));
    assertEquals("T1", train1.getTrainNumber());
  }

  /** Method under test: {@link Train#getNumberOfSeats()} */
  @Test
  void getNumberOfSeats() {
    assertEquals(
        WagonType.PASSENGER.getSeats() + WagonType.SLEEPING.getSeats(), train1.getNumberOfSeats());
  }
}
