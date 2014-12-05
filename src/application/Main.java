package application;

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
//            List<List<JTECities>> quarters = JTEGameData.loadCities();
//            for (List quarter : quarters) {
//                for (int i = 0; i < quarter.size(); i++) {
//                    System.out.println(quarter.get(i).toString());
//                }
//            }
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
        WINDOW_ICON, SPLASH_SCREEN_IMAGE_NAME, GAME_IMG_NAME, LOAD_IMG_NAME, HELP_IMG_NAME, EXIT_IMG_NAME, BACK_PATTERN_NAME, MAP1_IMAGE_NAME, MAP2_IMAGE_NAME, MAP3_IMAGE_NAME, MAP4_IMAGE_NAME,
        /* PLAYER FILE NAMES */
        PLAYER1_IMG_NAME, PLAYER2_IMG_NAME, PLAYER3_IMG_NAME, PLAYER4_IMG_NAME, PLAYER5_IMG_NAME, PLAYER6_IMG_NAME,

    }
}
