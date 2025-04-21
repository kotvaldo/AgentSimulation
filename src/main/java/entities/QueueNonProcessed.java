package entities;

import Statistics.TimeWeightedStatistic;

public class QueueNonProcessed extends Queue{
    public QueueNonProcessed(TimeWeightedStatistic queueLength) {
        super(queueLength);
    }
}
