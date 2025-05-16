package entities;

import Enums.FurnitureStateValues;
import IDGenerator.IDGenerator;
import OSPAnimator.AnimImageItem;
import OSPAnimator.AnimShape;
import OSPAnimator.AnimShapeItem;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class Furniture extends Entity {
    private final int id;
    private final Order order;
    private final int type;
    private int state;
    private WorkPlace workPlace;
    private boolean isDone = false;
    private double totalTime = -1;
    private double startTime;
    private double startTimeCuttingQueue;
    private double endTimeCuttingQueue;
    private double startTimeStainingQueue;
    private double endTimeStainingQueue;
    private double startTimePaintingQueue;
    private double endTimePaintingQueue;
    private double startTimeAssemblyQueue;
    private double endTimeAssemblyQueue;
    private double startTimeMontageQueue;
    private double endTimeMontageQueue;

    private double startTimeCutting = -1;
    private double endTimeCutting = -1;
    private double startTimeStaining = -1;
    private double endTimeStaining = -1;
    private double startTimePainting = -1;
    private double endTimePainting = -1;
    private double startTimeAssembly = -1;
    private double endTimeAssembly = -1;
    private double startTimeMontage = -1;
    private double endTimeMontage = -1;

    private Worker workerForCutting;
    private Worker workerForAssembly;
    private Worker workerForMontage;
    private Worker workerForPainting;
    private Worker workerForStaining;
    private Worker workerForDrying;


    public Furniture(int type, Order order) {
        this.id = IDGenerator.getInstance().getNextFurnitureId();
        this.type = type;
        this.order = order;
        this.workPlace = null;
        this.startTime = order.getArrivalTime();
        //initAnimationObject();
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
        if (this.workPlace != null) {
            this.workPlace.setActivity(FurnitureStateValues.getNameByValue(state));
        }
    }
    @Override
    public void initAnimationObject() {
        if (animImageItem != null) {
            return;
        }

        AnimImageItem imageItem = new AnimImageItem();

        String imagePath = switch (type) {
            case 1 -> "src/main/resources/images/table.png";
            case 2 -> "src/main/resources/images/chair.png";
            case 3 -> "src/main/resources/images/wardrobe.png";
            default -> null;
        };

        imageItem.setImage(imagePath);
        imageItem.setImageSize(30, 30);

        String tooltip = "Furniture ID: " + id + "\n" +
                "Type: " + type + "\n" +
                "State: " + FurnitureStateValues.getNameByValue(state) + "\n" +
                "Order ID: " + (order != null ? order.getId() : "None") + "\n" +
                "WorkPlace ID: " + (workPlace != null ? workPlace.getId() : "None");

        imageItem.setToolTip(tooltip);
        this.animImageItem = imageItem;
    }



    public double getStartTimeCuttingQueue() {
        return startTimeCuttingQueue;
    }

    public void setStartTimeCuttingQueue(double startTimeCuttingQueue) {
        this.startTimeCuttingQueue = startTimeCuttingQueue;
    }

    public void setIsDone(double endTime) {
        this.isDone = true;
        this.state = FurnitureStateValues.FURNITURE_DONE.getValue();
        this.totalTime = endTime - this.startTime;
    }

    public double getEndTimeCuttingQueue() {
        return endTimeCuttingQueue;
    }

    public void setEndTimeCuttingQueue(double endTimeCuttingQueue) {
        this.endTimeCuttingQueue = endTimeCuttingQueue;
    }

    public double getStartTimeStainingQueue() {
        return startTimeStainingQueue;
    }

    public void setStartTimeStainingQueue(double startTimeStainingQueue) {
        this.startTimeStainingQueue = startTimeStainingQueue;
    }

    public double getEndTimeStainingQueue() {
        return endTimeStainingQueue;
    }

    public void setEndTimeStainingQueue(double endTimeStainingQueue) {
        this.endTimeStainingQueue = endTimeStainingQueue;
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
        if (workPlace != null) {
            this.workPlace = workPlace;
            this.workPlace.setFurniture(this);
        } else {
            this.workPlace = null;
        }

    }

    public boolean isDone() {
        return isDone;
    }


    public double getStartTimePaintingQueue() {
        return startTimePaintingQueue;
    }

    public void setStartTimePaintingQueue(double startTimePaintingQueue) {
        this.startTimePaintingQueue = startTimePaintingQueue;
    }

    public double getEndTimePaintingQueue() {
        return endTimePaintingQueue;
    }

    public void setEndTimePaintingQueue(double endTimePaintingQueue) {
        this.endTimePaintingQueue = endTimePaintingQueue;
    }

    public double getStartTimeCutting() {
        return startTimeCutting;
    }

    public void setStartTimeCutting(double startTimeCutting) {
        this.startTimeCutting = startTimeCutting;
    }

    public double getEndTimeCutting() {
        return endTimeCutting;
    }

    public void setEndTimeCutting(double endTimeCutting) {
        this.endTimeCutting = endTimeCutting;
    }

    public double getStartTimeStaining() {
        return startTimeStaining;
    }

    public void setStartTimeStaining(double startTimeStaining) {
        this.startTimeStaining = startTimeStaining;
    }

    public double getEndTimeStaining() {
        return endTimeStaining;
    }

    public void setEndTimeStaining(double endTimeStaining) {
        this.endTimeStaining = endTimeStaining;
    }

    public double getStartTimePainting() {
        return startTimePainting;
    }

    public void setStartTimePainting(double startTimePainting) {
        this.startTimePainting = startTimePainting;
    }

    public double getEndTimePainting() {
        return endTimePainting;
    }

    public void setEndTimePainting(double endTimePainting) {
        this.endTimePainting = endTimePainting;
    }

    public double getStartTimeAssembly() {
        return startTimeAssembly;
    }

    public void setStartTimeAssembly(double startTimeAssembly) {
        this.startTimeAssembly = startTimeAssembly;
    }

    public double getEndTimeAssembly() {
        return endTimeAssembly;
    }

    public void setEndTimeAssembly(double endTimeAssembly) {
        this.endTimeAssembly = endTimeAssembly;
    }

    public double getStartTimeMontage() {
        return startTimeMontage;
    }

    public void setStartTimeMontage(double startTimeMontage) {
        this.startTimeMontage = startTimeMontage;
    }

    public double getEndTimeMontage() {
        return endTimeMontage;
    }

    public void setEndTimeMontage(double endTimeMontage) {
        this.endTimeMontage = endTimeMontage;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }

    public Worker getWorkerForCutting() {
        return workerForCutting;
    }

    public void setWorkerForCutting(Worker workerForCutting) {
        this.workerForCutting = workerForCutting;
    }

    public Worker getWorkerForAssembly() {
        return workerForAssembly;
    }

    public void setWorkerForAssembly(Worker workerForAssembly) {
        this.workerForAssembly = workerForAssembly;
    }

    public Worker getWorkerForMontage() {
        return workerForMontage;
    }

    public void setWorkerForMontage(Worker workerForMontage) {
        this.workerForMontage = workerForMontage;
    }

    public Worker getWorkerForPainting() {
        return workerForPainting;
    }

    public void setWorkerForPainting(Worker workerForPainting) {
        this.workerForPainting = workerForPainting;
    }

    public Worker getWorkerForStaining() {
        return workerForStaining;
    }

    public void setWorkerForStaining(Worker workerForStaining) {
        this.workerForStaining = workerForStaining;
    }


    public Worker getWorkerForDrying() {
        return workerForDrying;
    }

    public void setWorkerForDrying(Worker workerForDrying) {
        this.workerForDrying = workerForDrying;
    }
}
