package no.ntnu.erbj.tds.ui.shared;

import java.time.LocalTime;

/**
 * This class is responsible for keeping track of the current time. The current time can be manually
 * set, incremented, decremented, or reset to midnight.
 *
 * @version 1.0
 * @see LocalTime
 * @author Erik Bjørnsen
 */
public class TimeController {

  private static LocalTime currentTime = LocalTime.of(0, 0);

  /**
   * Get the current time.
   *
   * @return Current time.
   */
  public static LocalTime getCurrentTime() {
    return currentTime;
  }

  /**
   * Manually set the current time.
   *
   * @param time Time to set.
   */
  public static void setCurrentTime(LocalTime time) {
    currentTime = time;
  }

  /**
   * Manually increment the current time by a specified number of minutes.
   *
   * @param minutes Number of minutes to increment.
   */
  public static void increaseMinutes(int minutes) {
    currentTime = currentTime.plusMinutes(minutes);
  }

  /**
   * Manually decrement the current time by a specified number of minutes.
   *
   * @param minutes Number of minutes to decrement.
   */
  public static void decreaseMinutes(int minutes) {
    currentTime = currentTime.minusMinutes(minutes);
  }

  /** Manually increment the current time by one minute. */
  public static void incrementMinutes() {
    currentTime = currentTime.plusMinutes(1);
  }

  /** Manually decrement the current time by one minute. */
  public static void decrementMinutes() {
    currentTime = currentTime.minusMinutes(1);
  }

  /**
   * Manually increment the current time by a specified number of hours.
   *
   * @param hours Number of hours to increment.
   */
  public static void increaseHours(int hours) {
    currentTime = currentTime.plusHours(hours);
  }

  /**
   * Manually decrement the current time by a specified number of hours.
   *
   * @param hours Number of hours to decrement.
   */
  public static void decreaseHours(int hours) {
    currentTime = currentTime.minusHours(hours);
  }

  /** Manually increment the current time by one hour. */
  public static void incrementHours() {
    currentTime = currentTime.plusHours(1);
  }

  /** Manually decrement the current time by one hour. */
  public static void decrementHours() {
    currentTime = currentTime.minusHours(1);
  }

  /** Manually set the current time to midnight. */
  public static void resetTime() {
    currentTime = LocalTime.of(0, 0);
  }
}
