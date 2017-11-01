package ele.me.hackathon.tank; /**
 * Created by lanjiangang on 27/10/2017.
 */
import java.util.List;

public class GameStateMachine {
    private GameMap map;
    private List<Tank> tanks;
    private List<PlayOrder> orders;

    public void newOrders(List<PlayOrder> orders) throws InvalidOrder {
        validate(orders);
        evaluate(orders);
    }

    private void evaluate(List<PlayOrder> orders) {

    }

    public void validate(List<PlayOrder> orders) throws InvalidOrder {

    }
}
