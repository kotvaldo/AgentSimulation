package agents.agentpracovnikovc;

import OSPABA.*;
import OSPAnimator.Flags;
import entities.Worker;
import entities.WorkerC;
import simulation.*;

import java.util.LinkedList;

//meta! id="230"
public class AgentPracovnikovC extends OSPABA.Agent
{
	public AgentPracovnikovC(int id, Simulation mySim, Agent parent)
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
		new ManagerPracovnikovC(Id.managerPracovnikovC, mySim(), this);
		addOwnMessage(Mc.rVyberPracovnikaCMontaz);
		addOwnMessage(Mc.rVyberPracovnikaCMorenie);
		addOwnMessage(Mc.rVyberPracovnikaCLakovanie);
		addOwnMessage(Mc.noticeUvolniC);
	}

	public void initAnimator() {
		Flags.SHOW_WARNING = false;
		ManagerPracovnikovC managerNabytku = (ManagerPracovnikovC) myManager();
		if (_mySim.animatorExists()) {
			LinkedList<WorkerC> freeWorkers = managerNabytku.getFreeWorkers();
			if (freeWorkers != null) {
				for (Worker wp : freeWorkers) {
					mySim().animator().register(wp.getAnimImageItem());
					wp.setCurrPosition(wp.getCurrPosition());
					wp.getAnimImageItem().setVisible(true);
				}
			}
		}

	}
	//meta! tag="end"
}
