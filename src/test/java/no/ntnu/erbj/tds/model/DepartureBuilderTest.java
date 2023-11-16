package no.ntnu.erbj.tds.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalTime;

import no.ntnu.erbj.tds.model.departures.DepartureBuilder;
import org.junit.jupiter.api.Test;

class DepartureBuilderTest {
  /** Method under test: {@link DepartureBuilder#setDepartureTime(String)} */
  @Test
  void testSetDepartureTime() {
    assertThrows(
        IllegalArgumentException.class,
        () -> (new DepartureBuilder()).setDepartureTime("Departure Time String"));
    assertThrows(
        IllegalArgumentException.class, () -> (new DepartureBuilder()).setDepartureTime(null));
    assertThrows(
        IllegalArgumentException.class, () -> (new DepartureBuilder()).setDepartureTime(""));
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
    assertThrows(IllegalArgumentException.class, () -> (new DepartureBuilder()).setLine(null));
    assertThrows(IllegalArgumentException.class, () -> (new DepartureBuilder()).setLine(""));
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
    assertThrows(
        IllegalArgumentException.class, () -> (new DepartureBuilder()).setDestination(null));
    assertThrows(IllegalArgumentException.class, () -> (new DepartureBuilder()).setDestination(""));
    assertThrows(
        IllegalArgumentException.class, () -> (new DepartureBuilder()).setDestination(" "));
  }

  /** Method under test: {@link DepartureBuilder#setTrack(int)} */
  @Test
  void testSetTrackPog() {
    DepartureBuilder departureBuilder = new DepartureBuilder();
    DepartureBuilder actualSetTrackResult = departureBuilder.setTrack(1);
    assertSame(departureBuilder, actualSetTrackResult);
    assertEquals(1, actualSetTrackResult.getTrack());
  }

  /** Method under test: {@link DepartureBuilder#setTrack(int)} */
  @Test
  void testSetTrackNeg() {
    assertThrows(IllegalArgumentException.class, () -> (new DepartureBuilder()).setTrack(0));
  }

  /** Method under test: {@link DepartureBuilder#setDelay(String)} */
  @Test
  void testSetDelayNeg() {
    assertThrows(IllegalArgumentException.class, () -> (new DepartureBuilder()).setDelay("Delay"));
    assertThrows(IllegalArgumentException.class, () -> (new DepartureBuilder()).setDelay(null));
    assertThrows(IllegalArgumentException.class, () -> (new DepartureBuilder()).setDelay(""));
  }

  /** Method under test: {@link DepartureBuilder#setDelay(String)} */
  @Test
  void testSetDelayPositive() {
    DepartureBuilder departureBuilder = new DepartureBuilder();
    DepartureBuilder actualSetDelayResult = departureBuilder.setDelay("20:20");
    assertSame(departureBuilder, actualSetDelayResult);
    assertEquals(LocalTime.of(20, 20), actualSetDelayResult.getDelay());
  }

  /** Method under test: {@link DepartureBuilder#setTrain(Train)} */
  @Test
  void testSetTrainPos() {
    DepartureBuilder departureBuilder = new DepartureBuilder();
    Train train = new Train();
    DepartureBuilder actualSetTrainResult = departureBuilder.setTrain(train);
    assertSame(departureBuilder, actualSetTrainResult);
    assertSame(train, actualSetTrainResult.getTrain());
  }

  /** Method under test: {@link DepartureBuilder#setTrain(Train)} */
  @Test
  void testSetTrainNeg() {
    assertThrows(IllegalArgumentException.class, () -> (new DepartureBuilder()).setTrain(null));
  }
}
