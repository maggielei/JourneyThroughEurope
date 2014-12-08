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

    // GamePane
    private Label HangManLabel;
    private Button newGameButton;
    private BorderPane gamePanel = new BorderPane();
    static int quad = 0;
    static ArrayList<City> allCities = new ArrayList();
    // Player Select Pane
    private BorderPane playerSelectPane = new BorderPane();
    private BorderPane playerPanel = new BorderPane();
    private GridPane playerGrid = new GridPane();
    static int choice = 0;
    static Pane mapContainer = new Pane();
    static Button[] quad1 = new Button[20];
    static Button[] quad2 = new Button[35];
    static Button[] quad3 = new Button[65];
    static Button[] quad4 = new Button[60];
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

    public ArrayList<City> getAllCities() {
        return this.allCities;
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

//        props.addProperty(JTEPropertyType.INSETS, "0");
//        String str = props.getProperty(JTEPropertyType.INSETS);
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
        startButton.setDisable(true);
        startButton.setFont(Font.font("Georgia", 16));
        eventHandler.showPanes(1, playerGrid);
        playerComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                choice = Integer.parseInt(playerComboBox.getValue().toString());
                eventHandler.setNumPlayas(choice);
                eventHandler.showPanes(choice, playerGrid);
                startButton.setDisable(false);
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

        TextField nameField = new TextField();
        option.setPadding(new Insets(50));
        option.setPrefSize(240, 240);
        playerPane.getChildren().add(option);
        //playerPane.setVisible(false);
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

        Label dieLabel = new Label();
        String diePath = "die_1.jpg";
        Image dieImage = loadImage(diePath);
        ImageView dieImageView = new ImageView(dieImage);
        dieLabel.setGraphic(dieImageView);

        String CityPath = props.getProperty(JTEPropertyType.CITIES_PATH);
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File("data/cities.xml"));
            Node root = doc.getElementsByTagName("routes").item(0);
            NodeList cardlist = root.getChildNodes();
            String name = "";
            for (int i = 0; i < cardlist.getLength(); i++) {
                ArrayList<String> sea = new ArrayList();
                ArrayList<String> land = new ArrayList();

                Node cardNode = cardlist.item(i);
                if (cardNode.getNodeType() == Node.ELEMENT_NODE) {
                    NodeList cardAttrs = cardNode.getChildNodes();
                    // one card
                    for (int j = 0; j < cardAttrs.getLength(); j++) {
                        if (cardAttrs.item(j).getNodeType() == Node.ELEMENT_NODE) {
                            Node theNode = cardAttrs.item(j);
                            switch (theNode.getNodeName()) {
                                case "name":
                                    name = theNode.getTextContent();
//                                    System.out.println("City name: "
//                                            + theNode.getTextContent());
                                    break;
                                case "land":
                                    NodeList landList = theNode.getChildNodes();
                                    for (int k = 0; k < landList.getLength(); k++) {
                                        if (landList.item(k).getNodeType() == Node.ELEMENT_NODE) {
                                            land.add(landList.item(k).getTextContent());
//                                            System.out.println("Land neighbour: "
//                                                    + landList.item(k)
//                                                    .getTextContent());
                                        }
                                    }
                                    break;
                                case "sea":
                                    NodeList seaList = theNode.getChildNodes();
                                    for (int k = 0; k < seaList.getLength(); k++) {
                                        if (seaList.item(k).getNodeType() == Node.ELEMENT_NODE) {
                                            sea.add(seaList.item(k).getTextContent());
//                                            System.out.println("Sea neighbour: "
//                                                    + seaList.item(k)
//                                                    .getTextContent());
                                        }
                                    }
                                    break;
                            }
                        }
                    }
                    City newCity = new City(name, land, sea);
                    allCities.add(newCity);
                }
            }
            BufferedReader cities = new BufferedReader(new FileReader(CityPath));
            String line = "";
            while ((line = cities.readLine()) != null) {
                String[] csvCity = line.split(",");
                for (City rackCity : allCities) {
                    if (rackCity.getName().equalsIgnoreCase(csvCity[0])) {
                        // working
                        rackCity.setColor(csvCity[1]);
                        rackCity.setQuad(Integer.parseInt(csvCity[2]));
                        rackCity.setX(Double.parseDouble(csvCity[3]));
                        rackCity.setY(Double.parseDouble(csvCity[4]));
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

        int counter1 = 0;
        int counter2 = 0;
        int counter3 = 0;
        int counter4 = 0;

        // Creates city buttons for each quadrant
        for (City rackCity : allCities) {
            if (rackCity.getQuad() == 1) {
                quad1[counter1] = new Button();
                quad1[counter1].setStyle("-fx-color: black;-fx-background-radius: 50 50 50 50;");
                quad1[counter1].setOpacity(1);
                quad1[counter1].setPadding(new Insets(0, 5, 0, 5));
                quad1[counter1].setLayoutX(rackCity.getX() / 4.429 - 7);
                quad1[counter1].setLayoutY(rackCity.getY() / 4.429 - 7);
                quad1[counter1].setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        System.out.println(rackCity.getName());
                    }
                });
                counter1++;
            }
            if (rackCity.getQuad() == 2) {
                quad2[counter2] = new Button();
                quad2[counter2].setStyle("-fx-color: black;-fx-background-radius: 50 50 50 50;");
                quad2[counter2].setOpacity(1);
                quad2[counter2].setPadding(new Insets(0, 5, 0, 5));
                quad2[counter2].setLayoutX(rackCity.getX() / 4.429 - 7);
                quad2[counter2].setLayoutY(rackCity.getY() / 4.429 - 7);
                quad2[counter2].setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        System.out.println(rackCity.getName());
                    }
                });
                counter2++;
            }
            if (rackCity.getQuad() == 3) {
                quad3[counter3] = new Button();
                quad3[counter3].setStyle("-fx-color: black;-fx-background-radius: 50 50 50 50;");
                quad3[counter3].setOpacity(1);
                quad3[counter3].setPadding(new Insets(0, 5, 0, 5));
                quad3[counter3].setLayoutX(rackCity.getX() / 4.429 - 7);
                quad3[counter3].setLayoutY(rackCity.getY() / 4.429 - 7);
                quad3[counter3].setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {

                        System.out.println(rackCity.getName());
                    }
                });
                counter3++;
            }
            if (rackCity.getQuad() == 4) {
                quad4[counter4] = new Button();
                quad4[counter4].setStyle("-fx-color: black;-fx-background-radius: 50 50 50 50;");
                quad4[counter4].setOpacity(1);
                quad4[counter4].setPadding(new Insets(0, 5, 0, 5));
                quad4[counter4].setLayoutX(rackCity.getX() / 4.429 - 7);
                quad4[counter4].setLayoutY(rackCity.getY() / 4.429 - 7);
                quad4[counter4].setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        System.out.println(rackCity.getName());
                    }
                });
                counter4++;
            }
        }

        map1ImageView.setFitHeight(580);
        map1ImageView.setPreserveRatio(true);
        mapContainer.getChildren().add(map1ImageView);
        mapContainer.getChildren().addAll(Arrays.asList(quad1));

        // Add die imageview
        q1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (quad != 1) {
                    mapContainer.getChildren().removeAll(quad2);
                    mapContainer.getChildren().removeAll(quad3);
                    mapContainer.getChildren().removeAll(quad4);
                    map1ImageView.setImage(map1Image);
                    map1ImageView.setFitHeight(580);
                    map1ImageView.setPreserveRatio(true);
                    mapContainer.getChildren().addAll(Arrays.asList(quad1));
                    quad = 1;
                }
            }
        });
        q2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (quad != 2) {
                    mapContainer.getChildren().removeAll(quad1);
                    mapContainer.getChildren().removeAll(quad3);
                    mapContainer.getChildren().removeAll(quad4);
                    map1ImageView.setImage(map2Image);
                    map1ImageView.setFitHeight(580);
                    map1ImageView.setPreserveRatio(true);
                    mapContainer.getChildren().addAll(Arrays.asList(quad2));
                    quad = 2;
                }
            }
        });
        q3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (quad != 3) {
                    mapContainer.getChildren().removeAll(quad1);
                    mapContainer.getChildren().removeAll(quad2);
                    mapContainer.getChildren().removeAll(quad4);
                    map1ImageView.setImage(map3Image);
                    map1ImageView.setFitHeight(580);
                    map1ImageView.setPreserveRatio(true);
                    mapContainer.getChildren().addAll(Arrays.asList(quad3));
                    quad = 3;
                }
            }
        });
        q4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (quad != 4) {
                    mapContainer.getChildren().removeAll(quad1);
                    mapContainer.getChildren().removeAll(quad2);
                    mapContainer.getChildren().removeAll(quad3);
                    map1ImageView.setImage(map4Image);
                    map1ImageView.setFitHeight(580);
                    map1ImageView.setPreserveRatio(true);
                    mapContainer.getChildren().addAll(Arrays.asList(quad4));
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
        VBox leftSide = new VBox();
        Button hello = new Button("Deal Cards!");
        leftSide.getChildren().add(hello);
        hello.setFont(Font.font("Georgia", 16));
        hello.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO Auto-generated method stub
                eventHandler.respondToStartGameRequest(allCities);
            }
        });
        HBox gameContainer = new HBox();
        gameOptions.getChildren().addAll(dieLabel,
                gridSelector,
                flightButton,
                historyButton,
                aboutButton,
                saveButton);
        gameOptions.setStyle("-fx-alignment: center;");
        gameContainer.getChildren().addAll(leftSide,
                mapContainer,
                gameOptions);
        //gamePane.getChildren().add(mapContainer);
        //gamePane.setRight(gameOptions);
        gamePane.setCenter(gameContainer);
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
    public void addCards(String name){
        
    }
    public void addFigures(int figureQuad, double x, double y, int i) {
//        PropertiesManager props = PropertiesManager.getPropertiesManager();
//        String map1ImagePath = props.getProperty(JTEPropertyType.MAP1_IMAGE_NAME);
//        String map2ImagePath = props.getProperty(JTEPropertyType.MAP2_IMAGE_NAME);
//        String map3ImagePath = props.getProperty(JTEPropertyType.MAP3_IMAGE_NAME);
//        String map4ImagePath = props.getProperty(JTEPropertyType.MAP4_IMAGE_NAME);
//        Image map1Image = loadImage(map1ImagePath);
//        Image map2Image = loadImage(map2ImagePath);
//        Image map3Image = loadImage(map3ImagePath);
//        Image map4Image = loadImage(map4ImagePath);
//
//        ImageView map1ImageView = new ImageView(map1Image);
//        if (figureQuad == 1) {
//            if (quad != 1) {
//                mapContainer.getChildren().removeAll(quad2);
//                mapContainer.getChildren().removeAll(quad3);
//                mapContainer.getChildren().removeAll(quad4);
//                map1ImageView.setImage(map1Image);
//                map1ImageView.setFitHeight(580);
//                map1ImageView.setPreserveRatio(true);
//                mapContainer.getChildren().addAll(Arrays.asList(quad1));
//                quad = 1;
//            }
//        } else if (figureQuad == 2) {
//            if (quad != 2) {
//                mapContainer.getChildren().removeAll(quad1);
//                mapContainer.getChildren().removeAll(quad3);
//                mapContainer.getChildren().removeAll(quad4);
//                map1ImageView.setImage(map2Image);
//                map1ImageView.setFitHeight(580);
//                map1ImageView.setPreserveRatio(true);
//                mapContainer.getChildren().addAll(Arrays.asList(quad2));
//                quad = 2;
//            }
//        } else if (figureQuad == 3) {
//            if (quad != 3) {
//                mapContainer.getChildren().removeAll(quad1);
//                mapContainer.getChildren().removeAll(quad2);
//                mapContainer.getChildren().removeAll(quad4);
//                map1ImageView.setImage(map3Image);
//                map1ImageView.setFitHeight(580);
//                map1ImageView.setPreserveRatio(true);
//                mapContainer.getChildren().addAll(Arrays.asList(quad3));
//                quad = 3;
//            }
//
//        } else if (figureQuad == 4) {
//            if (quad != 4) {
//                mapContainer.getChildren().removeAll(quad1);
//                mapContainer.getChildren().removeAll(quad2);
//                mapContainer.getChildren().removeAll(quad3);
//                map1ImageView.setImage(map4Image);
//                map1ImageView.setFitHeight(580);
//                map1ImageView.setPreserveRatio(true);
//                mapContainer.getChildren().addAll(Arrays.asList(quad4));
//                quad = 4;
//            }
//        }
//        Label playerFigure = new Label();
//        Image playerImage = loadImage(i + ".png");
//        ImageView playerImageView = new ImageView(playerImage);
//        playerFigure.setGraphic(playerImageView);
//        playerFigure.setLayoutX(x);
//        playerFigure.setLayoutY(y);
//        mapContainer.getChildren().add(playerFigure);
    }
}
