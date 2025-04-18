package agents.agentpracovnikovc;

import OSPABA.*;
import simulation.*;

//meta! id="230"
public class ManagerPracovnikovC extends OSPABA.Manager
{
	public ManagerPracovnikovC(int id, Simulation mySim, Agent myAgent)
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

	//meta! sender="AgentPracovnikov", id="248", type="Response"
	public void processRVyberPracovnikaC(MessageForm message)
	{
	}

	//meta! sender="AgentPracovnikov", id="241", type="Notice"
	public void processUvolniPracovnikaC(MessageForm message)
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
		case Mc.uvolniPracovnikaC:
			processUvolniPracovnikaC(message);
		break;

		case Mc.rVyberPracovnikaC:
			processRVyberPracovnikaC(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public AgentPracovnikovC myAgent()
	{
		return (AgentPracovnikovC)super.myAgent();
	}

}
