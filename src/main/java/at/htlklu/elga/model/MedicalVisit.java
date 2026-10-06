package at.htlklu.elga.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "MEDICAL_VISIT")
public class MedicalVisit implements Serializable {
    //region static Properties
    //endregion

    //region Properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEDICAL_VISIT_ID")
    private Integer medicalVisitId;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "DATE_CREATED")
    private LocalDateTime dateCreated;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEDICAL_CASE_ID")
    private MedicalCase medicalCase;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTMENT_ID")
    private Department department;

    @Column(name = "ROOM_NUMBER")
    private String roomNumber;

    @Column(name = "COSTS_PER_DAY")
    private Double costsPerDay;

    @Column(name = "RATING")
    private Integer rating;

    @NotNull
    @Column(name = "DATE_FROM")
    private LocalDate dateFrom;

    @Column(name = "DATE_TO")
    private LocalDate dateTo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCTOR_ID_ATT")
    private Doctor doctorAtt;

    //endregion

    //region Constructors
    public MedicalVisit() {
    }

    public MedicalVisit(MedicalCase medicalCase, Department department, Doctor doctorAtt,
                        String roomNumber, Double costsPerDay, Integer rating,
                        LocalDate dateFrom, LocalDate dateTo) {
        this.medicalCase = medicalCase;
        this.department = department;
        this.doctorAtt = doctorAtt;
        this.roomNumber = roomNumber;
        this.costsPerDay = costsPerDay;
        this.rating = rating;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }
    //endregion

    //region Getter and Setter
    public Integer getMedicalVisitId() { return medicalVisitId; }
    public void setMedicalVisitId(Integer medicalVisitId) { this.medicalVisitId = medicalVisitId; }
    public String getCreatedBy() { return createdBy; }
    public LocalDateTime getDateCreated() { return dateCreated; }
    public MedicalCase getMedicalCase() { return medicalCase; }
    public void setMedicalCase(MedicalCase medicalCase) { this.medicalCase = medicalCase; }
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
    public Doctor getDoctorAtt() { return doctorAtt; }
    public void setDoctorAtt(Doctor doctorAtt) { this.doctorAtt = doctorAtt; }
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public Double getCostsPerDay() { return costsPerDay; }
    public void setCostsPerDay(Double costsPerDay) { this.costsPerDay = costsPerDay; }
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    public LocalDate getDateFrom() { return dateFrom; }
    public void setDateFrom(LocalDate dateFrom) { this.dateFrom = dateFrom; }
    public LocalDate getDateTo() { return dateTo; }
    public void setDateTo(LocalDate dateTo) { this.dateTo = dateTo; }
    //endregion

    //region Methodes

    // 1. Ziel: Aufenthaltsdauer in Tagen

    public int getDays(){
        if (this.dateTo.isAfter(LocalDate.now())){
         return (int)ChronoUnit.DAYS.between(this.dateFrom,LocalDate.now().plusDays(1));
        }
        else {
            return (int)ChronoUnit.DAYS.between(this.dateFrom, this.dateTo.plusDays(1));
        }

    }

    // 2. Ziel: Aufenthaltskosten

    public double getCosts(){
        return this.getDays() * this.costsPerDay;
    }


    //endregion

    //region toString
    @Override
    public String toString() {
        return String.format("MedicalVisit (%d), room %s", medicalVisitId, roomNumber);
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
        if (!(obj instanceof MedicalVisit medicalVisit)) return false;
        return medicalVisitId != null && Objects.equals(medicalVisitId, medicalVisit.getMedicalVisitId());
    }
    //endregion
}
