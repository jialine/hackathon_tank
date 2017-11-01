package ele.me.hackathon.tank;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static junit.framework.Assert.assertEquals;

/**
 * Created by lanjiangang on 01/11/2017.
 */
public class GameMapTest {


    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testLoadMapFromFile() throws IOException {
        InputStream in = this.getClass().getResourceAsStream("/samplemap.txt");
        GameMap map = GameMap.load(in);
        assertEquals(18, map.size());
    }
}