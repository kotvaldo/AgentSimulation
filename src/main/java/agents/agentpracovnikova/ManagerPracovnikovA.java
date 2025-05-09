package agents.agentpracovnikova;

import Enums.WorkerBussyState;
import OSPABA.*;
import entities.*;
import simulation.*;

import java.util.LinkedList;

//meta! id="228"
public class ManagerPracovnikovA extends OSPABA.Manager
{
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
			worker.setState(WorkerBussyState.NON_BUSY.getValue());
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
			worker.setState(WorkerBussyState.ASSIGNED.getValue());
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
			worker.setState(WorkerBussyState.ASSIGNED.getValue());
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

		if (msg.getWorkerForCutting() instanceof WorkerA) {
			worker = (WorkerA) msg.getWorkerForCutting();
		} else if (msg.getWorkerForMontage() instanceof WorkerA) {
			worker = (WorkerA) msg.getWorkerForMontage();
		}

		if (worker != null) {
			worker.setState(WorkerBussyState.NON_BUSY.getValue());
			freeWorkers.addLast(worker);
            /*System.out.println("Uvoľnený WorkerA ID: " + worker.getId());
            System.out.println("Je volnych> " + freeWorkers.size() + " pracovnikovA");*/
			//System.out.println("Free workersA in release: " + freeWorkers.size());
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
