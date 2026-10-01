package at.htlklu.elga.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "ADDRESS")
public class Address implements Serializable {
    //region static Properties
    //endregion

    //region Properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADDRESS_ID")
    private Integer addressId;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "DATE_CREATED")
    private LocalDateTime dateCreated;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PATIENT_ID")
    private Patient patient;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REGION_ID")
    private Region region;

    @NotBlank
    @Column(name = "CITY")
    private String city;

    @NotBlank
    @Column(name = "ZIP_CODE")
    private String zipCode;

    @NotBlank
    @Column(name = "STREET")
    private String street;
    //endregion

    //region Constructors
    public Address() {
    }

    public Address(Patient patient, Region region, String city, String zipCode, String street) {
        this.patient = patient;
        this.region = region;
        this.city = city;
        this.zipCode = zipCode;
        this.street = street;
    }
    //endregion

    //region Getter and Setter
    public Integer getAddressId() { return addressId; }
    public void setAddressId(Integer addressId) { this.addressId = addressId; }
    public String getCreatedBy() { return createdBy; }
    public LocalDateTime getDateCreated() { return dateCreated; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public Region getRegion() { return region; }
    public void setRegion(Region region) { this.region = region; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }
    //endregion

    //region toString
    @Override
    public String toString() {
        return String.format("%s, %s %s (%d)", street, zipCode, city, addressId);
    }
    //endregion

    //region Add and Remove
    //endregion

    //region HashCode and Equals
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Address address)) return false;
        return addressId != null && Objects.equals(addressId, address.getAddressId());
    }
    //endregion
}
