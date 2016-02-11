package model;

public class ItemType {

    private final int id;

    private final int weight;


    public ItemType(int weight, int id) {
        this.weight = weight;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getWeight() {
        return weight;
    }
}
