package agents.agentskladu;

import OSPABA.*;
import simulation.*;
import agents.agentskladu.continualassistants.*;

//meta! id="118"
public class AgentSkladu extends Agent
{
	public AgentSkladu(int id, Simulation mySim, Agent parent)
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
		new ManagerSkladu(Id.managerSkladu, mySim(), this);
		new ProcesPripravaVSklade(Id.procesPripravaVSklade, mySim(), this);
		addOwnMessage(Mc.init);
		addOwnMessage(Mc.rPripravVSklade);
	}
	//meta! tag="end"
}
