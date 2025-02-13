package com.outis.crm.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.outis.crm.domain.Customer} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CustomerDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    private String firstName;

    @Size(min = 2, max = 100)
    private String middleName;

    @Size(min = 2, max = 100)
    private String lastName;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private String email;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$")
    private String phone;

    private String address;

    private String city;

    private String state;

    @Pattern(regexp = "^\\d{5}(-\\d{4})?$")
    private String postalCode;

    private String country;

    private String socialMediaProfiles;

    @Lob
    private String notes;

    private String preferences;

    private BigDecimal budget;

    private BigDecimal rentalBudget;

    @Lob
    private String interactionHistory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSocialMediaProfiles() {
        return socialMediaProfiles;
    }

    public void setSocialMediaProfiles(String socialMediaProfiles) {
        this.socialMediaProfiles = socialMediaProfiles;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public BigDecimal getRentalBudget() {
        return rentalBudget;
    }

    public void setRentalBudget(BigDecimal rentalBudget) {
        this.rentalBudget = rentalBudget;
    }

    public String getInteractionHistory() {
        return interactionHistory;
    }

    public void setInteractionHistory(String interactionHistory) {
        this.interactionHistory = interactionHistory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerDTO)) {
            return false;
        }

        CustomerDTO customerDTO = (CustomerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, customerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", country='" + getCountry() + "'" +
            ", socialMediaProfiles='" + getSocialMediaProfiles() + "'" +
            ", notes='" + getNotes() + "'" +
            ", preferences='" + getPreferences() + "'" +
            ", budget=" + getBudget() +
            ", rentalBudget=" + getRentalBudget() +
            ", interactionHistory='" + getInteractionHistory() + "'" +
            "}";
    }
}
