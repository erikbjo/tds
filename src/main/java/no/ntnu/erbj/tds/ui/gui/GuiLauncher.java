package no.ntnu.erbj.tds.ui.gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import no.ntnu.erbj.tds.ui.gui.controllers.MainMenuController;

public class GuiLauncher extends Application {
  public static void launch(String[] args) {
    launch();
  }

  @Override
  public void start(Stage stage) {
    try {
      new MainMenuController(stage);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
