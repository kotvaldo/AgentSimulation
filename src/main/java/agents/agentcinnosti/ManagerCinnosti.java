package agents.agentcinnosti;

import Enums.FurnitureStateValues;
import Enums.WorkerBussyState;
import OSPABA.*;
import simulation.*;

//meta! id="267"
public class ManagerCinnosti extends OSPABA.Manager
{
	public ManagerCinnosti(int id, Simulation mySim, Agent myAgent)
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
	}

	//meta! sender="AgentNabytku", id="268", type="Notice"
	public void processInit(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="284", type="Request"
	public void processRUrobRezanie(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		msg.getWorkerForCutting().setState(WorkerBussyState.BUSY.getValue());
		msg.getFurniture().setState(FurnitureStateValues.PROCESSING_CUTTING.getValue());
		msg.getWorkPlace().setActualWorkingWorker(msg.getWorkerForCutting());
		msg.setCode(Mc.start);
		msg.setAddressee(myAgent().findAssistant(Id.procesRezania));
		startContinualAssistant(msg);
	}

	//meta! sender="AgentNabytku", id="288", type="Request"
	public void processRUrobSkladanie(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();
		msg.getWorkerForAssembly().setState(WorkerBussyState.BUSY.getValue());
		msg.getFurniture().setState(FurnitureStateValues.PROCESSING_ASSEMBLY.getValue());
		msg.getWorkPlace().setActualWorkingWorker(msg.getWorkerForAssembly());
		msg.setCode(Mc.start);
		msg.setAddressee(myAgent().findAssistant(Id.procesSkladania));
		startContinualAssistant(msg);
	}


	//meta! sender="ProcesLakovania", id="277", type="Finish"
	public void processFinishProcesLakovania(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		msg.getWorkPlace().setActualWorkingWorker(null);
		msg.getWorkerForPainting().setState(WorkerBussyState.NON_BUSY.getValue());
		msg.setCode(Mc.rUrobLakovanie);
		msg.setAddressee(mySim().findAgent(Id.agentNabytku));
		response(msg);
	}

	//meta! sender="ProcesMontaze", id="281", type="Finish"
	public void processFinishProcesMontaze(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		msg.getWorkPlace().setActualWorkingWorker(null);
		msg.getWorkerForMontage().setState(WorkerBussyState.NON_BUSY.getValue());
		msg.setCode(Mc.rUrobMontaz);
		msg.setAddressee(mySim().findAgent(Id.agentNabytku));
		response(msg);
	}

	//meta! sender="ProcesMorenia", id="279", type="Finish"
	public void processFinishProcesMorenia(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		msg.getWorkPlace().setActualWorkingWorker(null);
		msg.getWorkerForStaining().setState(WorkerBussyState.NON_BUSY.getValue());
		msg.setCode(Mc.rUrobMorenie);
		msg.setAddressee(mySim().findAgent(Id.agentNabytku));
		response(msg);
	}

	//meta! sender="ProcesSkladania", id="283", type="Finish"
	public void processFinishProcesSkladania(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		msg.getWorkPlace().setActualWorkingWorker(null);
		msg.getWorkerForAssembly().setState(WorkerBussyState.NON_BUSY.getValue());
		msg.setCode(Mc.rUrobSkladanie);
		msg.setAddressee(mySim().findAgent(Id.agentNabytku));
		response(msg);
	}

	//meta! sender="ProcesRezania", id="275", type="Finish"
	public void processFinishProcesRezania(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		msg.getWorkPlace().setActualWorkingWorker(null);
		msg.setCode(Mc.rUrobRezanie);
		msg.getWorkerForCutting().setState(WorkerBussyState.NON_BUSY.getValue());
		msg.setAddressee(mySim().findAgent(Id.agentNabytku));
		response(msg);
	}

	//meta! sender="AgentNabytku", id="286", type="Request"
	public void processRUrobMorenie(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();
		msg.getWorkerForStaining().setState(WorkerBussyState.BUSY.getValue());
		msg.getFurniture().setState(FurnitureStateValues.PROCESSING_STAINING.getValue());
		msg.getWorkPlace().setActualWorkingWorker(msg.getWorkerForStaining());
		msg.setCode(Mc.start);
		msg.setAddressee(myAgent().findAssistant(Id.procesMorenia));
		startContinualAssistant(msg);
	}


	//meta! sender="AgentNabytku", id="289", type="Request"
	public void processRUrobMontaz(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();
		msg.getWorkerForMontage().setState(WorkerBussyState.BUSY.getValue());
		msg.getFurniture().setState(FurnitureStateValues.PROCESSING_MONTAGE.getValue());
		msg.getWorkPlace().setActualWorkingWorker(msg.getWorkerForMontage());
		msg.setCode(Mc.start);
		msg.setAddressee(myAgent().findAssistant(Id.procesMontaze));
		startContinualAssistant(msg);
	}



	//meta! sender="AgentNabytku", id="287", type="Request"
	public void processRUrobLakovanie(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();
		msg.getWorkerForPainting().setState(WorkerBussyState.BUSY.getValue());
		msg.getFurniture().setState(FurnitureStateValues.PROCESSING_PAINTING.getValue());
		msg.getWorkPlace().setActualWorkingWorker(msg.getWorkerForPainting());
		msg.setCode(Mc.start);
		msg.setAddressee(myAgent().findAssistant(Id.procesLakovania));
		startContinualAssistant(msg);
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
		case Mc.rUrobMorenie:
			processRUrobMorenie(message);
		break;

		case Mc.finish:
			switch (message.sender().id())
			{
			case Id.procesLakovania:
				processFinishProcesLakovania(message);
			break;

			case Id.procesMontaze:
				processFinishProcesMontaze(message);
			break;

			case Id.procesMorenia:
				processFinishProcesMorenia(message);
			break;

			case Id.procesSkladania:
				processFinishProcesSkladania(message);
			break;

			case Id.procesRezania:
				processFinishProcesRezania(message);
			break;
			}
		break;

		case Mc.rUrobRezanie:
			processRUrobRezanie(message);
		break;

		case Mc.rUrobSkladanie:
			processRUrobSkladanie(message);
		break;

		case Mc.init:
			processInit(message);
		break;

		case Mc.rUrobMontaz:
			processRUrobMontaz(message);
		break;

		case Mc.rUrobLakovanie:
			processRUrobLakovanie(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public AgentCinnosti myAgent()
	{
		return (AgentCinnosti)super.myAgent();
	}

}
