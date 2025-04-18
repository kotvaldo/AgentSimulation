package agents.agentpracovisk.instantassistants;

import OSPABA.*;
import simulation.*;
import agents.agentpracovisk.*;

//meta! id="104"
public class ZmenaStavuPracovnehoMiesta extends OSPABA.Action
{
	public ZmenaStavuPracovnehoMiesta(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
	}

	@Override
	public void execute(MessageForm message)
	{
	}

	@Override
	public AgentPracovisk myAgent()
	{
		return (AgentPracovisk)super.myAgent();
	}

}
