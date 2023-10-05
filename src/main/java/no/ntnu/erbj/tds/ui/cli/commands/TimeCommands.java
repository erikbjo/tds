package no.ntnu.erbj.tds.ui.cli.commands;

import java.time.LocalTime;
import no.ntnu.erbj.tds.ui.cli.utilities.TdsLogger;
import no.ntnu.erbj.tds.ui.shared.TimeController;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class TimeCommands {

  @ShellMethod(value = "Get the current time.", key = "time-show")
  public String getCurrentTime() {
    return TimeController.getCurrentTime().toString();
  }

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

  @ShellMethod(value = "Increase minutes by the given amount.", key = "time-increase-minutes")
  public void increaseMinutes(int minutes) {
    TimeController.increaseMinutes(minutes);
  }

  @ShellMethod(value = "Decrease minutes by the given amount.", key = "time-decrease-minutes")
  public void decreaseMinutes(int minutes) {
    TimeController.decreaseMinutes(minutes);
  }

  @ShellMethod(
      value = "Increment minutes by one.",
      key = {"m++", "time-increment-minutes"})
  public void incrementMinutes() {
    TimeController.incrementMinutes();
  }

  @ShellMethod(
      value = "Decrement minutes by one.",
      key = {"m--", "time-decrement-minutes"})
  public void decrementMinutes() {
    TimeController.decrementMinutes();
  }

  @ShellMethod(
      value = "Increase hours by the given amount.",
      key = {"time-increase-hours"})
  public void increaseHours(int hours) {
    TimeController.increaseHours(hours);
  }

  @ShellMethod(
      value = "Decrease hours by the given amount.",
      key = {"time-decrease-hours"})
  public void decreaseHours(int hours) {
    TimeController.decreaseHours(hours);
  }

  @ShellMethod(
      value = "Increment hours by one.",
      key = {"h++", "time-increment-hours"})
  public void incrementHours() {
    TimeController.incrementHours();
  }

  @ShellMethod(
      value = "Decrement hours by one.",
      key = {"h--", "time-decrement-hours"})
  public void decrementHours() {
    TimeController.decrementHours();
  }

  @ShellMethod(value = "Reset the current time to midnight.", key = "time-reset")
  public void resetTime() {
    TimeController.resetTime();
  }
}
