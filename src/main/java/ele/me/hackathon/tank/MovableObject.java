package ele.me.hackathon.tank;

/**
 * Created by lanjiangang on 03/11/2017.
 */
public class MovableObject {
    private Position pos;
    private Direction dir;
    private int speed;

    public MovableObject(Position pos, Direction dir, int speed) {
        this.pos = pos;
        this.dir = dir;
        this.speed = speed;
    }

    public void turnTo(Direction dir) {
        this.dir = dir;
    }

    public Position getPos() {
        return pos;
    }

    public Direction getDir() {
        return dir;
    }

    /**
     * Because Tank class don't connected to the Map, it does not know if the movement is validate.
     * So it can only evaluate the move track and let the GameStateMachine to verify it .
                      (0,0) - (0,1) - (0,2)
                                | UP
                LEFT  (1,0) - (1,1) - (1,2)  RIGHT
                                |  DOWN
                              (2,1)
      * @return
     */
    public Position[] evaluateMoveTrack() {
        Position[] track = new Position[speed];
        for(int i = 0; i < speed; i++) {
            track[i] = pos.move(dir);
        }
        return track;
    }
}
