package agents.agentpracovnikovc;

import OSPABA.*;
import simulation.*;

//meta! id="230"
public class AgentPracovnikovC extends OSPABA.Agent
{
	public AgentPracovnikovC(int id, Simulation mySim, Agent parent)
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
		new ManagerPracovnikovC(Id.managerPracovnikovC, mySim(), this);
		addOwnMessage(Mc.rVyberPracovnikaC);
		addOwnMessage(Mc.uvolniPracovnikaC);
	}
	//meta! tag="end"
}
