package ele.me.hackathon.tank;

/**
 * Created by lanjiangang on 27/10/2017.
 */
public class Tank extends  MovableObject {
    private int shellSpeed;
    private Shell shell;

    public Tank(Position p, Direction dir, int speed, int shellSpeed) {
       super(p, dir, speed);
        this.shellSpeed = shellSpeed;
    }

    /**
    * fire at the given direction.
     * return the new shell if the previous shell is disappeared or the tank never fired.
     * Otherwise return the existing shell.
     */
    public Shell fireAt(Direction dir) {
        if(shell == null || shell.isDestroyed()) {
            shell =  new Shell(getPos().moveOneStep(dir), dir, shellSpeed);
        }

        return shell;
    }
}
