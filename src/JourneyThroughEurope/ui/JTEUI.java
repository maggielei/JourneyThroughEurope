package JourneyThroughEurope.ui;

import JourneyThroughEurope.game.JTECities;
import JourneyThroughEurope.game.JTEGameData;
import javafx.application.Application;
import application.Main.JTEPropertyType;
import java.util.ArrayList;
import JourneyThroughEurope.game.JTEGameStateManager;
import java.net.URL;
import java.util.List;
import properties_manager.PropertiesManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.scene.Parent;
import java.io.BufferedReader;
import java.io.FileReader;
public class JTEUI extends Pane{
    private BorderPane getGamePanel() {
        return gamePanel;
    }
    public enum JTEUIState {
        SPLASH_SCREEN_STATE, PLAYER_SELECT_STATE, PLAY_GAME_STATE, VIEW_HELP_STATE;
    }
    // mainStage
    private Stage primaryStage;

    // mainPane
    private BorderPane mainPane;

    // SplashScreen
    private ImageView splashScreenImageView;
    private Pane splashScreenPane;
    private Label splashScreenImageLabel;
    private ArrayList<Button> languageButtons;

    // GamePane
    private Label HangManLabel;
    private Button newGameButton;
    private BorderPane gamePanel = new BorderPane();
    
    // Player Select Pane
    private BorderPane playerSelectPane = new BorderPane();
    private BorderPane playerPanel = new BorderPane();
    private GridPane playerGrid = new GridPane();
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

    // splashScreen butons
    private VBox startBar;
    private Button gameButton;
    private Button loadButton;
    private Button helpButton;
    private Button exitButton;

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
        String gameButtonImagePath = props.getProperty(JTEPropertyType.GAME_IMG_NAME);
        String loadButtonImagePath = props.getProperty(JTEPropertyType.LOAD_IMG_NAME);
        String helpButtonImagePath = props.getProperty(JTEPropertyType.HELP_IMG_NAME);
        String exitButtonImagePath = props.getProperty(JTEPropertyType.EXIT_IMG_NAME);
        
        props.addProperty(JTEPropertyType.INSETS, "0");
        String str = props.getProperty(JTEPropertyType.INSETS);
        
        splashScreenPane = new Pane();
        Image splashScreenImage = loadImage(splashScreenImagePath);
        splashScreenImageView = new ImageView(splashScreenImage);
        splashScreenImageLabel = new Label();
        splashScreenImageLabel.setGraphic(splashScreenImageView);
        splashScreenImageLabel.setLayoutX(-45);
        splashScreenPane.getChildren().add(splashScreenImageLabel);
        
        startBar = new VBox();
        startBar.setAlignment(Pos.CENTER);
        startBar.setPrefHeight(750);
        // LOAD THE IMAGE
        Image game = loadImage(gameButtonImagePath);
        Image load = loadImage(loadButtonImagePath);
        Image help = loadImage(helpButtonImagePath);
        Image exit = loadImage(exitButtonImagePath);
        
        ImageView gameIcon = new ImageView(game);
        ImageView loadIcon = new ImageView(load);
        ImageView helpIcon = new ImageView(help);
        ImageView exitIcon = new ImageView(exit);
        
        gameButton = new Button();
        loadButton = new Button();
        helpButton = new Button();
        exitButton = new Button();
        
        gameButton.setStyle("-fx-background-color: transparent;");
        loadButton.setStyle("-fx-background-color: transparent;");
        helpButton.setStyle("-fx-background-color: transparent;");
        exitButton.setStyle("-fx-background-color: transparent;");
        
        gameButton.setGraphic(gameIcon);
        loadButton.setGraphic(loadIcon);
        helpButton.setGraphic(helpIcon);
        exitButton.setGraphic(exitIcon);
     
        gameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO Auto-generated method stub
                eventHandler.respondToPlayerSelectRequest();
            }
        });
        loadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO Auto-generated method stub
                eventHandler.respondToSwitchScreenRequest(JTEUIState.PLAYER_SELECT_STATE);
            }
        });
        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO Auto-generated method stub
                eventHandler.respondToAboutRequest();
            }
        });
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO Auto-generated method stub
                eventHandler.respondToExitRequest(primaryStage);
            }
        });
        
        startBar.getChildren().add(gameButton);
        startBar.getChildren().add(loadButton);
        startBar.getChildren().add(helpButton);
        startBar.getChildren().add(exitButton);

        mainPane.setCenter(splashScreenPane);
        mainPane.setBottom(startBar);
    }
    private void initWorkspace() {
        workspace = new Pane();
        workspace.setPrefSize(800, 800);
        mainPane.setCenter(workspace);
    }
    public void initPlayerSelectUI() {
        mainPane.getChildren().clear();
        initWorkspace();
        primaryStage.setTitle("Select Players");
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ComboBox playerComboBox = new ComboBox();
        playerComboBox.getItems().addAll(
                "1 Player",
                "2 Players",
                "3 Players",
                "4 Players",
                "5 Players",
                "6 Players"
        );
        GridPane playerGrid = new GridPane();
        playerGrid.setVgap(10);
        playerGrid.setHgap(10);
        playerGrid.setPadding(new Insets(10,10,10,10));
        String backPatternPath = props.getProperty(JTEPropertyType.BACK_PATTERN_NAME);     
        playerGrid.setStyle(String.format("-fx-background-image: url('%s');", ImgPath + backPatternPath));
        Label numPlayers = new Label("Number of Players:");
        numPlayers.setFont(Font.font("Georgia", 16));
        numPlayers.setTextFill(Color.WHITE);
        Button startButton = new Button("Start!");
        startButton.setFont(Font.font("Georgia", 16));
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO Auto-generated method stub
                eventHandler.respondToStartRequest();
            }
        });
        
        Pane player1 = createPlayerPane();
        Pane player2 = createPlayerPane();
        Pane player3 = createPlayerPane();
        Pane player4 = createPlayerPane();
        Pane player5 = createPlayerPane();
        Pane player6 = createPlayerPane();
        
        playerGrid.add(numPlayers, 0, 0);
        playerGrid.add(playerComboBox, 1, 0);
        playerGrid.add(startButton, 2, 0);
        playerGrid.add(player1, 0, 1);
        playerGrid.add(player2, 1, 1);
        playerGrid.add(player3, 2, 1);
        playerGrid.add(player4, 0, 2);
        playerGrid.add(player5, 1, 2);
        playerGrid.add(player6, 2, 2);
        playerGrid.setPrefSize(820, 600);
        workspace.getChildren().add(playerGrid);
    }
    public Pane createPlayerPane(){
        Pane playerPane = new Pane();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        //playerPane.setStyle(String.format("-fx-background-image: url('%s');", ImgPath + backPatternPath));
        playerPane.setStyle("-fx-background-color: transparent");
        VBox option = new VBox();
        ToggleGroup group = new ToggleGroup();
        option.setSpacing(10);
        RadioButton rb1 = new RadioButton("Player");
        option.getChildren().add(rb1);
        rb1.setToggleGroup(group);
        rb1.setFont(Font.font("Georgia", 12));
        rb1.setTextFill(Color.WHITE);
        RadioButton rb2 = new RadioButton("Computer");
        option.getChildren().add(rb2);
        rb2.setFont(Font.font("Georgia", 12));
        rb2.setTextFill(Color.WHITE);
        rb2.setToggleGroup(group);
        Label nameLabel = new Label("Name:");
        nameLabel.setFont(Font.font("Georgia", 12));
        nameLabel.setTextFill(Color.WHITE);
        
        TextField nameField = new TextField();
        option.setPadding(new Insets(50));
        option.setPrefSize(240, 240);
        option.getChildren().add(nameLabel);
        option.getChildren().add(nameField);
        playerPane.getChildren().add(option);
        return playerPane;
    }
    public void initHelpPane() {
        // MAKE THE HELP BUTTON
        mainPane.getChildren().clear();
        initWorkspace();
        primaryStage.setTitle("About");
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String aboutPath = props.getProperty(JTEPropertyType.ABOUT_NAME);
        Image aboutImage = loadImage(aboutPath);
        ImageView aboutImageView = new ImageView(aboutImage);
        Pane aboutPane = new Pane();
        Label about = new Label();
        about.setGraphic(aboutImageView);
        //about.setStyle(String.format("-fx-background-image: url('%s');", ImgPath + aboutPath));
        //about.setPrefSize(820,600);
        aboutPane.getChildren().add(about);
        
        String backPath = props.getProperty(JTEPropertyType.BACK_NAME);
        Image backImage = loadImage(backPath);
        ImageView backImageView = new ImageView(backImage);
        Button backButton = new Button();
        //backButton.setStyle(String.format("-fx-background-image: url('%s');", ImgPath + backPath));
        backButton.setGraphic(backImageView);
        backButton.setStyle("-fx-background-color: transparent;");
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO Auto-generated method stub
                eventHandler.respondToSwitchScreenRequest(JTEUIState.SPLASH_SCREEN_STATE);
            }
        });
        aboutPane.getChildren().add(backButton);
        workspace.getChildren().add(aboutPane);
    }
    public Image loadImage(String imageName) {
        Image img = new Image(ImgPath + imageName);
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
    public void initHistoryScreen(){
        initWorkspace();
        primaryStage.setTitle("History");
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String historyPath = props.getProperty(JTEPropertyType.BACK_PATTERN_NAME);
        Image historyImage = loadImage(historyPath);
        ImageView historyImageView = new ImageView(historyImage);
        Pane historyPane = new Pane();
        Label history = new Label();
        history.setGraphic(historyImageView);
        //about.setStyle(String.format("-fx-background-image: url('%s');", ImgPath + aboutPath));
        //about.setPrefSize(820,600);
        historyPane.getChildren().add(history);
        
        String backPath = props.getProperty(JTEPropertyType.BACK_NAME);
        Image backImage = loadImage(backPath);
        ImageView backImageView = new ImageView(backImage);
        Button backButton = new Button();
        //backButton.setStyle(String.format("-fx-background-image: url('%s');", ImgPath + backPath));
        backButton.setGraphic(backImageView);
        backButton.setStyle("-fx-background-color: transparent;");
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO Auto-generated method stub
                eventHandler.respondToSwitchScreenRequest(JTEUIState.SPLASH_SCREEN_STATE);
            }
        });
        historyPane.getChildren().add(backButton);
        workspace.getChildren().add(historyPane);
    }
    BorderPane gamePane;
    public void initGameScreen() {
        mainPane.getChildren().clear();
        initWorkspace();
        primaryStage.setTitle("Journey Through Europe");
        PropertiesManager props = PropertiesManager.getPropertiesManager();
//        String fxmlPath = props.getProperty(JTEPropertyType.FXML_PATH);
//        URL location = getClass().getResource("GameScreenUI.fxml");
//        System.out.println(location);
        
        BorderPane gamePane = new BorderPane();
        String backPatternPath = props.getProperty(JTEPropertyType.BACK_PATTERN_NAME);
        String map1ImagePath = props.getProperty(JTEPropertyType.MAP1_IMAGE_NAME);
        gamePane.setStyle(String.format("-fx-background-image: url('%s');", ImgPath + backPatternPath));
        Image backPatternImage = loadImage(backPatternPath);
        ImageView backImageView = new ImageView(backPatternImage);
        gamePane.getChildren().add(backImageView);
        Image map1Image = loadImage(map1ImagePath);
        ImageView map1ImageView = new ImageView(map1Image);
        map1ImageView.setFitHeight(580);
        map1ImageView.setPreserveRatio(true);
  
        VBox gameOptions = new VBox();
        Button flightButton = new Button();
        Button saveButton = new Button();
        Button aboutButton = new Button();
        Button historyButton = new Button();
        
        String aboutButtonPath = props.getProperty(JTEPropertyType.HELP_IMG_NAME);
        String flightButtonPath = props.getProperty(JTEPropertyType.FLIGHT_PLAN);
        String historyButtonPath = props.getProperty(JTEPropertyType.GAME_HISTORY);
        String saveButtonPath = props.getProperty(JTEPropertyType.SAVE_NAME);
        
        Image aboutImage = loadImage(aboutButtonPath);
        Image flightImage = loadImage(flightButtonPath);
        Image historyImage = loadImage(historyButtonPath);
        Image saveImage = loadImage(saveButtonPath);
        
        ImageView aboutImageView = new ImageView(aboutImage);
        ImageView flightImageView = new ImageView(flightImage);
        ImageView historyImageView = new ImageView(historyImage);
        ImageView saveImageView = new ImageView(saveImage);
        
        flightButton.setGraphic(flightImageView);
        saveButton.setGraphic(saveImageView);
        historyButton.setGraphic(historyImageView);
        aboutButton.setGraphic(aboutImageView);
        flightButton.setStyle("-fx-background-color: transparent;");
        saveButton.setStyle("-fx-background-color: transparent;");
        historyButton.setStyle("-fx-background-color: transparent;");
        aboutButton.setStyle("-fx-background-color: transparent;");

//        List<List<JTECities>> quarters = JTEGameData.loadCities();
//        for (List quarter : quarters) {
//            for (int i = 0; i < quarter.size(); i++) {
//                System.out.println(quarter.get(i).toString());
//                
//            }
//        }
//     
        String CityPath = props.getProperty(JTEPropertyType.CITIES_PATH);
        int thisCity = 0;
        String line = "";
        String split = ",";
        Button[] city = new Button[50];
        
        try{
            BufferedReader cities = new BufferedReader(new FileReader(CityPath));
            while((line = cities.readLine()) != null){
                String[] input = line.split(split);
                if(input[2].equals("1")){
                    city[thisCity] = new Button();
                    city[thisCity].setOpacity(.5);
                    city[thisCity].setPadding(new Insets(0,5,0,10));
                    city[thisCity].setLayoutX(Double.parseDouble(input[3])/4.429 - 7);
                    city[thisCity].setLayoutY(Double.parseDouble(input[4])/4.429 - 7);
                    city[thisCity].setOnAction((ActionEvent event) ->{
                        System.out.println(input[0]);
                        });
                    thisCity++;
                }
            }
        }
        catch(Exception E){
            E.printStackTrace();
        }
        
        aboutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO Auto-generated method stub
                eventHandler.respondToAboutRequest();
            }
        });
        
        historyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO Auto-generated method stub
                eventHandler.respondToHistoryRequest();
            }
        });
        
        gameOptions.getChildren().add(flightButton);
        gameOptions.getChildren().add(historyButton);
        gameOptions.getChildren().add(aboutButton);
        gameOptions.getChildren().add(saveButton);
        
        Pane mapShits = new Pane();
        
        mapShits.getChildren().add(map1ImageView);
        for(int i = 0; i < thisCity; i++){
            mapShits.getChildren().add(city[i]);
        }
        gamePane.setRight(gameOptions);
        gamePane.setCenter(mapShits);
        gamePane.setPrefSize(820, 600);
        workspace.getChildren().add(gamePane);
        
    }
    
    public void changeWorkspace(JTEUIState uiScreen) {
        if (uiScreen == JTEUIState.VIEW_HELP_STATE) {
            mainPane.setCenter(helpPanel);
        } else if (uiScreen == JTEUIState.PLAY_GAME_STATE) {
            mainPane.setCenter(gamePane);
        } else if (uiScreen == JTEUIState.SPLASH_SCREEN_STATE) {
            mainPane.setCenter(splashScreenPane);
            mainPane.setBottom(startBar);
        }
    }
}
