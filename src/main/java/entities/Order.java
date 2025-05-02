package entities;


import Enums.OrderStateValues;
import IDGenerator.IDGenerator;
import Statistics.Average;

import java.util.ArrayList;

public class Order {
    private final int id;
    private int state;
    private final double arrivalTime;
    private double endTime;
    private double timeOfWork = -1;
    private ArrayList<Furniture> furnitureList;
    private final Average timeOfWorkAverage;




    public Order(double arrivalTime, Average timeOfWorkAverage) {
        this.id = IDGenerator.getInstance().getNextOrderId();
        this.arrivalTime = arrivalTime;
        furnitureList = new ArrayList<>();
        this.state = OrderStateValues.ORDER_NEW.getValue();
        this.endTime = 0.0;
        this.timeOfWorkAverage = timeOfWorkAverage;
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    private boolean isOrderFinished(Furniture furniture) {
        for (Furniture f : furniture.getOrder().getFurnitureList()) {
            if (!f.isDone()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Order order = (Order) obj;
        return id == order.id;
    }

    public void addFurniture(Furniture furniture) {
        furnitureList.add(furniture);
    }
    public double getArrivalTime() {
        return arrivalTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
        this.timeOfWork = endTime - arrivalTime;
        this.state = OrderStateValues.ORDER_DONE.getValue();
        this.timeOfWorkAverage.add(timeOfWork);
    }

    public double getTimeOfWork() {
        return timeOfWork;
    }

    public ArrayList<Furniture> getFurnitureList() {
        return furnitureList;
    }

    public void setFurnitureList(ArrayList<Furniture> furnitureList) {
        this.furnitureList = furnitureList;
    }
}
