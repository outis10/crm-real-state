package com.outis.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "first_name", length = 100, nullable = false)
    private String firstName;

    @Size(min = 2, max = 100)
    @Column(name = "middle_name", length = 100)
    private String middleName;

    @Size(min = 2, max = 100)
    @Column(name = "last_name", length = 100)
    private String lastName;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email", nullable = false)
    private String email;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$")
    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Pattern(regexp = "^\\d{5}(-\\d{4})?$")
    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "country")
    private String country;

    @Column(name = "social_media_profiles")
    private String socialMediaProfiles;

    @Lob
    @Column(name = "notes")
    private String notes;

    @Column(name = "preferences")
    private String preferences;

    @Column(name = "budget", precision = 21, scale = 2)
    private BigDecimal budget;

    @Column(name = "rental_budget", precision = 21, scale = 2)
    private BigDecimal rentalBudget;

    @Lob
    @Column(name = "interaction_history")
    private String interactionHistory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "customer" }, allowSetters = true)
    private Set<ChatInteraction> chatInteractions = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "customer" }, allowSetters = true)
    private Set<Notification> notifications = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "customer", "property" }, allowSetters = true)
    private Set<Quotation> quotations = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "charges", "payments", "property", "customer" }, allowSetters = true)
    private Set<RentalContract> rentalContracts = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "customer", "rentalContract" }, allowSetters = true)
    private Set<Charge> charges = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "customer" }, allowSetters = true)
    private Set<Contact> contacts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Customer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Customer firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public Customer middleName(String middleName) {
        this.setMiddleName(middleName);
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Customer lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public Customer email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public Customer phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return this.address;
    }

    public Customer address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }

    public Customer city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public Customer state(String state) {
        this.setState(state);
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public Customer postalCode(String postalCode) {
        this.setPostalCode(postalCode);
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return this.country;
    }

    public Customer country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSocialMediaProfiles() {
        return this.socialMediaProfiles;
    }

    public Customer socialMediaProfiles(String socialMediaProfiles) {
        this.setSocialMediaProfiles(socialMediaProfiles);
        return this;
    }

    public void setSocialMediaProfiles(String socialMediaProfiles) {
        this.socialMediaProfiles = socialMediaProfiles;
    }

    public String getNotes() {
        return this.notes;
    }

    public Customer notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPreferences() {
        return this.preferences;
    }

    public Customer preferences(String preferences) {
        this.setPreferences(preferences);
        return this;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    public BigDecimal getBudget() {
        return this.budget;
    }

    public Customer budget(BigDecimal budget) {
        this.setBudget(budget);
        return this;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public BigDecimal getRentalBudget() {
        return this.rentalBudget;
    }

    public Customer rentalBudget(BigDecimal rentalBudget) {
        this.setRentalBudget(rentalBudget);
        return this;
    }

    public void setRentalBudget(BigDecimal rentalBudget) {
        this.rentalBudget = rentalBudget;
    }

    public String getInteractionHistory() {
        return this.interactionHistory;
    }

    public Customer interactionHistory(String interactionHistory) {
        this.setInteractionHistory(interactionHistory);
        return this;
    }

    public void setInteractionHistory(String interactionHistory) {
        this.interactionHistory = interactionHistory;
    }

    public Set<ChatInteraction> getChatInteractions() {
        return this.chatInteractions;
    }

    public void setChatInteractions(Set<ChatInteraction> chatInteractions) {
        if (this.chatInteractions != null) {
            this.chatInteractions.forEach(i -> i.setCustomer(null));
        }
        if (chatInteractions != null) {
            chatInteractions.forEach(i -> i.setCustomer(this));
        }
        this.chatInteractions = chatInteractions;
    }

    public Customer chatInteractions(Set<ChatInteraction> chatInteractions) {
        this.setChatInteractions(chatInteractions);
        return this;
    }

    public Customer addChatInteraction(ChatInteraction chatInteraction) {
        this.chatInteractions.add(chatInteraction);
        chatInteraction.setCustomer(this);
        return this;
    }

    public Customer removeChatInteraction(ChatInteraction chatInteraction) {
        this.chatInteractions.remove(chatInteraction);
        chatInteraction.setCustomer(null);
        return this;
    }

    public Set<Notification> getNotifications() {
        return this.notifications;
    }

    public void setNotifications(Set<Notification> notifications) {
        if (this.notifications != null) {
            this.notifications.forEach(i -> i.setCustomer(null));
        }
        if (notifications != null) {
            notifications.forEach(i -> i.setCustomer(this));
        }
        this.notifications = notifications;
    }

    public Customer notifications(Set<Notification> notifications) {
        this.setNotifications(notifications);
        return this;
    }

    public Customer addNotification(Notification notification) {
        this.notifications.add(notification);
        notification.setCustomer(this);
        return this;
    }

    public Customer removeNotification(Notification notification) {
        this.notifications.remove(notification);
        notification.setCustomer(null);
        return this;
    }

    public Set<Quotation> getQuotations() {
        return this.quotations;
    }

    public void setQuotations(Set<Quotation> quotations) {
        if (this.quotations != null) {
            this.quotations.forEach(i -> i.setCustomer(null));
        }
        if (quotations != null) {
            quotations.forEach(i -> i.setCustomer(this));
        }
        this.quotations = quotations;
    }

    public Customer quotations(Set<Quotation> quotations) {
        this.setQuotations(quotations);
        return this;
    }

    public Customer addQuotation(Quotation quotation) {
        this.quotations.add(quotation);
        quotation.setCustomer(this);
        return this;
    }

    public Customer removeQuotation(Quotation quotation) {
        this.quotations.remove(quotation);
        quotation.setCustomer(null);
        return this;
    }

    public Set<RentalContract> getRentalContracts() {
        return this.rentalContracts;
    }

    public void setRentalContracts(Set<RentalContract> rentalContracts) {
        if (this.rentalContracts != null) {
            this.rentalContracts.forEach(i -> i.setCustomer(null));
        }
        if (rentalContracts != null) {
            rentalContracts.forEach(i -> i.setCustomer(this));
        }
        this.rentalContracts = rentalContracts;
    }

    public Customer rentalContracts(Set<RentalContract> rentalContracts) {
        this.setRentalContracts(rentalContracts);
        return this;
    }

    public Customer addRentalContract(RentalContract rentalContract) {
        this.rentalContracts.add(rentalContract);
        rentalContract.setCustomer(this);
        return this;
    }

    public Customer removeRentalContract(RentalContract rentalContract) {
        this.rentalContracts.remove(rentalContract);
        rentalContract.setCustomer(null);
        return this;
    }

    public Set<Charge> getCharges() {
        return this.charges;
    }

    public void setCharges(Set<Charge> charges) {
        if (this.charges != null) {
            this.charges.forEach(i -> i.setCustomer(null));
        }
        if (charges != null) {
            charges.forEach(i -> i.setCustomer(this));
        }
        this.charges = charges;
    }

    public Customer charges(Set<Charge> charges) {
        this.setCharges(charges);
        return this;
    }

    public Customer addCharge(Charge charge) {
        this.charges.add(charge);
        charge.setCustomer(this);
        return this;
    }

    public Customer removeCharge(Charge charge) {
        this.charges.remove(charge);
        charge.setCustomer(null);
        return this;
    }

    public Set<Contact> getContacts() {
        return this.contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        if (this.contacts != null) {
            this.contacts.forEach(i -> i.setCustomer(null));
        }
        if (contacts != null) {
            contacts.forEach(i -> i.setCustomer(this));
        }
        this.contacts = contacts;
    }

    public Customer contacts(Set<Contact> contacts) {
        this.setContacts(contacts);
        return this;
    }

    public Customer addContact(Contact contact) {
        this.contacts.add(contact);
        contact.setCustomer(this);
        return this;
    }

    public Customer removeContact(Contact contact) {
        this.contacts.remove(contact);
        contact.setCustomer(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return getId() != null && getId().equals(((Customer) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
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
