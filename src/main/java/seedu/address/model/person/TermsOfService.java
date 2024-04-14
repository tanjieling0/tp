package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.util.TestCommandFormatUtil.checkArgumentContainSlash;

/**
 * Represents a Terms of Service in the address book.
 */
public class TermsOfService {

    public final String terms;

    /**
     * Represents the terms of service of an organization.
     */
    public TermsOfService() {
        terms = "";
    }

    /**
     * Constructs a {@code TermsOfService}.
     *
     * @param terms A valid terms of service.
     */
    public TermsOfService(String terms) {
        requireNonNull(terms);
        checkArgumentContainSlash(terms);
        this.terms = terms;
    }

    public String getTerms() {
        return terms;
    }

    @Override
    public String toString() {
        return terms;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TermsOfService)) {
            return false;
        }

        TermsOfService otherTermsOfService = (TermsOfService) other;
        return terms.equals(otherTermsOfService.terms);
    }

    @Override
    public int hashCode() {
        return terms.hashCode();
    }

}
