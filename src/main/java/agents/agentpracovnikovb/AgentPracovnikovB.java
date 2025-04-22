package agents.agentpracovnikovb;

import OSPABA.*;
import simulation.*;

//meta! id="229"
public class AgentPracovnikovB extends OSPABA.Agent
{
	public AgentPracovnikovB(int id, Simulation mySim, Agent parent)
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
		new ManagerPracovnikovB(Id.managerPracovnikovB, mySim(), this);
		addOwnMessage(Mc.noticeUvolniB);
		addOwnMessage(Mc.rVyberPracovnikaBRSkladanie);
	}
	//meta! tag="end"
}
