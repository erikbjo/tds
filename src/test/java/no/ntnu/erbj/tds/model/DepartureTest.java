package no.ntnu.erbj.tds.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DepartureTest {

  private Departure departure1;
  private Departure departure2;

  @BeforeEach
  void setUp() {
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
  }

  /** Method under test: {@link Departure#getDepartureTime()} */
  @Test
  void getDepartureTime() {
    assertEquals(LocalTime.of(20, 20), departure1.getDepartureTime());
    assertEquals(LocalTime.of(10, 10), departure2.getDepartureTime());
  }

  /** Method under test: {@link Departure#getLine()} */
  @Test
  void getLine() {
    assertEquals("L12", departure1.getLine());
    assertEquals("L12", departure2.getLine());
  }

  /** Method under test: {@link Departure#getDestination()} */
  @Test
  void getDestination() {
    assertEquals("Gjøvik", departure1.getDestination());
    assertEquals("Gjøvik", departure2.getDestination());
  }

  /** Method under test: {@link Departure#getTrack()} */
  @Test
  void getTrack() {
    assertEquals(0, departure1.getTrack());
    assertEquals(1, departure2.getTrack());
  }

  /** Method under test: {@link Departure#setTrack(int)} */
  @Test
  void setTrack() {
    departure1.setTrack(1);
    assertEquals(1, departure1.getTrack());
  }

  /** Method under test: {@link Departure#getDelay()} */
  @Test
  void getDelay() {
    assertEquals(LocalTime.of(0, 0), departure1.getDelay());
    assertEquals(LocalTime.of(0, 0), departure2.getDelay());
  }

  /** Method under test: {@link Departure#setDelay(String)} */
  @Test
  void setDelay() {
    departure1.setDelay("00:01");
    assertEquals(LocalTime.of(0, 1), departure1.getDelay());
  }

  /** Method under test: {@link Departure#getTrain()} */
  @Test
  void getTrain() {
    assertNull(departure1.getTrain());
    assertNull(departure2.getTrain());
  }
}
