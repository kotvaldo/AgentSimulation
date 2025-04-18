package agents.agentnabytku.instantassistants;

import OSPABA.*;
import agents.agentnabytku.*;
import simulation.*;

//meta! id="254"
public class AssigniPracovneMiesto extends OSPABA.Action
{
	public AssigniPracovneMiesto(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
	}

	@Override
	public void execute(MessageForm message)
	{
	}

	@Override
	public AgentNabytku myAgent()
	{
		return (AgentNabytku)super.myAgent();
	}

}
