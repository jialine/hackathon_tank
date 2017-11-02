package ele.me.hackathon.tank;

/**
 * Created by lanjiangang on 27/10/2017.
 */
public class Shell extends MovableObject {
    public Shell(Position pos, Direction dir, int speed) {
        super(pos, dir, speed);
    }

    public void turnTo(Direction dir) {
        throw new UnsupportedOperationException("Shell can't turn its direction");
    }
}
