package ele.me.hackathon.tank; /**
 * Created by lanjiangang on 27/10/2017.
 */

import java.util.*;
import java.util.stream.Collectors;

public class GameStateMachine {
    private GameMap map;
    private Map<Integer, Tank> tanks;
    private List<Shell> shells = new LinkedList<>();

    private List<TankOrder> turnDirOrders = new LinkedList<TankOrder>();
    private List<TankOrder> moveOrders = new LinkedList<TankOrder>();
    private List<TankOrder> fireOrders = new LinkedList<TankOrder>();

    public GameStateMachine(Map<Integer, Tank> tanks, GameMap map) {
        this.tanks = tanks;
        this.map = map;
    }

    public void newOrders(List<TankOrder> orders) throws InvalidOrder {
        validate(orders);
        evaluate(orders);
    }

    private void validate(List<TankOrder> orders) throws InvalidOrder {

    }

    private void evaluate(List<TankOrder> orders) {
        evaluateShells();
        classicOrders(orders, fireOrders, turnDirOrders, moveOrders);
        evaluateFireActions(fireOrders);
        evaluateTurnDirectionActions(turnDirOrders);
        evaluateMoveActions(moveOrders);
    }

    private void evaluateShells() {
        shells.forEach(shell -> {
            Position[] positions = shell.evaluateMoveTrack();
            for (Position pos : positions) {
                if (map.isBarrier(pos)) {
                    shell.destroyed();
                    break;
                } else {
                    List<Tank> tanksAt = getTankAt(pos);
                    if (tanksAt.size() > 0) {
                        tanksAt.forEach(Tank::destroyed);
                        shell.destroyed();
                        break;
                    }
                }
                shell.moveTo(pos);
            }
        });

        List<Tank> destroyedTanks = tanks.values().stream().filter(t -> t.isDestroyed()).collect(Collectors.toCollection(() -> new LinkedList<>()));
        destroyedTanks.forEach(t -> tanks.remove(t.getId()));

        shells.removeIf(shell -> shell.isDestroyed());

    }

    private List<Tank> getTankAt(Position pos) {
        List<Tank> tanksAt = tanks.values().stream().filter(t -> t.getPos().equals(pos)).collect(Collectors.toCollection(() -> new LinkedList<>()));
        return tanksAt;
    }

    private void classicOrders(List<TankOrder> orders, List<TankOrder> fireOrders, List<TankOrder> turnDirOrders, List<TankOrder> moveOrders) {
        fireOrders.clear();
        turnDirOrders.clear();
        moveOrders.clear();

        for (TankOrder order : orders) {
            if (!isValidateOder(order))
                continue;

            if (isFireOder(order)) {
                fireOrders.add(order);
            } else if (isTurnDirOrder(order)) {
                turnDirOrders.add(order);
            } else if (isMoveOrder(order)) {
                moveOrders.add(order);
            } else {
                System.out.println("invalid order : " + order);
            }
        }
    }

    private boolean isMoveOrder(TankOrder order) {
        if ("move".equals(order.getOrder())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isTurnDirOrder(TankOrder order) {
        if ("turnTo".equals(order.getOrder())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isFireOder(TankOrder order) {
        if ("fire".equals(order.getOrder())) {
            return true;
        } else {
            return false;
        }
    }

    private void evaluateFireActions(List<TankOrder> orders) {

    }

    private void evaluateTurnDirectionActions(List<TankOrder> orders) {
        for (TankOrder order : orders) {
            if (!isValidateOder(order))
                return;

            tanks.get(order.getTankId()).turnTo(order.getParameter());
        }
    }

    private boolean isValidateOder(TankOrder order) {
        if (!tanks.containsKey(order.getTankId()) ||  tanks.get(order.getTankId()).isDestroyed())
            return false;

        return true;
    }

    private void evaluateMoveActions(List<TankOrder> orders) {
        Map<Tank, Position[]> moveTracks = new LinkedHashMap<Tank, Position[]>(tanks.size());
        orders.forEach(o -> {
            if (isValidateOder(o)) {
                moveTracks.put(tanks.get(o.getTankId()), tanks.get(o.getTankId()).evaluateMoveTrack());
            }
        });

        int maxMoves = 0;
        for (Position[] p : moveTracks.values()) {
            if (p.length > maxMoves)
                maxMoves = p.length;
        }

        Map<Tank, Position> result = new LinkedHashMap<Tank, Position>();
        initResult(result);

        for (int i = 0; i < maxMoves; i++) {

            final int finalI = i;

            //check if tanks get overlapped
            List<Tank> overlappedTanks = checkOverlap(i, moveTracks);

            //check if tanks move into a barrier
            checkBarrier(i, moveTracks);

            //check if tank is destroyed by shells
            List<Tank> destroyedTanks = checkShells(i, moveTracks);
            destroyedTanks.forEach(t -> {
                result.remove(t);
                moveTracks.remove(t);
            });

            //record latest result
            moveTracks.forEach((tank, track) -> {
                result.put(tank, track[finalI]);
            });

            //remove tank which already evaluates all its movements.
            for (Iterator<Tank> itr = moveTracks.keySet().iterator(); itr.hasNext(); ) {
                Tank t = itr.next();
                if (moveTracks.get(t).length - 1 == i) {
                    itr.remove();
                }
            }

        }

        //apply the result
        result.forEach((tank, pos) -> tank.moveTo(pos));
    }

    private List<Tank> checkShells(int i, Map<Tank, Position[]> moveTracks) {
        List<Tank> tankList = new LinkedList<>();
        for (Tank t : moveTracks.keySet()) {
            List<Shell> shellList = getShellAt(moveTracks.get(t)[i]);

            shellList.forEach(s -> s.destroyed());

            if (shellList.size() >= t.getHp()) {
                t.destroyed();
                tankList.add(t);
            }
        }
        return tankList;
    }

    private List<Tank> checkBarrier(int i, Map<Tank, Position[]> moveTracks) {
        List<Tank> tankList = moveTracks.keySet().stream().filter(t -> map.isBarrier(moveTracks.get(t)[i]))
                .collect(Collectors.toCollection(() -> new LinkedList<>()));
        tankList.forEach(t -> moveTracks.remove(t));
        return tankList;

    }

    private List<Tank> checkOverlap(final int i, Map<Tank, Position[]> moveTracks) {
        List<Position> oneMove = new LinkedList<Position>();
        moveTracks.forEach((tank, track) -> {
            oneMove.add(track[i]);
        });

        List<Tank> tankList = moveTracks.keySet().stream().filter(t -> Collections.frequency(oneMove, moveTracks.get(t)[i]) > 1)
                .collect(Collectors.toCollection(() -> new LinkedList<>()));
        tankList.forEach(t -> moveTracks.remove(t));
        return tankList;
    }

    private void initResult(Map<Tank, Position> result) {
        tanks.values().forEach(t -> result.put(t, t.getPos()));
    }

    private List<Shell> getShellAt(Position position) {
        List<Shell> shellList = shells.stream().filter(shell -> !shell.isDestroyed() && shell.getPos().equals(position))
                .collect(Collectors.toCollection(() -> new LinkedList<Shell>()));
        return shellList;
    }

    public Map<Integer, Tank> getTanks() {
        return tanks;
    }

    public List<Tank> getLeftTanks() {
        List<Tank> left = tanks.values().stream().filter(t -> !t.isDestroyed()).collect(Collectors.toCollection(() -> new LinkedList<Tank>()));
        return left;
    }

    protected List<Shell> getShells() {
        return shells;
    }
}
