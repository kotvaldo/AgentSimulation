package Enums;

public enum FurnitureStateValues {
    WAITING_IN_QUEUE_NEW(0),
    CUTTING(1),
    WAITING_IN_QUEUE_STAINING(2),
    STAINING(3),
    WAITING_IN_QUEUE_PAINTING(4),
    PAINTING(5),
    WAITING_IN_QUEUE_ASSEMBLY(6),
    ASSEMBLY(7),
    MONTAGE(8),
    WAITING_IN_QUEUE_MONTAGE(9),
    FURNITURE_DONE(10),
    PREPARING_FOR_WORK(11),
    DRYING(12),
    WAITING_IN_QUEUE_DRYING(13);


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
