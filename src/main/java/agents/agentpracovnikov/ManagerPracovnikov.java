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

	//meta! sender="AgentNabytku", id="164", type="Request"
	public void processRVyberPracovnikaLakovanie(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;
		msg.setCode(Mc.rVyberPracovnikaCLakovanie);
		msg.setAddressee(mySim().findAgent(Id.agentPracovnikovC));
		request(msg);
	}

	//meta! sender="AgentPracovnikovA", id="242", type="Response"
	public void processRVyberPracovnikaARezanie(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="90", type="Request"
	public void processRVyberPracovnikaSkladanie(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="204", type="Notice"
	public void processNoticeUvolniPracovnikaB(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="167", type="Request"
	public void processRVyberPracovnikaMontaz(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="126", type="Notice"
	public void processNoticeUvolniPracovnikaA(MessageForm message)
	{
	}

	//meta! sender="AgentPracovnikovB", id="246", type="Response"
	public void processRVyberPracovnikaBRSkladanie(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="205", type="Notice"
	public void processNoticeUvolniPracovnikaC(MessageForm message)
	{
	}

	//meta! sender="AgentPracovnikovC", id="366", type="Response"
	public void processRVyberPracovnikaCMontaz(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="162", type="Request"
	public void processRVyberPracovnikaMorenie(MessageForm message)
	{
	}

	//meta! sender="AgentPracovnikovC", id="248", type="Response"
	public void processRVyberPracovnikaCMorenie(MessageForm message)
	{
	}

	//meta! sender="AgentPracovnikovC", id="379", type="Response"
	public void processRVyberPracovnikaCLakovanie(MessageForm message)
	{
	}

	//meta! sender="AgentPracovnikovA", id="365", type="Response"
	public void processRVyberPracovnikaAMontaz(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="168", type="Request"
	public void processRVyberPracovnikaRezanie(MessageForm message)
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
		case Mc.rVyberPracovnikaSkladanie:
			processRVyberPracovnikaSkladanie(message);
		break;

		case Mc.rVyberPracovnikaMontaz:
			processRVyberPracovnikaMontaz(message);
		break;

		case Mc.noticeUvolniPracovnikaB:
			processNoticeUvolniPracovnikaB(message);
		break;

		case Mc.rVyberPracovnikaRezanie:
			processRVyberPracovnikaRezanie(message);
		break;

		case Mc.rVyberPracovnikaLakovanie:
			processRVyberPracovnikaLakovanie(message);
		break;

		case Mc.rVyberPracovnikaMorenie:
			processRVyberPracovnikaMorenie(message);
		break;

		case Mc.rVyberPracovnikaCMontaz:
			processRVyberPracovnikaCMontaz(message);
		break;

		case Mc.rVyberPracovnikaBRSkladanie:
			processRVyberPracovnikaBRSkladanie(message);
		break;

		case Mc.init:
			processInit(message);
		break;

		case Mc.rVyberPracovnikaAMontaz:
			processRVyberPracovnikaAMontaz(message);
		break;

		case Mc.rVyberPracovnikaCMorenie:
			processRVyberPracovnikaCMorenie(message);
		break;

		case Mc.noticeUvolniPracovnikaA:
			processNoticeUvolniPracovnikaA(message);
		break;

		case Mc.rVyberPracovnikaCLakovanie:
			processRVyberPracovnikaCLakovanie(message);
		break;

		case Mc.rVyberPracovnikaARezanie:
			processRVyberPracovnikaARezanie(message);
		break;

		case Mc.noticeUvolniPracovnikaC:
			processNoticeUvolniPracovnikaC(message);
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
