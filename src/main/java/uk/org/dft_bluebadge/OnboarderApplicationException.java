package uk.org.dft_bluebadge;

public class OnboarderApplicationException extends RuntimeException {
    public OnboarderApplicationException (String errorMessage) {
        super(errorMessage);
    }
}
