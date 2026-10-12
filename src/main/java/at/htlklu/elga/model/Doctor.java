package at.htlklu.elga.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "DOCTOR")

public class Doctor implements Serializable {

    //region static Properties
    //endregion

    //region Properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DOCTOR_ID")
    private Integer doctorId;

    @NotBlank
    @Column(name = "FIRSTNAME")
    private String firstname;

    @NotBlank
    @Column(name = "SURNAME")
    private String surname;

    @NotNull
    @Column(name = "SEX")
    private Character sex;

    @NotBlank
    @Column(name = "TITLE")
    private String title;

    @NotNull
    @Column(name = "DATEOFBIRTH")
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "doctor",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Set<DoctorHospital> doctorHospitals = new HashSet<>();

    @OneToMany(mappedBy = "doctorAtt",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Set<MedicalVisit> medicalVisits = new HashSet<>();

        @OneToMany(mappedBy = "referringDoctor",
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY)
        private Set<MedicalCase> medicalCases = new HashSet<>();


    //endregion

    //region Constructors
    public Doctor() {
    }

    // (v) Constructor mit Allen verpflichtenden Attribute (ohne ID)

    public Doctor(String firstname, String surname, Character sex, String title, LocalDate dateOfBirth) {
        this.firstname = firstname;
        this.surname = surname;
        this.sex = sex;
        this.title = title;
        this.dateOfBirth = dateOfBirth;
    }


    //endregion

    //region Getter and Setter
    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Character getSex() {
        return sex;
    }

    public void setSex(Character sex) {
        this.sex = sex;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<DoctorHospital> getDoctorHospitals() {
        return doctorHospitals;
    }
    //endregion

    //region Methods

    //region DoctorHospital
    // 1. Ziel: Kennzahl Income
    public DoubleSummaryStatistics getIncomeStatistics() {
        return this.getDoctorHospitals().stream()
                .mapToDouble(DoctorHospital::getIncome)
                .summaryStatistics();
    }

    // 2. Ziel: Kennzahl Month
    public LongSummaryStatistics getMonthsStatistics() {
        return this.doctorHospitals.stream()
                .mapToLong(DoctorHospital::getMonths)
                .summaryStatistics();
    }

    // 3. Ziel: Kennzahl Total
    public DoubleSummaryStatistics getTotalIncomeStatistics() {
        return this.getDoctorHospitals().stream()
                .mapToDouble(DoctorHospital::getTotalIncome)
                .summaryStatistics();
    }

    // Ziel Häufigkeiten pro KA ermitteln

    public Map<Hospital, Long> getHospitalFrequencies() {
        return this.doctorHospitals.stream()
                .collect(Collectors.groupingBy(DoctorHospital::getHospital,
                        Collectors.counting()));
    }

    //region MedicalVisit

    public DoubleSummaryStatistics getCostsByDayStatistics() {
        return this.medicalVisits.stream()
                .mapToDouble(MedicalVisit::getCostsPerDay)
                .summaryStatistics();
    }

    public LongSummaryStatistics getDaysStatistics() {
        return this.medicalVisits.stream()
                .mapToLong(MedicalVisit::getDays)
                .summaryStatistics();
    }

    public DoubleSummaryStatistics getCostsStatistics() {
        return this.medicalVisits.stream()
                .mapToDouble(MedicalVisit::getCosts)
                .summaryStatistics();
    }

    public IntSummaryStatistics getRatingsStatistics() {
        return this.medicalVisits.stream()
                .mapToInt(MedicalVisit::getRating)
                .summaryStatistics();
    }
    //endregion MedicalCase
    public LongSummaryStatistics getDaysStatistic(){
        return this.medicalCases.stream()
                .mapToLong(MedicalCase::getDays)
                .summaryStatistics();
    }


    //region

    //region
    //endregion


    //endregion

    //region toString
    @Override
    public String toString() {
        return String.format("%s (%d)", this.surname, this.doctorId);
    }
    //endregion


    //region Add and Remove
    //endregion

    //region HashCode and Equals
    //endregion
}
