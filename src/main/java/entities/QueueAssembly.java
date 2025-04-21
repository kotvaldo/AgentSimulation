package entities;

import Statistics.TimeWeightedStatistic;

public class QueueAssembly extends Queue{
    public QueueAssembly(TimeWeightedStatistic queueLength) {
        super(queueLength);
    }
}
