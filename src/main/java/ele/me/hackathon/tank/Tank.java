package ele.me.hackathon.tank;

/**
 * Created by lanjiangang on 27/10/2017.
 */
public class Tank extends  MovableObject {
    private int id;
    private int shellSpeed;
    private Shell shell;
    private int hp;

    public Tank(int id, Position p, Direction dir, int speed, int shellSpeed, int hp) {
       super(p, dir, speed);
        this.id = id;
        this.shellSpeed = shellSpeed;
        this.hp = hp;
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

    public int getId() {
        return id;
    }

    public int getHp() {
        return hp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;

        Tank tank = (Tank) o;

        return id == tank.id;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id;
        return result;
    }
}
