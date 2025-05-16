package agents.agentcinnosti;

import OSPABA.*;
import simulation.*;
import agents.agentcinnosti.continualassistants.*;

//meta! id="267"
public class AgentCinnosti extends Agent
{
	public AgentCinnosti(int id, Simulation mySim, Agent parent)
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
		new ManagerCinnosti(Id.managerCinnosti, mySim(), this);
		new ProcesMontaze(Id.procesMontaze, mySim(), this);
		new ProcesLakovania(Id.procesLakovania, mySim(), this);
		new ProcesRezania(Id.procesRezania, mySim(), this);
		new ProcesMorenia(Id.procesMorenia, mySim(), this);
		new ProcesSusenia(Id.procesSusenia, mySim(), this);
		new ProcesSkladania(Id.procesSkladania, mySim(), this);
		addOwnMessage(Mc.init);
		addOwnMessage(Mc.rUrobRezanie);
		addOwnMessage(Mc.rUrobSkladanie);
		addOwnMessage(Mc.rUrobMorenie);
		addOwnMessage(Mc.rUrobMontaz);
		addOwnMessage(Mc.urobSusenie);
		addOwnMessage(Mc.rUrobLakovanie);
	}
	//meta! tag="end"
}
