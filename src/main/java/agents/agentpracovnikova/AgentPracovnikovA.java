package agents.agentpracovnikova;

import OSPABA.*;
import simulation.*;

//meta! id="228"
public class AgentPracovnikovA extends OSPABA.Agent
{
	public AgentPracovnikovA(int id, Simulation mySim, Agent parent)
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
		new ManagerPracovnikovA(Id.managerPracovnikovA, mySim(), this);
		addOwnMessage(Mc.uvolniPracovnikaA);
		addOwnMessage(Mc.rVyberPracovnikaA);
	}
	//meta! tag="end"
}
