package JourneyThroughEurope.ui;

import application.Main.JTEPropertyType;
import java.util.ArrayList;
import JourneyThroughEurope.game.JTEGameStateManager;
import properties_manager.PropertiesManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javafx.scene.layout.HBox;
import java.util.Arrays;
public class JTEUI extends Pane {

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
    static int quad = 0;
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

        playerComboBox.getItems().addAll("1",
                "2",
                "3",
                "4",
                "5",
                "6");

        GridPane playerGrid = new GridPane();
        playerGrid.setVgap(10);
        playerGrid.setHgap(10);
        playerGrid.setPadding(new Insets(10, 10, 10, 10));
        String backPatternPath = props.getProperty(JTEPropertyType.BACK_PATTERN_NAME);
        playerGrid.setStyle(String.format("-fx-background-image: url('%s');", ImgPath + backPatternPath));
        Label numPlayers = new Label("Number of Players:");
        numPlayers.setFont(Font.font("Georgia", 16));
        numPlayers.setTextFill(Color.WHITE);
        Button startButton = new Button("Start!");
        startButton.setFont(Font.font("Georgia", 16));
        playerComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int choice = Integer.parseInt(playerComboBox.getValue().toString());
                eventHandler.setNumPlayas(choice);
                eventHandler.showPanes(choice, playerGrid);
                System.out.println(choice);
            }
        });
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO Auto-generated method stub
                eventHandler.respondToStartRequest();
            }
        });

        playerGrid.add(numPlayers, 0, 0);
        playerGrid.add(playerComboBox, 1, 0);
        playerGrid.add(startButton, 2, 0);

        playerGrid.setPrefSize(820, 600);
        workspace.getChildren().add(playerGrid);
    }

    public Pane createPlayerPane() {
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
        playerPane.setVisible(false);
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

    public void setTooltip(Button button, String tooltip) {
        // GET THE TEXT AND SET IT AS THE TOOLITP
        Tooltip toolTip = new Tooltip(tooltip);
        // button.setToolTipText(tooltipText);
        button.setTooltip(toolTip);
    }

    public void initHistoryScreen() {
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
        String map2ImagePath = props.getProperty(JTEPropertyType.MAP2_IMAGE_NAME);
        String map3ImagePath = props.getProperty(JTEPropertyType.MAP3_IMAGE_NAME);
        String map4ImagePath = props.getProperty(JTEPropertyType.MAP4_IMAGE_NAME);
        gamePane.setStyle(String.format("-fx-background-image: url('%s');", ImgPath + backPatternPath));
        Image backPatternImage = loadImage(backPatternPath);
        ImageView backImageView = new ImageView(backPatternImage);
        gamePane.getChildren().add(backImageView);

        Image map1Image = loadImage(map1ImagePath);
        Image map2Image = loadImage(map2ImagePath);
        Image map3Image = loadImage(map3ImagePath);
        Image map4Image = loadImage(map4ImagePath);

        ImageView map1ImageView = new ImageView(map1Image);

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

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File("data/cities.xml"));
            Node root = doc.getElementsByTagName("routes").item(0);
            NodeList cardlist = root.getChildNodes();
            for (int i = 0; i < cardlist.getLength(); i++) {
                Node cardNode = cardlist.item(i);
                if (cardNode.getNodeType() == Node.ELEMENT_NODE) {
                    NodeList cardAttrs = cardNode.getChildNodes();
                    // one card
                    for (int j = 0; j < cardAttrs.getLength(); j++) {
                        if (cardAttrs.item(j).getNodeType() == Node.ELEMENT_NODE) {
                            Node theNode = cardAttrs.item(j);
                            switch (theNode.getNodeName()) {
                                case "name":
                                    System.out.println("City name: "
                                            + theNode.getTextContent());
                                    break;
                                case "land":
                                    NodeList landList = theNode.getChildNodes();
                                    for (int k = 0; k < landList.getLength(); k++) {
                                        if (landList.item(k).getNodeType() == Node.ELEMENT_NODE) {
                                            System.out.println("Land neighbour: "
                                                    + landList.item(k)
                                                    .getTextContent());
                                        }
                                    }
                                    break;
                                case "sea":
                                    NodeList seaList = theNode.getChildNodes();
                                    for (int k = 0; k < seaList.getLength(); k++) {
                                        if (seaList.item(k).getNodeType() == Node.ELEMENT_NODE) {
                                            System.out.println("Sea neighbour: "
                                                    + seaList.item(k)
                                                    .getTextContent());
                                        }
                                    }
                                    break;
                            }
                        }
                    }
                }
            }
            
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        HBox gridSelector = new HBox(4);
        Button q1 = new Button("Q1");
        Button q2 = new Button("Q2");
        Button q3 = new Button("Q3");
        Button q4 = new Button("Q4");
        gridSelector.getChildren().addAll(q1, q2, q3, q4);

        Pane mapShits1 = new Pane();
        String CityPath = props.getProperty(JTEPropertyType.CITIES_PATH);
        int counter1 = 0;
        int counter2 = 0;
        int counter3 = 0;
        int counter4 = 0;
        
        String line = "";
        String split = ",";

        Button[] quad1 = new Button[20];
        Button[] quad2 = new Button[35];
        Button[] quad3 = new Button[65];
        Button[] quad4 = new Button[59];
        try {
            BufferedReader cities = new BufferedReader(new FileReader(CityPath));
            while ((line = cities.readLine()) != null) {
                String[] city1 = line.split(split);
                if (city1[2].equals("1")) {
                    quad1[counter1] = new Button();
                    quad1[counter1].setOpacity(1);
                    quad1[counter1].setPadding(new Insets(0, 5, 0, 5));
                    quad1[counter1].setLayoutX(Double.parseDouble(city1[3]) / 4.429 - 7);
                    quad1[counter1].setLayoutY(Double.parseDouble(city1[4]) / 4.429 - 7);
                    quad1[counter1].setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            System.out.println(city1[0]);
                        }
                    });
                    counter1++;
                }
                String[] city2 = line.split(split);
                if (city1[2].equals("2")) {
                    quad2[counter2] = new Button();
                    quad2[counter2].setOpacity(1);
                    quad2[counter2].setPadding(new Insets(0, 5, 0, 5));
                    quad2[counter2].setLayoutX(Double.parseDouble(city2[3]) / 4.429 - 7);
                    quad2[counter2].setLayoutY(Double.parseDouble(city2[4]) / 4.429 - 7);
                    quad2[counter2].setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            System.out.println(city2[0]);
                        }
                    });
                    counter2++;
                }
                String[] city3 = line.split(split);
                if (city1[2].equals("3")) {
                    quad3[counter3] = new Button();
                    quad3[counter3].setOpacity(1);
                    quad3[counter3].setPadding(new Insets(0, 5, 0, 5));
                    quad3[counter3].setLayoutX(Double.parseDouble(city3[3]) / 4.429 - 7);
                    quad3[counter3].setLayoutY(Double.parseDouble(city3[4]) / 4.429 - 7);
                    quad3[counter3].setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            System.out.println(city3[0]);
                        }
                    });
                    counter3++;
                }
                String[] city4 = line.split(split);
                if (city1[2].equals("4")) {
                    quad4[counter4] = new Button();
                    quad4[counter4].setOpacity(1);
                    quad4[counter4].setPadding(new Insets(0, 5, 0, 5));
                    quad4[counter4].setLayoutX(Double.parseDouble(city1[3]) / 4.429 - 7);
                    quad4[counter4].setLayoutY(Double.parseDouble(city1[4]) / 4.429 - 7);
                    quad4[counter4].setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            System.out.println(city4[0]);
                        }
                    });
                    counter4++;
                }
            }
        } catch (Exception E) {
            E.printStackTrace();
        }
                
        map1ImageView.setFitHeight(580);
        map1ImageView.setPreserveRatio(true);
        mapShits1.getChildren().add(map1ImageView);
        mapShits1.getChildren().addAll(Arrays.asList(quad1));

        q1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(quad != 1){
                    mapShits1.getChildren().removeAll(quad2);
                    mapShits1.getChildren().removeAll(quad3);
                    mapShits1.getChildren().removeAll(quad4);
                    map1ImageView.setImage(map1Image);
                    map1ImageView.setFitHeight(580);
                    map1ImageView.setPreserveRatio(true);
                    mapShits1.getChildren().addAll(Arrays.asList(quad1));
                    quad = 1;
                }
            }
        });
        q2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(quad != 2){
                    mapShits1.getChildren().removeAll(quad1);
                    mapShits1.getChildren().removeAll(quad3);
                    mapShits1.getChildren().removeAll(quad4);
                    map1ImageView.setImage(map2Image);
                    map1ImageView.setFitHeight(580);
                    map1ImageView.setPreserveRatio(true);
                    mapShits1.getChildren().addAll(Arrays.asList(quad2));
                    quad = 2;
                }
            }
        });
        q3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(quad != 3){
                    mapShits1.getChildren().removeAll(quad1);
                    mapShits1.getChildren().removeAll(quad2);
                    mapShits1.getChildren().removeAll(quad4);
                    map1ImageView.setImage(map3Image);
                    map1ImageView.setFitHeight(580);
                    map1ImageView.setPreserveRatio(true);
                    mapShits1.getChildren().addAll(Arrays.asList(quad3));
                    quad = 3;
                }
            }
        });
        q4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(quad != 4){
                    mapShits1.getChildren().removeAll(quad1);
                    mapShits1.getChildren().removeAll(quad2);
                    mapShits1.getChildren().removeAll(quad3);
                    map1ImageView.setImage(map4Image);
                    map1ImageView.setFitHeight(580);
                    map1ImageView.setPreserveRatio(true);
                    mapShits1.getChildren().addAll(Arrays.asList(quad4));
                    quad = 4;
                }
            }
        });
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

        gameOptions.getChildren().add(gridSelector);
        gameOptions.getChildren().add(flightButton);
        gameOptions.getChildren().add(historyButton);
        gameOptions.getChildren().add(aboutButton);
        gameOptions.getChildren().add(saveButton);
        gamePane.getChildren().add(mapShits1);
        gamePane.setRight(gameOptions);
        gamePane.setPrefSize(820, 600);
        workspace.getChildren().add(gamePane);

    }

    public String randomCard() {
        Random rand = new Random();
        int randomCard = rand.nextInt((179 - 1) + 1) + 1;
        int count = 0;
        String name = "";
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String CityPath = props.getProperty(JTEPropertyType.CITIES_PATH);
        String line = "";
        String split = ",";
        try {
            BufferedReader cityFile = new BufferedReader(new FileReader(CityPath));
            while ((line = cityFile.readLine()) != null) {
                String[] city = line.split(split);
                if (count == randomCard) {
                    name = city[0];
                    break;
                } else {
                    count++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
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
