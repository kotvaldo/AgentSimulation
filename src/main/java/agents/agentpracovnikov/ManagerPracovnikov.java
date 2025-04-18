package agents.agentpracovnikov;

import OSPABA.*;
import simulation.*;

//meta! id="5"
public class ManagerPracovnikov extends OSPABA.Manager
{
	public ManagerPracovnikov(int id, Simulation mySim, Agent myAgent)
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

	//meta! sender="AgentNabytku", id="65", type="Notice"
	public void processInit(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="162", type="Response"
	public void processVyberPracovnikaMorenie(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="205", type="Notice"
	public void processUvolneniePracovnikaC(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="167", type="Response"
	public void processVyberPracovnikaMontaz(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="204", type="Notice"
	public void processUvolneniePracovnikaB(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="126", type="Notice"
	public void processUvolneniePracovnikaA(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="164", type="Response"
	public void processVyberPracovnikaLakovanie(MessageForm message)
	{
	}

	//meta! sender="AgentPracovnikovC", id="248", type="Request"
	public void processVyberPracovnikaC(MessageForm message)
	{
	}

	//meta! sender="AgentPracovnikovB", id="246", type="Request"
	public void processVyberPracovnikaB(MessageForm message)
	{
	}

	//meta! sender="AgentPracovnikovA", id="242", type="Request"
	public void processVyberPracovnikaA(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="168", type="Response"
	public void processVyberPracovnikaRezanie(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="90", type="Response"
	public void processVyberPracovnikaSkladanie(MessageForm message)
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
		case Mc.vyberPracovnikaMorenie:
			processVyberPracovnikaMorenie(message);
		break;

		case Mc.init:
			processInit(message);
		break;

		case Mc.vyberPracovnikaB:
			processVyberPracovnikaB(message);
		break;

		case Mc.vyberPracovnikaSkladanie:
			processVyberPracovnikaSkladanie(message);
		break;

		case Mc.vyberPracovnikaA:
			processVyberPracovnikaA(message);
		break;

		case Mc.uvolneniePracovnikaB:
			processUvolneniePracovnikaB(message);
		break;

		case Mc.vyberPracovnikaRezanie:
			processVyberPracovnikaRezanie(message);
		break;

		case Mc.uvolneniePracovnikaC:
			processUvolneniePracovnikaC(message);
		break;

		case Mc.vyberPracovnikaMontaz:
			processVyberPracovnikaMontaz(message);
		break;

		case Mc.uvolneniePracovnikaA:
			processUvolneniePracovnikaA(message);
		break;

		case Mc.vyberPracovnikaLakovanie:
			processVyberPracovnikaLakovanie(message);
		break;

		case Mc.vyberPracovnikaC:
			processVyberPracovnikaC(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public AgentPracovnikov myAgent()
	{
		return (AgentPracovnikov)super.myAgent();
	}

}
