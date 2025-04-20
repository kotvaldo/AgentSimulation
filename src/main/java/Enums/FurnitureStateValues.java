package Enums;

public enum FurnitureStateValues {
    WAITING_IN_QUEUE_NEW(0),
    PROCESSING_CUTTING(1),
    WAITING_IN_QUEUE_2(2),
    PROCESSING_STAINING(3),
    PROCESSING_PAINTING(4),
    WAITING_IN_QUEUE_3(5),
    PROCESSING_ASSEMBLY(6),
    PROCESSING_MONTAGE(7),
    WAITING_IN_QUEUE_4(8),
    FURNITURE_DONE(9);


    private final Integer value;

    FurnitureStateValues(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static String getNameByValue(int value) {
        for (FurnitureStateValues state : values()) {
            if (state.getValue() == value) {
                return state.name();
            }
        }
        return "UNKNOWN_STATE";
    }

    @Override
    public String toString() {
        return name();
    }
}
