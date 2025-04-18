package agents.agentpracovnikov;

import OSPABA.*;
import simulation.*;

//meta! id="5"
public class AgentPracovnikov extends OSPABA.Agent
{
	public AgentPracovnikov(int id, Simulation mySim, Agent parent)
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
		new ManagerPracovnikov(Id.managerPracovnikov, mySim(), this);
		addOwnMessage(Mc.init);
		addOwnMessage(Mc.vyberPracovnikaMorenie);
		addOwnMessage(Mc.uvolneniePracovnikaC);
		addOwnMessage(Mc.vyberPracovnikaMontaz);
		addOwnMessage(Mc.uvolneniePracovnikaB);
		addOwnMessage(Mc.uvolneniePracovnikaA);
		addOwnMessage(Mc.vyberPracovnikaLakovanie);
		addOwnMessage(Mc.vyberPracovnikaC);
		addOwnMessage(Mc.vyberPracovnikaB);
		addOwnMessage(Mc.vyberPracovnikaA);
		addOwnMessage(Mc.vyberPracovnikaRezanie);
		addOwnMessage(Mc.vyberPracovnikaSkladanie);
	}
	//meta! tag="end"
}
