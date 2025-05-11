package entities;

import OSPDataStruct.SimQueue;
import Statistics.QueueLength;
import Statistics.TimeWeightedStatistic;
import simulation.MyMessage;
import simulation.MySimulation;

import java.util.function.Predicate;

public class Queue extends SimQueue<MyMessage> {
    protected QueueLength queueLength;
    protected MySimulation mySimulation;

    public Queue(MySimulation mySimulation) {
        this.queueLength = new QueueLength();
        this.mySimulation = mySimulation;
    }

    @Override
    public void addLast(MyMessage myMessage) {
        super.addLast(myMessage);
        queueLength.recordChange(mySimulation.currentTime(), size());
    }

    @Override
    public MyMessage removeFirst() {
        MyMessage removed = super.removeFirst();
        queueLength.recordChange(mySimulation.currentTime(), size());
        return removed;
    }

    @Override
    public boolean removeIf(Predicate<? super MyMessage> filter) {
        boolean removed = super.removeIf(filter);
        if (removed) {
            queueLength.recordChange(mySimulation.currentTime(), size());
        }
        return removed;
    }

    @Override
    public void clear() {
        super.clear(); // Dôležité na vymazanie obsahu queue
        queueLength.clear();
        queueLength.recordChange(mySimulation.currentTime(), 0);
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    public TimeWeightedStatistic getQueueLength() {
        return queueLength;
    }

    public void clearStatistics() {
        this.queueLength.clear();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Queue contents:\n");

        for (MyMessage msg : this) {
            int furnitureId = msg.getFurniture() != null ? msg.getFurniture().getId() : -1;
            String hasWorker = msg.getWorkerForCutting() != null ? "Worker=YES" : "Worker=NO";
            String hasWorkPlace = msg.getWorkPlace() != null ? "WorkPlace=YES" : "WorkPlace=NO";

            sb.append(String.format("FurnitureID=%d | %s | %s%n", furnitureId, hasWorker, hasWorkPlace));
        }

        return sb.toString();
    }
}
