package agents.agentpracovnikovc;

import Enums.WorkerBussyState;
import OSPABA.*;
import entities.WorkerC;
import simulation.*;

import java.awt.geom.Point2D;
import java.util.LinkedList;

//meta! id="230"
public class ManagerPracovnikovC extends Manager
{
	public LinkedList<WorkerC> getFreeWorkers() {
		return freeWorkers;
	}

	private LinkedList<WorkerC> freeWorkers;

	public ManagerPracovnikovC(int id, Simulation mySim, Agent myAgent)
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

		for (WorkerC worker : sim.getWorkersCArrayList()) {
			worker.setState(WorkerBussyState.NON_BUSY.getValue(), mySim().currentTime());
			freeWorkers.add(worker);
		}
	}

	//meta! sender="AgentPracovnikov", id="366", type="Request"
	public void processRVyberPracovnikaCMontaz(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();

		WorkerC worker = null;
		if (!freeWorkers.isEmpty()) {
			worker = freeWorkers.removeFirst();
		}

		if (worker != null) {
			worker.setState(WorkerBussyState.ASSIGNED.getValue(), mySim().currentTime());
			msg.setWorkerForMontage(worker);
		} else {
			msg.setWorkerForMontage(null);
		}

		msg.setCode(Mc.rVyberPracovnikaCMontaz);
		msg.setAddressee(mySim().findAgent(Id.agentPracovnikov));
		response(msg);
	}


	//meta! sender="AgentPracovnikov", id="248", type="Request"
	public void processRVyberPracovnikaCMorenie(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();

		WorkerC worker = null;
		if (!freeWorkers.isEmpty()) {
			worker = freeWorkers.removeFirst();
		}

		if (worker != null) {
			worker.setState(WorkerBussyState.ASSIGNED.getValue(), mySim().currentTime());
			msg.setWorkerForStaining(worker);
		} else {
			msg.setWorkerForStaining(null);
		}

		msg.setCode(Mc.rVyberPracovnikaCMorenie);
		msg.setAddressee(mySim().findAgent(Id.agentPracovnikov));
		response(msg);
	}


	//meta! sender="AgentPracovnikov", id="379", type="Request"
	public void processRVyberPracovnikaCLakovanie(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();

		WorkerC worker = null;
		if (!freeWorkers.isEmpty()) {
			worker = freeWorkers.removeFirst();
		}

		if (worker != null) {
			msg.setWorkerForPainting(worker);
		} else {
			msg.setWorkerForPainting(null);
		}

		msg.setCode(Mc.rVyberPracovnikaCLakovanie);
		msg.setAddressee(mySim().findAgent(Id.agentPracovnikov));
		response(msg);
	}


	//meta! sender="AgentPracovnikov", id="241", type="Notice"
	public void processNoticeUvolniC(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();
		WorkerC worker = (WorkerC) msg.getWorkerForRelease();

		if (worker != null) {
			freeWorkers.addLast(worker); // pridaj najprv do zoznamu

			int index = freeWorkers.size() - 1;
			int spacing = 40;
			int x = Data.FREE_WORKERS_C_QUEUE_X + index * spacing;
			int y = Data.FREE_WORKERS_C_QUEUE_Y;

			Point2D destination = new Point2D.Double(x, y);
			worker.setCurrPosition(destination);

			if (mySim().animatorExists()) {
				worker.getAnimImageItem().moveTo(mySim().currentTime(), 1.0, destination);
			}

			worker.setState(WorkerBussyState.NON_BUSY.getValue(), mySim().currentTime());
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
		case Mc.noticeUvolniC:
			processNoticeUvolniC(message);
		break;

		case Mc.rVyberPracovnikaCMorenie:
			processRVyberPracovnikaCMorenie(message);
		break;

		case Mc.rVyberPracovnikaCMontaz:
			processRVyberPracovnikaCMontaz(message);
		break;

		case Mc.rVyberPracovnikaCLakovanie:
			processRVyberPracovnikaCLakovanie(message);
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
