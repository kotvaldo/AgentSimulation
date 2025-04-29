package simulation;

import OSPABA.*;
import entities.*;

public class MyMessage extends MessageForm
{
	private Order order;
	private Furniture furniture;
	private WorkPlace workPlace;
	private Worker workerForCutting;
	private Worker workerForAssembly;
	private Worker workerForMontage;
	private Worker workerForPainting;
	private Worker workerForStaining;
	private Integer type = null;

	public MyMessage(Simulation mySim)
	{
		super(mySim);
	}

	public MyMessage(MyMessage original)
	{
		super(original);
		// copy() is called in superclass
	}

	@Override
	public MessageForm createCopy()
	{
		return new MyMessage(this);
	}

	@Override
	protected void copy(MessageForm message)
	{
		super.copy(message);
		MyMessage original = (MyMessage)message;
		workPlace = original.getWorkPlace();
		workerForCutting = original.getWorkerForCutting();
		workerForAssembly = original.getWorkerForAssembly();
		workerForMontage = original.getWorkerForMontage();
		order = original.getOrder();
		furniture = original.getFurniture();
		type = original.getType();

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

	public WorkPlace getWorkPlace() {
		return workPlace;
	}

	public void setWorkPlace(WorkPlace workPlace) {
		this.workPlace = workPlace;
	}

	public Worker getWorkerForCutting() {
		return workerForCutting;
	}

	public void setWorkerForCutting(Worker workerForCutting) {
		this.workerForCutting = workerForCutting;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;

		MyMessage other = (MyMessage) obj;

		if (this.getFurniture() == null || other.getFurniture() == null)
			return false;

		return this.getFurniture().getId() == other.getFurniture().getId();
	}

    public Worker getWorkerForAssembly() {
        return workerForAssembly;
    }

    public void setWorkerForAssembly(WorkerB workerForAssembly) {
        this.workerForAssembly = workerForAssembly;
    }

    public Worker getWorkerForMontage() {
        return workerForMontage;
    }

    public void setWorkerForMontage(WorkerC workerForMontage) {
        this.workerForMontage = workerForMontage;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
}