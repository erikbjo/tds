package no.ntnu.erbj.tds.ui.cli.commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class TestCommand {
  @ShellMethod(value = "Displays a greeting message.", key = "greet")
  public String testCommand() {
    return "Hello";
  }
}
