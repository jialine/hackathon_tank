package ele.me.hackathon.tank;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by lanjiangang on 07/11/2017.
 */
public class GameState {
    private String playerName;
    private List<Tank> tanks = new LinkedList<>();
    private List<Shell> shells = new LinkedList<>();
    private int noOfFlag = 0;

    public GameState(String playerName) {
        this.playerName = playerName;
    }

    public List<Tank> getTanks() {
        return tanks;
    }

    public List<Shell> getShells() {
        return shells;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getNoOfFlag() {
        return noOfFlag;
    }

    public void setNoOfFlag(int noOfFlag) {
        this.noOfFlag = noOfFlag;
    }
}
