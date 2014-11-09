package JourneyThroughEurope.ui;

import application.Main;
import application.Main.JTEPropertyType;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;

import JourneyThroughEurope.file.JTEFileLoader;
import JourneyThroughEurope.game.JTEGameData;
import JourneyThroughEurope.game.JTEGameStateManager;
import application.Main.JTEPropertyType;
import properties_manager.PropertiesManager;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javax.swing.JScrollPane;

public class JTEUI extends Pane {

    private BorderPane getGamePanel() {
        return gamePanel;
    }

    public enum JTEUIState {
        SPLASH_SCREEN_STATE, PLAY_GAME_STATE, VIEW_HELP_STATE;
    }

    // mainStage
    private Stage primaryStage;

    // mainPane
    private BorderPane mainPane;

    // SplashScreen
    private ImageView splashScreenImageView;
    private Pane splashScreenPane;
    private Label splashScreenImageLabel;
    private HBox languageSelectionPane;
    private ArrayList<Button> languageButtons;

    // GamePane
    private Label HangManLabel;
    private Button newGameButton;
    private HBox letterButtonsPane;
    private HashMap<Character, Button> letterButtons;
    private BorderPane gamePanel = new BorderPane();

    //HelpPane
    private BorderPane helpPanel;
    private BorderPane helpPane;
    private Button homeButton;
    private Pane workspace;

    // Padding
    private Insets marginlessInsets;

    // Image path
    private String ImgPath = "file:img/";

    // mainPane weight && height
    private int paneWidth;
    private int paneHeight;

    // THIS CLASS WILL HANDLE ALL ACTION EVENTS FOR THIS PROGRAM
    private JTEEventHandler eventHandler;
    JTEGameStateManager gsm;

    public JTEUI() {
        // WE'LL USE THIS EVENT HANDLER FOR LOTS OF CONTROLS
        eventHandler = new JTEEventHandler(this);
        initMainPane();
        initSplashScreen();
    }

    public void SetStage(Stage stage) {
        primaryStage = stage;
    }

    public BorderPane GetMainPane() {
        return this.mainPane;
    }

    public JTEGameStateManager getGSM() {
        return gsm;
    }

    public BorderPane getHelpPane() {
        return helpPane;
    }

    public void initMainPane() {
        marginlessInsets = new Insets(5, 5, 5, 5);
        mainPane = new BorderPane();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        paneWidth = Integer.parseInt(props
                .getProperty(JTEPropertyType.WINDOW_WIDTH));
        paneHeight = Integer.parseInt(props
                .getProperty(JTEPropertyType.WINDOW_HEIGHT));
        mainPane.resize(paneWidth, paneHeight);
        mainPane.setPadding(marginlessInsets);
    }

    public void initSplashScreen() {
        // INIT THE SPLASH SCREEN CONTROLS
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String splashScreenImagePath = props.getProperty(JTEPropertyType.SPLASH_SCREEN_IMAGE_NAME);
        props.addProperty(JTEPropertyType.INSETS, "5");
        String str = props.getProperty(JTEPropertyType.INSETS);
        splashScreenPane = new Pane();
        Image splashScreenImage = loadImage(splashScreenImagePath);
        splashScreenImageView = new ImageView(splashScreenImage);
        splashScreenImageLabel = new Label();
        splashScreenImageLabel.setGraphic(splashScreenImageView);
        splashScreenImageLabel.setLayoutX(-45);
        splashScreenPane.getChildren().add(splashScreenImageLabel);
        mainPane.setCenter(splashScreenPane);
    }

    public void initJTEUI() {
        // FIRST REMOVE THE SPLASH SCREEN
        mainPane.getChildren().clear();
        // GET THE UPDATED TITLE
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String title = props.getProperty(JTEPropertyType.GAME_TITLE_TEXT);
        primaryStage.setTitle(title);
		// OUR WORKSPACE WILL STORE EITHER THE GAME, STATS,
        // OR HELP UI AT ANY ONE TIME
        initWorkspace();
        initGameScreen();
        initHelpPane();
        // WE'LL START OUT WITH THE GAME SCREEN
        changeWorkspace(JTEUIState.PLAY_GAME_STATE);
    }

    private Button initToolbarButton(HBox toolbar, JTEPropertyType prop) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imageName = props.getProperty(prop);
        // LOAD THE IMAGE
        Image image = loadImage(imageName);
        ImageView imageIcon = new ImageView(image);
        // MAKE THE BUTTON
        Button button = new Button();
        button.setGraphic(imageIcon);
        button.setPadding(marginlessInsets);
        toolbar.getChildren().add(button);
        // AND SEND BACK THE BUTTON
        return button;
    }

    private void initWorkspace() {
        workspace = new Pane();
        workspace.setPrefSize(800, 800);
        mainPane.setCenter(workspace);
    }
    
    BorderPane gamePane;
  
    private void initGameScreen() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        gamePane = new BorderPane();
        workspace.getChildren().add(gamePanel);
    }
    private void initHelpPane() {

        // MAKE THE HELP BUTTON
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String homeImgName = props.getProperty(JTEPropertyType.HOME_IMG_NAME);
        Image homeImg = loadImage(homeImgName);
        ImageView homeImgIcon = new ImageView(homeImg);
        homeButton = new Button();
        homeButton.setGraphic(homeImgIcon);
        setTooltip(homeButton, JTEPropertyType.HOME_TOOLTIP);
        // WE'LL PUT THE HOME BUTTON IN A TOOLBAR IN THE NORTH OF THIS SCREEN,
        // UNDER THE NORTH TOOLBAR THAT'S SHARED BETWEEN THE THREE SCREENS
        //Pane helpToolbar = new Pane();
        HBox helpToolbar = new HBox();
        helpToolbar.setAlignment(Pos.TOP_CENTER);
        helpPanel = new BorderPane();
        helpPanel.setTop(helpToolbar);
        helpPanel.setPrefSize(800, 800);
        helpToolbar.getChildren().add(homeButton);
        helpToolbar.setStyle("-fx-background-color:#85cff9");
        // LET OUR HELP PAGE GO HOME VIA THE HOME BUTTON
        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO Auto-generated method stub
                changeWorkspace(JTEUIState.SPLASH_SCREEN_STATE);
            }

        });
    }

    public Image loadImage(String imageName) {
        Image img = new Image(ImgPath + imageName);
        // System.out.print(imageName);
        return img;
    }

    private void setTooltip(Button button, JTEPropertyType tooltip) {
        // GET THE TEXT AND SET IT AS THE TOOLITP
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String tooltipText = props.getProperty(tooltip);
        Tooltip toolTip = new Tooltip(tooltipText);
        // button.setToolTipText(tooltipText);
        button.setTooltip(toolTip);
    }

    public void changeWorkspace(JTEUIState uiScreen) {
        if (uiScreen == JTEUIState.VIEW_HELP_STATE) {
            mainPane.setCenter(helpPanel);
        }
        else if (uiScreen == JTEUIState.PLAY_GAME_STATE) {
            mainPane.setCenter(gamePanel);
        }
        else if(uiScreen == JTEUIState.SPLASH_SCREEN_STATE){
            mainPane.setCenter(splashScreenPane);
        }
    }
}
