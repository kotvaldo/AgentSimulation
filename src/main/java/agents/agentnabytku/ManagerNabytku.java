package agents.agentnabytku;

import OSPABA.*;
import simulation.*;

//meta! id="9"
public class ManagerNabytku extends OSPABA.Manager
{
	public ManagerNabytku(int id, Simulation mySim, Agent myAgent)
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

	//meta! sender="AgentModelu", id="24", type="Notice"
	public void processInit(MessageForm message)
	{
	}

	//meta! sender="AgentModelu", id="81", type="Notice"
	public void processPrichodNovejObj(MessageForm message)
	{
	}

	//meta! sender="AgentPracovnikov", id="167", type="Request"
	public void processVyberPracovnikaMontaz(MessageForm message)
	{
	}

	//meta! sender="AgentPracovnikov", id="162", type="Request"
	public void processVyberPracovnikaMorenie(MessageForm message)
	{
	}

	//meta! sender="AgentPracovnikov", id="168", type="Request"
	public void processVyberPracovnikaRezanie(MessageForm message)
	{
	}

	//meta! sender="AgentCinnosti", id="287", type="Request"
	public void processUrobLakovanie(MessageForm message)
	{
	}

	//meta! sender="AgentPracovnikov", id="90", type="Request"
	public void processVyberPracovnikaSkladanie(MessageForm message)
	{
	}

	//meta! sender="AgentCinnosti", id="285", type="Request"
	public void processUrobPripravuVSklade(MessageForm message)
	{
	}

	//meta! sender="AgentCinnosti", id="289", type="Request"
	public void processUrobMontaz(MessageForm message)
	{
	}

	//meta! sender="AgentCinnosti", id="284", type="Request"
	public void processUrobRezanie(MessageForm message)
	{
	}

	//meta! sender="AgentPracovnikov", id="164", type="Request"
	public void processVyberPracovnikaLakovanie(MessageForm message)
	{
	}

	//meta! sender="AgentCinnosti", id="288", type="Request"
	public void processUrobSkladanie(MessageForm message)
	{
	}

	//meta! sender="AgentPohybu", id="138", type="Request"
	public void processPresunDoSkladu(MessageForm message)
	{
	}

	//meta! sender="AgentPohybu", id="157", type="Request"
	public void processPresunNaPracovisko(MessageForm message)
	{
	}

	//meta! sender="AgentCinnosti", id="286", type="Request"
	public void processUrobMorenie(MessageForm message)
	{
	}

	//meta! sender="AgentPracovisk", id="182", type="Request"
	public void processDajVolnePracovneMiesto(MessageForm message)
	{
	}

	//meta! sender="AgentSkladu", id="134", type="Notice"
	public void processKoniecPripravy(MessageForm message)
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
		case Mc.koniecPripravy:
			processKoniecPripravy(message);
		break;

		case Mc.vyberPracovnikaRezanie:
			processVyberPracovnikaRezanie(message);
		break;

		case Mc.vyberPracovnikaMontaz:
			processVyberPracovnikaMontaz(message);
		break;

		case Mc.presunNaPracovisko:
			processPresunNaPracovisko(message);
		break;

		case Mc.urobLakovanie:
			processUrobLakovanie(message);
		break;

		case Mc.vyberPracovnikaLakovanie:
			processVyberPracovnikaLakovanie(message);
		break;

		case Mc.urobRezanie:
			processUrobRezanie(message);
		break;

		case Mc.vyberPracovnikaMorenie:
			processVyberPracovnikaMorenie(message);
		break;

		case Mc.urobSkladanie:
			processUrobSkladanie(message);
		break;

		case Mc.dajVolnePracovneMiesto:
			processDajVolnePracovneMiesto(message);
		break;

		case Mc.vyberPracovnikaSkladanie:
			processVyberPracovnikaSkladanie(message);
		break;

		case Mc.init:
			processInit(message);
		break;

		case Mc.urobMontaz:
			processUrobMontaz(message);
		break;

		case Mc.presunDoSkladu:
			processPresunDoSkladu(message);
		break;

		case Mc.prichodNovejObj:
			processPrichodNovejObj(message);
		break;

		case Mc.urobPripravuVSklade:
			processUrobPripravuVSklade(message);
		break;

		case Mc.urobMorenie:
			processUrobMorenie(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public AgentNabytku myAgent()
	{
		return (AgentNabytku)super.myAgent();
	}

}
