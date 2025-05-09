package entities;

import OSPDataStruct.SimQueue;
import Statistics.QueueLength;
import Statistics.TimeWeightedStatistic;
import simulation.MyMessage;
import simulation.MySimulation;

import java.util.function.Predicate;

public class Queue {
    private final SimQueue<MyMessage> queue;
    protected QueueLength queueLength;
    protected MySimulation mySimulation;

    public Queue(MySimulation mySimulation) {
        this.queue = new SimQueue<>();
        this.queueLength = new QueueLength();
        this.mySimulation = mySimulation;
    }

    public void add(MyMessage myMessage) {
        queue.add(myMessage);
        queueLength.recordChange(mySimulation.currentTime(), queue.size());
    }

    public void addLast(MyMessage myMessage) {
        queue.addLast(myMessage);
        queueLength.recordChange(mySimulation.currentTime(), queue.size());
    }

    public MyMessage removeFirst() {
        MyMessage removed = queue.removeFirst();
        queueLength.recordChange(mySimulation.currentTime(), queue.size());
        return removed;
    }

    public boolean removeIf(Predicate<? super MyMessage> filter) {
        boolean removed = queue.removeIf(filter);
        if (removed) {
            queueLength.recordChange(mySimulation.currentTime(), queue.size());
        }
        return removed;
    }

    public void clear() {
        queue.clear();
        queueLength.clear();
        queueLength.recordChange(mySimulation.currentTime(), 0);
    }


    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public SimQueue<MyMessage> getQueue() {
        return queue;
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

        for (MyMessage msg : queue) {
            int furnitureId = msg.getFurniture() != null ? msg.getFurniture().getId() : -1;
            String hasWorker = msg.getWorkerForCutting() != null ? "Worker=YES" : "Worker=NO";
            String hasWorkPlace = msg.getWorkPlace() != null ? "WorkPlace=YES" : "WorkPlace=NO";

            sb.append(String.format("FurnitureID=%d | %s | %s%n", furnitureId, hasWorker, hasWorkPlace));
        }

        return sb.toString();
    }

}
