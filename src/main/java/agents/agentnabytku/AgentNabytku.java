package agents.agentnabytku;

import OSPABA.*;
import OSPAnimator.Flags;
import entities.WorkPlace;
import simulation.*;

//meta! id="9"
public class AgentNabytku extends Agent
{
	public AgentNabytku(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		init();
	}
	public void initAnimator() {
		Flags.SHOW_WARNING = false;

		if (_mySim.animatorExists()) {
			MySimulation mySimulation = (MySimulation) _mySim;

			for (WorkPlace wp : mySimulation.getWorkPlacesArrayList()) {
				wp.getAnimImageItem().setPosition(wp.getCurrPosition());
				wp.getAnimImageItem().setVisible(true);
				mySim().animator().register(wp.getAnimImageItem());
			}
		}
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
		new ManagerNabytku(Id.managerNabytku, mySim(), this);
		addOwnMessage(Mc.rPresunDoSkladu);
		addOwnMessage(Mc.init);
		addOwnMessage(Mc.rVyberPracovnikaLakovanie);
		addOwnMessage(Mc.rPresunNaPracovisko);
		addOwnMessage(Mc.rVyberPracovnikaSkladanie);
		addOwnMessage(Mc.rVyberPracovnikaMontaz);
		addOwnMessage(Mc.rUrobMorenie);
		addOwnMessage(Mc.rUrobMontaz);
		addOwnMessage(Mc.urobSusenie);
		addOwnMessage(Mc.rPresunZoSkladu);
		addOwnMessage(Mc.noticeSpracujObjednavku);
		addOwnMessage(Mc.rUrobRezanie);
		addOwnMessage(Mc.rUrobSkladanie);
		addOwnMessage(Mc.rVyberPracovnikaSusenie);
		addOwnMessage(Mc.rVyberPracovnikaMorenie);
		addOwnMessage(Mc.rPripravVSklade);
		addOwnMessage(Mc.rVyberPracovnikaRezanie);
		addOwnMessage(Mc.rUrobLakovanie);
	}
	//meta! tag="end"
}
