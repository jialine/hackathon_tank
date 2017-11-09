package ele.me.hackathon.tank;

import ele.me.hackathon.tank.player.PlayerServer;
import org.apache.thrift.TException;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

/**
 * Created by lanjiangang on 08/11/2017.
 */
public class PlayerInteract  {
    private final PlayerServer.Client client;
    private final List<Integer> tanks;
    private final GameMap map;

    private final String address;

    LinkedBlockingQueue<List<TankOrder>> commandQueue = new LinkedBlockingQueue<>();
    LinkedBlockingQueue<GameState> statusQueue = new LinkedBlockingQueue<>();

    Thread t = new Thread(new Runnable() {
        @Override
        public void run() {

            try {
                //wait an signal to go
                GameState state = statusQueue.take();

                client.uploadMap(convertMap(map));
                client.assignTanks(tanks);
            } catch (TException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for(;;) {

                try {
                    GameState state = statusQueue.take();
                    client.latestState(convert(state));
                } catch (TException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    commandQueue.offer(convertOrders(client.getNewOrders()));
                } catch (TException e) {
                    e.printStackTrace();
                    commandQueue.offer(new LinkedList<>());
                }

            }
        }
    });

    public void start() {
        t.start();
    }

    public PlayerInteract(String addr, PlayerServer.Client client, GameMap map, List<Integer> tanks) {
        this.address = addr;
        this.client = client;
        this.map = map;
        this.tanks = tanks;
    }


    protected ele.me.hackathon.tank.player.GameState convert(GameState state) {
        ele.me.hackathon.tank.player.GameState res = new ele.me.hackathon.tank.player.GameState();
        res.setTanks(convertTanks(state.getTanks()));
        res.setShells(convertShells(state.getShells()));
        res.setYourFlags(state.getNoOfFlag());
        return res;
    }

    private List<ele.me.hackathon.tank.player.Shell> convertShells(List<Shell> shells) {
        return null;
    }

    private List<ele.me.hackathon.tank.player.Tank> convertTanks(List<Tank> tanks) {
        return tanks.stream().map(t -> convertTank(t)).collect(Collectors.toList());
    }

    private ele.me.hackathon.tank.player.Tank convertTank(Tank t){
        ele.me.hackathon.tank.player.Tank res = new ele.me.hackathon.tank.player.Tank();
        res.setHp(t.getHp());
        res.setId(t.getId());
        res.setPos(convertPosition(t.getPos()));
        res.setDir(convertDir(t.getDir()));
        return res;
    }

    private ele.me.hackathon.tank.player.Direction convertDir(Direction dir) {
        return ele.me.hackathon.tank.player.Direction.findByValue(dir.getValue());
    }

    private ele.me.hackathon.tank.player.Position convertPosition(Position pos) {
        return new ele.me.hackathon.tank.player.Position(pos.getX(), pos.getY());
    }

    private List<TankOrder> convertOrders(List<ele.me.hackathon.tank.player.Order> newOrders) {
        List<TankOrder> res = newOrders.stream().map(o -> convertTankOrder(o)).collect(Collectors.toList());
        if(res.stream().filter(o -> !tanks.contains(o.getTankId())).count() > 0) {
            res.stream().filter(o -> !tanks.contains(o.getTankId())).forEach(o -> System.out.println(address + " sent an invalid order:" + o));
            return new LinkedList<>();
        }
        return res;
    }

    private TankOrder convertTankOrder(ele.me.hackathon.tank.player.Order o) {
        return new TankOrder(o.getTankId(), o.getOrder(), convertDir(o.getDir()));
    }

    private Direction convertDir(ele.me.hackathon.tank.player.Direction dir) {
        return Direction.findByValue(dir.getValue());
    }

    private List<List<Integer>> convertMap(GameMap map) {
        List<List<Integer>> m = new LinkedList<>();
        for(int[] line : map.getPixels()) {
            List<Integer> l = new LinkedList<>();
            for(int p : line) {
                l.add(p);
            }
            m.add(l);
        }
        return m;
    }

    public PlayerServer.Client getClient() {
        return client;
    }

    public LinkedBlockingQueue<List<TankOrder>> getCommandQueue() {
        return commandQueue;
    }

    public LinkedBlockingQueue<GameState> getStatusQueue() {
        return statusQueue;
    }

    public String getAddress() {
        return address;
    }
}
