package agents.agentpohybu;

import OSPABA.*;
import simulation.*;
import agents.agentpohybu.continualassistants.*;

//meta! id="35"
public class AgentPohybu extends OSPABA.Agent
{
	public AgentPohybu(int id, Simulation mySim, Agent parent)
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
		new ManagerPohybu(Id.managerPohybu, mySim(), this);
		new ProcesPresunNaPracovisko(Id.procesPresunNaPracovisko, mySim(), this);
		new ProcesPresunDoSkladu(Id.procesPresunDoSkladu, mySim(), this);
		addOwnMessage(Mc.rPresunDoSkladu);
		addOwnMessage(Mc.init);
		addOwnMessage(Mc.rPresunNaPracovisko);
	}
	//meta! tag="end"
}
