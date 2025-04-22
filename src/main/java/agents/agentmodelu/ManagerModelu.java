package agents.agentmodelu;

import OSPABA.*;
import simulation.*;

//meta! id="1"
public class ManagerModelu extends OSPABA.Manager
{
	public ManagerModelu(int id, Simulation mySim, Agent myAgent)
	{
		super(id, mySim, myAgent);
		init();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();

		if (petriNet() != null)
		{
			petriNet().clear();
		}

	}

	//meta! sender="AgentNabytku", id="153", type="Notice"
	public void processNoticeHotovaObjednavka(MessageForm message)
	{

	}

	//meta! sender="AgentOkolia", id="328", type="Notice"
	public void processNoticePrichodObjednavky(MessageForm message)
	{
		MyMessage myMessage = (MyMessage) message.createCopy();
		myMessage.setAddressee(mySim().findAgent(Id.agentModelu));
		myMessage.setCode(Mc.noticeSpracujObjednavku);
		notice(myMessage);
	}

	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
			// doplň default spracovanie
		}
	}

	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
			case Mc.noticeHotovaObjednavka:
				processNoticeHotovaObjednavka(message);
				break;

			case Mc.noticePrichodObjednavky:
				processNoticePrichodObjednavky(message);
				break;
			case Mc.init:
				init();
			default:
				processDefault(message);
				break;
		}
	}

	public void init()
	{

	}

	@Override
	public AgentModelu myAgent()
	{
		return (AgentModelu) super.myAgent();
	}
}
