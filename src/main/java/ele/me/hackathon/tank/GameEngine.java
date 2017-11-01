package ele.me.hackathon.tank;

/**
 * Created by lanjiangang on 27/10/2017.
 */
public class GameEngine {
    private Player playerA;
    private Player playerB;

    public static void main(String[] args){
        GameEngine engine = new GameEngine();
        engine.startGame();
    }

    private void startGame() {
        initGameStateMachine();
        waitForPlayers();
        play();
    }

    private void initGameStateMachine() {
        GameMap map = loadMap();
        generateTanks();
    }

    private void generateTanks() {
    }

    private void play() {

    }

    private Player[] waitForPlayers() {
        return null;
    }

    private GameMap loadMap() {
        return null;
    }
}
