package simulation;

import OSPABA.*;
import entities.Furniture;
import entities.Order;
import entities.WorkPlace;
import entities.Worker;

public class MyMessage extends OSPABA.MessageForm
{
	protected Order order;
	protected Furniture furniture;
	protected WorkPlace workPlace;
	protected Worker worker;

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
		worker = original.getWorker();
		order = original.getOrder();
		furniture = original.getFurniture();

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

	public Worker getWorker() {
		return worker;
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}
}