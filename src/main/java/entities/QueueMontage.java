package entities;

import Statistics.TimeWeightedStatistic;

public class QueueMontage extends Queue{
    public QueueMontage(TimeWeightedStatistic queueLength) {
        super(queueLength);
    }
}
