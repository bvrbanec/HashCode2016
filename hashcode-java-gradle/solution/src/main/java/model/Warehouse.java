package model;

import java.util.HashMap;
import java.util.Map;

public class Warehouse {

    private final int id;

    private final Position position;

    private Map<ItemType, Integer> storage = new HashMap<>();


    public Warehouse(int id, Position position) {
        this.id = id;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public Position getPosition() {
        return position;
    }

    public Map<ItemType, Integer> getStorage() {
        return storage;
    }

    public void setStorage(Map<ItemType, Integer> storage) {
        this.storage = storage;
    }
}
