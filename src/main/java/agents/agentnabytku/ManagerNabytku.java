package agents.agentnabytku;

import Enums.OrderStateValues;
import Enums.WorkPlaceStateValues;
import Enums.WorkerBussyState;
import OSPABA.*;
import entities.*;
import simulation.Id;
import simulation.Mc;
import simulation.MyMessage;
import simulation.MySimulation;


//meta! id="9"
public class ManagerNabytku extends OSPABA.Manager {
    private QueueNonProcessed queueNonProcessed;
    private QueueColoring queueColoring;
    private QueueAssembly queueAssembly;
    private QueueMontage queueMontage;

    public ManagerNabytku(int id, Simulation mySim, Agent myAgent) {
        super(id, mySim, myAgent);
        init();
        queueNonProcessed = new QueueNonProcessed(null);
        queueColoring = new QueueColoring(null);
        queueAssembly = new QueueAssembly(null);
        queueMontage = new QueueMontage(null);

    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication

        if (petriNet() != null) {
            petriNet().clear();
        }
        queueNonProcessed.getQueue().clear();
        queueColoring.getQueue().clear();
        queueAssembly.getQueue().clear();
        queueMontage.getQueue().clear();
    }


    //meta! sender="AgentModelu", id="24", type="Notice"
    public void processInit(MessageForm message) {
    }

    //Pracovne miesta

    //meta! sender="AgentPracovisk", id="388", type="Response"
    public void processDajPracovneMiestoRezanie(MessageForm message) {
        MyMessage msg = (MyMessage) message.createCopy();
        if (msg.getWorkerA() == null && msg.getWorkPlace() != null) {
            MyMessage reqPlace = new MyMessage(msg);
            reqPlace.setCode(Mc.dajPracovneMiestoRezanie);
            reqPlace.setAddressee(mySim().findAgent(Id.agentPracovisk));
            request(reqPlace);

        } else if (msg.getWorkerA() != null && msg.getWorkPlace() != null) {
            msg.getWorkPlace().setState(WorkPlaceStateValues.ASSIGNED.getValue());
            msg.getFurniture().setWorkPlace(msg.getWorkPlace());
            msg.getWorkPlace().setFurniture(msg.getFurniture());

            if (msg.getWorkerA().getCurrentWorkPlace() != null) {
                msg.setCode(Mc.rPresunDoSkladu);
                msg.setAddressee(mySim().findAgent(Id.agentPohybu));
                msg.getWorkerA().setState(WorkerBussyState.MOVING_TO_STORAGE.getValue());
            } else {
                msg.setCode(Mc.rPripravVSklade);
                msg.setAddressee(mySim().findAgent(Id.agentSkladu));
                msg.getWorkerA().setState(WorkerBussyState.PREPARING_IN_STORAGE.getValue());
            }
            request(msg);
        }
    }

    //meta! sender="AgentPracovisk", id="389", type="Response"
    public void processRDajPracovneMiestoLakovanie(MessageForm message) {
    }

    //meta! sender="AgentPracovisk", id="390", type="Response"
    public void processRDajPracovneMiestoMontaz(MessageForm message) {
    }

    //meta! sender="AgentPracovisk", id="182", type="Response"
    public void processRDajPracovneMiestoMorenie(MessageForm message) {
    }

    //meta! sender="AgentPracovisk", id="387", type="Response"
    public void processRDajPracovneMiestoSkladanie(MessageForm message) {
    }


    //pohyb
    //meta! sender="AgentPohybu", id="138", type="Response"
    public void processRPresunDoSkladu(MessageForm message) {

    }

    //meta! sender="AgentPohybu", id="157", type="Response"
    public void processRPresunNaPracovisko(MessageForm message) {
    }

    //meta! sender="AgentPohybu", id="385", type="Response"
    public void processRPresunZoSkladu(MessageForm message) {
    }


    // Pracovnici Response agent Pracovnikov

    //meta! sender="AgentPracovnikov", id="164", type="Response"
    public void processRVyberPracovnikaLakovanie(MessageForm message) {
    }

    //meta! sender="AgentPracovnikov", id="162", type="Response"
    public void processRVyberPracovnikaMorenie(MessageForm message) {
    }

    //meta! sender="AgentPracovnikov", id="90", type="Response"
    public void processRVyberPracovnikaSkladanie(MessageForm message) {
    }

    //meta! sender="AgentPracovnikov", id="167", type="Response"
    public void processRVyberPracovnikaMontaz(MessageForm message) {
    }

    //meta! sender="AgentPracovnikov", id="168", type="Response"
    public void processRVyberPracovnikaRezanie(MessageForm message) {
        MyMessage msg = (MyMessage) message.createCopy();
        if (msg.getWorkerA() != null && msg.getWorkPlace() == null) {
            MyMessage reqPlace = new MyMessage(msg);
            reqPlace.setCode(Mc.dajPracovneMiestoRezanie);
            reqPlace.setAddressee(mySim().findAgent(Id.agentPracovisk));
            request(reqPlace);
        } else if (msg.getWorkerA() != null && msg.getWorkPlace() != null) {

            msg.getWorkPlace().setState(WorkPlaceStateValues.ASSIGNED.getValue());
            msg.getFurniture().setWorkPlace(msg.getWorkPlace());
            msg.getWorkPlace().setFurniture(msg.getFurniture());

            if (msg.getWorkerA().getCurrentWorkPlace() != null) {
                msg.setCode(Mc.rPresunDoSkladu);
                msg.setAddressee(mySim().findAgent(Id.agentPohybu));
                msg.getWorkerA().setState(WorkerBussyState.MOVING_TO_STORAGE.getValue());
            } else {
                msg.setCode(Mc.rPripravVSklade);
                msg.setAddressee(mySim().findAgent(Id.agentSkladu));
                msg.getWorkerA().setState(WorkerBussyState.PREPARING_IN_STORAGE.getValue());
            }

            request(msg);

        }

    }


    //Cinnosti

    //meta! sender="AgentCinnosti", id="286", type="Response"
    public void processRUrobMorenie(MessageForm message) {
    }

    //meta! sender="AgentCinnosti", id="289", type="Response"
    public void processRUrobMontaz(MessageForm message) {
    }

    //meta! sender="AgentCinnosti", id="284", type="Response"
    public void processRUrobRezanie(MessageForm message) {

    }

    //meta! sender="AgentCinnosti", id="288", type="Response"
    public void processRUrobSkladanie(MessageForm message) {

    }

    //meta! sender="AgentCinnosti", id="287", type="Response"
    public void processRUrobLakovanie(MessageForm message) {

    }


    //Sklad
    //meta! sender="AgentSkladu", id="324", type="Response"
    public void processRPripravVSklade(MessageForm message) {
       /* MyMessage msg = (MyMessage) message.createCopy();
        msg.setCode(Mc.rPresunZoSkladu);

        msg.setAddressee(myAgent().findAssistant(Id.agentPohybu));
        request(msg);*/
    }


    // Prichod novej objednavky

    //meta! sender="AgentModelu", id="81", type="Notice"
    public void processNoticeSpracujObjednavku(MessageForm message) {
        MyMessage msg = (MyMessage) message.createCopy();
        MySimulation mySimulation = (MySimulation) _mySim;
        msg.getOrder().setState(OrderStateValues.PROCESSING_ORDER.getValue());
        for (Furniture furniture : msg.getOrder().getFurnitureList()) {
            MyMessage furnitureMsg = new MyMessage(mySimulation);
            furnitureMsg.setOrder(msg.getOrder());
            furnitureMsg.setFurniture(furniture);
            furnitureMsg.setWorkPlace(null);
            furnitureMsg.setWorkerA(null);
            furnitureMsg.setWorkerB(null);
            furnitureMsg.setWorkerC(null);
            this.queueNonProcessed.getQueue().addLast(furnitureMsg);
            //System.out.println(queueNonProcessed.getQueue());
        }
        for (MyMessage msgFromQueue : queueNonProcessed.getQueue()) {
            checkProcessingQueueNonProcessed(msgFromQueue);
        }

    }


    private void checkProcessingQueueNonProcessed(MyMessage msg) {
        if (msg.getWorkerA() != null && msg.getWorkPlace() != null) {
            queueNonProcessed.getQueue().removeIf(m -> m.equals(msg));

            msg.getWorkPlace().setState(WorkPlaceStateValues.ASSIGNED.getValue());
            msg.getFurniture().setWorkPlace(msg.getWorkPlace());
            msg.getWorkPlace().setFurniture(msg.getFurniture());

            if (msg.getWorkerA().getCurrentWorkPlace() != null) {
                msg.setCode(Mc.rPresunDoSkladu);
                msg.setAddressee(mySim().findAgent(Id.agentPohybu));
                msg.getWorkerA().setState(WorkerBussyState.MOVING_TO_STORAGE.getValue());
            } else {
                msg.setCode(Mc.rPripravVSklade);
                msg.setAddressee(mySim().findAgent(Id.agentSkladu));
                msg.getWorkerA().setState(WorkerBussyState.PREPARING_IN_STORAGE.getValue());
            }

            request(msg);
            return; // Správa bola vybavená, ostatné netreba
        }

        if (msg.getWorkerA() != null && msg.getWorkPlace() == null) {
            MyMessage reqPlace = new MyMessage(msg);
            reqPlace.setCode(Mc.dajPracovneMiestoRezanie);
            reqPlace.setAddressee(mySim().findAgent(Id.agentPracovisk));
            request(reqPlace);
            return;
        }

        if (msg.getWorkPlace() != null && msg.getWorkerA() == null) {
            MyMessage reqWorker = new MyMessage(msg);
            reqWorker.setCode(Mc.rVyberPracovnikaRezanie);
            reqWorker.setAddressee(mySim().findAgent(Id.agentPracovnikov));
            request(reqWorker);
            return;
        }

        if (msg.getWorkPlace() == null && msg.getWorkerA() == null) {
            MyMessage reqWorker = new MyMessage(msg);
            reqWorker.setCode(Mc.rVyberPracovnikaRezanie);
            reqWorker.setAddressee(mySim().findAgent(Id.agentPracovnikov));
            request(reqWorker);
        }
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
            case Mc.rVyberPracovnikaMontaz:
                processRVyberPracovnikaMontaz(message);
                break;

            case Mc.rVyberPracovnikaMorenie:
                processRVyberPracovnikaMorenie(message);
                break;

            case Mc.rUrobMontaz:
                processRUrobMontaz(message);
                break;

            case Mc.rVyberPracovnikaSkladanie:
                processRVyberPracovnikaSkladanie(message);
                break;

            case Mc.dajPracovneMiestoRezanie:
                processDajPracovneMiestoRezanie(message);
                break;

            case Mc.rDajPracovneMiestoMontaz:
                processRDajPracovneMiestoMontaz(message);
                break;

            case Mc.rDajPracovneMiestoMorenie:
                processRDajPracovneMiestoMorenie(message);
                break;
            case Mc.rDajPracovneMiestoSkladanie:
                processRDajPracovneMiestoSkladanie(message);
                break;

            case Mc.rDajPracovneMiestoLakovanie:
                processRDajPracovneMiestoLakovanie(message);
                break;

            case Mc.rUrobRezanie:
                processRUrobRezanie(message);
                break;

            case Mc.rPresunDoSkladu:
                processRPresunDoSkladu(message);
                break;

            case Mc.rPresunZoSkladu:
                processRPresunZoSkladu(message);
                break;

            case Mc.rPresunNaPracovisko:
                processRPresunNaPracovisko(message);
                break;


            case Mc.rVyberPracovnikaLakovanie:
                processRVyberPracovnikaLakovanie(message);
                break;

            case Mc.rUrobMorenie:
                processRUrobMorenie(message);
                break;

            case Mc.rUrobLakovanie:
                processRUrobLakovanie(message);
                break;

            case Mc.rUrobSkladanie:
                processRUrobSkladanie(message);
                break;

            case Mc.init:
                processInit(message);
                break;

            case Mc.rVyberPracovnikaRezanie:
                processRVyberPracovnikaRezanie(message);
                break;

            case Mc.rPripravVSklade:
                processRPripravVSklade(message);
                break;

            case Mc.noticeSpracujObjednavku:
                processNoticeSpracujObjednavku(message);
                break;

            default:
                processDefault(message);
                break;
        }
    }
    //meta! tag="end"

    @Override
    public AgentNabytku myAgent() {
        return (AgentNabytku) super.myAgent();
    }

}
