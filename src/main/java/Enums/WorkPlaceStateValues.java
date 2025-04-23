package Enums;

public enum WorkPlaceStateValues {
    NOT_WORKING(0),
    ASSIGNED(1),
    WORKING(2);

    private final int value;

    WorkPlaceStateValues(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static WorkPlaceStateValues fromValue(int value) {
        for (WorkPlaceStateValues state : WorkPlaceStateValues.values()) {
            if (state.getValue() == value) {
                return state;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
