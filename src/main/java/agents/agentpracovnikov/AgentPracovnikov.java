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
		addOwnMessage(Mc.rVyberPracovnikaB);
		addOwnMessage(Mc.init);
		addOwnMessage(Mc.rVyberPracovnikaC);
		addOwnMessage(Mc.rVyberPracovnikaLakovanie);
		addOwnMessage(Mc.rVyberPracovnikaA);
		addOwnMessage(Mc.rVyberPracovnikaMorenie);
		addOwnMessage(Mc.noticeUvolniPracovnikaB);
		addOwnMessage(Mc.rVyberPracovnikaSkladanie);
		addOwnMessage(Mc.rVyberPracovnikaMontaz);
		addOwnMessage(Mc.noticeUvolniPracovnikaA);
		addOwnMessage(Mc.rVyberPracovnikaRezanie);
		addOwnMessage(Mc.noticeUvolniPracovnikaC);
	}
	//meta! tag="end"
}
