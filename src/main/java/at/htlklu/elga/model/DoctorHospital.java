package at.htlklu.elga.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ManyToAny;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "DOCTOR_HOSPITAL")
public class DoctorHospital {
    //region Properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DOCTOR_HOSPITAL_ID")
    private Integer doctorHospitalId;

    @NotBlank
    @Column(name = "DATE_FROM")
    private LocalDate dateFrom;

    @NotBlank
    @Column(name = "DATE_TO")
    private LocalDate dateTo;

    @NotBlank
    @Column(name = "INCOME")
    private Double income;


    @NotNull        // verpflichtend
    @ManyToOne(fetch = FetchType.LAZY)
    // Wir müssen den Fremdschlüssel von Hospital in der Tabelle DEPARTMENT angeben
    // Hier: HOSPITAL_ID
    @JoinColumn(name = "HOSPITAL_ID")
    // In der Variable "hospital" wird die Instanz des zugehörigen Hospitals gespeichert
    private Hospital hospital;

    @NotNull        // verpflichtend
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCTOR_ID")
    private Doctor doctor;
    //endregion

    //region Constructor
    public DoctorHospital() {
    }
    //endregion

    //region Getter and Setter
    public Integer getDoctorHospitalId() {
        return doctorHospitalId;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public Double getIncome() {
        return income;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    // 1. Ziel: getMonths


    public double getTotalIncome(){
        return this.getMonths() * this.income;
    }

    public long getMonths(){
        if (this.dateTo.isAfter(LocalDate.now()))
            return ChronoUnit.MONTHS.between(this.dateFrom, LocalDate.now());
        else
            return ChronoUnit.MONTHS.between(this.dateFrom, this.dateTo.plusDays(1));
    }

    //region toString
    @Override
    public String toString() {
        return "DoctorHospital{" +
                "doctorHospitalId=" + doctorHospitalId +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", income=" + income +
                '}';
    }
    //endregion
}
