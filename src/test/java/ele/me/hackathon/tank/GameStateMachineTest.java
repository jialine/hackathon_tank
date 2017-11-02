package ele.me.hackathon.tank;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by lanjiangang on 01/11/2017.
 */
public class GameStateMachineTest {
    private  static GameMap map = null;
    @BeforeClass
    public static void setup() throws IOException {
        map = GameMap.load(GameStateMachineTest.class.getResourceAsStream("/samplemap.txt"));

    }

    @Test
    public void testNewOrders() throws Exception {

    }

}