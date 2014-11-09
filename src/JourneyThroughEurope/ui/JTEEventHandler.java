package JourneyThroughEurope.ui;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JEditorPane;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import application.Main.JTEPropertyType;
import properties_manager.PropertiesManager;
import xml_utilities.InvalidXMLFileFormatException;
import JourneyThroughEurope.file.JTEFileLoader;
import JourneyThroughEurope.game.JTEGameStateManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;

public class JTEEventHandler {
    private JTEUI ui;
    public JTEEventHandler(JTEUI initUI) {
        ui = initUI;
    }
    public void respondToSwitchScreenRequest(JTEUI.JTEUIState uiState) {
        ui.changeWorkspace(uiState);
    }
    public void respondToNewGameRequest() {
        JTEGameStateManager gsm = ui.getGSM();
        gsm.startNewGame();
    }
    public void respondToExitRequest(Stage primaryStage) {
        // ENGLISH IS THE DEFAULT
        String options[] = new String[]{"Yes", "No"};
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        options[0] = props.getProperty(JTEPropertyType.DEFAULT_YES_TEXT);
        options[1] = props.getProperty(JTEPropertyType.DEFAULT_NO_TEXT);
        String verifyExit = props.getProperty(JTEPropertyType.DEFAULT_EXIT_TEXT);
        // FIRST MAKE SURE THE USER REALLY WANTS TO EXIT
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        BorderPane exitPane = new BorderPane();
        HBox optionPane = new HBox();
        Button yesButton = new Button(options[0]);
        Button noButton = new Button(options[1]);
        optionPane.setSpacing(10.0);
        optionPane.getChildren().addAll(yesButton, noButton);
        optionPane.setAlignment(Pos.CENTER);
        Label exitLabel = new Label(verifyExit);
        exitPane.setCenter(exitLabel);
        exitPane.setBottom(optionPane);
        Scene scene = new Scene(exitPane, 300, 100);
        dialogStage.setScene(scene);
        dialogStage.show();
        // WHAT'S THE USER'S DECISION?
        yesButton.setOnAction(e -> {
            // YES, LET'S EXIT
            System.exit(0);
        });
        noButton.setOnAction(e -> {
            // NO, DON'T EXIT
            Stage closeStage = (Stage) noButton.getScene().getWindow();
            closeStage.close();
        });
    }

}
