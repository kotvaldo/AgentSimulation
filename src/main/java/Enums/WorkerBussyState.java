package Enums;

public enum WorkerBussyState {
    NON_BUSY(0),
    ASSIGNED(1),
    BUSY(2),
    MOVING_TO_STORAGE(3),
    MOVING_FROM_STORAGE(4),
    MOVE_TO_WORKPLACE(5),
    PREPARING_IN_STORAGE(6);
    private final int stateCode;

    WorkerBussyState(int stateCode) {
        this.stateCode = stateCode;
    }

    public int getValue() {
        return stateCode;
    }

    public static String getNameByValue(int value) {
        for (WorkerBussyState state : WorkerBussyState.values()) {
            if (state.getValue() == value) {
                return state.name();
            }
        }
        return "UNKNOWN";
    }
}
