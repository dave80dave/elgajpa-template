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
@Table(name = "MEDICAL_CASE")
public class MedicalCase implements Serializable {
    //region static Properties
    //endregion

    //region Properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEDICAL_CASE_ID")
    private Integer medicalCaseId;

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
    @JoinColumn(name = "HOSPITAL_ID")
    private Hospital hospital;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCTOR_ID_REF")
    private Doctor referringDoctor;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CS_ID_DIAGNOSIS")
    private ClassificationSystem diagnosis;

    @NotBlank
    @Column(name = "STATUS")
    private String status;

    @NotBlank
    @Column(name = "CASE_NUMBER")
    private String caseNumber;

    @NotNull
    @Column(name = "DATE_FROM")
    private LocalDate dateFrom;

    @Column(name = "DATE_TO")
    private LocalDate dateTo;

    @OneToMany(mappedBy = "medicalCase", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MedicalVisit> medicalVisits = new HashSet<>();
    //endregion

    //region Constructors
    public MedicalCase() {
    }

    public MedicalCase(Patient patient, Hospital hospital, Doctor referringDoctor,
                       ClassificationSystem diagnosis, String status, String caseNumber,
                       LocalDate dateFrom, LocalDate dateTo) {
        this.patient = patient;
        this.hospital = hospital;
        this.referringDoctor = referringDoctor;
        this.diagnosis = diagnosis;
        this.status = status;
        this.caseNumber = caseNumber;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }
    //endregion

    //region Getter and Setter
    public Integer getMedicalCaseId() { return medicalCaseId; }
    public void setMedicalCaseId(Integer medicalCaseId) { this.medicalCaseId = medicalCaseId; }
    public String getCreatedBy() { return createdBy; }
    public LocalDateTime getDateCreated() { return dateCreated; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public Hospital getHospital() { return hospital; }
    public void setHospital(Hospital hospital) { this.hospital = hospital; }
    public Doctor getReferringDoctor() { return referringDoctor; }
    public void setReferringDoctor(Doctor referringDoctor) { this.referringDoctor = referringDoctor; }
    public ClassificationSystem getDiagnosis() { return diagnosis; }
    public void setDiagnosis(ClassificationSystem diagnosis) { this.diagnosis = diagnosis; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCaseNumber() { return caseNumber; }
    public void setCaseNumber(String caseNumber) { this.caseNumber = caseNumber; }
    public LocalDate getDateFrom() { return dateFrom; }
    public void setDateFrom(LocalDate dateFrom) { this.dateFrom = dateFrom; }
    public LocalDate getDateTo() { return dateTo; }
    public void setDateTo(LocalDate dateTo) { this.dateTo = dateTo; }
    public Set<MedicalVisit> getMedicalVisits() { return medicalVisits; }
    //endregion

    //region toString
    @Override
    public String toString() {
        return String.format("%s (%d)", caseNumber, medicalCaseId);
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
        if (!(obj instanceof MedicalCase medicalCase)) return false;
        return medicalCaseId != null && Objects.equals(medicalCaseId, medicalCase.getMedicalCaseId());
    }
    //endregion
}
