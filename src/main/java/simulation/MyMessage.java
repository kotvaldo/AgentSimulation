package simulation;

import OSPABA.*;
import entities.*;

public class MyMessage extends OSPABA.MessageForm
{
	private Order order;
	private Furniture furniture;
	private WorkPlace workPlace;
	private WorkerA workerA;
	private WorkerB workerB;
	private WorkerC workerC;
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
		workerA = original.getWorkerA();
		workerB = original.getWorkerB();
		workerC = original.getWorkerC();
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

	public WorkerA getWorkerA() {
		return workerA;
	}

	public void setWorkerA(WorkerA workerA) {
		this.workerA = workerA;
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

    public WorkerB getWorkerB() {
        return workerB;
    }

    public void setWorkerB(WorkerB workerB) {
        this.workerB = workerB;
    }

    public WorkerC getWorkerC() {
        return workerC;
    }

    public void setWorkerC(WorkerC workerC) {
        this.workerC = workerC;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}