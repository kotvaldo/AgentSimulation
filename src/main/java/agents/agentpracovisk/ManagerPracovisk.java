package agents.agentpracovisk;

import Enums.WorkPlaceStateValues;
import OSPABA.*;
import entities.WorkPlace;
import simulation.*;

import java.util.LinkedList;

//meta! id="62"
public class ManagerPracovisk extends Manager {
    private LinkedList<WorkPlace> freeWorkPlaces = new LinkedList<>();

    public ManagerPracovisk(int id, Simulation mySim, Agent myAgent) {
        super(id, mySim, myAgent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();

        if (petriNet() != null) {
            petriNet().clear();
        }

        MySimulation mySimulation = (MySimulation) _mySim;
        freeWorkPlaces.clear();
        freeWorkPlaces.addAll(mySimulation.getWorkPlacesArrayList());
    }

    private void pridelPracovisko(MyMessage message) {
        WorkPlace workPlace = freeWorkPlaces.stream()
            .filter(wp -> wp.getState() == WorkPlaceStateValues.NOT_WORKING.getValue())
            .findFirst()
            .orElse(null);

        if (workPlace != null) {
            workPlace.setState(WorkPlaceStateValues.ASSIGNED.getValue());
        }

        message.setWorkPlace(workPlace);
        message.setCode(Mc.rDajVolnePracovneMiesto);
        message.setAddressee(Id.agentNabytku);
        response(message);
    }

    //meta! sender="AgentNabytku", id="72", type="Notice"
    public void processInit(MessageForm message) {
    }

    //meta! sender="AgentNabytku", id="207", type="Notice"
    public void processNoticeUvolniPracovneMiesto(MessageForm message) {
        MyMessage myMessage = (MyMessage) message.createCopy();
        myMessage.getWorkPlace().setState(WorkPlaceStateValues.NOT_WORKING.getValue());
    }

    //meta! sender="AgentNabytku", id="388", type="Request"
    public void processDajPracovneMiestoRezanie(MessageForm message) {
        pridelPracovisko((MyMessage) message);
    }

    //meta! sender="AgentNabytku", id="387", type="Request"
    public void processRDajPracovneMiestoSkladanie(MessageForm message) {
        pridelPracovisko((MyMessage) message);
    }

    //meta! sender="AgentNabytku", id="390", type="Request"
    public void processRDajPracovneMiestoMontaz(MessageForm message) {
        pridelPracovisko((MyMessage) message);
    }

    //meta! sender="AgentNabytku", id="389", type="Request"
    public void processRDajPracovneMiestoLakovanie(MessageForm message) {
        pridelPracovisko((MyMessage) message);
    }

    //meta! sender="AgentNabytku", id="182", type="Request"
    public void processRDajPracovneMiestoMorenie(MessageForm message) {
        pridelPracovisko((MyMessage) message);
    }

    //meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {
        switch (message.code()) {
        }
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    public void init() {
    }

    @Override
    public void processMessage(MessageForm message) {
        switch (message.code()) {
            case Mc.init:
                processInit(message);
                break;

            case Mc.rDajPracovneMiestoMorenie:
                processRDajPracovneMiestoMorenie(message);
                break;

            case Mc.noticeUvolniPracovneMiesto:
                processNoticeUvolniPracovneMiesto(message);
                break;

            case Mc.rDajPracovneMiestoMontaz:
                processRDajPracovneMiestoMontaz(message);
                break;

            case Mc.rDajPracovneMiestoSkladanie:
                processRDajPracovneMiestoSkladanie(message);
                break;

            case Mc.dajPracovneMiestoRezanie:
                processDajPracovneMiestoRezanie(message);
                break;

            case Mc.rDajPracovneMiestoLakovanie:
                processRDajPracovneMiestoLakovanie(message);
                break;

            default:
                processDefault(message);
                break;
        }
    }
    //meta! tag="end"

    @Override
    public AgentPracovisk myAgent() {
        return (AgentPracovisk) super.myAgent();
    }
}