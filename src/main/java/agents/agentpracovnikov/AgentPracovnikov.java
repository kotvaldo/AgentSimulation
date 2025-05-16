package agents.agentpracovnikov;

import OSPABA.*;
import simulation.*;

//meta! id="5"
public class AgentPracovnikov extends Agent
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
		addOwnMessage(Mc.noticeUvolniSkladanie);
		addOwnMessage(Mc.noticeUvolniRezanie);
		addOwnMessage(Mc.rVyberPracovnikaLakovanie);
		addOwnMessage(Mc.noticeUvolniLakovanie);
		addOwnMessage(Mc.rVyberPracovnikaASusenie);
		addOwnMessage(Mc.rVyberPracovnikaARezanie);
		addOwnMessage(Mc.rVyberPracovnikaSkladanie);
		addOwnMessage(Mc.rVyberPracovnikaMontaz);
		addOwnMessage(Mc.rVyberPracovnikaBRSkladanie);
		addOwnMessage(Mc.rVyberPracovnikaCMontaz);
		addOwnMessage(Mc.rVyberPracovnikaSusenie);
		addOwnMessage(Mc.rVyberPracovnikaMorenie);
		addOwnMessage(Mc.noticeUvolniMorenie);
		addOwnMessage(Mc.rVyberPracovnikaCMorenie);
		addOwnMessage(Mc.noticeUvolniMontaz);
		addOwnMessage(Mc.rVyberPracovnikaCLakovanie);
		addOwnMessage(Mc.rVyberPracovnikaAMontaz);
		addOwnMessage(Mc.rVyberPracovnikaRezanie);
	}
	//meta! tag="end"
}
