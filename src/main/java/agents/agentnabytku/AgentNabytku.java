package agents.agentnabytku;

import OSPABA.*;
import simulation.*;

//meta! id="9"
public class AgentNabytku extends OSPABA.Agent
{
	public AgentNabytku(int id, Simulation mySim, Agent parent)
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
		new ManagerNabytku(Id.managerNabytku, mySim(), this);
		addOwnMessage(Mc.rPresunDoSkladu);
		addOwnMessage(Mc.init);
		addOwnMessage(Mc.rVyberPracovnikaLakovanie);
		addOwnMessage(Mc.rPresunNaPracovisko);
		addOwnMessage(Mc.rVyberPracovnikaSkladanie);
		addOwnMessage(Mc.rVyberPracovnikaMontaz);
		addOwnMessage(Mc.rUrobMorenie);
		addOwnMessage(Mc.rDajVolnePracovneMiesto);
		addOwnMessage(Mc.rUrobMontaz);
		addOwnMessage(Mc.rUrobRezanie);
		addOwnMessage(Mc.rUrobSkladanie);
		addOwnMessage(Mc.rVyberPracovnikaMorenie);
		addOwnMessage(Mc.spracujObjednavku);
		addOwnMessage(Mc.rPripravVSklade);
		addOwnMessage(Mc.rVyberPracovnikaRezanie);
		addOwnMessage(Mc.rUrobLakovanie);
		addOwnMessage(Mc.rUrobPripravuVSklade);
	}
	//meta! tag="end"
}
