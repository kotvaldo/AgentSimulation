package agents.agentnabytku;

import OSPABA.*;
import simulation.*;
import agents.agentnabytku.instantassistants.*;

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
		new AssigniPracovneMiesto(Id.assigniPracovneMiesto, mySim(), this);
		addOwnMessage(Mc.init);
		addOwnMessage(Mc.prichodNovejObj);
		addOwnMessage(Mc.vyberPracovnikaMontaz);
		addOwnMessage(Mc.vyberPracovnikaMorenie);
		addOwnMessage(Mc.vyberPracovnikaRezanie);
		addOwnMessage(Mc.urobLakovanie);
		addOwnMessage(Mc.vyberPracovnikaSkladanie);
		addOwnMessage(Mc.urobPripravuVSklade);
		addOwnMessage(Mc.urobMontaz);
		addOwnMessage(Mc.urobRezanie);
		addOwnMessage(Mc.vyberPracovnikaLakovanie);
		addOwnMessage(Mc.urobSkladanie);
		addOwnMessage(Mc.presunDoSkladu);
		addOwnMessage(Mc.presunNaPracovisko);
		addOwnMessage(Mc.urobMorenie);
		addOwnMessage(Mc.dajVolnePracovneMiesto);
		addOwnMessage(Mc.koniecPripravy);
	}
	//meta! tag="end"
}
