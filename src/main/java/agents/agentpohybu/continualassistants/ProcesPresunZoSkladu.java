package agents.agentpohybu.continualassistants;

import Enums.PresetSimulationValues;
import Enums.WorkerBussyState;
import OSPABA.*;
import simulation.*;
import agents.agentpohybu.*;
import OSPABA.Process;

//meta! id="394"
public class ProcesPresunZoSkladu extends Process {
    public ProcesPresunZoSkladu(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
    }

    //meta! sender="AgentPohybu", id="395", type="Start"
    public void processStart(MessageForm message) {
        MyMessage msg = (MyMessage) message;

        if (msg.getWorkerA() != null) {
            msg.getWorkerA().setState(WorkerBussyState.MOVING_FROM_STORAGE.getValue());
        } else if (msg.getWorkerB() != null) {
            msg.getWorkerB().setState(WorkerBussyState.MOVING_FROM_STORAGE.getValue());
        } else if (msg.getWorkerC() != null) {
            msg.getWorkerC().setState(WorkerBussyState.MOVING_FROM_STORAGE.getValue());
        }

        double newTime = ((MySimulation) mySim()).getGenerators().getTimeMovingIntoStorageDist().sample();
        if (newTime + mySim().currentTime() < PresetSimulationValues.END_OF_SIMULATION.getValue()) {
            hold(newTime, msg);
        }
    }

    //meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {
        switch (message.code()) {
        }
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    @Override
    public void processMessage(MessageForm message) {
        switch (message.code()) {
            case Mc.start:
                processStart(message);
                break;

            default:
                processDefault(message);
                break;
        }
    }
    //meta! tag="end"

    @Override
    public AgentPohybu myAgent() {
        return (AgentPohybu) super.myAgent();
    }

}
