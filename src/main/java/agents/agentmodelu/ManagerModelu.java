package agents.agentmodelu;

import OSPABA.*;
import simulation.*;

//meta! id="1"
public class ManagerModelu extends OSPABA.Manager
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
		// Setup component for the next replication

		if (petriNet() != null)
		{
			petriNet().clear();
		}
	}

	//meta! sender="AgentOkolia", id="328", type="Notice"
	public void processNovaObjednavka(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="153", type="Notice"
	public void processHotovaObjednavka(MessageForm message)
	{
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
		}
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
		case Mc.hotovaObjednavka:
			processHotovaObjednavka(message);
		break;

		case Mc.novaObjednavka:
			processNovaObjednavka(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public AgentModelu myAgent()
	{
		return (AgentModelu)super.myAgent();
	}

}
