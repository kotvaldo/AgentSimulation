package agents.agentpohybu;

import OSPABA.*;
import simulation.*;

//meta! id="35"
public class ManagerPohybu extends OSPABA.Manager
{
	public ManagerPohybu(int id, Simulation mySim, Agent myAgent)
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

	//meta! sender="AgentNabytku", id="138", type="Response"
	public void processRPresunDoSkladu(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="71", type="Notice"
	public void processInit(MessageForm message)
	{
	}

	//meta! sender="ProcesPresunDoSkladu", id="117", type="Finish"
	public void processFinishProcesPresunDoSkladu(MessageForm message)
	{
	}

	//meta! sender="ProcesPresunNaPracovisko", id="115", type="Finish"
	public void processFinishProcesPresunNaPracovisko(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="157", type="Response"
	public void processRPresunNaPracovisko(MessageForm message)
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
		case Mc.rPresunNaPracovisko:
			processRPresunNaPracovisko(message);
		break;

		case Mc.finish:
			switch (message.sender().id())
			{
			case Id.procesPresunDoSkladu:
				processFinishProcesPresunDoSkladu(message);
			break;

			case Id.procesPresunNaPracovisko:
				processFinishProcesPresunNaPracovisko(message);
			break;
			}
		break;

		case Mc.rPresunDoSkladu:
			processRPresunDoSkladu(message);
		break;

		case Mc.init:
			processInit(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public AgentPohybu myAgent()
	{
		return (AgentPohybu)super.myAgent();
	}

}
