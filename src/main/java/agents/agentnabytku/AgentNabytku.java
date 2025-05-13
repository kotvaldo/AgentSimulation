package agents.agentnabytku;

import OSPABA.*;
import OSPAnimator.Flags;
import entities.WorkPlace;
import simulation.*;

import java.awt.*;

//meta! id="9"
public class AgentNabytku extends OSPABA.Agent
{
	public AgentNabytku(int id, Simulation mySim, Agent parent)
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

		if (_mySim.animatorExists()) {
			MySimulation mySimulation = (MySimulation) _mySim;
			int count = 0;
			int size = 30;
			int gap = size * 3;
			int itemsPerRow = 10;

			for (WorkPlace wp : mySimulation.getWorkPlacesArrayList()) {
				if (wp.getCurrPosition() != null) {
					wp.getAnimImageItem().setPosition(wp.getCurrPosition());
				} else {
					int row = count / itemsPerRow;
					int col = count % itemsPerRow;

					int x = col * (size + gap);
					int y = row * (size + gap);

					wp.getAnimImageItem().setPosition(new Point(x, y));
					count++;
				}

				wp.getAnimImageItem().setVisible(true);
				mySim().animator().register(wp.getAnimImageItem());
			}
		}
	}




	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new ManagerNabytku(Id.managerNabytku, mySim(), this);
		addOwnMessage(Mc.rPresunDoSkladu);
		addOwnMessage(Mc.init);
		addOwnMessage(Mc.rVyberPracovnikaLakovanie);
		addOwnMessage(Mc.rPresunNaPracovisko);
		addOwnMessage(Mc.rVyberPracovnikaSkladanie);
		addOwnMessage(Mc.rVyberPracovnikaMontaz);
		addOwnMessage(Mc.rUrobMorenie);
		addOwnMessage(Mc.rUrobMontaz);
		addOwnMessage(Mc.rPresunZoSkladu);
		addOwnMessage(Mc.noticeSpracujObjednavku);
		addOwnMessage(Mc.rUrobRezanie);
		addOwnMessage(Mc.rUrobSkladanie);
		addOwnMessage(Mc.rVyberPracovnikaMorenie);
		addOwnMessage(Mc.rPripravVSklade);
		addOwnMessage(Mc.rVyberPracovnikaRezanie);
		addOwnMessage(Mc.rUrobLakovanie);
	}
	//meta! tag="end"
}
