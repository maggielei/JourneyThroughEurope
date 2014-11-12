package application;

import JourneyThroughEurope.game.JTECities;
import JourneyThroughEurope.game.JTEGameData;
import properties_manager.PropertiesManager;
import JourneyThroughEurope.ui.JTEUI;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Main extends Application {

    static String PROPERTY_TYPES_LIST = "property_types.txt";
    static String UI_PROPERTIES_FILE_NAME = "properties.xml";
    static String PROPERTIES_SCHEMA_FILE_NAME = "properties_schema.xsd";
    static String DATA_PATH = "./data/";

    @Override
    public void start(Stage primaryStage) {
        try {
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            props.addProperty(JTEPropertyType.UI_PROPERTIES_FILE_NAME,
                    UI_PROPERTIES_FILE_NAME);
            props.addProperty(JTEPropertyType.PROPERTIES_SCHEMA_FILE_NAME,
                    PROPERTIES_SCHEMA_FILE_NAME);
            props.addProperty(JTEPropertyType.DATA_PATH.toString(),
                    DATA_PATH);
            props.loadProperties(UI_PROPERTIES_FILE_NAME,
                    PROPERTIES_SCHEMA_FILE_NAME);

            // DEBUG
            List<List<JTECities>> quarters = JTEGameData.loadCities();
            for (List quarter : quarters) {
                for (int i = 0; i < quarter.size(); i++) {
                    System.out.println(quarter.get(i).toString());
                }
            }

            // GET THE LOADED TITLE AND SET IT IN THE FRAME
            String title = props.getProperty(JTEPropertyType.SPLASH_SCREEN_TITLE_TEXT);
            primaryStage.setTitle(title);

            JTEUI root = new JTEUI();
            BorderPane mainPane = root.GetMainPane();
            root.SetStage(primaryStage);

            // SET THE WINDOW ICON
            String mainPaneIconFile = props.getProperty(JTEPropertyType.WINDOW_ICON);
            Image mainPaneIcon = root.loadImage(mainPaneIconFile);
            primaryStage.getIcons().add(mainPaneIcon);

            Scene scene = new Scene(mainPane, mainPane.getWidth(), mainPane.getHeight());
            //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public enum JTEPropertyType {
        /* SETUP FILE NAMES */

        UI_PROPERTIES_FILE_NAME, PROPERTIES_SCHEMA_FILE_NAME,
        /* DIRECTORIES FOR FILE LOADING */
        DATA_PATH, IMG_PATH, CITIES_PATH, FXML_PATH, ABOUT_NAME, BACK_NAME, FLIGHT_PLAN, GAME_HISTORY, SAVE_NAME,
        /* WINDOW DIMENSIONS */
        WINDOW_WIDTH, WINDOW_HEIGHT,
        /* GAME TEXT */
        SPLASH_SCREEN_TITLE_TEXT, GAME_TITLE_TEXT, GAME_SUBHEADER_TEXT, WIN_DISPLAY_TEXT, LOSE_DISPLAY_TEXT, GAME_RESULTS_TEXT, GUESS_LABEL, LETTER_OPTIONS, EXIT_REQUEST_TEXT, YES_TEXT, NO_TEXT, DEFAULT_YES_TEXT, DEFAULT_NO_TEXT, DEFAULT_EXIT_TEXT,
        /* IMAGE FILE NAMES */
        WINDOW_ICON, SPLASH_SCREEN_IMAGE_NAME, GAME_IMG_NAME, LOAD_IMG_NAME, HELP_IMG_NAME, EXIT_IMG_NAME, BACK_PATTERN_NAME, MAP1_IMAGE_NAME,
        /* PLAYER FILE NAMES */
        PLAYER1_IMG_NAME, PLAYER2_IMG_NAME, PLAYER3_IMG_NAME, PLAYER4_IMG_NAME, PLAYER5_IMG_NAME, PLAYER6_IMG_NAME,
        /* DATA FILE STUFF */
        GAME_FILE_NAME, STATS_FILE_NAME, HELP_FILE_NAME, WORD_LIST_FILE_NAME,
        /* TOOLTIPS */
        GAME_TOOLTIP, STATS_TOOLTIP, HELP_TOOLTIP, EXIT_TOOLTIP, NEW_GAME_TOOLTIP, HOME_TOOLTIP,
        /* FONT DATA */
        LETTERS_FONT_FAMILY, LETTERS_FONT_SIZE, GUESSES_FONT_FAMILY, GUESSES_FONT_SIZE,
        /*
         * THESE ARE FOR LANGUAGE-DEPENDENT ERROR HANDLING, LIKE FOR TEXT PUT
         * INTO DIALOG BOXES TO NOTIFY THE USER WHEN AN ERROR HAS OCCURED
         */
        ERROR_DIALOG_TITLE_TEXT, DUPLICATE_WORD_ERROR_TEXT, IMAGE_LOADING_ERROR_TEXT, INVALID_URL_ERROR_TEXT, INVALID_DOC_ERROR_TEXT, INVALID_XML_FILE_ERROR_TEXT, INVALID_GUESS_LENGTH_ERROR_TEXT, WORD_NOT_IN_DICTIONARY_ERROR_TEXT, INVALID_DICTIONARY_ERROR_TEXT,
        INSETS
    }
}
