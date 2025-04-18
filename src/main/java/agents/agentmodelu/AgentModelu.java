package agents.agentmodelu;

import OSPABA.*;
import simulation.*;

//meta! id="1"
public class AgentModelu extends OSPABA.Agent
{
	public AgentModelu(int id, Simulation mySim, Agent parent)
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
		new ManagerModelu(Id.managerModelu, mySim(), this);
		addOwnMessage(Mc.hotovaObj);
		addOwnMessage(Mc.prichodObjednavky);
	}
	//meta! tag="end"
}
