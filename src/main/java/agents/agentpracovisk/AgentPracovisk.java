package agents.agentpracovisk;

import OSPABA.*;
import entities.WorkPlace;
import simulation.*;

import java.util.LinkedList;

//meta! id="62"
public class AgentPracovisk extends OSPABA.Agent
{

	public AgentPracovisk(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		init();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new ManagerPracovisk(Id.managerPracovisk, mySim(), this);
		addOwnMessage(Mc.init);
		addOwnMessage(Mc.noticeUvolniPracovneMiesto);
		addOwnMessage(Mc.rDajVolnePracovneMiesto);
	}
	//meta! tag="end"
}
