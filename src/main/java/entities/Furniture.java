package entities;

import IDGenerator.IDGenerator;

public class Furniture {
    private final int id;
    private final Order order;
    private final int type;

    private double startTimeCuttingQueue;

    public Furniture(int type, Order order) {
        this.id = IDGenerator.getInstance().getNextFurnitureId();
        this.type = type;
        this.order = order;
    }

    public int getType() {
        return type;
    }

    public Order getOrder() {
        return order;
    }

    public int getId() {
        return id;
    }
}
