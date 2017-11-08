package ele.me.hackathon.tank;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by lanjiangang on 09/11/2017.
 */
public class PlayerInteractTest {
    private static GameMap map = null;
    private GameStateMachine stateMachine = null;
    private Tank tankA;
    private Tank tankB;

    @BeforeClass
    public static void beforeClass() throws IOException {
        map = GameMap.load(GameStateMachineTest.class.getResourceAsStream("/samplemap.txt"));
    }

    @Before
    public void setup() {
        tankA = new Tank(1, new Position(1, 1), Direction.DOWN, 1, 2, 1);
        tankB = new Tank(2, new Position(1, 5), Direction.DOWN, 1, 2, 2);

        Map tanks = new LinkedHashMap<Integer, Tank>();
        tanks.put(1, tankA);
        tanks.put(2, tankB);

        stateMachine = new GameStateMachine(tanks, map);

        Map<String, Player> players = new LinkedHashMap<>();
        players.put("playerA", new Player("playerA", Arrays.asList(new Integer[] { 1 })));
        players.put("playerB", new Player("playerB", Arrays.asList(new Integer[] { 2 })));
        stateMachine.setPlayers(players);

    }

    @Test
    public void testConvertGameState() {
        PlayerInteract interactor = new PlayerInteract("playerA", null, null, null);
        Map<String, GameState> state = stateMachine.reportState();
        ele.me.hackathon.tank.player.GameState res = interactor.convert(state.get("playerA"));
        assertEquals(2, res.getTanks().size());
        assertEquals(ele.me.hackathon.tank.player.Direction.DOWN, res.getTanks().get(0).getDir());
        assertEquals(1, res.getTanks().get(0).getHp());
        assertEquals(new ele.me.hackathon.tank.player.Position(1, 1), res.getTanks().get(0).getPos());
        assertEquals(1, res.getTanks().get(0).getId());

        assertEquals(2, res.getTanks().get(1).getHp());

    }


}