package agents.agentpracovisk;

import agents.agentpracovisk.instantassistants.*;
import OSPABA.*;
import simulation.*;

//meta! id="62"
public class AgentPracovisk extends OSPABA.Agent
{
	public AgentPracovisk(int id, Simulation mySim, Agent parent)
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
		new ManagerPracovisk(Id.managerPracovisk, mySim(), this);
		new ZmenaStavuPracovnehoMiesta(Id.zmenaStavuPracovnehoMiesta, mySim(), this);
		addOwnMessage(Mc.init);
		addOwnMessage(Mc.uvolniPracovneMiesto);
		addOwnMessage(Mc.dajVolnePracovneMiesto);
	}
	//meta! tag="end"
}
