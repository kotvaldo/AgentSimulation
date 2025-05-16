package agents.agentpracovnikovb;

import Enums.WorkerBussyState;
import OSPABA.*;
import entities.WorkerB;
import simulation.*;

import java.awt.geom.Point2D;
import java.util.LinkedList;

//meta! id="229"
public class ManagerPracovnikovB extends Manager
{
	public LinkedList<WorkerB> getFreeWorkers() {
		return freeWorkers;
	}

	private LinkedList<WorkerB> freeWorkers;
	public ManagerPracovnikovB(int id, Simulation mySim, Agent myAgent)
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

		for (WorkerB worker : sim.getWorkersBArrayList()) {
			worker.setState(WorkerBussyState.NON_BUSY.getValue(), mySim().currentTime());
			freeWorkers.add(worker);
		}
	}

	public void processRVyberPracovnikaBRSkladanie(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();

		WorkerB worker = null;
		if (!freeWorkers.isEmpty()) {
			worker = freeWorkers.removeFirst();
		}

		if (worker != null) {
			msg.setWorkerForAssembly(worker);
		} else {
			msg.setWorkerForAssembly(null);
		}

		msg.setCode(Mc.rVyberPracovnikaBRSkladanie);
		msg.setAddressee(mySim().findAgent(Id.agentPracovnikov));
		response(msg);
	}


	//meta! sender="AgentPracovnikov", id="240", type="Notice"
	public void processNoticeUvolniB(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();
		WorkerB worker = (WorkerB) msg.getWorkerForRelease();

		if (worker != null) {
			freeWorkers.addLast(worker);

			int index = freeWorkers.size() - 1;
			int spacing = 40;
			int x = Data.FREE_WORKERS_B_QUEUE_X + index * spacing;
			int y = Data.FREE_WORKERS_B_QUEUE_Y;

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
		case Mc.rVyberPracovnikaBRSkladanie:
			processRVyberPracovnikaBRSkladanie(message);
		break;

		case Mc.noticeUvolniB:
			processNoticeUvolniB(message);
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
