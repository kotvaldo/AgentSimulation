package agents.agentmodelu;

import OSPABA.*;
import entities.Order;
import simulation.*;

//meta! id="1"
public class ManagerModelu extends Manager
{
	public ManagerModelu(int id, Simulation mySim, Agent myAgent)
	{
		super(id, mySim, myAgent);
		init();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();

		if (petriNet() != null)
		{
			petriNet().clear();
		}

		MessageForm msgOkolie = new MyMessage(_mySim);
		msgOkolie.setCode(Mc.init);
		msgOkolie.setAddressee(_mySim.findAgent(Id.agentOkolia));
		notice(msgOkolie);

	}

	//meta! sender="AgentNabytku", id="153", type="Notice"
	public void processNoticeHotovaObjednavka(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		MySimulation mySimulation = (MySimulation) mySim();
		Order order	= msg.getOrder();
		mySimulation.getFinishedOrders().add(order);
		order.setEndTime(mySimulation.currentTime());
	}

	//meta! sender="AgentOkolia", id="328", type="Notice"
	public void processNoticePrichodObjednavky(MessageForm message)
	{
		MyMessage myMessage = (MyMessage) message.createCopy();
		myMessage.setAddressee(mySim().findAgent(Id.agentModelu));
		myMessage.setCode(Mc.noticeSpracujObjednavku);
		notice(myMessage);
	}

	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
		}
	}

	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
			case Mc.noticeHotovaObjednavka:
				processNoticeHotovaObjednavka(message);
				break;

			case Mc.noticePrichodObjednavky:
				processNoticePrichodObjednavky(message);
				break;
			case Mc.init:
				init();
			default:
				processDefault(message);
				break;
		}
	}

	public void init()
	{
	}

	@Override
	public AgentModelu myAgent()
	{
		return (AgentModelu) super.myAgent();
	}
}
