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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class JTEEventHandler {

    private JTEUI ui;
    private JTEGameStateManager gsm;
    private int numPlayers;

    public JTEEventHandler(JTEUI initUI) {
        ui = initUI;
    }

    public void respondToPlayerSelectRequest() {
        ui.initPlayerSelectUI();
    }

    public void respondToSwitchScreenRequest(JTEUI.JTEUIState uiState) {
        ui.changeWorkspace(uiState);
    }

    public void setNumPlayas(int playas) {
        numPlayers = playas;
    }

    public int getNumPlayas() {
        return this.numPlayers;
    }

    public void respondToStartRequest() {
        ui.initGameScreen();
        System.out.println("WE HAVE " + this.numPlayers + " PLAYAS");
    }

    public void respondToAboutRequest() {
        ui.initHelpPane();
    }

    public void showPanes(int playas, GridPane playerGrid) {
        Pane player1 = ui.createPlayerPane();
        Pane player2 = ui.createPlayerPane();
        Pane player3 = ui.createPlayerPane();
        Pane player4 = ui.createPlayerPane();
        Pane player5 = ui.createPlayerPane();
        Pane player6 = ui.createPlayerPane();

        playerGrid.add(player1, 0, 1);
        playerGrid.add(player2, 1, 1);
        playerGrid.add(player3, 2, 1);
        playerGrid.add(player4, 0, 2);
        playerGrid.add(player5, 1, 2);
        playerGrid.add(player6, 2, 2);
        
        if (playas == 1) {
            player1.setVisible(true);
        } else if (playas == 2) {
            player2.setVisible(true);
        } else if (playas == 3) {
            player3.setVisible(true);
        } else if (playas == 4) {
            player4.setVisible(true);
        } else if (playas == 5) {
            player5.setVisible(true);
        } else if (playas == 6) {
            player6.setVisible(true);
        }
    }

    public void respondToHistoryRequest() {
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
