package agents.agentpohybu.continualassistants;

import Enums.WorkerBussyState;
import OSPABA.*;
import simulation.*;
import agents.agentpohybu.*;
import OSPABA.Process;

//meta! id="116"
public class ProcesPresunDoSkladu extends Process
{
	public ProcesPresunDoSkladu(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="AgentPohybu", id="117", type="Start"
	public void processStart(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;


		if (msg.getWorkerA() != null) {
			msg.getWorkerA().setState(WorkerBussyState.MOVING_TO_STORAGE.getValue());
		}

		double trvaniePresunu = ((MySimulation) mySim()).getGenerators().getTimeMovingIntoStorageDist().sample();
		hold(trvaniePresunu, msg);
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
	public AgentPohybu myAgent()
	{
		return (AgentPohybu)super.myAgent();
	}

}
