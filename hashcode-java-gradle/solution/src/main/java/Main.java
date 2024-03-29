import model.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        int rows, cols, numOfDrones, deadline, maxLoad;

        BufferedReader br = new BufferedReader(new FileReader("busy_day.in"));

        // Simulation information
        String line = br.readLine();
        String[] split = line.split(" ");

        rows = new Integer(split[0]);
        cols = new Integer(split[1]);
        numOfDrones = new Integer(split[2]);
        deadline = new Integer(split[3]);
        maxLoad = new Integer(split[4]);


        // Product types
        int numOfProdTypes = new Integer(br.readLine());

        List<ItemType> itemTypes = new ArrayList<>();
        line = br.readLine();
        split = line.split(" ");
        for(int i = 0; i < split.length; i++) {
            itemTypes.add(new ItemType(new Integer(split[i]), i));
        }


        // Warehouses
        int numOfWarehouses = new Integer(br.readLine());

        List<Warehouse> warehouses = new ArrayList<>();
        for(int i = 0; i < numOfWarehouses; i++) {
            // read position
            line = br.readLine();
            split = line.split(" ");
            Position position = new Position(new Integer(split[0]) , new Integer(split[1]));

            Warehouse warehouse = new Warehouse(i, position);

            // read items
            Map<ItemType, Integer> warehouseProducts = new HashMap<>();
            line = br.readLine();
            split = line.split(" ");
            for(int productIndex = 0; productIndex < numOfProdTypes; productIndex++) {
                warehouseProducts.put(itemTypes.get(productIndex), new Integer(split[productIndex]));
            }
            warehouse.setStorage(warehouseProducts);
            warehouses.add(warehouse);
        }

        // Orders
        int numOfOrders = new Integer(br.readLine());
        List<CustomerOrder> orders = new ArrayList<>();
        for(int i = 0; i < numOfOrders; i++) {
            line = br.readLine();
            split = line.split(" ");
            Position orderPosition = new Position(new Integer(split[0]), new Integer(split[1]));

            //irrelevant - number of items
            line = br.readLine();

            // order items
            List<ItemType> itemOrders = new ArrayList<>();
            line = br.readLine();
            split = line.split(" ");
            for(int item = 0; item < split.length; item++) {
                itemOrders.add(itemTypes.get(new Integer(split[item])));
            }

            orders.add(new CustomerOrder(i, orderPosition, itemOrders));


        }

        // Call simulation
        Area area = new Area(rows, cols);

        List<Drone> drones = new ArrayList<>();
        for(int i = 0; i < numOfDrones; i++) {
            drones.add(new Drone(i, new Position(0, 0)));
        }

        Simulation simulation = new Simulation(area, deadline, maxLoad, drones, warehouses, orders);
        simulation.run();


    }

}
