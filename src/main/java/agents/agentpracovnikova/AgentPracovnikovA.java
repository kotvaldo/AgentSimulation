package agents.agentpracovnikova;

import OSPABA.*;
import OSPAnimator.Flags;
import entities.Worker;
import entities.WorkerA;
import simulation.*;

import java.util.LinkedList;

//meta! id="228"
public class AgentPracovnikovA extends OSPABA.Agent {
    public AgentPracovnikovA(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    private void init() {
        new ManagerPracovnikovA(Id.managerPracovnikovA, mySim(), this);
        addOwnMessage(Mc.rVyberPracovnikaARezanie);
        addOwnMessage(Mc.noticeUvolniA);
        addOwnMessage(Mc.rVyberPracovnikaAMontaz);
    }

    public void initAnimator() {
        Flags.SHOW_WARNING = false;
        ManagerPracovnikovA managerNabytku = (ManagerPracovnikovA) myManager();
        if (_mySim.animatorExists()) {
            LinkedList<WorkerA> freeWorkers = managerNabytku.getFreeWorkers();
            if (freeWorkers != null) {
                for (Worker wp : freeWorkers) {
                    mySim().animator().register(wp.getAnimShapeItem());
                    wp.setCurrPosition(wp.getCurrPosition());
                    wp.getAnimShapeItem().setVisible(true);
                }
            }
        }
    }
    //meta! tag="end"
}
