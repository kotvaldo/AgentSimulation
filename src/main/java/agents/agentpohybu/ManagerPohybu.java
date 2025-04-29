package agents.agentpohybu;

import Enums.WorkerBussyState;
import OSPABA.*;
import simulation.*;

//meta! id="35"
public class ManagerPohybu extends Manager
{
	public ManagerPohybu(int id, Simulation mySim, Agent myAgent)
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

	//meta! sender="AgentNabytku", id="138", type="Request"
	public void processRPresunDoSkladu(MessageForm message)
	{
		MyMessage myMessage = (MyMessage) message.createCopy();
		myMessage.setAddressee(myAgent().findAssistant(Id.procesPresunDoSkladu));
		if(myMessage.getWorkerForCutting() != null) {
			myMessage.getWorkerForCutting().setState(WorkerBussyState.MOVING_TO_STORAGE.getValue());
			myMessage.setCode(Mc.start);
			startContinualAssistant(myMessage);
		}
	}

	//meta! sender="AgentNabytku", id="385", type="Request"
	public void processRPresunZoSkladu(MessageForm message)
	{
		MyMessage myMessage = (MyMessage) message.createCopy();
		myMessage.setAddressee(myAgent().findAssistant(Id.procesPresunZoSkladu));
		myMessage.setCode(Mc.start);
		startContinualAssistant(myMessage);
	}

	//meta! sender="AgentNabytku", id="71", type="Notice"
	public void processInit(MessageForm message)
	{
	}

	//meta! sender="ProcesPresunDoSkladu", id="117", type="Finish"
	public void processFinishProcesPresunDoSkladu(MessageForm message)
	{
		MyMessage myMessage = (MyMessage) message.createCopy();
		myMessage.setCode(Mc.rPresunDoSkladu);
		if(myMessage.getWorkerForCutting() != null) {
			myMessage.getWorkerForCutting().setCurrentWorkPlace(null);
		}
		myMessage.setAddressee(mySim().findAgent(Id.agentNabytku));
		response(myMessage);
	}

	//meta! sender="ProcesPresunZoSkladu", id="395", type="Finish"
	public void processFinishProcesPresunZoSkladu(MessageForm message)
	{
		MyMessage myMessage = (MyMessage) message.createCopy();
		myMessage.setCode(Mc.rPresunZoSkladu);
		if(myMessage.getWorkerForCutting() != null) {
			myMessage.getWorkerForCutting().setCurrentWorkPlace(myMessage.getWorkPlace());
			myMessage.getWorkPlace().setActualWorkingWorker(myMessage.getWorkerForCutting());
		} else if(myMessage.getWorkerForAssembly() != null) {
			myMessage.getWorkerForAssembly().setCurrentWorkPlace(myMessage.getWorkPlace());
			myMessage.getWorkPlace().setActualWorkingWorker(myMessage.getWorkerForAssembly());
		} else if(myMessage.getWorkerForMontage() != null) {
			myMessage.getWorkerForMontage().setCurrentWorkPlace(myMessage.getWorkPlace());
			myMessage.getWorkPlace().setActualWorkingWorker(myMessage.getWorkerForMontage());
		}
		myMessage.setAddressee(mySim().findAgent(Id.agentNabytku));
		response(myMessage);
	}

	//meta! sender="ProcesPresunNaPracovisko", id="115", type="Finish"
	public void processFinishProcesPresunNaPracovisko(MessageForm message)
	{

	}

	//meta! sender="AgentNabytku", id="157", type="Request"
	public void processRPresunNaPracovisko(MessageForm message)
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
		case Mc.rPresunNaPracovisko:
			processRPresunNaPracovisko(message);
		break;

		case Mc.init:
			processInit(message);
		break;

		case Mc.finish:
			switch (message.sender().id())
			{
			case Id.procesPresunDoSkladu:
				processFinishProcesPresunDoSkladu(message);
			break;

			case Id.procesPresunZoSkladu:
				processFinishProcesPresunZoSkladu(message);
			break;

			case Id.procesPresunNaPracovisko:
				processFinishProcesPresunNaPracovisko(message);
			break;
			}
		break;

		case Mc.rPresunDoSkladu:
			processRPresunDoSkladu(message);
		break;

		case Mc.rPresunZoSkladu:
			processRPresunZoSkladu(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public AgentPohybu myAgent()
	{
		return (AgentPohybu)super.myAgent();
	}

}
