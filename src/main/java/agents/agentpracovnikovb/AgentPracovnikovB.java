package agents.agentpracovnikovb;

import OSPABA.*;
import OSPAnimator.Flags;
import entities.Worker;
import entities.WorkerB;
import simulation.*;

import java.util.LinkedList;

//meta! id="229"
public class AgentPracovnikovB extends OSPABA.Agent
{
	public AgentPracovnikovB(int id, Simulation mySim, Agent parent)
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
		new ManagerPracovnikovB(Id.managerPracovnikovB, mySim(), this);
		addOwnMessage(Mc.noticeUvolniB);
		addOwnMessage(Mc.rVyberPracovnikaBRSkladanie);
	}

	public void initAnimator() {
		Flags.SHOW_WARNING = false;
		ManagerPracovnikovB managerNabytku = (ManagerPracovnikovB) myManager();
		if (_mySim.animatorExists()) {
			LinkedList<WorkerB> freeWorkers = managerNabytku.getFreeWorkers();
			if (freeWorkers != null) {
				for (Worker wp : freeWorkers) {
					mySim().animator().register(wp.getAnimShapeItem());
					wp.setCurrPosition(wp.getCurrPosition());
					wp.getAnimShapeItem().setVisible(true);
				}
			}
		}

	}
	//meta! tag="end"
}
