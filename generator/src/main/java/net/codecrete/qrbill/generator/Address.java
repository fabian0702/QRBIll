//
// Swiss QR Bill Generator
// Copyright (c) 2017 Manuel Bleichenbacher
// Licensed under MIT License
// https://opensource.org/licenses/MIT
//
package net.codecrete.qrbill.generator;

import java.io.Serializable;
import java.util.Objects;

/**
 * Address of creditor or debtor.
 * <p>
 * You can either set street, house number, postal code and town (type <i>structured address</i>)
 * or address line 1 and 2 (type <i>combined address elements</i>). The type is automatically set
 * once any of these fields is set. Before setting the fields, the address type is <i>undetermined</i>.
 * If fields of both types are set, the address type becomes <i>conflicting</i>.
 * Name and country code must always be set unless all fields are empty.
 * </p>
 */
public class Address implements Serializable {

    /**
     * Address type
     */
    public enum Type {
        /**
         * Undetermined
         */
        UNDETERMINED,
        /**
         * Structured address
         */
        STRUCTURED,
        /**
         * Combined address elements
         */
        COMBINED_ELEMENTS,
        /**
         * Conflicting
         */
        CONFLICTING
    }

    private static final long serialVersionUID = -8833174154173397772L;

    /** Address type */
    private Type type = Type.UNDETERMINED;
    /** Name of person or company */
    private String name;
    /** Address line 1 */
    private String addressLine1;
    /** Address line 2 */
    private String addressLine2;
    /** Street */
    private String street;
    /** House or building number */
    private String houseNo;
    /** Postal code */
    private String postalCode;
    /** Town */
    private String town;
    /** ISO country code */
    private String countryCode;

    /**
     * Gets the address type.
     * <p>
     * The address type is automatically set by either setting street / house number
     * or address line 1 and 2. Before setting the fields, the address type is <i>undetermined</i>.
     * If fields of both types are set, the address type becomes <i>conflicting</i>.
     * </p>
     * <p>
     * The address type can be reset by calling {@link #clear}.
     * </p>
     *
     * @return address type
     */
    public Type getType() {
        return type;
    }

    private void changeType(Type desiredType) {
        if (type == desiredType)
            return;
        if (type == Type.UNDETERMINED)
            type = desiredType;
        else
            type = Type.CONFLICTING;
    }

    /**
     * Gets the name, either the first and last name of a natural person or the
     * company name of a legal person.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name, either the first and last name of a natural person or the
     * company name of a legal person.
     * <p>
     * The name is mandatory unless the entire address contains {@code null} or
     * empty values.
     * </p>
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the address line 1
     * <p>
     * Address line 1 contains street name, house number or P.O. box.
     * </p>
     * <p>
     * This field is only used for combined elements addresses and is optional.
     * </p>
     *
     * @return address line 1
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     * Sets the address line 1.
     * <p>
     * Address line 1 contains street name, house number or P.O. box.
     * </p>
     * <p>
     * Setting this field sets the address type to {@link Type#COMBINED_ELEMENTS} unless it's already
     * {@link Type#STRUCTURED}, in which case it becomes {@link Type#CONFLICTING}.
     * </p>
     * <p>
     * This field is only used for combined elements addresses and is optional.
     * </p>
     *
     * @param addressLine1 address line 1
     */
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        changeType(Type.COMBINED_ELEMENTS);
    }

    /**
     * Gets the address line 2
     * <p>
     * Address line 2 contains postal code and town.
     * </p>
     * <p>
     * This field is only used for combined elements addresses. For this type, it's mandatory.
     * </p>
     *
     * @return address line 2
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     * Sets the address line 2.
     * <p>
     * Address line 2 contains postal code and town
     * </p>
     * <p>
     * Setting this field sets the address type to {@link Type#COMBINED_ELEMENTS} unless it's already
     * {@link Type#STRUCTURED}, in which case it becomes {@link Type#CONFLICTING}.
     * </p>
     * <p>
     * This field is only used for combined elements addresses. For this type, it's mandatory.
     * </p>
     *
     * @param addressLine2 address line 2
     */
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        changeType(Type.COMBINED_ELEMENTS);
    }

    /**
     * Gets the street.
     * <p>
     * This field is only used for structured addresses and is optional.
     * </p>
     *
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the street.
     * <p>
     * This field must not contain the house or building number.
     * </p>
     * <p>
     * Setting this field sets the address type to {@link Type#STRUCTURED} unless it's already
     * {@link Type#COMBINED_ELEMENTS}, in which case it becomes {@link Type#CONFLICTING}.
     * </p>
     * <p>
     * This field is only used for structured addresses and is optional.
     * </p>
     *
     * @param street the street
     */
    public void setStreet(String street) {
        this.street = street;
        changeType(Type.STRUCTURED);
    }

    /**
     * Gets the house or building number.
     * <p>
     * This field is only used for structured addresses and is optional.
     * </p>
     *
     * @return the house number
     */
    public String getHouseNo() {
        return houseNo;
    }

    /**
     * Sets the house or building number.
     * <p>
     * Setting this field sets the address type to {@link Type#STRUCTURED} unless it's already
     * {@link Type#COMBINED_ELEMENTS}, in which case it becomes {@link Type#CONFLICTING}.
     * </p>
     * <p>
     * This field is only used for structured addresses and is optional.
     * </p>
     *
     * @param houseNo the house number
     */
    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
        changeType(Type.STRUCTURED);
    }

    /**
     * Gets the postal code
     * <p>
     * This field is only used for structured addresses. For this type, it's mandatory.
     * </p>
     *
     * @return the postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the postal code
     * <p>
     * Setting this field sets the address type to {@link Type#STRUCTURED} unless it's already
     * {@link Type#COMBINED_ELEMENTS}, in which case it becomes {@link Type#CONFLICTING}.
     * </p>
     * <p>
     * This field is only used for structured addresses. For this type, it's mandatory.
     * </p>
     *
     * @param postalCode the postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        changeType(Type.STRUCTURED);
    }

    /**
     * Gets the town.
     * <p>
     * This field is only used for structured addresses. For this type, it's mandatory.
     * </p>
     *
     * @return the town
     */
    public String getTown() {
        return town;
    }

    /**
     * Sets the town
     * <p>
     * Setting this field sets the address type to {@link Type#STRUCTURED} unless it's already
     * {@link Type#COMBINED_ELEMENTS}, in which case it becomes {@link Type#CONFLICTING}.
     * </p>
     * <p>
     * This field is only used for structured addresses. For this type, it's mandatory.
     * </p>
     *
     * @param town the town
     */
    public void setTown(String town) {
        this.town = town;
        changeType(Type.STRUCTURED);
    }

    /**
     * Gets the two-letter ISO country code.
     *
     * @return the ISO country code
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Sets the two-letter ISO country code
     * <p>
     * The country code is mandatory unless the entire address contains {@code null}
     * or empty values.
     * </p>
     *
     * @param countryCode the ISO country code
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * Clears all fields and set the type to {@link Type#UNDETERMINED}.
     */
    public void clear() {
        type = Type.UNDETERMINED;
        name = null;
        addressLine1 = null;
        addressLine2 = null;
        street = null;
        houseNo = null;
        postalCode = null;
        town = null;
        countryCode = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(name, address.name) &&
                type == address.type &&
                Objects.equals(street, address.street) &&
                Objects.equals(houseNo, address.houseNo) &&
                Objects.equals(addressLine1, address.addressLine1) &&
                Objects.equals(addressLine2, address.addressLine2) &&
                Objects.equals(postalCode, address.postalCode) &&
                Objects.equals(town, address.town) &&
                Objects.equals(countryCode, address.countryCode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, type, street, houseNo, addressLine1, addressLine2, postalCode, town, countryCode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Address{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", street='" + street + '\'' +
                ", houseNo='" + houseNo + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", town='" + town + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }
}
