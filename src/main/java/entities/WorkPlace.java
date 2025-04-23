package entities;

import Enums.WorkPlaceStateValues;
import IDGenerator.IDGenerator;

public class WorkPlace {
    private final int id;
    private Furniture furniture;
    private Worker worker;
    private String activity = "Nothing";
    private int state;

    public WorkPlace() {
        this.id = IDGenerator.getInstance().getNextWorkplaceId();
        this.state = WorkPlaceStateValues.NOT_WORKING.getValue();
        this.furniture = null;
        this.worker = null;
    }

    public Furniture getFurniture() {
        return furniture;
    }

    public void setFurniture(Furniture furniture) {
        this.furniture = furniture;
        if (furniture != null) {
            this.state = WorkPlaceStateValues.ASSIGNED.getValue();
        } else if (worker == null) {
            this.state = WorkPlaceStateValues.NOT_WORKING.getValue();
        }
    }

    public boolean isBusy() {
        return state == WorkPlaceStateValues.ASSIGNED.getValue()
                || state == WorkPlaceStateValues.WORKING.getValue();
    }

    public void setBusy(boolean busy) {
        if (!busy) {
            this.furniture = null;
            this.state = WorkPlaceStateValues.NOT_WORKING.getValue();
        } else {
            this.state = WorkPlaceStateValues.WORKING.getValue();
        }
    }

    public int getId() {
        return id;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
        if (worker != null && furniture != null) {
            this.state = WorkPlaceStateValues.WORKING.getValue();
        } else if (worker != null) {
            this.state = WorkPlaceStateValues.ASSIGNED.getValue();
        } else if (furniture == null) {
            this.state = WorkPlaceStateValues.NOT_WORKING.getValue();
        }
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        WorkPlace workplace = (WorkPlace) obj;
        return id == workplace.id;
    }
}
