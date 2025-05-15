package agents.agentokolia.continualassistants;

import Enums.PresetSimulationValues;
import OSPABA.*;
import agents.agentokolia.*;
import simulation.*;

//meta! id="301"
public class PlanovacPrichodObjednavky extends Scheduler
{
	
	public PlanovacPrichodObjednavky(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="AgentOkolia", id="302", type="Start"
	public void processStart(MessageForm message)
	{
		MySimulation simulationCore = (MySimulation)mySim();
		MyMessage msg = (MyMessage)message.createCopy();
		msg.setCode(Mc.noticeNovaObjednavka);
		var newTime = simulationCore.getGenerators().getOrderArrivalDist().sample();
		if (newTime + _mySim.currentTime() < PresetSimulationValues.END_OF_SIMULATION.asDouble())
		{
			hold(newTime, msg);
		}
	}

	//meta! sender="AgentOkolia", id="338", type="Notice"
	public void processNoticeNovaObjednavka(MessageForm message)
	{
		MyMessage msg = (MyMessage)message.createCopy();
		msg.setAddressee(myAgent());
		notice(msg);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
		}
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
			case Mc.noticeNovaObjednavka:
				processNoticeNovaObjednavka(message);
				break;

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
	public AgentOkolia myAgent()
	{
		return (AgentOkolia)super.myAgent();
	}

}
