package ele.me.hackathon.tank;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by lanjiangang on 02/11/2017.
 */
public class TankTest {
    Tank tank;

    @Before
    public void setup() {
        tank = new Tank(new Position(1, 1), Direction.DOWN, 1);
    }

    @Test
    public void testTurnTo() {
        tank.turnTo(Direction.LEFT);
        assertEquals(Direction.LEFT, tank.getDir());
    }

    /*
                      (0,0) - (0,1) - (0,2)
                                | UP
                LEFT  (1,0) - (1,1) - (1,2)  RIGHT
                                |  DOWN
                              (2,1)
     */
    @Test
    public void testEvaluateMoveTrack() {
        Position[] track = tank.evaluateMoveTrack();
        assertArrayEquals(new Position[] { new Position(2, 1) }, track);

        tank.turnTo(Direction.RIGHT);
        assertArrayEquals(new Position[] { new Position(1, 2) }, tank.evaluateMoveTrack());

        tank.turnTo(Direction.UP);
        assertArrayEquals(new Position[] { new Position(0, 1) }, tank.evaluateMoveTrack());

        tank.turnTo(Direction.LEFT);
        assertArrayEquals(new Position[] { new Position(1, 0) }, tank.evaluateMoveTrack());
    }
}