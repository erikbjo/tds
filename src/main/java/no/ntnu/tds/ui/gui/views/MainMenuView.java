package no.ntnu.tds.ui.gui.views;

import java.io.IOException;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import no.ntnu.tds.ui.gui.controllers.MainMenuController;

public class MainMenuView implements View {
  private final MainMenuController controller;
  private final Stage stage;
  private final Button continueButton;
  private final Button newGameButton;
  private final Button settingsButton;
  private final Button exitButton;

  public MainMenuView(MainMenuController controller, Stage stage) throws IOException {
    this.controller = controller;
    this.stage = stage;

    stage.setTitle("Main menu");
    stage.setOnCloseRequest(
        event -> {
          event.consume();
          Platform.exit();
        });

    BorderPane root = new BorderPane();
    root.setPrefSize(1200, 800);

    AnchorPane centerPane = new AnchorPane();

    HBox centerHBox = new HBox();
    centerHBox.setAlignment(Pos.CENTER_LEFT);
    centerHBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

    HBox buttonsHBox = new HBox();
    buttonsHBox.setId("buttonsHBox");
    buttonsHBox.setAlignment(Pos.CENTER);
    buttonsHBox.setPrefSize(100, 200);
    buttonsHBox.setPadding(new Insets(5, 5, 5, 5));

    continueButton = new Button();
    newGameButton = new Button();
    settingsButton = new Button();
    exitButton = new Button();

    buttonsHBox.getChildren().addAll(continueButton, newGameButton, settingsButton, exitButton);

    AnchorPane.setBottomAnchor(centerHBox, 0.0);
    AnchorPane.setTopAnchor(centerHBox, 0.0);
    AnchorPane.setLeftAnchor(centerHBox, 0.0);
    AnchorPane.setRightAnchor(centerHBox, 0.0);
    centerPane.getChildren().add(centerHBox);

    root.setBottom(buttonsHBox);
    root.setCenter(centerPane);

    // CONTROLLER
    controller.configureExitButton(exitButton);

    Scene scene = new Scene(root, 1200, 800);
    // scene.getStylesheets().add("css/mainMenu.css");

    stage.setScene(scene);
    stage.show();
  }

  public Stage getStage() {
    return stage;
  }

  @Override
  public void updateLanguage() {}
}
