package agents.agentcinnosti.continualassistants;

import Enums.PresetSimulationValues;
import OSPABA.*;
import simulation.*;
import agents.agentcinnosti.*;
import OSPABA.Process;

//meta! id="276"
public class ProcesLakovania extends Process
{
	public ProcesLakovania(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="AgentCinnosti", id="277", type="Start"
	public void processStart(MessageForm message)
	{
		MyMessage myMessage = (MyMessage) message.createCopy();
		MySimulation simulation = (MySimulation) _mySim;
		myMessage.setCode(Mc.finish);

		double newTime = switch (myMessage.getFurniture().getType()) {
			case 1 -> simulation.getGenerators().getPaintingTableDist().sample();
			case 2 -> simulation.getGenerators().getPaintingChairDist().sample();
			case 3 -> simulation.getGenerators().getPaintingWardrobeDist().sample();
			default -> 0.0;
		};

		if (newTime + simulation.currentTime() <= PresetSimulationValues.END_OF_SIMULATION.asDouble()) {
			hold(newTime, myMessage);
		}
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
			case Mc.finish -> {
				MyMessage msg = (MyMessage) message.createCopy();
				msg.setAddressee(myAgent());
				assistantFinished(msg);
			}
		}
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
		case Mc.start:
			processStart(message);
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
