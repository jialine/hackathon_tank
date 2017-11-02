package ele.me.hackathon.tank;

/**
 * Created by lanjiangang on 27/10/2017.
 */
public class Tank extends  MovableObject {
    private boolean fired;

    public Tank(Position p, Direction dir, int speed) {
       super(p, dir, speed);
    }

    public boolean isFired() {
        return fired;
    }

}
