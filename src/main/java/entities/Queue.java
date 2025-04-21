package entities;

import Statistics.TimeWeightedStatistic;

import java.util.LinkedList;

public class Queue {
    private final LinkedList<Furniture> queue;
    private final TimeWeightedStatistic queueLength;


    public Queue(TimeWeightedStatistic queueLength) {
        this.queueLength = queueLength;
        this.queue = new LinkedList<>();
    }


    public LinkedList<Furniture> getQueue() {
        return queue;
    }

    public TimeWeightedStatistic getQueueLength() {
        return queueLength;
    }
}
