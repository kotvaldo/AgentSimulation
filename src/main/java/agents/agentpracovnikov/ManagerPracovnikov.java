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

	//meta! sender="AgentPracovnikovB", id="246", type="Request"
	public void processRVyberPracovnikaB(MessageForm message)
	{
	}

	//meta! sender="AgentPracovnikovC", id="248", type="Request"
	public void processRVyberPracovnikaC(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="65", type="Notice"
	public void processInit(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="164", type="Response"
	public void processRVyberPracovnikaLakovanie(MessageForm message)
	{
	}

	//meta! sender="AgentPracovnikovA", id="242", type="Request"
	public void processRVyberPracovnikaA(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="162", type="Response"
	public void processRVyberPracovnikaMorenie(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="90", type="Response"
	public void processRVyberPracovnikaSkladanie(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="204", type="Notice"
	public void processNoticeUvolniPracovnikaB(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="126", type="Notice"
	public void processNoticeUvolniPracovnikaA(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="167", type="Response"
	public void processRVyberPracovnikaMontaz(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="168", type="Response"
	public void processRVyberPracovnikaRezanie(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="205", type="Notice"
	public void processNoticeUvolniPracovnikaC(MessageForm message)
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
		case Mc.rVyberPracovnikaRezanie:
			processRVyberPracovnikaRezanie(message);
		break;

		case Mc.rVyberPracovnikaSkladanie:
			processRVyberPracovnikaSkladanie(message);
		break;

		case Mc.rVyberPracovnikaB:
			processRVyberPracovnikaB(message);
		break;

		case Mc.rVyberPracovnikaC:
			processRVyberPracovnikaC(message);
		break;

		case Mc.init:
			processInit(message);
		break;

		case Mc.noticeUvolniPracovnikaC:
			processNoticeUvolniPracovnikaC(message);
		break;

		case Mc.rVyberPracovnikaMorenie:
			processRVyberPracovnikaMorenie(message);
		break;

		case Mc.noticeUvolniPracovnikaA:
			processNoticeUvolniPracovnikaA(message);
		break;

		case Mc.noticeUvolniPracovnikaB:
			processNoticeUvolniPracovnikaB(message);
		break;

		case Mc.rVyberPracovnikaLakovanie:
			processRVyberPracovnikaLakovanie(message);
		break;

		case Mc.rVyberPracovnikaA:
			processRVyberPracovnikaA(message);
		break;

		case Mc.rVyberPracovnikaMontaz:
			processRVyberPracovnikaMontaz(message);
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
