package agents.agentokolia;

import Enums.OrderStateValues;
import OSPABA.*;
import entities.*;
import simulation.*;

//meta! id="2"
public class ManagerOkolia extends OSPABA.Manager
{
	public ManagerOkolia(int id, Simulation mySim, Agent myAgent)
	{
		super(id, mySim, myAgent);
		init();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication

		if (petriNet() != null)
		{
			petriNet().clear();
		}
	}

	//meta! sender="AgentModelu", id="20", type="Notice"
	public void processInit(MessageForm message)
	{
		MyMessage msg = (MyMessage)message.createCopy();
		msg.setAddressee(_myAgent.findAssistant(Id.planovacPrichodObjednavky));
		startContinualAssistant(new MyMessage(msg));
	}

	//meta! sender="PlanovacPrichodObjednavky", id="302", type="Finish"
	public void processFinish(MessageForm message)
	{
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		MyMessage msg = (MyMessage)message.createCopy();
		MySimulation mySimulation = (MySimulation) mySim();
		Order order	= new Order(mySimulation.currentTime(), mySimulation.getPartialTimeOfWork());
		order.setState(OrderStateValues.ORDER_NEW.getValue());
		mySimulation.getOrderArrayList().add(order);
		int countOfFurniture = mySimulation.getGenerators().getCountOfFurnitureDist().sample();
		for (int i = 0; i < countOfFurniture; i++) {
			int type = mySimulation.getGenerators().getTypeOfFurnitureDist().sample();
			if(type == 1) {
				Table table = new Table(order);
				order.addFurniture(table);
				mySimulation.getFurnitureArrayList().add(table);
			} else if( type == 2) {
				Chair chair = new Chair(order);
				order.addFurniture(chair);
				mySimulation.getFurnitureArrayList().add(chair);

			} else if( type == 3) {
				Wardrobe wardrobe = new Wardrobe(order);
				order.addFurniture(wardrobe);
				mySimulation.getFurnitureArrayList().add(wardrobe);
			}
		}
		msg.setOrder(order);
		msg.setAddressee(mySimulation.findAgent(Id.agentModelu));
		msg.setCode(Mc.noticePrichodObjednavky);

		notice(msg);

		MyMessage newMsg = new MyMessage(mySimulation);
		newMsg.setAddressee(_myAgent.findAssistant(Id.planovacPrichodObjednavky));
		startContinualAssistant(newMsg);

	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	public void init()
	{
	}

	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
			case Mc.init:
				processInit(message);
				break;

			case Mc.finish:
				processFinish(message);
				break;

			default:
				processDefault(message);
				break;
		}
	}
	//meta! tag="end"

	@Override
	public AgentOkolia myAgent()
	{
		return (AgentOkolia)super.myAgent();
	}

}
