package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

            final int maxDrones = drones.size() / warehouses.size();

            for (final Warehouse warehouse : warehouses) {
                final List<Drone> freeDrones = inactiveDrones.stream()
                        .sorted((d1, d2) ->
                                        Double.compare(warehouse.getPosition().distanceTo(d1.getPosition()),
                                                warehouse.getPosition().distanceTo(d2.getPosition()))
                        )
                        .collect(Collectors.toList());
                final int dronesToTake = Math.min(freeDrones.size(), maxDrones);
                for(int droneNumber = 0; droneNumber < dronesToTake; ++droneNumber) {
                    final Drone drone = freeDrones.get(droneNumber);
                    // TODO get dispatch order
                    final DispatchOrder order = null;
                }
            }

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
            for (final ItemType itemType : customer.getContents()) {
                final Warehouse warehouse = findNearestWarehouseForItemType(customer.getPosition(), itemType);
                final int warehouseAmount = warehouse.getStorage().get(itemType);
                final DispatchOrder dispatchOrder = new DispatchOrder(
                        warehouse, itemType, 1, customer.getId()
                );

                dispatchOrders.add(dispatchOrder);

                if (warehouseAmount > 1) {
                    warehouse.getStorage().put(itemType, warehouseAmount - 1);
                } else {
                    warehouse.getStorage().remove(itemType);
                }
            }
        }
    }

    private Warehouse findNearestWarehouseForItemType(Position position, final ItemType itemType) {
        return warehouses.stream()
                .filter(w -> w.getStorage().containsKey(itemType) && w.getStorage().get(itemType) > 0)
                .sorted(
                        (w1, w2) -> Double.compare(position.distanceTo(w1.getPosition()), position.distanceTo(w2.getPosition()))
                )
                .findFirst()
                .get();
    }

}
