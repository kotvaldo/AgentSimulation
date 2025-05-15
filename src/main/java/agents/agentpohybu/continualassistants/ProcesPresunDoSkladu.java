package agents.agentpohybu.continualassistants;

import Enums.PresetSimulationValues;
import OSPABA.*;
import entities.Worker;
import simulation.*;
import agents.agentpohybu.*;
import OSPABA.Process;

import java.awt.geom.Point2D;

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
		MyMessage myMessage = (MyMessage) message.createCopy();
		MySimulation simulation = (MySimulation) _mySim;
		myMessage.setCode(Mc.finish);

		double newTime = simulation.getGenerators().getTimeMovingIntoStorageDist().sample();

		Worker worker = myMessage.getAssignedWorker();
		if (worker != null) {
			Point2D transitionPoint = new Point2D.Double(Data.SKLAD_X, Data.SKLAD_Y);
			worker.setCurrPosition(transitionPoint);

			if (mySim().animatorExists()) {
				worker.getAnimImageItem().moveTo(mySim().currentTime(), newTime, transitionPoint);
			}
		}

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
	public AgentPohybu myAgent()
	{
		return (AgentPohybu)super.myAgent();
	}

}
