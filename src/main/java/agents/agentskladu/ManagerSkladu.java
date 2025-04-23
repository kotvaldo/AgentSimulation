package agents.agentskladu;

import Enums.WorkerBussyState;
import OSPABA.*;
import simulation.*;

//meta! id="118"
public class ManagerSkladu extends OSPABA.Manager
{
	public ManagerSkladu(int id, Simulation mySim, Agent myAgent)
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

	//meta! sender="AgentNabytku", id="130", type="Notice"
	public void processInit(MessageForm message)
	{
	}

	//meta! sender="ProcesPripravaVSklade", id="129", type="Finish"
	public void processFinish(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		msg.setCode(Mc.rPripravVSklade);
		msg.setAddressee(_mySim.findAgent(Id.agentNabytku));
		response(msg);
	}

	//meta! sender="AgentNabytku", id="324", type="Request"
	public void processRPripravVSklade(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		msg.getWorkerA().setState(WorkerBussyState.PREPARING_IN_STORAGE.getValue());
		msg.setCode(Mc.start);
		msg.setAddressee(myAgent().findAssistant(Id.procesPripravaVSklade));
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
		case Mc.rPripravVSklade:
			processRPripravVSklade(message);
		break;

		case Mc.init:
			processInit(message);
		break;

		case Mc.finish:
			processFinish(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public AgentSkladu myAgent()
	{
		return (AgentSkladu)super.myAgent();
	}

}
