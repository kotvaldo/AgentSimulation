package agents.agentpracovnikova;

import OSPABA.*;
import simulation.*;

//meta! id="228"
public class ManagerPracovnikovA extends OSPABA.Manager
{
	public ManagerPracovnikovA(int id, Simulation mySim, Agent myAgent)
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

	//meta! sender="AgentPracovnikov", id="239", type="Notice"
	public void processUvolneniePracovnikaA(MessageForm message)
	{
	}

	//meta! sender="AgentPracovnikov", id="242", type="Response"
	public void processVyberPracovnikaA(MessageForm message)
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
		case Mc.uvolneniePracovnikaA:
			processUvolneniePracovnikaA(message);
		break;

		case Mc.vyberPracovnikaA:
			processVyberPracovnikaA(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public AgentPracovnikovA myAgent()
	{
		return (AgentPracovnikovA)super.myAgent();
	}

}
