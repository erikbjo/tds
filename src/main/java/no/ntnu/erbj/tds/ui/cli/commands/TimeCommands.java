package no.ntnu.erbj.tds.ui.cli.commands;

import java.time.LocalTime;
import no.ntnu.erbj.tds.ui.cli.utilities.TdsLogger;
import no.ntnu.erbj.tds.ui.shared.TimeController;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

/**
 * Commands for manipulating the current time.
 *
 * @author Erik
 * @version 1.0
 */
@ShellComponent
public class TimeCommands {

  /** Gets the current time. */
  @ShellMethod(value = "Get the current time.", key = "time-show")
  public String getCurrentTime() {
    return TimeController.getCurrentTime().toString();
  }

  /** Sets the current time. */
  @ShellMethod(value = "Set the current time. Format: HH:mm", key = "time-set")
  public void setCurrentTime(String time) {
    String[] timeSplit = time.split(":");

    if (timeSplit.length != 2 || timeSplit[0].length() > 2 || timeSplit[1].length() > 2) {
      TdsLogger.getInstance().warn("Invalid time format. Format: HH:mm");
      return;
    }

    if (Integer.parseInt(timeSplit[0]) > 23
        || Integer.parseInt(timeSplit[1]) > 59
        || Integer.parseInt(timeSplit[0]) < 0
        || Integer.parseInt(timeSplit[1]) < 0) {
      TdsLogger.getInstance().warn("Invalid time format. Time must be between 00:00 and 23:59");
      return;
    }

    TimeController.setCurrentTime(LocalTime.parse(time));
  }

  /** Increments the current time by one minute. */
  @ShellMethod(value = "Increase minutes by the given amount.", key = "time-increase-minutes")
  public void increaseMinutes(int minutes) {
    TimeController.increaseMinutes(minutes);
  }

  /** Decreases the current time by the given amount of minutes. */
  @ShellMethod(value = "Decrease minutes by the given amount.", key = "time-decrease-minutes")
  public void decreaseMinutes(int minutes) {
    TimeController.decreaseMinutes(minutes);
  }

  /** Increments the current time by one minute. */
  @ShellMethod(
      value = "Increment minutes by one.",
      key = {"m++", "time-increment-minutes"})
  public void incrementMinutes() {
    TimeController.incrementMinutes();
  }

  /** Decrements the current time by one minute. */
  @ShellMethod(
      value = "Decrement minutes by one.",
      key = {"m--", "time-decrement-minutes"})
  public void decrementMinutes() {
    TimeController.decrementMinutes();
  }

  /** Increments the current time by one minute. */
  @ShellMethod(
      value = "Increase hours by the given amount.",
      key = {"time-increase-hours"})
  public void increaseHours(int hours) {
    TimeController.increaseHours(hours);
  }

  /** Decreases the current time by the given amount of hours. */
  @ShellMethod(
      value = "Decrease hours by the given amount.",
      key = {"time-decrease-hours"})
  public void decreaseHours(int hours) {
    TimeController.decreaseHours(hours);
  }

  /** Increments the current time by one hour. */
  @ShellMethod(
      value = "Increment hours by one.",
      key = {"h++", "time-increment-hours"})
  public void incrementHours() {
    TimeController.incrementHours();
  }

  /** Decrements the current time by one hour. */
  @ShellMethod(
      value = "Decrement hours by one.",
      key = {"h--", "time-decrement-hours"})
  public void decrementHours() {
    TimeController.decrementHours();
  }

  /** Resets the current time to midnight. */
  @ShellMethod(value = "Reset the current time to midnight.", key = "time-reset")
  public void resetTime() {
    TimeController.resetTime();
  }
}
