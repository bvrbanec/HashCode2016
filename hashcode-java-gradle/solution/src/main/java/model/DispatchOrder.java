package model;

public class DispatchOrder {

    private final Warehouse origin;

    private final ItemType itemType;

    private final int amount;

    private final int destination;


    public DispatchOrder(Warehouse origin, ItemType itemType, int amount, int destination) {
        this.origin = origin;
        this.itemType = itemType;
        this.amount = amount;
        this.destination = destination;
    }

    public Warehouse getOrigin() {
        return origin;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public int getAmount() {
        return amount;
    }

    public int getDestination() {
        return destination;
    }
}
