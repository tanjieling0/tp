package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will cause a {@code Person}'s id
 * to be modified.
 */
public class IdModifiedException extends RuntimeException {
    public IdModifiedException() {
        super("Operation would result in id of person to be modified");
    }
}
