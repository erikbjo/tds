package no.ntnu.erbj.tds.model;

import static org.junit.jupiter.api.Assertions.*;

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

  @BeforeEach
  void setUp() {
    gjovikS = new Station("Gjøvik S", "Gjøvik", 2);

    departure1 =
        new DepartureBuilder()
            .setDepartureTime("20:20")
            .setLine("L12")
            .setDestination("Gjøvik")
            .build();

    departure2 =
        new DepartureBuilder()
            .setDepartureTime("10:10")
            .setLine("L12")
            .setDestination("Gjøvik")
            .setTrack(1)
            .setDelay("00:00")
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
}
