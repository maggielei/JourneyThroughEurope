package JourneyThroughEurope.game;

import JourneyThroughEurope.ui.JTEEventHandler;
import java.util.ArrayList;
import java.util.Iterator;
import JourneyThroughEurope.ui.JTEUI;

public class JTEGameStateManager {
    public enum JTEGameState {
        GAME_NOT_STARTED, GAME_IN_PROGRESS, GAME_OVER
    }
    JTEEventHandler handler;
    private JTEGameState currentGameState;
    private JTEUI ui;
    private final String NEWLINE_DELIMITER = "\n";
    public JTEGameStateManager(JTEUI initUI) {
        ui = initUI;
        currentGameState = JTEGameState.GAME_NOT_STARTED;
    }
    public boolean isGameNotStarted() {
        return currentGameState == JTEGameState.GAME_NOT_STARTED;
    }
    public boolean isGameOver() {
        return currentGameState == JTEGameState.GAME_OVER;
    }
    public boolean isGameInProgress() {
        return currentGameState == JTEGameState.GAME_IN_PROGRESS;
    }
    public void startNewGame(int numPlayas) {
        makeNewGame();
    }
    public void makeNewGame() {
        currentGameState = JTEGameState.GAME_IN_PROGRESS;
    }
}
