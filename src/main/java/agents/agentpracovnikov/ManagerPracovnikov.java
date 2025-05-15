package agents.agentpracovnikov;

import OSPABA.*;
import entities.*;
import simulation.*;

//meta! id="5"
public class ManagerPracovnikov extends OSPABA.Manager
{
	public ManagerPracovnikov(int id, Simulation mySim, Agent myAgent)
	{
		super(id, mySim, myAgent);
		init();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication

		if (petriNet() != null)
		{
			petriNet().clear();
		}
	}

	//meta! sender="AgentNabytku", id="65", type="Notice"
	public void processInit(MessageForm message)
	{
	}

	//meta! sender="AgentNabytku", id="399", type="Notice"
	public void processNoticeUvolniSkladanie(MessageForm message)
	{
		MyMessage myMessage = (MyMessage) message.createCopy();
		if (myMessage.getWorkerForRelease() instanceof WorkerB) {
			myMessage.setCode(Mc.noticeUvolniB);
			myMessage.setAddressee(mySim().findAgent(Id.agentPracovnikovB));
			notice(myMessage);
		}
	}

	//meta! sender="AgentNabytku", id="400", type="Notice"
	public void processNoticeUvolniLakovanie(MessageForm message)
	{
		MyMessage myMessage = (MyMessage) message.createCopy();
		if (myMessage.getWorkerForRelease() instanceof WorkerC) {
			myMessage.setCode(Mc.noticeUvolniC);
			myMessage.setAddressee(mySim().findAgent(Id.agentPracovnikovC)); // správny agent
			notice(myMessage);
		}
	}

	//meta! sender="AgentNabytku", id="401", type="Notice"
	public void processNoticeUvolniMontaz(MessageForm message)
	{
		MyMessage myMessage = (MyMessage) message.createCopy();
		if (myMessage.getWorkerForRelease() instanceof WorkerC) {
			myMessage.setCode(Mc.noticeUvolniC);
			myMessage.setAddressee(mySim().findAgent(Id.agentPracovnikovC));
			notice(myMessage);
		} else if (myMessage.getWorkerForRelease() instanceof WorkerA) {
			myMessage.setCode(Mc.noticeUvolniA);
			myMessage.setAddressee(mySim().findAgent(Id.agentPracovnikovA));
			notice(myMessage);
		}
	}
	//meta! sender="AgentNabytku", id="398", type="Notice"
	public void processNoticeUvolniRezanie(MessageForm message)
	{
		MyMessage myMessage = (MyMessage) message.createCopy();
		if (myMessage.getWorkerForRelease() instanceof WorkerA) {
			myMessage.setCode(Mc.noticeUvolniA);
			myMessage.setAddressee(mySim().findAgent(Id.agentPracovnikovA));
			notice(myMessage);
		}
	}

	//meta! sender="AgentNabytku", id="395", type="Notice"
	public void processNoticeUvolniMorenie(MessageForm message)
	{
		MyMessage myMessage = (MyMessage) message.createCopy();
		if (myMessage.getWorkerForRelease() instanceof WorkerC) {
			myMessage.setCode(Mc.noticeUvolniC);
			myMessage.setAddressee(mySim().findAgent(Id.agentPracovnikovC));
			notice(myMessage);
		}
	}
	//meta! sender="AgentPracovnikovA", id="242", type="Response"
	public void processRVyberPracovnikaARezanie(MessageForm message) {
		MyMessage msg = (MyMessage) message;
		msg.setCode(Mc.rVyberPracovnikaRezanie);
		msg.setAddressee(mySim().findAgent(Id.agentNabytku));
		response(msg);
	}


	//meta! sender="AgentPracovnikovB", id="246", type="Response"
	public void processRVyberPracovnikaBRSkladanie(MessageForm message) {
		MyMessage msg = (MyMessage) message.createCopy();
		msg.setCode(Mc.rVyberPracovnikaSkladanie);
		msg.setAddressee(mySim().findAgent(Id.agentNabytku));
		response(msg);
	}

	//meta! sender="AgentPracovnikovC", id="366", type="Response"
	public void processRVyberPracovnikaCMontaz(MessageForm message) {
		MyMessage myMessage = (MyMessage) message.createCopy();
		myMessage.setCode(Mc.rVyberPracovnikaMontaz);
		myMessage.setAddressee(mySim().findAgent(Id.agentNabytku));
		response(myMessage);


	}


	//meta! sender="AgentPracovnikovC", id="248", type="Response"
	public void processRVyberPracovnikaCMorenie(MessageForm message) {
		MyMessage myMessage = (MyMessage) message.createCopy();
		myMessage.setCode(Mc.rVyberPracovnikaMorenie);
		myMessage.setAddressee(mySim().findAgent(Id.agentNabytku));
		response(myMessage);
	}

	//meta! sender="AgentPracovnikovC", id="379", type="Response"
	public void processRVyberPracovnikaCLakovanie(MessageForm message) {
		MyMessage myMessage = (MyMessage) message.createCopy();
		myMessage.setCode(Mc.rVyberPracovnikaLakovanie);
		myMessage.setAddressee(mySim().findAgent(Id.agentNabytku));
		response(myMessage);

	}

	//meta! sender="AgentPracovnikovA", id="365", type="Response"
	public void processRVyberPracovnikaAMontaz(MessageForm message) {
		MyMessage myMessage = (MyMessage) message.createCopy();
		if(myMessage.getWorkerForMontage() != null) {
			myMessage.setCode(Mc.rVyberPracovnikaMontaz);
			myMessage.setAddressee(mySim().findAgent(Id.agentNabytku));
			response(myMessage);
		} else {
			myMessage.setCode(Mc.rVyberPracovnikaCMontaz);
			myMessage.setAddressee(mySim().findAgent(Id.agentPracovnikovC));
			request(myMessage);
		}

	}

	//meta! sender="AgentNabytku", id="164", type="Request"
	public void processRVyberPracovnikaLakovanie(MessageForm message) {
		MyMessage myMessage = (MyMessage) message.createCopy();
		myMessage.setAddressee(mySim().findAgent(Id.agentPracovnikovC));
		myMessage.setCode(Mc.rVyberPracovnikaCLakovanie);
		request(myMessage);
	}

	//meta! sender="AgentNabytku", id="167", type="Request"
	public void processRVyberPracovnikaMontaz(MessageForm message) {
		MyMessage myMessage = (MyMessage) message.createCopy();
		myMessage.setCode(Mc.rVyberPracovnikaAMontaz);
		myMessage.setAddressee(mySim().findAgent(Id.agentPracovnikovA));
		request(myMessage);
	}

	//meta! sender="AgentNabytku", id="90", type="Request"
	public void processRVyberPracovnikaSkladanie(MessageForm message) {
		MyMessage myMessage = (MyMessage) message.createCopy();
		myMessage.setCode(Mc.rVyberPracovnikaBRSkladanie);
		myMessage.setAddressee(mySim().findAgent(Id.agentPracovnikovB));
		request(myMessage);
	}

	//meta! sender="AgentNabytku", id="162", type="Request"
	public void processRVyberPracovnikaMorenie(MessageForm message) {
		MyMessage myMessage = (MyMessage) message.createCopy();
		myMessage.setCode(Mc.rVyberPracovnikaCMorenie);
		myMessage.setAddressee(mySim().findAgent(Id.agentPracovnikovC));
		request(myMessage);
	}

	//meta! sender="AgentNabytku", id="168", type="Request"
	public void processRVyberPracovnikaRezanie(MessageForm message) {
		MyMessage msg = (MyMessage) message;
		msg.setCode(Mc.rVyberPracovnikaARezanie);
		msg.setAddressee(mySim().findAgent(Id.agentPracovnikovA));
		request(msg);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
		}
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	public void init()
	{
	}

	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
		case Mc.rVyberPracovnikaARezanie:
			processRVyberPracovnikaARezanie(message);
		break;

		case Mc.noticeUvolniLakovanie:
			processNoticeUvolniLakovanie(message);
		break;

		case Mc.rVyberPracovnikaMorenie:
			processRVyberPracovnikaMorenie(message);
		break;

		case Mc.init:
			processInit(message);
		break;

		case Mc.rVyberPracovnikaRezanie:
			processRVyberPracovnikaRezanie(message);
		break;

		case Mc.noticeUvolniMorenie:
			processNoticeUvolniMorenie(message);
		break;

		case Mc.rVyberPracovnikaLakovanie:
			processRVyberPracovnikaLakovanie(message);
		break;

		case Mc.rVyberPracovnikaCMontaz:
			processRVyberPracovnikaCMontaz(message);
		break;

		case Mc.rVyberPracovnikaCLakovanie:
			processRVyberPracovnikaCLakovanie(message);
		break;

		case Mc.noticeUvolniMontaz:
			processNoticeUvolniMontaz(message);
		break;

		case Mc.rVyberPracovnikaAMontaz:
			processRVyberPracovnikaAMontaz(message);
		break;

		case Mc.rVyberPracovnikaSkladanie:
			processRVyberPracovnikaSkladanie(message);
		break;

		case Mc.rVyberPracovnikaBRSkladanie:
			processRVyberPracovnikaBRSkladanie(message);
		break;

		case Mc.noticeUvolniSkladanie:
			processNoticeUvolniSkladanie(message);
		break;

		case Mc.rVyberPracovnikaMontaz:
			processRVyberPracovnikaMontaz(message);
		break;

		case Mc.rVyberPracovnikaCMorenie:
			processRVyberPracovnikaCMorenie(message);
		break;

		case Mc.noticeUvolniRezanie:
			processNoticeUvolniRezanie(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public AgentPracovnikov myAgent()
	{
		return (AgentPracovnikov)super.myAgent();
	}

}
