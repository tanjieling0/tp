package seedu.address.model.person;

import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a supplier in the address book.
 * Subclass of the Person class.
 * Contains information about the supplier's products and terms of service.
 */
public class Supplier extends Person {
    private final Products products;
    private final TermsOfService termsOfService;

    /**
     * Every field must be present and not null.
     */
    public Supplier(Name name, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags,
                    Products products, TermsOfService termsOfService) {
        super(name, phone, email, address, tags, remark);
        this.products = products;
        this.termsOfService = termsOfService;
    }

    /**
     * Every field must be present and not null.
     */
    public Supplier(Id id, Name name, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags,
                    Products products, TermsOfService termsOfService) {
        super(id, name, phone, email, address, tags, remark);
        this.products = products;
        this.termsOfService = termsOfService;
    }


    /**
     * Returns the products offered by the supplier.
     *
     * @return The products offered by the supplier.
     */
    public Products getProducts() {
        return this.products;
    }

    /**
     * Returns the products preferred by the client.
     *
     * @return The products preferred by the client as a String.
     */
    public String getProductsAsString() {
        String result = String.join(", ", products.getProducts());
        if (result.isEmpty()) {
            return result;
        }
        return "\"" + result + "\"";
    }

    /**
     * Returns the terms of service provided by the supplier.
     *
     * @return The terms of service provided by the supplier.
     */
    public TermsOfService getTermsOfService() {
        return termsOfService;
    }

    public String getRole() {
        return "Supplier";
    }

    /**
     * Checks if this supplier is equal to another object.
     * Two suppliers are considered equal if they have the same attributes.
     *
     * @param other The object to compare with.
     * @return True if the suppliers are equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Supplier
                && super.equals(other)
                && products.equals(((Supplier) other).products)
                && termsOfService.equals(((Supplier) other).termsOfService));
    }

    /**
     * Returns the hash code of the supplier.
     *
     * @return The hash code of the supplier.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), products, termsOfService);
    }

    /**
     * Returns a string representation of the supplier.
     *
     * @return A string representation of the supplier.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("id", id)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("remark", remark)
                .add("tags", tags)
                .add("products", products)
                .add("termsOfService", termsOfService)
                .toString();
    }
}
