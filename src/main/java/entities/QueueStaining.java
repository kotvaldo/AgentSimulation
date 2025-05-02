package entities;

import Statistics.TimeWeightedStatistic;

public class QueueStaining extends Queue{
    public QueueStaining(TimeWeightedStatistic queueLength) {
        super(queueLength);
    }
}
