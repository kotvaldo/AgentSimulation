package agents.agentnabytku;

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
		for (Furniture furniture : msg.getOrder().getFurnitureList()) {
			MyMessage furnitureMsg = new MyMessage(mySimulation);
			furnitureMsg.setFurniture(furniture);
			this.queueNonProcessed.getQueue().addLast(furnitureMsg);
		}

		for (MyMessage furnitureMsg : this.queueNonProcessed.getQueue()) {
			MyMessage workerRequest = new MyMessage(furnitureMsg);
			workerRequest.setCode(Mc.rVyberPracovnikaRezanie);
			workerRequest.setAddressee(mySim().findAgent(Id.agentPracovnikov));
			request(workerRequest);
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
