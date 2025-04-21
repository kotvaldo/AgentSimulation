package agents.agentpracovnikovc;

import OSPABA.*;
import entities.WorkerA;
import entities.WorkerB;
import entities.WorkerC;
import simulation.*;

import java.util.LinkedList;

//meta! id="230"
public class ManagerPracovnikovC extends OSPABA.Manager
{
	private LinkedList<WorkerC> workersC = new LinkedList<>();
	public ManagerPracovnikovC(int id, Simulation mySim, Agent myAgent)
	{
		super(id, mySim, myAgent);
		init();
		MySimulation mySimulation = (MySimulation) mySim;
		for (int i = 0; i < mySimulation.getCountWorkerC(); i++) {
			this.workersC.add(new WorkerC());
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
		workersC.clear();
		MySimulation mySimulation = (MySimulation) mySim();
		for (int i = 0; i < mySimulation.getCountWorkerC(); i++) {
			this.workersC.add(new WorkerC());
		}

	}

	//meta! sender="AgentPracovnikov", id="248", type="Request"
	public void processRVyberPracovnikaC(MessageForm message)
	{
	}

	//meta! sender="AgentPracovnikov", id="241", type="Notice"
	public void processNoticeUvolniC(MessageForm message)
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
