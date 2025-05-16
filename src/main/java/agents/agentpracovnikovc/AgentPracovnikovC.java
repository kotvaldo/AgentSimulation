package agents.agentpracovnikovc;

import OSPABA.*;
import OSPAnimator.Flags;
import entities.Worker;
import entities.WorkerB;
import entities.WorkerC;
import simulation.*;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;

//meta! id="230"
public class AgentPracovnikovC extends Agent
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
		MySimulation mySim = (MySimulation) mySim();

		if (mySim.animatorExists()) {
			ArrayList<WorkerC> freeWorkers = mySim.getWorkersCArrayList();
			if (freeWorkers != null) {
				int i = 0;
				int spacing = 40;

				for (WorkerC wp : freeWorkers) {
					Point2D position = wp.getCurrPosition();
					if (position == null) {
						int x = Data.SKLAD_X + 40;
						int y = Data.SKLAD_Y + i * spacing;
						position = new Point2D.Double(x, y);
						wp.setCurrPosition(position);
						i++;
					}

					wp.getAnimImageItem().setPosition(position);
					wp.getAnimImageItem().setVisible(true);
					mySim.animator().register(wp.getAnimImageItem());
				}
			}
		}
	}

	//meta! tag="end"
}
