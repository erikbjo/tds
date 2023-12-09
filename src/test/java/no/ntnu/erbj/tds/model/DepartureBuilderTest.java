package no.ntnu.erbj.tds.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalTime;
import no.ntnu.erbj.tds.model.departures.DepartureBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DepartureBuilderTest {
  private DepartureBuilder departureBuilder;

  @BeforeEach
  void setUp() {
    departureBuilder = new DepartureBuilder();
  }

  /** Method under test: {@link DepartureBuilder#setDepartureTime(String)} */
  @Test
  void testSetDepartureTime() {
    assertThrows(
        IllegalArgumentException.class,
        () -> departureBuilder.setDepartureTime("Departure Time String"));
    assertThrows(
        IllegalArgumentException.class, () -> departureBuilder.setDepartureTime(null));
    assertThrows(
        IllegalArgumentException.class, () -> departureBuilder.setDepartureTime(""));
  }

  /** Method under test: {@link DepartureBuilder#setDepartureTime(String)} */
  @Test
  void testSetDepartureTimePositive() {
    DepartureBuilder departureBuilder = new DepartureBuilder();
    DepartureBuilder actualSetDepartureTimeResult = departureBuilder.setDepartureTime("20:20");
    assertSame(departureBuilder, actualSetDepartureTimeResult);
    assertEquals(LocalTime.of(20, 20), actualSetDepartureTimeResult.getDepartureTime());
  }

  /** Method under test: {@link DepartureBuilder#setLine(String)} */
  @Test
  void testSetLinePos() {
    DepartureBuilder departureBuilder = new DepartureBuilder();
    DepartureBuilder actualSetLineResult = departureBuilder.setLine("Line");
    assertSame(departureBuilder, actualSetLineResult);
    assertEquals("Line", actualSetLineResult.getLine());
  }

  /** Method under test: {@link DepartureBuilder#setLine(String)} */
  @Test
  void testSetLineNeg() {
    assertThrows(IllegalArgumentException.class, () -> departureBuilder.setLine(null));
    assertThrows(IllegalArgumentException.class, () -> departureBuilder.setLine(""));
  }

  /** Method under test: {@link DepartureBuilder#setDestination(String)} */
  @Test
  void testSetDestinationPos() {
    DepartureBuilder departureBuilder = new DepartureBuilder();
    DepartureBuilder actualSetDestinationResult = departureBuilder.setDestination("Destination");
    assertSame(departureBuilder, actualSetDestinationResult);
    assertEquals("Destination", actualSetDestinationResult.getDestination());
  }

  /** Method under test: {@link DepartureBuilder#setDestination(String)} */
  @Test
  void testSetDestinationNeg() {
    assertThrows(IllegalArgumentException.class, () -> departureBuilder.setDestination(null));
    assertThrows(IllegalArgumentException.class, () -> departureBuilder.setDestination(""));
    assertThrows(IllegalArgumentException.class, () -> departureBuilder.setDestination(" "));
  }

  /** Method under test: {@link DepartureBuilder#setTrack(int)} */
  @Test
  void testSetTrackPog() {
    DepartureBuilder actualSetTrackResult = departureBuilder.setTrack(1);
    assertSame(departureBuilder, actualSetTrackResult);
    assertEquals(1, actualSetTrackResult.getTrack());
  }

  /** Method under test: {@link DepartureBuilder#setTrack(int)} */
  @Test
  void testSetTrackNeg() {
    assertThrows(IllegalArgumentException.class, () ->departureBuilder.setTrack(0));
  }

  /** Method under test: {@link DepartureBuilder#setDelay(String)} */
  @Test
  void testSetDelayNeg() {
    assertThrows(IllegalArgumentException.class, () -> departureBuilder.setDelay("Delay"));
    assertThrows(IllegalArgumentException.class, () -> departureBuilder.setDelay(null));
    assertThrows(IllegalArgumentException.class, () -> departureBuilder.setDelay(""));
  }

  /** Method under test: {@link DepartureBuilder#setDelay(String)} */
  @Test
  void testSetDelayPositive() {
    DepartureBuilder actualSetDelayResult = departureBuilder.setDelay("20:20");
    assertSame(departureBuilder, actualSetDelayResult);
    assertEquals(LocalTime.of(20, 20), actualSetDelayResult.getDelay());
  }

  /** Method under test: {@link DepartureBuilder#setTrain(Train)} */
  @Test
  void testSetTrainPos() {
    Train train = new Train();
    DepartureBuilder actualSetTrainResult = departureBuilder.setTrain(train);
    assertSame(departureBuilder, actualSetTrainResult);
    assertSame(train, actualSetTrainResult.getTrain());
  }

  /** Method under test: {@link DepartureBuilder#setTrain(Train)} */
  @Test
  void testSetTrainNeg() {
    assertThrows(IllegalArgumentException.class, () -> departureBuilder.setTrain(null));
  }
}
