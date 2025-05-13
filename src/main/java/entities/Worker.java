package entities;

import Enums.WorkerBussyState;
import IDGenerator.IDGenerator;
import OSPAnimator.AnimShapeItem;
import Statistics.Utilisation;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Worker extends Entity {
    private final int id;
    private int state;
    private final String type;
    private Furniture furniture;
    private WorkPlace currentWorkPlace;
    protected final Utilisation utilisation = new Utilisation();

    public Worker(String type) {
        this.id = IDGenerator.getInstance().getNextPersonId();
        this.type = type;
        state = 0;
        furniture = null;
        currentWorkPlace = null;
    }

    public int getState() {
        return state;
    }

    public void setState(int newState, double currentTime) {
        int oldMappedValue = this.state == WorkerBussyState.NON_BUSY.getValue() ? 0 : 1;
        int newMappedValue = newState == WorkerBussyState.NON_BUSY.getValue() ? 0 : 1;

        if (this.state != newState) {
            if (oldMappedValue != newMappedValue) {
                utilisation.recordChange(currentTime, newMappedValue);
            }

            this.state = newState;

            if (newState == WorkerBussyState.NON_BUSY.getValue()) {
                furniture = null;
            }
        }
    }


    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Furniture getFurniture() {
        return furniture;
    }

    public void setFurniture(Furniture furnitureID, double currentTime) {
        this.furniture = furnitureID;
    }


    public WorkPlace getCurrentWorkPlace() {
        return currentWorkPlace;
    }

    public void setCurrentWorkPlace(WorkPlace currentWorkPlace) {
        this.currentWorkPlace = currentWorkPlace;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Worker worker = (Worker) obj;
        return id == worker.id;
    }

    public Utilisation getUtilisation() {
        return utilisation;
    }

    public void clear() {
        furniture = null;
        currentWorkPlace = null;
        state = WorkerBussyState.NON_BUSY.getValue();
        utilisation.clear();

    }


    @Override
    public void initAnimationObject() {
        if (animShapeItem != null) {
            return;
        }

        AnimShapeItem shapeItem = new AnimShapeItem();
        assignRandomPosition();
        // shapeItem.setPosition(currPosition); // odkomentuj, ak máš definovanú pozíciu

        Shape shape = new Ellipse2D.Double(0, 0, 15, 15);
        shapeItem.setShape(shape);


        Color color = switch (type) {
            case "A" -> Color.RED;
            case "B" -> Color.GREEN;
            case "C" -> Color.BLUE;
            default -> Color.GRAY;
        };
        shapeItem.setColor(color);

        this.animShapeItem = shapeItem;
    }




}
