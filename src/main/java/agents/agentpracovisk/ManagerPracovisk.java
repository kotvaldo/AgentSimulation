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

    private WorkPlace dajPrveVolneMiestoPodlaId() {
        return freeWorkPlaces.stream()
                .filter(wp -> wp.getState() == WorkPlaceStateValues.NOT_WORKING.getValue())
                .findFirst()
                .orElse(null);
    }

    //meta! sender="AgentNabytku", id="72", type="Notice"
    public void processInit(MessageForm message) {
    }

    //meta! sender="AgentNabytku", id="207", type="Notice"
    public void processNoticeUvolniPracovneMiesto(MessageForm message) {
        MyMessage myMessage = (MyMessage) message.createCopy();
        myMessage.getWorkPlace().setState(WorkPlaceStateValues.NOT_WORKING.getValue());
    }

    public void processDajPracovneMiestoRezanie(MessageForm message) {
        MyMessage msg = (MyMessage) message;
        WorkPlace workPlace = dajPrveVolneMiestoPodlaId();

        if (workPlace != null) {
            workPlace.setState(WorkPlaceStateValues.ASSIGNED.getValue());
        }

        msg.setWorkPlace(workPlace);
        msg.setCode(Mc.dajPracovneMiestoRezanie);
        msg.setAddressee(Id.agentNabytku);
        response(msg);
    }

    // Pridelenie pracoviska pre skladanie
    public void processRDajPracovneMiestoSkladanie(MessageForm message) {
        MyMessage msg = (MyMessage) message;
        WorkPlace workPlace = dajPrveVolneMiestoPodlaId();

        if (workPlace != null) {
            workPlace.setState(WorkPlaceStateValues.ASSIGNED.getValue());
        }
        msg.setWorkPlace(workPlace);
        msg.setCode(Mc.rDajPracovneMiestoSkladanie);
        msg.setAddressee(Id.agentNabytku);
        response(msg);
    }

    // Pridelenie pre montáž
    public void processRDajPracovneMiestoMontaz(MessageForm message) {
        MyMessage msg = (MyMessage) message;
        WorkPlace workPlace = dajPrveVolneMiestoPodlaId();

        if (workPlace != null) {
            workPlace.setState(WorkPlaceStateValues.ASSIGNED.getValue());
        }
        msg.setWorkPlace(workPlace);
        msg.setCode(Mc.rDajPracovneMiestoMontaz);
        msg.setAddressee(Id.agentNabytku);
        response(msg);
    }

    // Pridelenie pre morenie
    public void processRDajPracovneMiestoMorenie(MessageForm message) {
        MyMessage msg = (MyMessage) message;
        WorkPlace workPlace = dajPrveVolneMiestoPodlaId();

        if (workPlace != null) {
            workPlace.setState(WorkPlaceStateValues.ASSIGNED.getValue());
        }
        msg.setWorkPlace(workPlace);
        msg.setCode(Mc.rDajPracovneMiestoMorenie);
        msg.setAddressee(Id.agentNabytku);
        response(msg);
    }

    // Pridelenie pre lakovanie
    public void processRDajPracovneMiestoLakovanie(MessageForm message) {
        MyMessage msg = (MyMessage) message;
        WorkPlace workPlace = dajPrveVolneMiestoPodlaId();

        if (workPlace != null) {
            workPlace.setState(WorkPlaceStateValues.ASSIGNED.getValue());
        }
        msg.setWorkPlace(workPlace);
        msg.setCode(Mc.rDajPracovneMiestoLakovanie);
        msg.setAddressee(Id.agentNabytku);
        response(msg);
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