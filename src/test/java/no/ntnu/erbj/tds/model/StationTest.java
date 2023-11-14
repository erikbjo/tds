package no.ntnu.erbj.tds.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StationTest {

  private Station osloS;
  private Station gjovikS;
  private List<Departure> departures;
  private Departure departure1;
  private Departure departure2;
  private Train train1;
  private Train train2;

  @BeforeEach
  void setUp() {
    gjovikS = new Station("Gjøvik S", "Gjøvik", 2);
    train1 = new Train("T1");
    train2 = new Train("T2");

    departure1 =
        new DepartureBuilder()
            .setDepartureTime("20:20")
            .setLine("L12")
            .setDestination("Gjøvik")
            .setTrain(train1)
            .build();

    departure2 =
        new DepartureBuilder()
            .setDepartureTime("10:10")
            .setLine("L12")
            .setDestination("Gjøvik")
            .setTrack(1)
            .setDelay("00:00")
            .setTrain(train2)
            .build();

    departures = new ArrayList<>();
    departures.add(departure1);
    departures.add(departure2);

    osloS = new Station("Oslo S", "Oslo", departures, 10);
  }

  /** Method under test: {@link Station#Station(String, String, java.util.List, int)} */
  @Test
  void constructorNegative() {
    assertThrows(IllegalArgumentException.class, () -> new Station(null, "Valid", 10));
    assertThrows(IllegalArgumentException.class, () -> new Station("", "Valid", 10));
    assertThrows(IllegalArgumentException.class, () -> new Station(" ", "Valid", 10));
    assertThrows(IllegalArgumentException.class, () -> new Station("Valid", null, 10));
    assertThrows(IllegalArgumentException.class, () -> new Station("Valid", "", 10));
    assertThrows(IllegalArgumentException.class, () -> new Station("Valid", " ", 10));
    assertThrows(IllegalArgumentException.class, () -> new Station("Valid", "Valid", -1));
    assertThrows(IllegalArgumentException.class, () -> new Station("Valid", "Valid", 0));
    assertThrows(IllegalArgumentException.class, () -> new Station("Valid", "Valid", null, 10));
  }

  /** Method under test: {@link Station#getName()} */
  @Test
  void getName() {
    assertEquals("Oslo S", osloS.getName());
    assertEquals("Gjøvik S", gjovikS.getName());
  }

  /** Method under test: {@link Station#getLocation()} */
  @Test
  void getLocation() {
    assertEquals("Oslo", osloS.getLocation());
    assertEquals("Gjøvik", gjovikS.getLocation());
  }

  /** Method under test: {@link Station#getDepartures()} */
  @Test
  void getDepartures() {
    assertEquals(2, osloS.getDepartures().size());
    assertEquals(0, gjovikS.getDepartures().size());
  }

  /** Method under test: {@link Station#getPlatforms()} */
  @Test
  void getPlatforms() {
    assertEquals(10, osloS.getPlatforms());
    assertEquals(2, gjovikS.getPlatforms());
  }

  /** Method under test: {@link Station#addDeparture(Departure)} */
  @Test
  void addDeparturePos() {
    Departure departure3 =
        new DepartureBuilder()
            .setDepartureTime("10:10")
            .setLine("L12")
            .setDestination("Gjøvik")
            .setTrack(1)
            .setDelay("00:00")
            .build();
    gjovikS.addDeparture(departure3);
    assertEquals(1, gjovikS.getDepartures().size());
  }

  /** Method under test: {@link Station#addDeparture(Departure)} */
  @Test
  void addDepartureNeg() {
    Train train = new Train("TestTrainNumber");
    Departure departure3 = new DepartureBuilder().setTrain(train).build();
    Departure departure4 = new DepartureBuilder().setTrain(train).build();
    gjovikS.addDeparture(departure3);
    assertThrows(IllegalArgumentException.class, () -> gjovikS.addDeparture(departure4));
    assertThrows(IllegalArgumentException.class, () -> gjovikS.addDeparture(null));
  }

  /** Method under test: {@link Station#removeDeparture(Departure)} */
  @Test
  void removeDeparturePos() {
    gjovikS.addDeparture(departure1);
    assertEquals(1, gjovikS.getDepartures().size());
    gjovikS.removeDeparture(departure1);
    assertEquals(0, gjovikS.getDepartures().size());
  }

  /** Method under test: {@link Station#removeDeparture(Departure)} */
  @Test
  void removeDepartureNeg() {
    assertThrows(IllegalArgumentException.class, () -> gjovikS.removeDeparture(departure1));
    assertThrows(IllegalArgumentException.class, () -> gjovikS.removeDeparture(null));
  }

  /** Method under test: {@link Station#getDepartureByTrainNumber(String)} */
  @Test
  void getDepartureByTrainNumberPos() {
    gjovikS.addDeparture(departure1);
    assertEquals(departure1, gjovikS.getDepartureByTrainNumber(train1.getTrainNumber()));
  }

  /** Method under test: {@link Station#getDepartureByTrainNumber(String)} */
  @Test
  void getDepartureByTrainNumberNeg() {
    gjovikS.addDeparture(departure1);
    String trainNumber = train2.getTrainNumber();
    assertNull(gjovikS.getDepartureByTrainNumber(trainNumber));
    assertThrows(IllegalArgumentException.class, () -> gjovikS.getDepartureByTrainNumber(null));
  }

  /** Method under test: {@link Station#getDeparturesByDestination(String)} */
  @Test
  void getDeparturesByDestinationPos() {
    gjovikS.addDeparture(departure1);
    gjovikS.addDeparture(departure2);
    assertEquals(2, gjovikS.getDeparturesByDestination("Gjøvik").size());
  }

  /** Method under test: {@link Station#getDeparturesByDestination(String)} */
  @Test
  void getDeparturesByDestinationNeg() {
    assertThrows(IllegalArgumentException.class, () -> gjovikS.getDeparturesByDestination(null));
    assertThrows(IllegalArgumentException.class, () -> gjovikS.getDeparturesByDestination(""));
    assertThrows(IllegalArgumentException.class, () -> gjovikS.getDeparturesByDestination(" "));
  }

  /** Method under test: {@link Station#removeDeparturesBeforeTime(java.time.LocalTime)} */
  @Test
  void removeDeparturesBeforeTimePos() {
    gjovikS.addDeparture(departure1);
    gjovikS.addDeparture(departure2);
    gjovikS.removeDeparturesBeforeTime(LocalTime.of(13, 10));
    assertEquals(1, gjovikS.getDepartures().size());
  }

  /** Method under test: {@link Station#removeDeparturesBeforeTime(LocalTime)} */
  @Test
  void removeDeparturesBeforeTimeNeg() {
    assertThrows(IllegalArgumentException.class, () -> gjovikS.removeDeparturesBeforeTime(null));
  }

  /** Method under test: {@link Station#getAllDeparturesSortedByDepartureTime()} */
  @Test
  void getAllDeparturesSortedByDepartureTime() {
    gjovikS.addDeparture(departure1);
    gjovikS.addDeparture(departure2);
    List<Departure> departures = gjovikS.getAllDeparturesSortedByDepartureTime();
    assertEquals(2, departures.size());
    assertEquals(departure2, departures.get(0));
    assertEquals(departure1, departures.get(1));
  }
}
