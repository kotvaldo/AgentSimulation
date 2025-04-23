package agents.agentnabytku;

import Enums.OrderStateValues;
import Enums.WorkPlaceStateValues;
import Enums.WorkerBussyState;
import OSPABA.*;
import entities.*;
import simulation.Id;
import simulation.Mc;
import simulation.MyMessage;
import simulation.MySimulation;


//meta! id="9"
public class ManagerNabytku extends OSPABA.Manager {
	private QueueNonProcessed queueNonProcessed;
	private QueueColoring queueColoring;
	private QueueAssembly queueAssembly;
	private QueueMontage queueMontage;
	public ManagerNabytku(int id, Simulation mySim, Agent myAgent) {
		super(id, mySim, myAgent);
		init();
		queueNonProcessed = new QueueNonProcessed(null);
		queueColoring = new QueueColoring(null);
		queueAssembly = new QueueAssembly(null);
		queueMontage = new QueueMontage(null);

	}

	@Override
	public void prepareReplication() {
		super.prepareReplication();
		// Setup component for the next replication

		if (petriNet() != null) {
			petriNet().clear();
		}
		queueNonProcessed.getQueue().clear();
		queueColoring.getQueue().clear();
		queueAssembly.getQueue().clear();
		queueMontage.getQueue().clear();
	}


	//meta! sender="AgentModelu", id="24", type="Notice"
	public void processInit(MessageForm message) {
	}


	//meta! sender="AgentPohybu", id="138", type="Response"
	public void processRPresunDoSkladu(MessageForm message) {
	}


	//meta! sender="AgentPracovnikov", id="164", type="Response"
	public void processRVyberPracovnikaLakovanie(MessageForm message) {
	}

	//meta! sender="AgentPohybu", id="157", type="Response"
	public void processRPresunNaPracovisko(MessageForm message) {
	}

	//meta! sender="AgentPracovnikov", id="90", type="Response"
	public void processRVyberPracovnikaSkladanie(MessageForm message) {
	}

	//meta! sender="AgentPracovnikov", id="167", type="Response"
	public void processRVyberPracovnikaMontaz(MessageForm message) {
	}

	//meta! sender="AgentCinnosti", id="286", type="Response"
	public void processRUrobMorenie(MessageForm message) {
	}

	//meta! sender="AgentCinnosti", id="289", type="Response"
	public void processRUrobMontaz(MessageForm message) {
	}

	//meta! sender="AgentPracovisk", id="182", type="Response"
	public void processRDajVolnePracovneMiesto(MessageForm message) {
	}

	//meta! sender="AgentModelu", id="81", type="Notice"
	public void processNoticeSpracujObjednavku(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();
		MySimulation mySimulation = (MySimulation) _mySim;
		msg.getOrder().setState(OrderStateValues.PROCESSING_ORDER.getValue());
		for (Furniture furniture : msg.getOrder().getFurnitureList()) {
			MyMessage furnitureMsg = new MyMessage(mySimulation);
			furnitureMsg.setOrder(msg.getOrder());
			furnitureMsg.setFurniture(furniture);
			furnitureMsg.setWorkPlace(null);
			furnitureMsg.setWorkerA(null);
			furnitureMsg.setWorkerB(null);
			furnitureMsg.setWorkerC(null);
			this.queueNonProcessed.getQueue().addLast(furnitureMsg);

		}
		checkProcessingQueueNonProcessed();

	}


	private void checkProcessingQueueNonProcessed() {
		for (MyMessage msg : queueNonProcessed.getQueue()) {
			// Ak má oboch – môžeš rovno poslať na pohyb
			if (msg.getWorkerA() != null && msg.getWorkPlace() != null) {

				queueNonProcessed.getQueue().remove(msg);

				MyMessage moveMsg = new MyMessage(msg);
				moveMsg.setCode(Mc.rPresunDoSkladu);
				moveMsg.setAddressee(mySim().findAgent(Id.agentPohybu));
				msg.getWorkerA().setState(WorkerBussyState.MOVING_TO_STORAGE.getValue());
				msg.getWorkPlace().setState(WorkPlaceStateValues.ASSIGNED.getValue());
				request(moveMsg);

				break; // spracovávame len jeden naraz, aby sme nezmienili front počas iterácie
			}

			// Ak má worker, ale nie workplace
			if (msg.getWorkerA() != null && msg.getWorkPlace() == null) {
				MyMessage reqPlace = new MyMessage(msg);
				reqPlace.setCode(Mc.rDajVolnePracovneMiesto);
				reqPlace.setAddressee(mySim().findAgent(Id.agentPracovisk));
				request(reqPlace);
			}

			// Ak má workplace, ale nie worker-a
			if (msg.getWorkPlace() != null && msg.getWorkerA() == null) {
				MyMessage reqWorker = new MyMessage(msg);
				reqWorker.setCode(Mc.rVyberPracovnikaRezanie);
				reqWorker.setAddressee(mySim().findAgent(Id.agentPracovnikov));
				request(reqWorker);
			}
		}
	}

	//meta! sender="AgentCinnosti", id="284", type="Response"
	public void processRUrobRezanie(MessageForm message) {
	}

	//meta! sender="AgentCinnosti", id="288", type="Response"
	public void processRUrobSkladanie(MessageForm message) {
	}

	//meta! sender="AgentPracovnikov", id="162", type="Response"
	public void processRVyberPracovnikaMorenie(MessageForm message) {
	}

	//meta! sender="AgentSkladu", id="324", type="Response"
	public void processRPripravVSklade(MessageForm message) {
	}

	//meta! sender="AgentPracovnikov", id="168", type="Response"
	public void processRVyberPracovnikaRezanie(MessageForm message) {
		MyMessage msg = (MyMessage) message;

		if (msg.getWorkerA() != null) {
			MyMessage reqPlace = new MyMessage(msg);
			reqPlace.setCode(Mc.rDajVolnePracovneMiesto);
			reqPlace.setAddressee(mySim().findAgent(Id.agentPracovisk));
			request(reqPlace);
		}
	}


	//meta! sender="AgentCinnosti", id="285", type="Response"
	public void processRUrobPripravuVSklade(MessageForm message) {
	}

	//meta! sender="AgentCinnosti", id="287", type="Response"
	public void processRUrobLakovanie(MessageForm message) {
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message) {
		switch (message.code()) {
		}
	}



	//meta! userInfo="Generated code: do not modify", tag="begin"
	public void init() {
	}

	@Override
	public void processMessage(MessageForm message) {
		switch (message.code()) {
			case Mc.rVyberPracovnikaMontaz:
				processRVyberPracovnikaMontaz(message);
				break;

			case Mc.rVyberPracovnikaMorenie:
				processRVyberPracovnikaMorenie(message);
				break;

			case Mc.rUrobMontaz:
				processRUrobMontaz(message);
				break;

			case Mc.rVyberPracovnikaSkladanie:
				processRVyberPracovnikaSkladanie(message);
				break;

			case Mc.rDajVolnePracovneMiesto:
				processRDajVolnePracovneMiesto(message);
				break;

			case Mc.rUrobRezanie:
				processRUrobRezanie(message);
				break;

			case Mc.rPresunDoSkladu:
				processRPresunDoSkladu(message);
				break;

			case Mc.rPresunNaPracovisko:
				processRPresunNaPracovisko(message);
				break;

			case Mc.rUrobPripravuVSklade:
				processRUrobPripravuVSklade(message);
				break;

			case Mc.rVyberPracovnikaLakovanie:
				processRVyberPracovnikaLakovanie(message);
				break;

			case Mc.rUrobMorenie:
				processRUrobMorenie(message);
				break;

			case Mc.rUrobLakovanie:
				processRUrobLakovanie(message);
				break;

			case Mc.rUrobSkladanie:
				processRUrobSkladanie(message);
				break;

			case Mc.init:
				processInit(message);
				break;

			case Mc.rVyberPracovnikaRezanie:
				processRVyberPracovnikaRezanie(message);
				break;

			case Mc.rPripravVSklade:
				processRPripravVSklade(message);
				break;

			case Mc.noticeSpracujObjednavku:
				processNoticeSpracujObjednavku(message);
				break;

			default:
				processDefault(message);
				break;
		}
	}
	//meta! tag="end"

	@Override
	public AgentNabytku myAgent() {
		return (AgentNabytku) super.myAgent();
	}

}
