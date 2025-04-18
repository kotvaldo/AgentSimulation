package agents.agentcinnosti;

import OSPABA.*;
import simulation.*;

//meta! id="267"
public class ManagerCinnosti extends OSPABA.Manager
{
	public ManagerCinnosti(int id, Simulation mySim, Agent myAgent)
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

	//meta! sender="AgentNabytku", id="268", type="Notice"
	public void processInit(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="285", type="Response"
	public void processUrobPripravuVSklade(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="289", type="Response"
	public void processUrobMontaz(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="284", type="Response"
	public void processUrobRezanie(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="288", type="Response"
	public void processUrobSkladanie(MessageForm message)
	{
	}

	//meta! sender="ProcesMorenia", id="279", type="Finish"
	public void processFinishProcesMorenia(MessageForm message)
	{
	}

	//meta! sender="ProcesPripravaVSklade", id="291", type="Finish"
	public void processFinishProcesPripravaVSklade(MessageForm message)
	{
	}

	//meta! sender="ProcesRezania", id="275", type="Finish"
	public void processFinishProcesRezania(MessageForm message)
	{
	}

	//meta! sender="ProcesMontaze", id="281", type="Finish"
	public void processFinishProcesMontaze(MessageForm message)
	{
	}

	//meta! sender="ProcesLakovania", id="277", type="Finish"
	public void processFinishProcesLakovania(MessageForm message)
	{
	}

	//meta! sender="ProcesSkladania", id="283", type="Finish"
	public void processFinishProcesSkladania(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="286", type="Response"
	public void processUrobMorenie(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="287", type="Response"
	public void processUrobLakovanie(MessageForm message)
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
		case Mc.finish:
			switch (message.sender().id())
			{
			case Id.procesMorenia:
				processFinishProcesMorenia(message);
			break;

			case Id.procesPripravaVSklade:
				processFinishProcesPripravaVSklade(message);
			break;

			case Id.procesRezania:
				processFinishProcesRezania(message);
			break;

			case Id.procesMontaze:
				processFinishProcesMontaze(message);
			break;

			case Id.procesLakovania:
				processFinishProcesLakovania(message);
			break;

			case Id.procesSkladania:
				processFinishProcesSkladania(message);
			break;
			}
		break;

		case Mc.urobSkladanie:
			processUrobSkladanie(message);
		break;

		case Mc.urobMontaz:
			processUrobMontaz(message);
		break;

		case Mc.init:
			processInit(message);
		break;

		case Mc.urobLakovanie:
			processUrobLakovanie(message);
		break;

		case Mc.urobRezanie:
			processUrobRezanie(message);
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
	public AgentCinnosti myAgent()
	{
		return (AgentCinnosti)super.myAgent();
	}

}
