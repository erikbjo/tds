package no.ntnu.erbj.tds.ui.cli.commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

/** DOES NOT WORK CURRENTLY */
@ShellComponent
public class OverrideDefaultCommands {
  @ShellMethod(value = "Override the help command", key = "help")
  public String overrideHelp() {
    return "This command has been disabled.";
  }
}
