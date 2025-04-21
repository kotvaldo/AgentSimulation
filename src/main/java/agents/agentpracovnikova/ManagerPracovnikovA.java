package agents.agentpracovnikova;

import OSPABA.*;
import entities.WorkerA;
import entities.WorkerB;
import simulation.*;

import java.util.LinkedList;

//meta! id="228"
public class ManagerPracovnikovA extends OSPABA.Manager
{
	private LinkedList<WorkerA> workersA = new LinkedList<>();
	private LinkedList<WorkerA> freeWorkersA = new LinkedList<>();

	public ManagerPracovnikovA(int id, Simulation mySim, Agent myAgent)
	{
		super(id, mySim, myAgent);
		init();
		MySimulation mySimulation = (MySimulation) mySim;
		for (int i = 0; i < mySimulation.getCountWorkerA(); i++) {
			this.workersA.add(new WorkerA());
		}
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
		workersA.clear();
		MySimulation mySimulation = (MySimulation) _mySim;
		for (int i = 0; i < mySimulation.getCountWorkerA(); i++) {
			this.workersA.add(new WorkerA());
		}
	}

	//meta! sender="AgentPracovnikov", id="242", type="Request"
	public void processRVyberPracovnikaA(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();

		msg.setCode(Mc.rVyberPracovnikaA);
		msg.setAddressee(mySim().findAgent(Id.agentPracovnikov));
		response(msg);
	}

	//meta! sender="AgentPracovnikov", id="239", type="Notice"
	public void processNoticeUvolniA(MessageForm message)
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
		case Mc.noticeUvolniA:
			processNoticeUvolniA(message);
		break;

		case Mc.rVyberPracovnikaA:
			processRVyberPracovnikaA(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public AgentPracovnikovA myAgent()
	{
		return (AgentPracovnikovA)super.myAgent();
	}

}
