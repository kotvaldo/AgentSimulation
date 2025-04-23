package agents.agentpohybu;

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
	public void processRPresunDoSkladu(MessageForm message) {
		MyMessage msg = (MyMessage) message;
		msg.setCode(Mc.start);

		msg.setAddressee(myAgent().findAssistant(Id.procesPresunDoSkladu));
		startContinualAssistant(msg);
	}

	//meta! sender="AgentNabytku", id="385", type="Request"
	public void processRPresunZoSkladu(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		msg.setAddressee(myAgent().findAssistant(Id.procesPresunZoSkladu));
		msg.setCode(Mc.start);
		startContinualAssistant(msg);
	}

	//meta! sender="AgentNabytku", id="71", type="Notice"
	public void processInit(MessageForm message)
	{
	}

	//meta! sender="ProcesPresunDoSkladu", id="117", type="Finish"
	public void processFinishProcesPresunDoSkladu(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		msg.setCode(Mc.rPresunDoSkladu);
		msg.setAddressee(_mySim.findAgent(Id.agentNabytku));
		response(msg);
	}

	//meta! sender="ProcesPresunZoSkladu", id="395", type="Finish"
	public void processFinishProcesPresunZoSkladu(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;
		msg.setCode(Mc.rPresunZoSkladu);

		if (msg.getWorkerA() != null) {
			msg.getWorkerA().setCurrentWorkPlace(msg.getWorkPlace());
		} else if (msg.getWorkerB() != null) {
			msg.getWorkerB().setCurrentWorkPlace(msg.getWorkPlace());
		} else if (msg.getWorkerC() != null) {
			msg.getWorkerC().setCurrentWorkPlace(msg.getWorkPlace());
		}
		if (msg.getWorkPlace() != null) {
			msg.getWorkPlace().setWorker(msg.getWorkerA() != null ? msg.getWorkerA() :
					msg.getWorkerB() != null ? msg.getWorkerB() :
							msg.getWorkerC());
		}


		msg.setAddressee(_mySim.findAgent(Id.agentNabytku));
		response(msg);
	}

	//meta! sender="ProcesPresunNaPracovisko", id="115", type="Finish"
	public void processFinishProcesPresunNaPracovisko(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;
		msg.setCode(Mc.rPresunNaPracovisko);

		if (msg.getWorkerA() != null) {
			msg.getWorkerA().setCurrentWorkPlace(msg.getWorkPlace());
		} else if (msg.getWorkerB() != null) {
			msg.getWorkerB().setCurrentWorkPlace(msg.getWorkPlace());
		} else if (msg.getWorkerC() != null) {
			msg.getWorkerC().setCurrentWorkPlace(msg.getWorkPlace());
		}

		if (msg.getWorkPlace() != null) {
			msg.getWorkPlace().setWorker(msg.getWorkerA() != null ? msg.getWorkerA() :
					msg.getWorkerB() != null ? msg.getWorkerB() :
							msg.getWorkerC());
		}

		msg.setAddressee(_mySim.findAgent(Id.agentNabytku));
		response(msg);
	}

	//meta! sender="AgentNabytku", id="157", type="Request"
	public void processRPresunNaPracovisko(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		msg.setCode(Mc.start);
		msg.setAddressee(myAgent().findAssistant(Id.procesPresunNaPracovisko));
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

		case Mc.init:
			processInit(message);
		break;

		case Mc.rPresunNaPracovisko:
			processRPresunNaPracovisko(message);
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
