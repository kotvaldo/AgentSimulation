package entities;

import OSPDataStruct.SimQueue;
import Statistics.TimeWeightedStatistic;
import simulation.MyMessage;

public class Queue {
    private final SimQueue<MyMessage> queue;
    private final TimeWeightedStatistic queueLength;


    public Queue(TimeWeightedStatistic queueLength) {
        this.queueLength = queueLength;
        this.queue = new SimQueue<>();
    }


    public SimQueue<MyMessage> getQueue() {
        return queue;
    }

    public TimeWeightedStatistic getQueueLength() {
        return queueLength;
    }
}
