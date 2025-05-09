package agents.agentcinnosti.continualassistants;

import Enums.PresetSimulationValues;
import OSPABA.*;
import simulation.*;
import agents.agentcinnosti.*;
import OSPABA.Process;

//meta! id="274"
public class ProcesRezania extends Process
{
	public ProcesRezania(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="AgentCinnosti", id="275", type="Start"
	public void processStart(MessageForm message)
	{
		MyMessage myMessage = (MyMessage) message.createCopy();
		MySimulation simulation = (MySimulation) _mySim;
		myMessage.setCode(Mc.finish);
		double newTime = 0;
		if(myMessage.getFurniture().getType() == 1) {
			newTime = simulation.getGenerators().getCuttingTableDist().sample();
		} else if(myMessage.getFurniture().getType() == 2) {
			newTime = simulation.getGenerators().getCuttingChairDist().sample();
		} else if(myMessage.getFurniture().getType() == 3) {
			newTime = simulation.getGenerators().getCuttingWardrobeDist().sample();
		}
		if(newTime + simulation.currentTime() <= PresetSimulationValues.END_OF_SIMULATION.asDouble()) {
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
