package Enums;

public enum PresetSimulationValues {
    END_OF_SIMULATION(60*60*8.0*249),
    START_SIMULATION_TIME(0.0),
    UPDATES_PER_SECOND(21.0),
    WORKERS_A_COUNT(18.0),
    WORKERS_B_COUNT(2.0),
    WORKERS_C_COUNT(18.0),
    WORKPLACES_COUNT(50.0),
    REPLICATIONS_COUNT(1000.0),
    BURN_IN_COUNT(10.0);
    private final Number value;

    PresetSimulationValues(Double value) {
        this.value = value;
    }

    public Double asDouble() {
        return value.doubleValue();
    }

    public Integer asInteger() {
        return value.intValue();
    }
}
