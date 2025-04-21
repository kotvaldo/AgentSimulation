package agents.agentpracovnikovb;

import OSPABA.*;
import entities.WorkerA;
import entities.WorkerB;
import simulation.*;

import java.util.LinkedList;

//meta! id="229"
public class ManagerPracovnikovB extends OSPABA.Manager
{
	private LinkedList<WorkerB> freeWorkersB = new LinkedList<>();
	public ManagerPracovnikovB(int id, Simulation mySim, Agent myAgent)
	{
		super(id, mySim, myAgent);
		init();

		MySimulation mySimulation = (MySimulation) _mySim;
		freeWorkersB = new LinkedList<>(mySimulation.getWorkersBArrayList());
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
		freeWorkersB.clear();
		MySimulation mySimulation = (MySimulation) _mySim;
		freeWorkersB = new LinkedList<>(mySimulation.getWorkersBArrayList());
	}

	//meta! sender="AgentPracovnikov", id="246", type="Request"
	public void processRVyberPracovnikaB(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;

		if (!freeWorkersB.isEmpty()) {
			WorkerB workerB = freeWorkersB.removeFirst();
			msg.setWorkerB(workerB);
		} else {
			msg.setWorkerA(null);
		}

		response(msg);
	}

	//meta! sender="AgentPracovnikov", id="240", type="Notice"
	public void processNoticeUvolniB(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;
		msg.getWorkerB().setIsBusy(false);
		this.freeWorkersB.addLast(msg.getWorkerB());
		msg.setWorkerB(null);
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
		case Mc.noticeUvolniB:
			processNoticeUvolniB(message);
		break;

		case Mc.rVyberPracovnikaB:
			processRVyberPracovnikaB(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public AgentPracovnikovB myAgent()
	{
		return (AgentPracovnikovB)super.myAgent();
	}

}
