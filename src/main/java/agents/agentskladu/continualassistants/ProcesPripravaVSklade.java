package agents.agentskladu.continualassistants;

import Enums.PresetSimulationValues;
import Enums.WorkerBussyState;
import OSPABA.*;
import agents.agentskladu.*;
import simulation.*;

//meta! id="128"
public class ProcesPripravaVSklade extends OSPABA.Process
{
	public ProcesPripravaVSklade(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="AgentSkladu", id="129", type="Start"
	public void processStart(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();

		if (msg.getWorkerForCutting() != null) {
			msg.getWorkerForCutting().setState(WorkerBussyState.PREPARING_IN_STORAGE.getValue());
		}
		msg.setCode(Mc.finish);
		double newTime = ((MySimulation) mySim()).getGenerators().getTimeSpentInStorageDist().sample();
		/*System.out.println("New time " + newTime);
		System.out.println(Utility.Utility.fromSecondsToTime(newTime + mySim().currentTime()));
		System.out.println(msg);*/

		if (newTime + mySim().currentTime() < PresetSimulationValues.END_OF_SIMULATION.getValue()) {
			hold(newTime, msg);
		} else {
			assistantFinished(msg);
		}
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code()) {
			case Mc.finish:
				MyMessage msg = (MyMessage) message.createCopy();
				message.setAddressee(myAgent());
				assistantFinished(msg);
				break;
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
	public AgentSkladu myAgent()
	{
		return (AgentSkladu)super.myAgent();
	}

}
