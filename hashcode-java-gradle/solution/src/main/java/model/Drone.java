package model;

import java.util.HashMap;
import java.util.Map;

public class Drone {

    private final int id;
    private Position position;
    private final Map<ItemType, Integer> cargo = new HashMap<>();

    public Drone(int id, Position position) {
        this.id = id;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Map<ItemType, Integer> getCargo() {
        return cargo;
    }

}
