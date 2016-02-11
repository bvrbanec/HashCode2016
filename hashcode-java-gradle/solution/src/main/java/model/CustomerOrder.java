package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerOrder {

    private final int id;

    private final Position position;

    private final List<ItemType> contents = new ArrayList<>();

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

    public List<ItemType> getContents() {
        return contents;
    }
}
