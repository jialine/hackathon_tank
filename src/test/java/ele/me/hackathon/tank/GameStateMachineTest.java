package ele.me.hackathon.tank;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by lanjiangang on 01/11/2017.
 */
public class GameStateMachineTest {
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
        tankA = new Tank(1, new Position(1, 1), Direction.DOWN, 1, 2, 2);
        tankB = new Tank(2, new Position(1, 5), Direction.DOWN, 1, 2, 2);

        Map tanks = new LinkedHashMap<Integer, Tank>();
        tanks.put(1, tankA);
        tanks.put(2, tankB);

        stateMachine = new GameStateMachine(tanks, map);
    }

    @Test
    public void testTurnToOrders() throws Exception {

        List<TankOrder> orders = new LinkedList<TankOrder>();
        orders.add(new TankOrder(1, "turnTo", Direction.RIGHT));
        orders.add(new TankOrder(2, "turnTo", Direction.LEFT));

        stateMachine.newOrders(orders);

        Map<Integer, Tank> tanks = stateMachine.getTanks();
        assertEquals(new Position(1, 1), tanks.get(1).getPos());
        assertEquals(Direction.RIGHT, tanks.get(1).getDir());

        assertEquals(new Position(1, 5), tanks.get(2).getPos());
        assertEquals(Direction.LEFT, tanks.get(2).getDir());
    }

    @Test
    public void testMoveOrders() throws Exception {

        List<TankOrder> orders = new LinkedList<TankOrder>();
        orders.add(new TankOrder(1, "move", Direction.RIGHT));
        orders.add(new TankOrder(2, "move", Direction.LEFT));

        stateMachine.newOrders(orders);

        Map<Integer, Tank> tanks = stateMachine.getTanks();
        assertEquals(new Position(1, 1).moveOneStep(tanks.get(1).getDir()), tanks.get(1).getPos());
        assertEquals(new Position(1, 5).moveOneStep(tanks.get(2).getDir()), tanks.get(2).getPos());
    }

    @Test
    public void testMoveToBarrier() {
        tankA.turnTo(Direction.UP);

        List<TankOrder> orders = new LinkedList<TankOrder>();
        orders.add(new TankOrder(1, "move", Direction.UP));

        assertEquals(new Position(1, 1), tankA.getPos());
    }

    @Test
    public  void testMoveToSamePos() {
        tankA.turnTo(Direction.DOWN);
        tankA.moveTo(new Position(1, 1));

        tankB.turnTo(Direction.LEFT);
        tankB.moveTo(new Position(2, 2));

        List<TankOrder> orders = new LinkedList<TankOrder>();
        orders.add(new TankOrder(1, "move", Direction.UP));
        orders.add(new TankOrder(2, "move", Direction.UP));

        assertEquals(new Position(1, 1), tankA.getPos());
        assertEquals(new Position(2, 2), tankB.getPos());

    }

}