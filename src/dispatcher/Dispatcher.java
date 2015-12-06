package dispatcher;


import org.apache.log4j.Logger;
import port.Berth;
import port.Port;
import ship.Ship;
import warehouse.Warehouse;

import java.util.List;
import java.util.Map;

/**
 * Created by Антон on 12.11.2015.
 */
public class Dispatcher implements Runnable {

    private final static int TIME_TO_SLEEP = 500;

    private Port port;
    private static Logger logger = Logger.getLogger("file");
    private volatile boolean stopThread = false;

    public Dispatcher(Port port) {
        this.port = port;
    }

    public void run() {
        while (!stopThread) {
            logMessage();
            sleep();
        }
    }

    public void stopThread() {
        stopThread = true;
    }

    private void logMessage() {
            Warehouse warehouse = port.getPortWarehouse();
            logger.debug("На складе порта хранится " + warehouse.getRealSize() + " контейнеров");
            Map<Ship, Berth> berths = port.getUsedBerths();
                for (Map.Entry<Ship, Berth> pair : berths.entrySet()) {
                    logger.debug("Корабль " + pair.getKey().getName() + " находится у причала " + pair.getValue().getId() + ". "
                            + "На корабле хранится " + pair.getKey().getWarehouse().getRealSize() + " контейнеров");
                }

            List<Berth> emptyBetrhs = port.getEmptyBerths();
                for (Berth berth : emptyBetrhs) {
                    logger.debug("Причал " + berth.getId() + " пустой");
                }


            try {
                Thread.sleep(TIME_TO_SLEEP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

    }

    private void sleep() {
        try {
            Thread.sleep(TIME_TO_SLEEP);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

}
