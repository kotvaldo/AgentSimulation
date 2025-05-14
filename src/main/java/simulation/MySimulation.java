package simulation;

import Enums.PresetSimulationValues;
import Generation.Generators;
import IDGenerator.IDGenerator;
import OSPABA.*;
import Observer.Subject;
import Statistics.Average;
import agents.agentokolia.*;
import agents.agentnabytku.*;
import agents.agentskladu.*;
import agents.agentpohybu.*;
import agents.agentcinnosti.*;
import agents.agentpracovnikovb.*;
import agents.agentpracovnikova.*;
import agents.agentmodelu.*;
import agents.agentpracovnikov.*;
import agents.agentpracovnikovc.*;
import entities.*;

import javax.swing.*;
import java.util.ArrayList;

public class MySimulation extends Simulation {
    private final Generators generators;
    private final ArrayList<Order> orderArrayList;
    private final ArrayList<Furniture> furnitureArrayList;
    private final Average partialTimeOfWork;
    private final Average timeOfWorkAverage;
    private final Average cuttingQueueLengthAverage;
    private final Average stainingQueueLengthAverage;
    private final Average paintingQueueLengthAverage;
    private final Average montageQueueLengthAverage;
    private final Average assemblyQueueLengthAverage;
    private final ArrayList<Average> workersAUtilisationAverage;
    private final ArrayList<Average> workersBUtilisationAverage;
    private final ArrayList<Average> workersCUtilisationAverage;
    private final Average finishedOrdersAverage;

    public Average getFinishedOrdersAverage() {
        return finishedOrdersAverage;
    }

    public Average getAllOrdersAverage() {
        return allOrdersAverage;
    }

    private final Average allOrdersAverage;
    private final Average utilisationAll;
    private final Average utilisationA;
    private final Average utilisationB;
    private final Average utilisationC;


    public ArrayList<Order> getFinishedOrders() {
        return finishedOrders;
    }

    private final ArrayList<Order> finishedOrders;
    private int countWorkerA;
    private int countWorkerB;
    private int countWorkerC;
    private boolean slowMode = false;
    private int workPlacesCount;
    private int actualRepCount = 0;
    private final ArrayList<WorkerA> workersAArrayList;
    private final ArrayList<WorkerB> workersBArrayList;
    private final ArrayList<WorkerC> workersCArrayList;
    private final ArrayList<WorkPlace> workPlacesArrayList;
    private int BurnInCount;
    private int replicationsCount;


    public MySimulation() {
        this._simEndTime = PresetSimulationValues.END_OF_SIMULATION.asDouble();
        this.generators = new Generators();
        orderArrayList = new ArrayList<>();
        furnitureArrayList = new ArrayList<>();
        utilisationAll = new Average();
        utilisationA = new Average();
        utilisationB = new Average();
        utilisationC = new Average();

        partialTimeOfWork = new Average();
        timeOfWorkAverage = new Average();
        cuttingQueueLengthAverage = new Average();
        stainingQueueLengthAverage = new Average();
        paintingQueueLengthAverage = new Average();
        allOrdersAverage = new Average();
        finishedOrdersAverage = new Average();
        montageQueueLengthAverage = new Average();
        finishedOrders = new ArrayList<>();
        workersAUtilisationAverage = new ArrayList<>();
        workersBUtilisationAverage = new ArrayList<>();
        workersCUtilisationAverage = new ArrayList<>();
        assemblyQueueLengthAverage = new Average();
        workersAArrayList = new ArrayList<>();
        workersBArrayList = new ArrayList<>();
        workersCArrayList = new ArrayList<>();
        workPlacesArrayList = new ArrayList<>();
        init();


    }

    @Override
    public void prepareSimulation() {
        actualRepCount = 0;
        IDGenerator.getInstance().clearGenerators();
        timeOfWorkAverage.clear();
        cuttingQueueLengthAverage.clear();
        workersAUtilisationAverage.clear();
        workersBUtilisationAverage.clear();
        workersCUtilisationAverage.clear();
        stainingQueueLengthAverage.clear();
        paintingQueueLengthAverage.clear();
        montageQueueLengthAverage.clear();
        assemblyQueueLengthAverage.clear();
        workersAArrayList.clear();
        workersBArrayList.clear();
        workersCArrayList.clear();
        utilisationAll.clear();
        utilisationA.clear();
        utilisationB.clear();
        utilisationC.clear();
        workPlacesArrayList.clear();
        finishedOrdersAverage.clear();
        allOrdersAverage.clear();

        for (int i = 0; i < countWorkerA; i++) {
            workersAArrayList.add(new WorkerA());
            workersAUtilisationAverage.add(new Average());
            workersAArrayList.get(i).initAnimationObject();
        }
        for (int i = 0; i < countWorkerB; i++) {
            workersBArrayList.add(new WorkerB());
            workersBUtilisationAverage.add(new Average());
            workersBArrayList.get(i).initAnimationObject();
        }
        for (int i = 0; i < countWorkerC; i++) {
            workersCArrayList.add(new WorkerC());
            workersCUtilisationAverage.add(new Average());
            workersCArrayList.get(i).initAnimationObject();
        }

        for (int i = 0; i < workPlacesCount; i++) {
            workPlacesArrayList.add(new WorkPlace());
            workPlacesArrayList.get(i).initAnimationObject();
        }


        super.prepareSimulation();

    }

    @Override
    public void prepareReplication() {
        orderArrayList.clear();
        furnitureArrayList.clear();
        partialTimeOfWork.clear();
        finishedOrders.clear();
        IDGenerator.getInstance().clearGenerators();
        for (int i = 0; i < countWorkerA; i++) {
            workersAArrayList.get(i).clear();
        }
        for (int i = 0; i < countWorkerB; i++) {
            workersBArrayList.get(i).clear();
        }
        for (int i = 0; i < countWorkerC; i++) {
            workersCArrayList.get(i).clear();
        }
        for (int i = 0; i < workPlacesCount; i++) {
            workPlacesArrayList.get(i).clear();
        }

        super.prepareReplication();

        if(this.animatorExists()) {
            initAnimator();
        }
        // Reset entities, queues, local statistics, etc...
    }

    @Override
    public void replicationFinished() {
        actualRepCount++;
        //System.out.println(actualRepCount);
        ManagerNabytku managerNabytku = (ManagerNabytku) agentNabytku().myManager();
        timeOfWorkAverage.add(partialTimeOfWork.mean());


        this.cuttingQueueLengthAverage.add(managerNabytku.getQueueNonProcessed().getQueueLength().getMean());
        this.assemblyQueueLengthAverage.add(managerNabytku.getQueueAssembly().getQueueLength().getMean());
        this.stainingQueueLengthAverage.add(managerNabytku.getQueueStaining().getQueueLength().getMean());
        this.paintingQueueLengthAverage.add(managerNabytku.getQueuePainting().getQueueLength().getMean());
        this.montageQueueLengthAverage.add(managerNabytku.getQueueMontage().getQueueLength().getMean());
        finishedOrdersAverage.add(finishedOrders.size());
        allOrdersAverage.add(orderArrayList.size());

        for (int i = 0; i < workersAArrayList.size(); i++) {
            double utilisation = workersAArrayList.get(i).getUtilisation().getMean();
            workersAUtilisationAverage.get(i).add(utilisation);
        }

        for (int i = 0; i < workersBArrayList.size(); i++) {
            double utilisation = workersBArrayList.get(i).getUtilisation().getMean();
            workersBUtilisationAverage.get(i).add(utilisation);
        }

        for (int i = 0; i < workersCArrayList.size(); i++) {
            double utilisation = workersCArrayList.get(i).getUtilisation().getMean();
            workersCUtilisationAverage.get(i).add(utilisation);
        }

        // Celkový súčet všetkých vyťažeností (pre všetkých pracovníkov A, B, C)
        double sumAll = 0;
        double sumA = 0;
        double sumB = 0;
        double sumC = 0;

        for (WorkerA workerA : workersAArrayList) {
            double u = workerA.getUtilisation().getMean();
            sumA += u;
        }
        for (WorkerB workerB : workersBArrayList) {
            double u = workerB.getUtilisation().getMean();
            sumB += u;
        }
        for (WorkerC workerC : workersCArrayList) {
            double u = workerC.getUtilisation().getMean();
            sumC += u;
        }

        int totalWorkers = workersAArrayList.size() + workersBArrayList.size() + workersCArrayList.size();

        if (totalWorkers > 0) {
            utilisationAll.add((sumA + sumB + sumC) / totalWorkers);
        }
        if (!workersAArrayList.isEmpty()) {
            utilisationA.add(sumA / workersAArrayList.size());
        }
        if (!workersBArrayList.isEmpty()) {
            utilisationB.add(sumB / workersBArrayList.size());
        }
        if (!workersCArrayList.isEmpty()) {
            utilisationC.add(sumC / workersCArrayList.size());
        }

        if (animatorExists()) {
            animator().clearAll();
        }

        if (!slowMode) {
            for (ISimDelegate delegate : this.delegates()) {
                delegate.refresh(this);
            }
        }

       /* System.out.println("=== Štatistiky replikácie " + actualRepCount + " ===");
        System.out.printf("Priemerný čas výroby: %.2f%n", timeOfWorkAverage.mean());
        System.out.printf("Priemerný počet dokončených objednávok: %.2f%n", finishedOrdersAverage.mean());
        System.out.printf("Priemerný počet všetkých objednávok: %.2f%n", allOrdersAverage.mean());
        System.out.println();

        System.out.printf("Priemerná vyťaženosť všetkých pracovníkov: %.2f%%%n", utilisationAll.mean() * 100);
        System.out.printf("Priemerná vyťaženosť pracovníkov A: %.2f%%%n", utilisationA.mean() * 100);
        System.out.printf("Priemerná vyťaženosť pracovníkov B: %.2f%%%n", utilisationB.mean() * 100);
        System.out.printf("Priemerná vyťaženosť pracovníkov C: %.2f%%%n", utilisationC.mean() * 100);
        System.out.println();

        System.out.printf("Priemerná dĺžka frontu - Rezanie: %.2f%n", cuttingQueueLengthAverage.mean());
        System.out.printf("Priemerná dĺžka frontu - Morenie: %.2f%n", stainingQueueLengthAverage.mean());
        System.out.printf("Priemerná dĺžka frontu - Maľovanie: %.2f%n", paintingQueueLengthAverage.mean());
        System.out.printf("Priemerná dĺžka frontu - Montáž: %.2f%n", montageQueueLengthAverage.mean());
        System.out.printf("Priemerná dĺžka frontu - Kompletizácia: %.2f%n", assemblyQueueLengthAverage.mean());
        System.out.println();
*/
       /* System.out.println("Vyťaženosť jednotlivých pracovníkov A:");
        for (int i = 0; i < workersAUtilisationAverage.size(); i++) {
            System.out.printf("  Pracovník A%d: %.2f%%%n", i + 1, workersAUtilisationAverage.get(i).mean() * 100);
        }
        System.out.println();

        System.out.println("Vyťaženosť jednotlivých pracovníkov B:");
        for (int i = 0; i < workersBUtilisationAverage.size(); i++) {
            System.out.printf("  Pracovník B%d: %.2f%%%n", i + 1, workersBUtilisationAverage.get(i).mean() * 100);
        }
        System.out.println();

        System.out.println("Vyťaženosť jednotlivých pracovníkov C:");
        for (int i = 0; i < workersCUtilisationAverage.size(); i++) {
            System.out.printf("  Pracovník C%d: %.2f%%%n", i + 1, workersCUtilisationAverage.get(i).mean() * 100);
        }
        System.out.println();*/

        // Collect local statistics into global, update UI, etc...

        super.replicationFinished();
    }

    @Override
    public void simulationFinished() {
        // Display simulation results
        super.simulationFinished();
    }

    @Override
    public void createAnimator(){
        super.createAnimator();
    }

    public void initAnimator() {
        this._agentNabytku.initAnimator();
        this._agentPracovnikovA.initAnimator();
        this._agentPracovnikovB.initAnimator();
        this._agentPracovnikovC.initAnimator();
    }
    //meta! userInfo="Generated code: do not modify", tag="begin"
    private void init() {
        setAgentModelu(new AgentModelu(Id.agentModelu, this, null));
        setAgentOkolia(new AgentOkolia(Id.agentOkolia, this, agentModelu()));
        setAgentNabytku(new AgentNabytku(Id.agentNabytku, this, agentModelu()));
        setAgentCinnosti(new AgentCinnosti(Id.agentCinnosti, this, agentNabytku()));
        setAgentSkladu(new AgentSkladu(Id.agentSkladu, this, agentNabytku()));
        setAgentPohybu(new AgentPohybu(Id.agentPohybu, this, agentNabytku()));
        setAgentPracovnikov(new AgentPracovnikov(Id.agentPracovnikov, this, agentNabytku()));
        setAgentPracovnikovC(new AgentPracovnikovC(Id.agentPracovnikovC, this, agentPracovnikov()));
        setAgentPracovnikovA(new AgentPracovnikovA(Id.agentPracovnikovA, this, agentPracovnikov()));
        setAgentPracovnikovB(new AgentPracovnikovB(Id.agentPracovnikovB, this, agentPracovnikov()));
    }

    private AgentModelu _agentModelu;

    public AgentModelu agentModelu() {
        return _agentModelu;
    }

    public void setAgentModelu(AgentModelu agentModelu) {
        _agentModelu = agentModelu;
    }

    private AgentOkolia _agentOkolia;

    public AgentOkolia agentOkolia() {
        return _agentOkolia;
    }

    public void setAgentOkolia(AgentOkolia agentOkolia) {
        _agentOkolia = agentOkolia;
    }

    private AgentNabytku _agentNabytku;

    public AgentNabytku agentNabytku() {
        return _agentNabytku;
    }

    public void setAgentNabytku(AgentNabytku agentNabytku) {
        _agentNabytku = agentNabytku;
    }

    private AgentCinnosti _agentCinnosti;

    public AgentCinnosti agentCinnosti() {
        return _agentCinnosti;
    }

    public void setAgentCinnosti(AgentCinnosti agentCinnosti) {
        _agentCinnosti = agentCinnosti;
    }

    private AgentSkladu _agentSkladu;

    public AgentSkladu agentSkladu() {
        return _agentSkladu;
    }

    public void setAgentSkladu(AgentSkladu agentSkladu) {
        _agentSkladu = agentSkladu;
    }

    private AgentPohybu _agentPohybu;

    public AgentPohybu agentPohybu() {
        return _agentPohybu;
    }

    public void setAgentPohybu(AgentPohybu agentPohybu) {
        _agentPohybu = agentPohybu;
    }

    private AgentPracovnikov _agentPracovnikov;

    public AgentPracovnikov agentPracovnikov() {
        return _agentPracovnikov;
    }
    public ArrayList<Average> getWorkersAUtilisationAverage() {
        return workersAUtilisationAverage;
    }

    public ArrayList<Average> getWorkersBUtilisationAverage() {
        return workersBUtilisationAverage;
    }

    public ArrayList<Average> getWorkersCUtilisationAverage() {
        return workersCUtilisationAverage;
    }


    public void setAgentPracovnikov(AgentPracovnikov agentPracovnikov) {
        _agentPracovnikov = agentPracovnikov;
    }

    private AgentPracovnikovC _agentPracovnikovC;

    public AgentPracovnikovC agentPracovnikovC() {
        return _agentPracovnikovC;
    }

    public void setAgentPracovnikovC(AgentPracovnikovC agentPracovnikovC) {
        _agentPracovnikovC = agentPracovnikovC;
    }

    private AgentPracovnikovA _agentPracovnikovA;

    public AgentPracovnikovA agentPracovnikovA() {
        return _agentPracovnikovA;
    }

    public void setAgentPracovnikovA(AgentPracovnikovA agentPracovnikovA) {
        _agentPracovnikovA = agentPracovnikovA;
    }

    private AgentPracovnikovB _agentPracovnikovB;

    public AgentPracovnikovB agentPracovnikovB() {
        return _agentPracovnikovB;
    }

    public void setAgentPracovnikovB(AgentPracovnikovB agentPracovnikovB) {
        _agentPracovnikovB = agentPracovnikovB;
    }

    public Generators getGenerators() {
        return generators;
    }

    public ArrayList<Order> getOrderArrayList() {
        return orderArrayList;
    }

    public ArrayList<Furniture> getFurnitureArrayList() {
        return furnitureArrayList;
    }

    public Average getPartialTimeOfWork() {
        return partialTimeOfWork;
    }

    public Average getTimeOfWorkAverage() {
        return timeOfWorkAverage;
    }


    public int getCountWorkerA() {
        return countWorkerA;
    }

    public void setCountWorkerA(int countWorkerA) {
        this.countWorkerA = countWorkerA;
    }

    public int getCountWorkerB() {
        return countWorkerB;
    }

    public void setCountWorkerB(int countWorkerB) {
        this.countWorkerB = countWorkerB;
    }

    public int getCountWorkerC() {
        return countWorkerC;
    }

    public void setCountWorkerC(int countWorkerC) {
        this.countWorkerC = countWorkerC;
    }

    public boolean isSlowMode() {
        return slowMode;
    }

    public void setSlowMode(boolean slowMode) {
        this.slowMode = slowMode;
        if(!slowMode) {
            setMaxSimSpeed();
        }
    }

    public Average getStainingQueueLengthAverage() {
        return stainingQueueLengthAverage;
    }

    public Average getPaintingQueueLengthAverage() {
        return paintingQueueLengthAverage;
    }

    public Average getMontageQueueLengthAverage() {
        return montageQueueLengthAverage;
    }

    public Average getAssemblyQueueLengthAverage() {
        return assemblyQueueLengthAverage;
    }

    public Average getUtilisationAll() {
        return utilisationAll;
    }

    public Average getUtilisationA() {
        return utilisationA;
    }

    public Average getUtilisationB() {
        return utilisationB;
    }

    public Average getUtilisationC() {
        return utilisationC;
    }

    public int getActualRepCount() {
        return actualRepCount;
    }

    public int getWorkPlacesCount() {
        return workPlacesCount;
    }

    public void setWorkPlacesCount(int workPlacesCount) {
        this.workPlacesCount = workPlacesCount;
    }

    public ArrayList<WorkPlace> getWorkPlacesArrayList() {
        return workPlacesArrayList;
    }

    public ArrayList<WorkerC> getWorkersCArrayList() {
        return workersCArrayList;
    }

    public ArrayList<WorkerB> getWorkersBArrayList() {
        return workersBArrayList;
    }

    public ArrayList<WorkerA> getWorkersAArrayList() {
        return workersAArrayList;
    }

    public int getReplicationsCount() {
        return replicationsCount;
    }

    public void setReplicationsCount(int replicationsCount) {
        this.replicationsCount = replicationsCount;
    }

    public int getBurnInCount() {
        return BurnInCount;
    }

    public void setBurnInCount(int burnInCount) {
        BurnInCount = burnInCount;
    }

    public Average getCuttingQueueLengthAverage() {
        return cuttingQueueLengthAverage;
    }
    //meta! tag="end"
}
