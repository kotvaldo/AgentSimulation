package entities;

import Enums.WorkerBussyState;
import IDGenerator.IDGenerator;
import Statistics.Utilisation;

public class Worker {
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

    public void setState(int state) {
        this.state = state;
        if (state == 0) {
            furniture = null;
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
}
