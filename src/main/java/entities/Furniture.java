package entities;

import Enums.FurnitureStateValues;
import IDGenerator.IDGenerator;

public class Furniture {
    private final int id;
    private final Order order;
    private final int type;
    private int state;
    private WorkPlace workPlace;
    private boolean isDone = false;

    private double startTimeCuttingQueue;
    private double endTimeCuttingQueue;
    private double startTimeColoringQueue;
    private double endTimeColoringQueue;
    private double startTimeAssemblyQueue;
    private double endTimeAssemblyQueue;
    private double startTimeMontageQueue;
    private double endTimeMontageQueue;

    public Furniture(int type, Order order) {
        this.id = IDGenerator.getInstance().getNextFurnitureId();
        this.type = type;
        this.order = order;
        this.workPlace = null;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public double getStartTimeCuttingQueue() {
        return startTimeCuttingQueue;
    }

    public void setStartTimeCuttingQueue(double startTimeCuttingQueue) {
        this.startTimeCuttingQueue = startTimeCuttingQueue;
    }
    public void setIsDone() {
        this.isDone = true;
        this.state = FurnitureStateValues.FURNITURE_DONE.getValue();
    }
    public double getEndTimeCuttingQueue() {
        return endTimeCuttingQueue;
    }

    public void setEndTimeCuttingQueue(double endTimeCuttingQueue) {
        this.endTimeCuttingQueue = endTimeCuttingQueue;
    }

    public double getStartTimeColoringQueue() {
        return startTimeColoringQueue;
    }

    public void setStartTimeColoringQueue(double startTimeColoringQueue) {
        this.startTimeColoringQueue = startTimeColoringQueue;
    }

    public double getEndTimeColoringQueue() {
        return endTimeColoringQueue;
    }

    public void setEndTimeColoringQueue(double endTimeColoringQueue) {
        this.endTimeColoringQueue = endTimeColoringQueue;
    }

    public double getStartTimeAssemblyQueue() {
        return startTimeAssemblyQueue;
    }

    public void setStartTimeAssemblyQueue(double startTimeAssemblyQueue) {
        this.startTimeAssemblyQueue = startTimeAssemblyQueue;
    }

    public double getEndTimeAssemblyQueue() {
        return endTimeAssemblyQueue;
    }

    public void setEndTimeAssemblyQueue(double endTimeAssemblyQueue) {
        this.endTimeAssemblyQueue = endTimeAssemblyQueue;
    }

    public double getStartTimeMontageQueue() {
        return startTimeMontageQueue;
    }

    public void setStartTimeMontageQueue(double startTimeMontageQueue) {
        this.startTimeMontageQueue = startTimeMontageQueue;
    }

    public double getEndTimeMontageQueue() {
        return endTimeMontageQueue;
    }

    public void setEndTimeMontageQueue(double endTimeMontageQueue) {
        this.endTimeMontageQueue = endTimeMontageQueue;
    }

    public WorkPlace getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(WorkPlace workPlace) {
        this.workPlace = workPlace;
    }

    public boolean isDone() {
        return isDone;
    }


}
