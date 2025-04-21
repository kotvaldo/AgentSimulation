package entities;

import IDGenerator.IDGenerator;

public class WorkPlace {
    private final int id;
    private Furniture furniture;
    private boolean isBusy;
    private Worker worker;
    private String activity = "Nothing";

    public WorkPlace() {
        this.id = IDGenerator.getInstance().getNextWorkplaceId();
        isBusy = false;
        furniture = null;
        worker = null;
    }


    public Furniture getFurniture() {
        return furniture;

    }

    public void setFurniture(Furniture furniture) {
        this.furniture = furniture;
        this.isBusy = furniture != null;
    }

    public boolean isBusy() {
        return this.isBusy;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        WorkPlace workplace = (WorkPlace) obj;
        return id == workplace.id;
    }

    public void setBusy(boolean busy) {
        if(!busy) {
            this.furniture = null;
        }
        isBusy = busy;
    }

    public int getId() {
        return id;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
