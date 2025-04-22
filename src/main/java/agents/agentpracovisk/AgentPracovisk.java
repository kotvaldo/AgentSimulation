package agents.agentpracovisk;

import OSPABA.*;
import simulation.*;

//meta! id="62"
public class AgentPracovisk extends Agent
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
