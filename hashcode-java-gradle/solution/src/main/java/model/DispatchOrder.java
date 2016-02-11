package model;

public class DispatchOrder {

    private final Warehouse origin;

    private final ItemType itemType;

    private final int amount;

    private final CustomerOrder destination;


    public DispatchOrder(Warehouse origin, ItemType itemType, int amount, CustomerOrder destination) {
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

    public CustomerOrder getDestination() {
        return destination;
    }
}
