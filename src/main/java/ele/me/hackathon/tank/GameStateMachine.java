package ele.me.hackathon.tank; /**
 * Created by lanjiangang on 27/10/2017.
 */
import java.util.List;

public class GameStateMachine {
    private GameMap map;
    private List<Tank> tanks;
    private List<PlayOrder> orders;

    public GameStateMachine(List<Tank> tanks, GameMap map) {
        this.tanks = tanks;
        this.map = map;
    }

    public void newOrders(List<PlayOrder> orders) throws InvalidOrder {
        validate(orders);
        evaluate(orders);
    }

    private void evaluate(List<PlayOrder> orders) {

    }

    private void validate(List<PlayOrder> orders) throws InvalidOrder {

    }
}
