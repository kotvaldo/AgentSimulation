package agents.agentpracovisk;

import OSPABA.*;
import entities.WorkPlace;
import simulation.*;

import java.util.LinkedList;

//meta! id="62"
public class ManagerPracovisk extends OSPABA.Manager
{
	private LinkedList<WorkPlace> freeWorkPlaces = new LinkedList<>();
	public ManagerPracovisk(int id, Simulation mySim, Agent myAgent)
	{
		super(id, mySim, myAgent);
		init();
		MySimulation mySimulation = (MySimulation) _mySim;
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

	public WorkPlace dajPrveVolneMiestoPodlaId() {
		MySimulation mySimulation = (MySimulation) _mySim;

        return mySimulation.getWorkPlacesArrayList().stream()
                .filter(p -> !p.isBusy())
                .findFirst()
                .orElse(null);
	}
	//meta! sender="AgentNabytku", id="72", type="Notice"
	public void processInit(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="207", type="Notice"
	public void processNoticeUvolniPracovneMiesto(MessageForm message)
	{
		MyMessage myMessage = (MyMessage) message.createCopy();
		myMessage.getWorkPlace().setBusy(false);
	}

	//meta! sender="AgentNabytku", id="182", type="Response"
	public void processRDajVolnePracovneMiesto(MessageForm message)
	{
		MyMessage myMessage = (MyMessage) message;
		WorkPlace workPlace = dajPrveVolneMiestoPodlaId();
		myMessage.setWorkPlace(workPlace);
		myMessage.getWorkPlace().setBusy(true);

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
		case Mc.rDajVolnePracovneMiesto:
			processRDajVolnePracovneMiesto(message);
		break;

		case Mc.noticeUvolniPracovneMiesto:
			processNoticeUvolniPracovneMiesto(message);
		break;

		case Mc.init:
			processInit(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public AgentPracovisk myAgent()
	{
		return (AgentPracovisk)super.myAgent();
	}

}
