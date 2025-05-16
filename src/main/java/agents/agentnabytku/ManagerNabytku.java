package agents.agentnabytku;

import Enums.*;
import OSPABA.*;
import entities.*;
import simulation.*;

import java.util.Comparator;
import java.util.PriorityQueue;

//meta! id="9"
public class ManagerNabytku extends Manager
{
	private final QueueNonProcessed queueNonProcessed;
	private final QueueStaining queueStaining;
	private final QueueAssembly queueAssembly;
	private final QueueMontage queueMontage;
	private final QueueDrying queueDrying;
	public PriorityQueue<WorkPlace> getFreeWorkplaces() {
		return freeWorkplaces;
	}

	private final PriorityQueue<WorkPlace> freeWorkplaces;

	public QueueNonProcessed getQueueNonProcessed() {
		return queueNonProcessed;
	}

	public QueueStaining getQueueStaining() {
		return queueStaining;
	}

	public QueueAssembly getQueueAssembly() {
		return queueAssembly;
	}

	public QueueMontage getQueueMontage() {
		return queueMontage;
	}

	public QueuePainting getQueuePainting() {
		return queuePainting;
	}


	private final QueuePainting queuePainting;
	public ManagerNabytku(int id, Simulation mySim, Agent myAgent)
	{
		super(id, mySim, myAgent);
		init();
		MySimulation mySimulation = (MySimulation) mySim;
		queueNonProcessed = new QueueNonProcessed(mySimulation);
		queueStaining = new QueueStaining(mySimulation);
		queueAssembly = new QueueAssembly(mySimulation);
		queueMontage = new QueueMontage(mySimulation);
		queuePainting = new QueuePainting(mySimulation);
		queueDrying = new QueueDrying(mySimulation);
		freeWorkplaces = new PriorityQueue<>(Comparator.comparingInt(WorkPlace::getId));
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
		MySimulation mySimulation = (MySimulation)mySim();
		queueNonProcessed.clear();
		queueStaining.clear();
		queueAssembly.clear();
		queueMontage.clear();
		queuePainting.clear();
		freeWorkplaces.clear();
		freeWorkplaces.addAll(mySimulation.getWorkPlacesArrayList());
		for (WorkPlace workPlace : freeWorkplaces) {
			workPlace.clear();
		}

	}
	//meta! sender="AgentModelu", id="24", type="Notice"
	public void processInit(MessageForm message) {
	}



	public void tryToReassignWorkerA(WorkerA worker) {
		//System.out.println("Reassigning worker " + worker);
		if(!queueDrying.isEmpty()) {
			MyMessage myMessage = queueDrying.getFirst();
			myMessage.setWorkerForDrying(worker);
			sendToDrying(myMessage);
			return;

		}
		if(!queueMontage.isEmpty()) {
			MyMessage myMessage = queueMontage.getFirst();
			myMessage.setWorkerForMontage(worker);
			sendToMontage(myMessage);
			return;
		}

		if(!queueNonProcessed.isEmpty()) {
			MyMessage myMessage = queueNonProcessed.getFirst();
			myMessage.setWorkerForCutting(worker);
			WorkPlace wp = tryAssignFreeWorkplace();
			if(wp == null) {
				MyMessage returnWorkerA = new MyMessage(mySim());
				returnWorkerA.setWorkerForRelease(worker);
				myMessage.setWorkerForCutting(null);
				returnWorkerA.setAddressee(Id.agentPracovnikov);
				returnWorkerA.setCode(Mc.noticeUvolniRezanie);
				notice(returnWorkerA);
				return;
			}
			myMessage.setWorkPlace(wp);
			sendToCutting(myMessage);
			return;
		}

		MyMessage returnWorkerA = new MyMessage(mySim());
		returnWorkerA.setWorkerForRelease(worker);
		returnWorkerA.setAddressee(Id.agentPracovnikov);
		returnWorkerA.setCode(Mc.noticeUvolniRezanie);
		notice(returnWorkerA);

	}
	public void tryToReassignWorkerB(WorkerB worker) {
		if(!queueAssembly.isEmpty()) {
			MyMessage myMessage = queueAssembly.getFirst();
			myMessage.setWorkerForAssembly(worker);
			sendToAssembly(myMessage);
			return;
		}

		MyMessage returnWorkerB = new MyMessage(mySim());
		returnWorkerB.setWorkerForRelease(worker);
		returnWorkerB.setAddressee(Id.agentPracovnikov);
		returnWorkerB.setCode(Mc.noticeUvolniSkladanie);
		notice(returnWorkerB);
	}

	public void tryToReassignWorkerC(WorkerC worker) {
		if(!queueMontage.isEmpty()) {
			MyMessage myMessage = queueMontage.getFirst();
			myMessage.setWorkerForMontage(worker);
			sendToMontage(myMessage);
			return;
		}
		if(!queueStaining.isEmpty()) {
			MyMessage myMessage = queueStaining.getFirst();
			myMessage.setWorkerForStaining(worker);
			sendToStaining(myMessage);
			return;
		}

		MyMessage returnWorkerC = new MyMessage(mySim());
		returnWorkerC.setWorkerForRelease(worker);
		returnWorkerC.setAddressee(Id.agentPracovnikov);
		returnWorkerC.setCode(Mc.noticeUvolniMorenie);
		notice(returnWorkerC);
	}

	public void tryToReassignCutting(MyMessage myMessage) {
		if(myMessage.getWorkerForCutting() != null) {
			WorkPlace wp = tryAssignFreeWorkplace();
			if(wp == null) {
				return;
			}
			myMessage.setWorkPlace(wp);
			sendToCutting(myMessage);
			if(!queueNonProcessed.isEmpty()) {
				tryToReassignCutting(queueNonProcessed.getFirst());
			}
			return;
		}

		myMessage.setCode(Mc.rVyberPracovnikaRezanie);
		myMessage.setAddressee(Id.agentPracovnikov);
		request(myMessage);
	}


	public void tryToReassignStaining(MyMessage myMessage) {
		MyMessage newMessage = new MyMessage(myMessage);
		newMessage.setCode(Mc.rVyberPracovnikaMorenie);
		newMessage.setAddressee(Id.agentPracovnikov);
		request(newMessage);
	}

	public void tryToReassignAssembly(MyMessage myMessage) {
		MyMessage newMessage = new MyMessage(myMessage);
		newMessage.setCode(Mc.rVyberPracovnikaSkladanie);
		newMessage.setAddressee(Id.agentPracovnikov);
		request(newMessage);

	}

	public void tryToReassingDrying(MyMessage myMessage) {
		MyMessage newMessage = new MyMessage(myMessage);
		newMessage.setCode(Mc.rVyberPracovnikaSusenie);
		newMessage.setAddressee(Id.agentPracovnikov);
		request(newMessage);
	}

	public void tryToReassignMontage(MyMessage myMessage) {
		MyMessage newMessage = new MyMessage(myMessage);
		newMessage.setCode(Mc.rVyberPracovnikaMontaz);
		newMessage.setAddressee(Id.agentPracovnikov);
		request(newMessage);
	}
	public void sendToCutting(MyMessage myMessage) {
		if(myMessage.getWorkerForCutting() == null && myMessage.getWorkPlace() == null) {
			System.out.println("Chýba worker alebo workplace pri Cutting");
			throw new IllegalStateException("Tuto sa nemôže dostať");
		}
		queueNonProcessed.removeIf(msg ->
				msg.getFurniture() != null && msg.getFurniture().getId() == myMessage.getFurniture().getId()
		);

		MyMessage newMessage = new MyMessage(myMessage);
		newMessage.getWorkerForCutting().setState(WorkerBussyState.BUSY.getValue(), _mySim.currentTime());
		newMessage.getFurniture().setState(FurnitureStateValues.PREPARING_FOR_WORK.getValue());
		if(myMessage.getWorkerForCutting().getCurrentWorkPlace() == null) {
			newMessage.setCode(Mc.rPripravVSklade);
			newMessage.setAddressee(Id.agentSkladu);
		} else {
			newMessage.setCode(Mc.rPresunDoSkladu);
			newMessage.setAddressee(Id.agentPohybu);
		}
		request(newMessage);

	}

	public void sendToDrying(MyMessage myMessage) {
		if (myMessage.getWorkerForDrying() == null || myMessage.getWorkPlace() == null) {
			System.out.print("Chýba worker alebo workplace pri skladaní");
			throw new IllegalStateException("Chýba worker alebo workplace pri skladaní");
		}

		queueDrying.removeIf(msg ->
				msg.getFurniture() != null && msg.getFurniture().getId() == myMessage.getFurniture().getId()
		);

		MyMessage newMessage = new MyMessage(myMessage);
		WorkPlace current = myMessage.getWorkerForDrying().getCurrentWorkPlace();
		WorkPlace target = myMessage.getFurniture().getWorkPlace();
		newMessage.getFurniture().setState(FurnitureStateValues.PREPARING_FOR_WORK.getValue());
		newMessage.getWorkerForDrying().setState(WorkerBussyState.BUSY.getValue(), mySim().currentTime());
		if (current == null) {
			newMessage.setCode(Mc.rPresunZoSkladu);
			newMessage.setAddressee(Id.agentPohybu);
		} else if (!current.equals(target)) {
			newMessage.setCode(Mc.rPresunNaPracovisko);
			newMessage.setAddressee(Id.agentPohybu);
		} else {
			newMessage.setCode(Mc.urobSusenie);
			newMessage.setAddressee(Id.agentCinnosti);
		}

		request(newMessage);
	}

	public void sendToStaining(MyMessage myMessage) {
		if(myMessage.getWorkerForStaining() == null && myMessage.getWorkPlace() == null) {
			System.out.println("Chýba worker alebo workplace pri staining");
			throw new IllegalStateException("Tuto sa nemôže dostať");
		}
		queueStaining.removeIf(msg ->
				msg.getFurniture() != null && msg.getFurniture().getId() == myMessage.getFurniture().getId()
		);
		MyMessage newMessage = new MyMessage(myMessage);
		WorkPlace current = myMessage.getWorkerForStaining().getCurrentWorkPlace();
		WorkPlace target = myMessage.getFurniture().getWorkPlace();
		newMessage.getFurniture().setState(FurnitureStateValues.PREPARING_FOR_WORK.getValue());
		newMessage.getWorkerForStaining().setState(WorkerBussyState.BUSY.getValue(), _mySim.currentTime());
		if (current == null) {
			newMessage.setCode(Mc.rPresunZoSkladu);
			newMessage.setAddressee(Id.agentPohybu);
		} else if (!current.equals(target)) {
			newMessage.setCode(Mc.rPresunNaPracovisko);
			newMessage.setAddressee(Id.agentPohybu);
		} else {
			newMessage.setCode(Mc.rUrobMorenie);
			newMessage.setAddressee(Id.agentCinnosti);
		}
		request(newMessage);
	}

	public void sendToAssembly(MyMessage myMessage) {
		if (myMessage.getWorkerForAssembly() == null || myMessage.getWorkPlace() == null) {
			System.out.print("Chýba worker alebo workplace pri skladaní");
			throw new IllegalStateException("Chýba worker alebo workplace pri skladaní");
		}

		queueAssembly.removeIf(msg ->
				msg.getFurniture() != null && msg.getFurniture().getId() == myMessage.getFurniture().getId()
		);

		MyMessage newMessage = new MyMessage(myMessage);
		WorkPlace current = myMessage.getWorkerForAssembly().getCurrentWorkPlace();
		WorkPlace target = myMessage.getFurniture().getWorkPlace();
		newMessage.getFurniture().setState(FurnitureStateValues.PREPARING_FOR_WORK.getValue());
		newMessage.getWorkerForAssembly().setState(WorkerBussyState.BUSY.getValue(), mySim().currentTime());
		if (current == null) {
			newMessage.setCode(Mc.rPresunZoSkladu);
			newMessage.setAddressee(Id.agentPohybu);
		} else if (!current.equals(target)) {
			newMessage.setCode(Mc.rPresunNaPracovisko);
			newMessage.setAddressee(Id.agentPohybu);
		} else {
			newMessage.setCode(Mc.rUrobSkladanie);
			newMessage.setAddressee(Id.agentCinnosti);
		}

		request(newMessage);
	}


	public void sendToMontage(MyMessage myMessage) {
		if (myMessage.getWorkerForMontage() == null || myMessage.getWorkPlace() == null) {
			System.out.println("Chýba worker alebo workplace pri montage");
			throw new IllegalStateException("Chýba worker alebo workplace pri montáži");
		}

		queueMontage.removeIf(msg ->
				msg.getFurniture() != null && msg.getFurniture().getId() == myMessage.getFurniture().getId()
		);

		MyMessage newMessage = new MyMessage(myMessage);
		WorkPlace current = myMessage.getWorkerForMontage().getCurrentWorkPlace();
		WorkPlace target = myMessage.getFurniture().getWorkPlace();
		newMessage.getFurniture().setState(FurnitureStateValues.PREPARING_FOR_WORK.getValue());
		newMessage.getWorkerForMontage().setState(WorkerBussyState.BUSY.getValue(), _mySim.currentTime());
		if (current == null) {
			newMessage.setCode(Mc.rPresunZoSkladu);
			newMessage.setAddressee(Id.agentPohybu);
		} else if (!current.equals(target)) {
			newMessage.setCode(Mc.rPresunNaPracovisko);
			newMessage.setAddressee(Id.agentPohybu);
		} else {
			newMessage.setCode(Mc.rUrobMontaz);
			newMessage.setAddressee(Id.agentCinnosti);
		}

		request(newMessage);
	}



	public WorkPlace tryAssignFreeWorkplace() {
		WorkPlace wp = freeWorkplaces.poll();
		if (wp != null) {
			wp.setState(WorkPlaceStateValues.ASSIGNED.getValue());
		}
		return wp;
	}

	public void releaseWorkPlace(WorkPlace wp) {
		if (wp == null) return;

		wp.setState(WorkPlaceStateValues.NOT_WORKING.getValue());

		if (!queueNonProcessed.isEmpty()) {
			MyMessage msg = queueNonProcessed.getFirst();
			if (msg != null && msg.getWorkerForCutting() != null) {
				msg = queueNonProcessed.removeFirst();
				msg.setWorkPlace(wp);
				wp.setState(WorkPlaceStateValues.ASSIGNED.getValue());
				sendToCutting(msg);
				return;
			}
		}

		wp.clear();
		freeWorkplaces.add(wp);
	}


	public void processNoticeSpracujObjednavku(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();
		MySimulation mySimulation = (MySimulation) _mySim;
		msg.getOrder().setState(OrderStateValues.PROCESSING_ORDER.getValue());
		for (Furniture furniture : msg.getOrder().getFurnitureList()) {
			MyMessage furnitureMsg = new MyMessage(mySimulation);
			furnitureMsg.setOrder(msg.getOrder());
			furnitureMsg.setFurniture(furniture);
			furnitureMsg.setWorkPlace(null);
			furnitureMsg.getFurniture().setState(FurnitureStateValues.WAITING_IN_QUEUE_NEW.getValue());
			furnitureMsg.setWorkerForCutting(null);
			furnitureMsg.setWorkerForAssembly(null);
			furnitureMsg.setWorkerForMontage(null);
			this.queueNonProcessed.addLast(furnitureMsg);
		}

		if(!queueNonProcessed.isEmpty()) {
			tryToReassignCutting(queueNonProcessed.getFirst());
		}

	}

	//meta! sender="AgentPracovnikov", id="168", type="Response"
	public void processRVyberPracovnikaRezanie(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();
		if(msg.getWorkerForCutting() == null) {
			return;
		}

		WorkPlace wp = tryAssignFreeWorkplace();
		if (wp == null) {
			MyMessage returnWorkerA = new MyMessage(mySim());
			returnWorkerA.setWorkerForRelease(msg.getWorkerForCutting());
			msg.setWorkerForCutting(null);
			returnWorkerA.setAddressee(Id.agentPracovnikov);
			returnWorkerA.setCode(Mc.noticeUvolniRezanie);
			notice(returnWorkerA);
			return;
		}
		msg.setWorkPlace(wp);

		sendToCutting(msg);

		if(!queueNonProcessed.isEmpty()) {
			tryToReassignCutting(queueNonProcessed.getFirst());
		}

	}

// Pracovnici Response agent Pracovnikov

	//meta! sender="AgentPracovnikov", id="XXX", type="Response"
	public void processRVyberPracovnikaLakovanie(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();


	}


	//meta! sender="AgentPracovnikov", id="XXX", type="Response"
	public void processRVyberPracovnikaMorenie(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();
		if(msg.getWorkerForStaining() == null) {
			return;
		}
		sendToStaining(msg);
	}


	//meta! sender="AgentPracovnikov", id="XXX", type="Response"
	public void processRVyberPracovnikaSkladanie(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();
		if(msg.getWorkerForAssembly() == null) {
			return;
		}
		sendToAssembly(msg);
		/*if(!queueAssembly.isEmpty()) {
			tryToReassignAssembly(queueAssembly.getFirst());
		}*/
	}



	//meta! sender="AgentPracovnikov", id="XXX", type="Response"
	public void processRVyberPracovnikaMontaz(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();
		if(msg.getWorkerForMontage() == null) {
			return;
		}
		sendToMontage(msg);
		/*if(!queueMontage.isEmpty()) {
			tryToReassignMontage(queueMontage.getFirst());
		}*/
	}

	//Sklad
	//meta! sender="AgentSkladu", id="324", type="Response"
	public void processRPripravVSklade(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();
		msg.setCode(Mc.rPresunZoSkladu);
		msg.getWorkerForCutting().setState(WorkerBussyState.MOVING_FROM_STORAGE.getValue(), mySim().currentTime());
		msg.setAddressee(_mySim.findAgent(Id.agentPohybu));
		request(msg);
	}

	//pohyb
	//meta! sender="AgentPohybu", id="138", type="Response"
	public void processRPresunDoSkladu(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();
		msg.setCode(Mc.rPripravVSklade);
		msg.setAddressee(mySim().findAgent(Id.agentSkladu));
		request(msg);
	}

	//meta! sender="AgentPohybu", id="157", type="Response"
	//meta! sender="AgentPohybu", id="157", type="Response"
	public void processRPresunNaPracovisko(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();

		if (msg.getWorkerForStaining() != null) {
			msg.setCode(Mc.rUrobMorenie);
		} else if (msg.getWorkerForPainting() != null) {
			msg.setCode(Mc.rUrobLakovanie);
		} else if (msg.getWorkerForAssembly() != null) {
			msg.setCode(Mc.rUrobSkladanie);
		} else if (msg.getWorkerForMontage() != null) {
			msg.setCode(Mc.rUrobMontaz);
		} else if (msg.getWorkerForDrying() != null) {
			msg.setCode(Mc.urobSusenie); // použiješ správny enum
		} else {
			throw new IllegalStateException("Presun na pracovisko: žiadny worker nie je nastavený.");
		}

		msg.setAddressee(mySim().findAgent(Id.agentCinnosti));
		request(msg);
	}



	//meta! sender="AgentPohybu", id="385", type="Response"
	public void processRPresunZoSkladu(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();

		if (msg.getWorkerForCutting() != null) {
			msg.setCode(Mc.rUrobRezanie);
		} else if (msg.getWorkerForStaining() != null) {
			msg.setCode(Mc.rUrobMorenie);
		} else if (msg.getWorkerForPainting() != null) {
			msg.setCode(Mc.rUrobLakovanie);
		} else if (msg.getWorkerForAssembly() != null) {
			msg.setCode(Mc.rUrobSkladanie);
		} else if (msg.getWorkerForMontage() != null) {
			msg.setCode(Mc.rUrobMontaz);
		} else if (msg.getWorkerForDrying() != null) {
			msg.setCode(Mc.urobSusenie);  // pridaj správny message code
		} else {
			throw new IllegalStateException("Presun zo skladu: žiadny worker nie je nastavený.");
		}

		msg.setAddressee(mySim().findAgent(Id.agentCinnosti));
		request(msg);
	}






	//Cinnosti

	//meta! sender="AgentCinnosti", id="284", type="Response"
	public void processRUrobRezanie(MessageForm message) {

		MyMessage msg = (MyMessage) message.createCopy();
		Worker previousWorkerFromCutting = msg.getWorkerForCutting();
		msg.setWorkerForCutting(null);

		msg.getFurniture().setState(FurnitureStateValues.WAITING_IN_QUEUE_STAINING.getValue());

		queueStaining.addLast(msg);
		tryToReassignStaining(queueStaining.getFirst());
		tryToReassignWorkerA((WorkerA) previousWorkerFromCutting);
	}

	//meta! sender="AgentCinnosti", id="286", type="Response"
	public void processRUrobMorenie(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();
		MySimulation mySimulation = (MySimulation) _mySim;
		if(mySimulation.getGenerators().getRealisePaintingDist().nextDouble() < 0.15) {
			Worker workerForPainting = msg.getWorkerForStaining();
			msg.setWorkerForStaining(null);
			msg.setWorkerForPainting(workerForPainting);
			msg.setCode(Mc.rUrobLakovanie);
			msg.setAddressee(mySim().findAgent(Id.agentCinnosti));
			request(msg);
		} else {
			Worker previousWorkerFromStaining = msg.getWorkerForStaining();
			msg.setWorkerForStaining(null);
			queueDrying.addLast(msg);
			msg.getFurniture().setState(FurnitureStateValues.WAITING_IN_QUEUE_DRYING.getValue());

			tryToReassignWorkerC((WorkerC) previousWorkerFromStaining);
			tryToReassingDrying(queueDrying.getFirst());
		}

	}


	//meta! sender="AgentCinnosti", id="287", type="Response"
	public void processRUrobLakovanie(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();
		Worker previousWorkerFromPainting = msg.getWorkerForPainting();
		msg.setWorkerForPainting(null);
		queueDrying.addLast(msg);
		msg.getFurniture().setState(FurnitureStateValues.WAITING_IN_QUEUE_DRYING.getValue());

		tryToReassignWorkerC((WorkerC) previousWorkerFromPainting);
		if(!queueDrying.isEmpty()) {
			tryToReassingDrying(queueDrying.getFirst());

		}


	}

	//meta! sender="AgentCinnosti", id="288", type="Response"
	public void processRUrobSkladanie(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();
		Worker previousWorkerFromAssembly = msg.getWorkerForAssembly();
		msg.setWorkerForAssembly(null);

		if(msg.getFurniture().getType() == 3) {
			queueMontage.addLast(msg);
			msg.getFurniture().setState(FurnitureStateValues.WAITING_IN_QUEUE_MONTAGE.getValue());
			tryToReassignMontage(queueMontage.getFirst());
		} else {
			msg.getFurniture().setIsDone(_mySim.currentTime());
			releaseWorkPlace(msg.getWorkPlace());
			msg.getWorkPlace().clear();
			msg.getFurniture().setWorkPlace(null);
			Order order = msg.getOrder();
			if(order.isOrderFinished()) {
				MyMessage msg2 = new MyMessage(mySim());
				msg2.setCode(Mc.noticeHotovaObjednavka);
				msg2.setAddressee(mySim().findAgent(Id.agentModelu));
				msg2.setOrder(order);
				notice(msg2);
			}

		}
		tryToReassignWorkerB((WorkerB) previousWorkerFromAssembly);
	}


	//meta! sender="AgentCinnosti", id="289", type="Response"
	// Použitie v processRUrobMontaz
	public void processRUrobMontaz(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();
		Worker previousWorkerForMontage = msg.getWorkerForMontage();
		msg.setWorkerForMontage(null);

		if(previousWorkerForMontage instanceof WorkerA) {
			tryToReassignWorkerA((WorkerA) previousWorkerForMontage);
		} else if(previousWorkerForMontage instanceof WorkerC) {
			tryToReassignWorkerC((WorkerC) previousWorkerForMontage);
		}

		msg.getFurniture().setIsDone(_mySim.currentTime());
		releaseWorkPlace(msg.getWorkPlace());
		msg.getFurniture().setWorkPlace(null);

		if(msg.getFurniture().getOrder().isOrderFinished()) {
			MyMessage msg2 = new MyMessage(mySim());
			msg2.setCode(Mc.noticeHotovaObjednavka);
			msg2.setAddressee(mySim().findAgent(Id.agentModelu));
			msg2.setOrder(msg.getFurniture().getOrder());
			notice(msg2);
		}
	}



	//meta! sender="AgentCinnosti", id="430", type="Response"
	public void processUrobSusenie(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		Worker previousWorkerFromDrying = msg.getWorkerForDrying();
		msg.setWorkerForDrying(null);
		queueAssembly.addLast(msg);
		msg.getFurniture().setState(FurnitureStateValues.WAITING_IN_QUEUE_ASSEMBLY.getValue());

		tryToReassignWorkerA((WorkerA) previousWorkerFromDrying);
		if(!queueAssembly.isEmpty()) {
			tryToReassignAssembly(queueAssembly.getFirst());

		}
	}

	//meta! sender="AgentPracovnikov", id="435", type="Response"
	public void processRVyberPracovnikaSusenie(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		if(msg.getWorkerForDrying() == null) {
			return;
		}
		sendToDrying(msg);
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
		case Mc.rUrobMontaz:
			processRUrobMontaz(message);
		break;

		case Mc.noticeSpracujObjednavku:
			processNoticeSpracujObjednavku(message);
		break;

		case Mc.init:
			processInit(message);
		break;

		case Mc.rPripravVSklade:
			processRPripravVSklade(message);
		break;

		case Mc.urobSusenie:
			processUrobSusenie(message);
		break;

		case Mc.rPresunDoSkladu:
			processRPresunDoSkladu(message);
		break;

		case Mc.rUrobRezanie:
			processRUrobRezanie(message);
		break;

		case Mc.rVyberPracovnikaSkladanie:
			processRVyberPracovnikaSkladanie(message);
		break;

		case Mc.rVyberPracovnikaMontaz:
			processRVyberPracovnikaMontaz(message);
		break;

		case Mc.rUrobSkladanie:
			processRUrobSkladanie(message);
		break;

		case Mc.rPresunNaPracovisko:
			processRPresunNaPracovisko(message);
		break;

		case Mc.rVyberPracovnikaSusenie:
			processRVyberPracovnikaSusenie(message);
		break;

		case Mc.rUrobMorenie:
			processRUrobMorenie(message);
		break;

		case Mc.rVyberPracovnikaMorenie:
			processRVyberPracovnikaMorenie(message);
		break;

		case Mc.rPresunZoSkladu:
			processRPresunZoSkladu(message);
		break;

		case Mc.rVyberPracovnikaLakovanie:
			processRVyberPracovnikaLakovanie(message);
		break;

		case Mc.rUrobLakovanie:
			processRUrobLakovanie(message);
		break;

		case Mc.rVyberPracovnikaRezanie:
			processRVyberPracovnikaRezanie(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public AgentNabytku myAgent()
	{
		return (AgentNabytku)super.myAgent();
	}

}
