package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Simulation {

    private final Area area;

    private final int simulationDeadline;

    private int simulationStep = 0;

    private final int droneMaximumLoad;

    private final List<Drone> drones;

    private final List<Warehouse> warehouses;

    private final List<CustomerOrder> orders;

    private final List<DispatchOrder> dispatchOrders = new ArrayList<>();

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
        setUp();
        simulate();
    }

    private void simulate() {
        while (simulationStep < simulationDeadline && !dispatchOrders.isEmpty()) {
            populateFreeDronesFromCurrentStep();

            ++simulationStep;
        }
    }

    private void populateFreeDronesFromCurrentStep() {
        final List<Drone> freeDrones = droneFreeMap.get(simulationStep);
        inactiveDrones.addAll(freeDrones);
    }

    private void setUp() {
        inactiveDrones.addAll(drones);

        for (final CustomerOrder customer : orders) {
            for (final ItemType itemType : customer.getContents().keySet()) {
                int count = customer.getContents().get(itemType);
                while (count > 0) {
                    final Warehouse warehouse = findNearestWarehouseForItemType(customer.getPosition(), itemType);
                    final int warehouseAmount = warehouse.getStorage().get(itemType);
                    final int reservedAmount = Math.min(warehouseAmount, count);
                    final DispatchOrder dispatchOrder = new DispatchOrder(
                            warehouse, itemType, reservedAmount, customer.getId()
                    );

                    dispatchOrders.add(dispatchOrder);

                    count -= reservedAmount;
                    warehouse.getStorage().put(itemType, warehouseAmount - reservedAmount);
                }
            }
        }
    }

    private Warehouse findNearestWarehouseForItemType(Position position, final ItemType itemType) {
        return warehouses.stream()
                .filter(w -> w.getStorage().containsKey(itemType))
                .sorted(
                        (w1, w2) -> Double.compare(position.distanceTo(w1.getPosition()), position.distanceTo(w2.getPosition()))
                )
                .findFirst()
                .get();
    }

}
