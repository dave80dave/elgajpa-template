package at.htlklu.elga.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "PATIENT")
public class Patient implements Serializable {
    //region static Properties
    //endregion

    //region Properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PATIENT_ID")
    private Integer patientId;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "DATE_CREATED")
    private LocalDateTime dateCreated;

    @NotBlank
    @Column(name = "FIRSTNAME")
    private String firstname;

    @NotBlank
    @Column(name = "SURNAME")
    private String surname;

    @NotNull
    @Column(name = "SEX")
    private Character sex;

    @Column(name = "TITLE")
    private String title;

    @NotNull
    @Column(name = "DATEOFBIRTH")
    private LocalDate dateOfBirth;

    @Column(name = "SOCIALSECURITYNUMBER")
    private String socialSecurityNumber;

    @Column(name = "WEIGHT")
    private Double weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CS_ID_CARRIER")
    private ClassificationSystem carrier;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Address> addresses = new HashSet<>();
    //endregion

    //region Constructors
    public Patient() {
    }

    public Patient(String firstname, String surname, Character sex, String title,
                   LocalDate dateOfBirth, String socialSecurityNumber, Double weight) {
        this.firstname = firstname;
        this.surname = surname;
        this.sex = sex;
        this.title = title;
        this.dateOfBirth = dateOfBirth;
        this.socialSecurityNumber = socialSecurityNumber;
        this.weight = weight;
    }
    //endregion

    //region Getter and Setter
    public Integer getPatientId() { return patientId; }
    public void setPatientId(Integer patientId) { this.patientId = patientId; }
    public String getCreatedBy() { return createdBy; }
    public LocalDateTime getDateCreated() { return dateCreated; }
    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public Character getSex() { return sex; }
    public void setSex(Character sex) { this.sex = sex; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getSocialSecurityNumber() { return socialSecurityNumber; }
    public void setSocialSecurityNumber(String socialSecurityNumber) { this.socialSecurityNumber = socialSecurityNumber; }
    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }
    public ClassificationSystem getCarrier() { return carrier; }
    public void setCarrier(ClassificationSystem carrier) { this.carrier = carrier; }
    public Set<Address> getAddresses() { return addresses; }
    //endregion

    //region toString
    @Override
    public String toString() {
        return String.format("%s %s (%d)", firstname, surname, patientId);
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
        if (!(obj instanceof Patient patient)) return false;
        return patientId != null && Objects.equals(patientId, patient.getPatientId());
    }
    //endregion
}
