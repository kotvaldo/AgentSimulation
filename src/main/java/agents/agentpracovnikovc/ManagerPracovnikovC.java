package agents.agentpracovnikovc;

import OSPABA.*;
import entities.WorkerB;
import entities.WorkerC;
import simulation.*;

import java.util.LinkedList;

//meta! id="230"
public class ManagerPracovnikovC extends OSPABA.Manager
{
	private LinkedList<WorkerC> freeWorkersC = new LinkedList<>();
	public ManagerPracovnikovC(int id, Simulation mySim, Agent myAgent)
	{
		super(id, mySim, myAgent);
		init();
		MySimulation mySimulation = (MySimulation) _mySim;
		freeWorkersC = new LinkedList<>(mySimulation.getWorkersCArrayList());
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
		freeWorkersC.clear();
		MySimulation mySimulation = (MySimulation) _mySim;
		freeWorkersC = new LinkedList<>(mySimulation.getWorkersCArrayList());

	}

	//meta! sender="AgentPracovnikov", id="248", type="Request"
	public void processRVyberPracovnikaC(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;

		if (!freeWorkersC.isEmpty()) {
			WorkerC workerC = freeWorkersC.removeFirst();
			msg.setWorkerC(workerC);
		} else {
			msg.setWorkerA(null);
		}

		response(msg);
	}

	//meta! sender="AgentPracovnikov", id="241", type="Notice"
	public void processNoticeUvolniC(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;
		msg.getWorkerC().setIsBusy(false);
		this.freeWorkersC.addLast(msg.getWorkerC());
		msg.setWorkerC(null);

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
		case Mc.noticeUvolniC:
			processNoticeUvolniC(message);
		break;

		case Mc.rVyberPracovnikaC:
			processRVyberPracovnikaC(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public AgentPracovnikovC myAgent()
	{
		return (AgentPracovnikovC)super.myAgent();
	}

}
