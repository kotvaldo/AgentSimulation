package simulation;

import OSPABA.*;
import OSPAnimator.AnimItem;
import entities.*;

public class MyMessage extends MessageForm {
	private Order order;
	private Furniture furniture;
	private Worker workerForRelease;
	private AnimItem animItem;

	public MyMessage(Simulation mySim) {
		super(mySim);
	}

	public MyMessage(MyMessage original) {
		super(original);
	}

	@Override
	public MessageForm createCopy() {
		return new MyMessage(this);
	}

	@Override
	protected void copy(MessageForm message) {
		super.copy(message);
		MyMessage original = (MyMessage) message;
		this.order = original.getOrder();
		this.furniture = original.getFurniture();
		this.workerForRelease = original.getWorkerForRelease();
		this.animItem = original.getAnimItem();
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Furniture getFurniture() {
		return furniture;
	}

	public void setFurniture(Furniture furniture) {
		this.furniture = furniture;
	}

	// Delegované workeri cez furniture
	public Worker getWorkerForCutting() {
		return furniture != null ? furniture.getWorkerForCutting() : null;
	}

	public void setWorkerForCutting(Worker worker) {
		if (furniture != null) furniture.setWorkerForCutting(worker);
	}

	public Worker getWorkerForAssembly() {
		return furniture != null ? furniture.getWorkerForAssembly() : null;
	}

	public void setWorkerForAssembly(Worker worker) {
		if (furniture != null) furniture.setWorkerForAssembly(worker);
	}

	public Worker getWorkerForMontage() {
		return furniture != null ? furniture.getWorkerForMontage() : null;
	}

	public void setWorkerForMontage(Worker worker) {
		if (furniture != null) furniture.setWorkerForMontage(worker);
	}

	public Worker getWorkerForPainting() {
		return furniture != null ? furniture.getWorkerForPainting() : null;
	}

	public void setWorkerForPainting(Worker worker) {
		if (furniture != null) furniture.setWorkerForPainting(worker);
	}

	public Worker getWorkerForStaining() {
		return furniture != null ? furniture.getWorkerForStaining() : null;
	}

	public void setWorkerForStaining(Worker worker) {
		if (furniture != null) furniture.setWorkerForStaining(worker);
	}

	public WorkPlace getWorkPlace() {
		return furniture != null ? furniture.getWorkPlace() : null;
	}

	public void setWorkPlace(WorkPlace workPlace) {
		if (furniture != null) furniture.setWorkPlace(workPlace);
	}
	public Worker getAssignedWorker() {
		if (getWorkerForCutting() != null) return getWorkerForCutting();
		if (getWorkerForAssembly() != null) return getWorkerForAssembly();
		if (getWorkerForMontage() != null) return getWorkerForMontage();
		if (getWorkerForPainting() != null) return getWorkerForPainting();
		if (getWorkerForStaining() != null) return getWorkerForStaining();
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;

		MyMessage other = (MyMessage) obj;

		if (this.furniture == null || other.furniture == null) return false;

		return this.furniture.getId() == other.furniture.getId();
	}

	@Override
	public String toString() {
		return "MyMessage{" +
				"furniture=" + (furniture != null ? furniture.getId() : "null") +
				", code=" + code() +
				", workerA=" + getWorkerForCutting() +
				", order=" + getOrder() +
				", sender=" + sender() +
				", addressee=" + addressee() +
				", time=" + mySim().currentTime() +
				'}';
	}

    public Worker getWorkerForRelease() {
        return workerForRelease;
    }

    public void setWorkerForRelease(Worker workerForRelease) {
        this.workerForRelease = workerForRelease;
    }

    public AnimItem getAnimItem() {
        return animItem;
    }

    public void setAnimItem(AnimItem animItem) {
        this.animItem = animItem;
    }
}
