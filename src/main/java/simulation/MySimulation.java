package simulation;

import OSPABA.*;
import agents.agentokolia.*;
import agents.agentnabytku.*;
import agents.agentskladu.*;
import agents.agentpohybu.*;
import agents.agentcinnosti.*;
import agents.agentpracovnikovb.*;
import agents.agentpracovnikova.*;
import agents.agentmodelu.*;
import agents.agentpracovnikov.*;
import agents.agentpracovisk.*;
import agents.agentpracovnikovc.*;

public class MySimulation extends OSPABA.Simulation
{
	public MySimulation()
	{
		init();
	}

	@Override
	public void prepareSimulation()
	{
		super.prepareSimulation();
		// Create global statistcis
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Reset entities, queues, local statistics, etc...
	}

	@Override
	public void replicationFinished()
	{
		// Collect local statistics into global, update UI, etc...
		super.replicationFinished();
	}

	@Override
	public void simulationFinished()
	{
		// Display simulation results
		super.simulationFinished();
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		setAgentModelu(new AgentModelu(Id.agentModelu, this, null));
		setAgentOkolia(new AgentOkolia(Id.agentOkolia, this, agentModelu()));
		setAgentNabytku(new AgentNabytku(Id.agentNabytku, this, agentModelu()));
		setAgentCinnosti(new AgentCinnosti(Id.agentCinnosti, this, agentNabytku()));
		setAgentSkladu(new AgentSkladu(Id.agentSkladu, this, agentNabytku()));
		setAgentPracovisk(new AgentPracovisk(Id.agentPracovisk, this, agentNabytku()));
		setAgentPohybu(new AgentPohybu(Id.agentPohybu, this, agentNabytku()));
		setAgentPracovnikov(new AgentPracovnikov(Id.agentPracovnikov, this, agentNabytku()));
		setAgentPracovnikovB(new AgentPracovnikovB(Id.agentPracovnikovB, this, agentPracovnikov()));
		setAgentPracovnikovC(new AgentPracovnikovC(Id.agentPracovnikovC, this, agentPracovnikov()));
		setAgentPracovnikovA(new AgentPracovnikovA(Id.agentPracovnikovA, this, agentPracovnikov()));
	}

	private AgentModelu _agentModelu;

public AgentModelu agentModelu()
	{ return _agentModelu; }

	public void setAgentModelu(AgentModelu agentModelu)
	{_agentModelu = agentModelu; }

	private AgentOkolia _agentOkolia;

public AgentOkolia agentOkolia()
	{ return _agentOkolia; }

	public void setAgentOkolia(AgentOkolia agentOkolia)
	{_agentOkolia = agentOkolia; }

	private AgentNabytku _agentNabytku;

public AgentNabytku agentNabytku()
	{ return _agentNabytku; }

	public void setAgentNabytku(AgentNabytku agentNabytku)
	{_agentNabytku = agentNabytku; }

	private AgentCinnosti _agentCinnosti;

public AgentCinnosti agentCinnosti()
	{ return _agentCinnosti; }

	public void setAgentCinnosti(AgentCinnosti agentCinnosti)
	{_agentCinnosti = agentCinnosti; }

	private AgentSkladu _agentSkladu;

public AgentSkladu agentSkladu()
	{ return _agentSkladu; }

	public void setAgentSkladu(AgentSkladu agentSkladu)
	{_agentSkladu = agentSkladu; }

	private AgentPracovisk _agentPracovisk;

public AgentPracovisk agentPracovisk()
	{ return _agentPracovisk; }

	public void setAgentPracovisk(AgentPracovisk agentPracovisk)
	{_agentPracovisk = agentPracovisk; }

	private AgentPohybu _agentPohybu;

public AgentPohybu agentPohybu()
	{ return _agentPohybu; }

	public void setAgentPohybu(AgentPohybu agentPohybu)
	{_agentPohybu = agentPohybu; }

	private AgentPracovnikov _agentPracovnikov;

public AgentPracovnikov agentPracovnikov()
	{ return _agentPracovnikov; }

	public void setAgentPracovnikov(AgentPracovnikov agentPracovnikov)
	{_agentPracovnikov = agentPracovnikov; }

	private AgentPracovnikovB _agentPracovnikovB;

public AgentPracovnikovB agentPracovnikovB()
	{ return _agentPracovnikovB; }

	public void setAgentPracovnikovB(AgentPracovnikovB agentPracovnikovB)
	{_agentPracovnikovB = agentPracovnikovB; }

	private AgentPracovnikovC _agentPracovnikovC;

public AgentPracovnikovC agentPracovnikovC()
	{ return _agentPracovnikovC; }

	public void setAgentPracovnikovC(AgentPracovnikovC agentPracovnikovC)
	{_agentPracovnikovC = agentPracovnikovC; }

	private AgentPracovnikovA _agentPracovnikovA;

public AgentPracovnikovA agentPracovnikovA()
	{ return _agentPracovnikovA; }

	public void setAgentPracovnikovA(AgentPracovnikovA agentPracovnikovA)
	{_agentPracovnikovA = agentPracovnikovA; }
	//meta! tag="end"
}
