package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Simulation {

    private final Area area;

    private final int simulationDeadline;

    private final int droneMaximumLoad;

    private final List<Drone> drones;

    private final List<Warehouse> warehouses;

    private final List<CustomerOrder> orders;

    private List<Drone> inactiveDrones = new ArrayList<>();

    private Map<Integer, List<Drone>> droneFreeMap = new HashMap<>();

    public Simulation(Area area, int simulationDeadline, int droneMaximumLoad, List<Drone> drones, List<Warehouse> warehouses, List<CustomerOrder> orders) {
        this.area = area;
        this.simulationDeadline = simulationDeadline;
        this.droneMaximumLoad = droneMaximumLoad;
        this.drones = drones;
        this.warehouses = warehouses;
        this.orders = orders;
    }

    public void run() {
        // TODO
    }

}
