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
import JourneyThroughEurope.game.JTEGameStateManager;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.geometry.Insets;

public class JTEEventHandler {
    private JTEUI ui;
    public JTEEventHandler(JTEUI initUI) {
        ui = initUI;
    }
    public void respondToPlayerSelectRequest(){
        ui.initPlayerSelectUI();
    }
    public void respondToSwitchScreenRequest(JTEUI.JTEUIState uiState) {
        ui.changeWorkspace(uiState);
    }
    public void respondToStartRequest(){
        ui.initGameScreen();
    }
    public void respondToAboutRequest(){
        ui.initHelpPane();
    }

    public void respondToHistoryRequest(){
        ui.initHistoryScreen();
    }
    public void respondToExitRequest(Stage primaryStage) {
        // ENGLISH IS THE DEFAULT
        String options[] = new String[]{"Yes", "No"};
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        options[0] = props.getProperty(JTEPropertyType.DEFAULT_YES_TEXT);
        options[1] = props.getProperty(JTEPropertyType.DEFAULT_NO_TEXT);
        String verifyExit = props.getProperty(JTEPropertyType.DEFAULT_EXIT_TEXT);
        
        if (props.getProperty(JTEPropertyType.YES_TEXT) != null) {
            options[0] = props.getProperty(JTEPropertyType.YES_TEXT);
            options[1] = props.getProperty(JTEPropertyType.NO_TEXT);
            verifyExit = props.getProperty(JTEPropertyType.EXIT_REQUEST_TEXT);
        }
        
        // FIRST MAKE SURE THE USER REALLY WANTS TO EXIT
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Quit");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        BorderPane exitPane = new BorderPane();
        HBox optionPane = new HBox();
        Button yesButton = new Button(options[0]);
        yesButton.setFont(Font.font("Georgia", 12));
        yesButton.setPrefWidth(60);
        Button noButton = new Button(options[1]);
        noButton.setFont(Font.font("Georgia", 12));
        noButton.setPrefWidth(60);
        optionPane.setSpacing(15.0);
        optionPane.getChildren().addAll(yesButton, noButton);
        optionPane.setAlignment(Pos.CENTER);
        Label exitLabel = new Label(verifyExit);
        exitLabel.setFont(Font.font("Georgia", 12));
        exitPane.setCenter(exitLabel);
        exitPane.setBottom(optionPane);
        exitPane.setPadding(new Insets(5, 5, 5, 5));
        Scene scene = new Scene(exitPane, 180, 80);
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
