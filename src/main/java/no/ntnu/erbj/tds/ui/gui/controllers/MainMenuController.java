package no.ntnu.erbj.tds.ui.gui.controllers;

import java.io.IOException;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import no.ntnu.erbj.tds.ui.gui.views.MainMenuView;

public class MainMenuController implements Controller {
    private final Stage stage;
    private final MainMenuView view;

    public MainMenuController(MainMenuView view) {
        this.view = view;
        this.stage = view.getStage();
    }

    public MainMenuController(Stage stage) throws IOException {
        this.stage = stage;
        this.view = new MainMenuView(this, stage);
    }


    public void configureExitButton(Button exitButton) {
        exitButton.setOnAction(
                event -> {
                    Platform.exit();
                });
    }

    @Override
    public Stage getStage() {
        return stage;
    }
}
