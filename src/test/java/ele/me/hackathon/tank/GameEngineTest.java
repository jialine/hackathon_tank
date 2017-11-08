package ele.me.hackathon.tank;

import org.junit.Before;

/**
 * Created by lanjiangang on 08/11/2017.
 */
public class GameEngineTest {

    @Before
    public void setUp() throws Exception {
        GameStateMachineTest.class.getResourceAsStream("/samplemap.txt");
    }
}