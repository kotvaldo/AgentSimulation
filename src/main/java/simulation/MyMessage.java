package simulation;

import OSPABA.*;
import entities.Furniture;
import entities.Order;
import entities.WorkPlace;

public class MyMessage extends OSPABA.MessageForm
{
	private WorkPlace workPlace;
	private Furniture furniture;
	private Order order;



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
		// Copy attributes
	}
}
