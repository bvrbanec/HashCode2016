package model;

import java.util.*;
import java.util.stream.Collectors;

public class Simulation {

    private final Area area;

    private final int simulationDeadline;

    private int simulationStep = 0;

    // TODO take into account
    private final int droneMaximumLoad;

    private final List<Drone> drones;

    private final List<Warehouse> warehouses;

    private final List<CustomerOrder> orders;

    private final Map<Warehouse, List<DispatchOrder>> dispatchOrders = new HashMap<>();

    private List<Drone> inactiveDrones = new ArrayList<>();

    private Map<Integer, List<Drone>> droneFreeMap = new HashMap<>();

    private Map<Warehouse, Double> warehouseWeights = new HashMap<>();

    private final List<String> output = new LinkedList<>();

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
        calculateWarehouseWeights();
        simulate();

        System.out.println(output.size());
        output.stream().forEach(System.out::println);
    }

    private void calculateWarehouseWeights() {
        final int totalDispatchOrders = dispatchOrders.values().stream().mapToInt(i -> i.size()).sum();
        for (final Warehouse w : dispatchOrders.keySet()) {
            final List<DispatchOrder> orders = dispatchOrders.get(w);
            final double weight = (double) orders.size() / totalDispatchOrders;
            warehouseWeights.put(w, weight);
        }
        warehouses.sort((w1, w2) -> -Double.compare(warehouseWeights.get(w1), warehouseWeights.get(w2)));
    }

    private void simulate() {
        while (simulationStep < simulationDeadline && !dispatchOrders.values().stream().allMatch(i -> i.isEmpty())) {
            populateFreeDronesFromCurrentStep();

            for (final Warehouse warehouse : warehouses) {
                final double weight = warehouseWeights.get(warehouse);
                final List<DispatchOrder> warehouseOrders = dispatchOrders.getOrDefault(warehouse, new ArrayList<>());
                if (warehouseOrders.isEmpty()) {
                    continue;
                }
                final List<Drone> freeDrones = inactiveDrones.stream()
                        .sorted((d1, d2) ->
                                        -Double.compare(warehouse.getPosition().distanceTo(d1.getPosition()),
                                                warehouse.getPosition().distanceTo(d2.getPosition()))
                        )
                        .collect(Collectors.toList());
                final int dronesToTake = Math.min(Math.min(freeDrones.size(), (int) (weight * drones.size())), warehouseOrders.size());
                for (int droneNumber = 0; droneNumber < dronesToTake; ++droneNumber) {
                    final Drone drone = freeDrones.get(droneNumber);
                    final DispatchOrder order = warehouseOrders.get(0);
                    warehouseOrders.remove(order);
                    dispatch(drone, order);
                }
                dispatchOrders.put(warehouse, warehouseOrders);
            }

            ++simulationStep;
        }
    }

    private void dispatch(Drone drone, DispatchOrder order) {
        final int loadStepDuration = (int) Math.ceil(drone.getPosition().distanceTo(order.getOrigin().getPosition())) + 1;
        final int dispatchStepDuration = (int) Math.ceil(drone.getPosition().distanceTo(order.getDestination().getPosition())) + 1;

        final int stepDuration = loadStepDuration + dispatchStepDuration;
        final int freeUntil = simulationStep + stepDuration;
        if (freeUntil > simulationDeadline) {
            return;
        }

        output.add(String.format("%d L %d %d %d", drone.getId(), order.getOrigin().getId(), order.getItemType().getId(), 1));
        output.add(String.format("%d D %d %d %d", drone.getId(), order.getDestination().getId(), order.getItemType().getId(), 1));

        final List<Drone> freeDronesForStep = droneFreeMap.getOrDefault(freeUntil, new ArrayList<>());
        freeDronesForStep.add(drone);
        inactiveDrones.remove(drone);
        droneFreeMap.put(freeUntil, freeDronesForStep);
    }

    private void populateFreeDronesFromCurrentStep() {
        final List<Drone> freeDrones = droneFreeMap.getOrDefault(simulationStep, new ArrayList<>());
        inactiveDrones.addAll(freeDrones);
    }

    private void setUp() {
        inactiveDrones.addAll(drones);

        for (final CustomerOrder customer : orders) {
            for (final ItemType itemType : customer.getContents()) {
                final Warehouse warehouse = findNearestWarehouseForItemType(customer.getPosition(), itemType);
                final int warehouseAmount = warehouse.getStorage().get(itemType);
                final DispatchOrder dispatchOrder = new DispatchOrder(
                        warehouse, itemType, 1, customer
                );

                final List<DispatchOrder> warehouseOrders = dispatchOrders.getOrDefault(warehouse, new ArrayList<>());
                warehouseOrders.add(dispatchOrder);
                dispatchOrders.put(warehouse, warehouseOrders);

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
                        (w1, w2) -> -Double.compare(position.distanceTo(w1.getPosition()), position.distanceTo(w2.getPosition()))
                )
                .findFirst()
                .get();
    }

}
