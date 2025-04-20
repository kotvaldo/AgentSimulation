package agents.agentpracovnikovb;

import OSPABA.*;
import simulation.*;

//meta! id="229"
public class ManagerPracovnikovB extends OSPABA.Manager
{
	public ManagerPracovnikovB(int id, Simulation mySim, Agent myAgent)
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

	//meta! sender="AgentPracovnikov", id="246", type="Response"
	public void processRVyberPracovnikaB(MessageForm message)
	{
	}

	//meta! sender="AgentPracovnikov", id="240", type="Notice"
	public void processNoticeUvolniB(MessageForm message)
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
		case Mc.rVyberPracovnikaB:
			processRVyberPracovnikaB(message);
		break;

		case Mc.noticeUvolniB:
			processNoticeUvolniB(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public AgentPracovnikovB myAgent()
	{
		return (AgentPracovnikovB)super.myAgent();
	}

}
