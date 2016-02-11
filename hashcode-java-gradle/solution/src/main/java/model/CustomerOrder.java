package model;

import java.util.HashMap;
import java.util.Map;

public class CustomerOrder {

    private final int id;

    private final Position position;

    private final Map<ItemType, Integer> contents = new HashMap<>();

    public CustomerOrder(int id, Position position) {
        this.id = id;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public Position getPosition() {
        return position;
    }

    public Map<ItemType, Integer> getContents() {
        return contents;
    }
}
