package entities;

import Enums.WorkPlaceStateValues;
import IDGenerator.IDGenerator;
import OSPAnimator.AnimImageItem;

public class WorkPlace extends Entity {
    private final int id;
    private Furniture furniture;
    private Worker actualWorkingWorker;
    private String activity = "Nothing";
    private int state;

    public WorkPlace() {
        this.id = IDGenerator.getInstance().getNextWorkplaceId();
        this.state = WorkPlaceStateValues.NOT_WORKING.getValue();
        this.furniture = null;
        this.actualWorkingWorker = null;
    }

    public Furniture getFurniture() {
        return furniture;
    }

    public void setFurniture(Furniture furniture) {
        this.furniture = furniture;
        if (furniture != null) {
            this.state = WorkPlaceStateValues.ASSIGNED.getValue();
        } else if (actualWorkingWorker == null) {
            this.state = WorkPlaceStateValues.NOT_WORKING.getValue();
            this.activity = "Nothing";
        }
        updateToolTip();
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

    public Worker getActualWorkingWorker() {
        return actualWorkingWorker;
    }

    public void setActualWorkingWorker(Worker actualWorkingWorker) {
        this.actualWorkingWorker = actualWorkingWorker;
        if (actualWorkingWorker != null && furniture != null) {
            this.state = WorkPlaceStateValues.WORKING.getValue();
        } else if (actualWorkingWorker != null) {
            this.state = WorkPlaceStateValues.ASSIGNED.getValue();
        } else if (furniture == null) {
            this.state = WorkPlaceStateValues.NOT_WORKING.getValue();
        }
        updateToolTip();
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
        updateToolTip();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        WorkPlace workplace = (WorkPlace) obj;
        return id == workplace.id;
    }
    public void clear() {
        this.furniture = null;
        this.actualWorkingWorker = null;
        this.activity = "Nothing";
        this.state = WorkPlaceStateValues.NOT_WORKING.getValue();
    }

    @Override
    public void initAnimationObject() {
        if (animImageItem != null) {
            return;
        }
        AnimImageItem shapeItem = new AnimImageItem("src/main/resources/images/crafting_table.jpg", 512, 512);

        shapeItem.setImageSize(30, 30);
        String tooltip = "WorkPlace ID: " + id + "\n" +
                "State: " + state + "\n" +
                "Activity: " + activity + "\n" +
                "Furniture ID: " + (furniture != null ? furniture.getId() : "None") + "\n" +
                "Worker ID: " + (actualWorkingWorker != null ? actualWorkingWorker.getId() : "None");

        shapeItem.setToolTip(tooltip);
        this.animImageItem = shapeItem;

    }
    public void updateToolTip() {
        if (animImageItem == null) return;

        String tooltip = "WorkPlace ID: " + id + "\n" +
                "State: " + state + "\n" +
                "Activity: " + activity + "\n" +
                "Furniture ID: " + (furniture != null ? furniture.getId() : "None") + "\n" +
                "Worker ID: " + (actualWorkingWorker != null ? actualWorkingWorker.getId() : "None");

        animImageItem.setToolTip(tooltip);
    }







}
