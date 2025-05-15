package agents.agentpracovnikova;

import Enums.WorkerBussyState;
import OSPABA.*;
import entities.*;
import simulation.*;

import java.awt.geom.Point2D;
import java.util.LinkedList;

//meta! id="228"
public class ManagerPracovnikovA extends OSPABA.Manager
{
	public LinkedList<WorkerA> getFreeWorkers() {
		return freeWorkers;
	}

	private LinkedList<WorkerA> freeWorkers;
	public ManagerPracovnikovA(int id, Simulation mySim, Agent myAgent)
	{
		super(id, mySim, myAgent);
		init();
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
		MySimulation sim = (MySimulation) _mySim;
		freeWorkers = new LinkedList<>();

		for (WorkerA worker : sim.getWorkersAArrayList()) {
			worker.setState(WorkerBussyState.NON_BUSY.getValue(), mySim().currentTime());
			freeWorkers.add(worker);
		}
	}

	//meta! sender="AgentPracovnikov", id="242", type="Request"
	public void processRVyberPracovnikaARezanie(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();

		WorkerA worker = null;
		if (!freeWorkers.isEmpty()) {
			worker = freeWorkers.removeFirst();
		}
		//System.out.println("Free workersA in Cutting: " + freeWorkers.size());
		if (worker != null) {
			msg.setWorkerForCutting(worker);
		} else {
			msg.setWorkerForCutting(null);
		}

		msg.setCode(Mc.rVyberPracovnikaARezanie);
		msg.setAddressee(mySim().findAgent(Id.agentPracovnikov));
		response(msg);
	}


	//meta! sender="AgentPracovnikov", id="365", type="Request"
	public void processRVyberPracovnikaAMontaz(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();

		WorkerA worker = null;
		if (!freeWorkers.isEmpty()) {
			worker = freeWorkers.removeFirst();
		}

		if (worker != null) {
			msg.setWorkerForMontage(worker);
		} else {
			msg.setWorkerForMontage(null);
		}
		//System.out.println("Free workersA in Montage: " + freeWorkers.size());
		msg.setCode(Mc.rVyberPracovnikaAMontaz);
		msg.setAddressee(mySim().findAgent(Id.agentPracovnikov));
		response(msg);
	}


	//meta! sender="AgentPracovnikov", id="239", type="Notice"
	public void processNoticeUvolniA(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();
		WorkerA worker = null;

		if (msg.getWorkerForRelease() instanceof WorkerA) {
			worker = (WorkerA) msg.getWorkerForRelease();
		} else if (msg.getWorkerForMontage() instanceof WorkerA) {
			worker = (WorkerA) msg.getWorkerForMontage();
		}

		if (worker != null) {
			worker.setState(WorkerBussyState.NON_BUSY.getValue(), mySim().currentTime());
			freeWorkers.addLast(worker);
			int index = freeWorkers.size() - 1;
			int spacing = 40;

			int x = Data.FREE_WORKERS_A_QUEUE_X + index * spacing;
			int y = Data.FREE_WORKERS_A_QUEUE_Y;

			Point2D destination = new Point2D.Double(x, y);
			worker.setCurrPosition(destination);
			if (mySim().animatorExists()) {
				worker.getAnimImageItem().moveTo(mySim().currentTime(), 1.0, destination);
			}

		}
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
		case Mc.rVyberPracovnikaARezanie:
			processRVyberPracovnikaARezanie(message);
		break;

		case Mc.noticeUvolniA:
			processNoticeUvolniA(message);
		break;

		case Mc.rVyberPracovnikaAMontaz:
			processRVyberPracovnikaAMontaz(message);
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
