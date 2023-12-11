package lib.exceptions;

public class BadTargetEntityTypeException extends Exception {
    private final Class<?> badType;

    public Class<?> getBadType() {
        return badType;
    }

    public BadTargetEntityTypeException(Class<?> badType) {
        super();
        this.badType = badType;
    }
}
