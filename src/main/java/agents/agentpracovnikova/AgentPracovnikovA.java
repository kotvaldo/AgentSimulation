package agents.agentpracovnikova;

import OSPABA.*;
import OSPAnimator.Flags;
import entities.Worker;
import entities.WorkerA;
import simulation.*;

import java.awt.geom.Point2D;
import java.util.ArrayList;

//meta! id="228"
public class AgentPracovnikovA extends Agent
{
	public AgentPracovnikovA(int id, Simulation mySim, Agent parent)
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

	public void initAnimator() {
		Flags.SHOW_WARNING = false;
		ManagerPracovnikovA managerNabytku = (ManagerPracovnikovA) myManager();
		MySimulation mySim = (MySimulation) mySim();
		if (_mySim.animatorExists()) {
			ArrayList<WorkerA> freeWorkers = mySim.getWorkersAArrayList();
			if (freeWorkers != null) {
				int i = 0;
				int spacing = 40;

				for (Worker wp : freeWorkers) {
					Point2D position = wp.getCurrPosition();
					if (position == null) {
						int x = Data.SKLAD_X;
						int y = Data.SKLAD_Y + i * spacing;
						position = new Point2D.Double(x, y);
						wp.setCurrPosition(position);
						i++;
					}
					mySim().animator().register(wp.getAnimImageItem());
					wp.getAnimImageItem().setPosition(position);
					wp.getAnimImageItem().setVisible(true);
				}

			}
		}
	}
	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new ManagerPracovnikovA(Id.managerPracovnikovA, mySim(), this);
		addOwnMessage(Mc.rVyberPracovnikaASusenie);
		addOwnMessage(Mc.rVyberPracovnikaARezanie);
		addOwnMessage(Mc.rVyberPracovnikaAMontaz);
		addOwnMessage(Mc.noticeUvolniA);
	}
	//meta! tag="end"
}
