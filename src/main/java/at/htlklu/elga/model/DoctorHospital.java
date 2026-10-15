package at.htlklu.elga.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Entity
@Table(name = "DOCTOR_HOSPITAL")
public class DoctorHospital
{
    //region static Properties
    public static Comparator<DoctorHospital> BY_DATE_FROM = Comparator.comparing(DoctorHospital::getDateFrom);

    //endregion

    //region Properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DOCTOR_HOSPITAL_ID")
    private Integer doctorHospitalId;

    @NotNull
    @Column(name = "DATE_FROM")
    private LocalDate dateFrom;

    @Column(name = "DATE_TO")
    private LocalDate dateTo;

    @Column(name = "INCOME")
    private Double income;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOSPITAL_ID")
    private Hospital hospital;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCTOR_ID")
    private Doctor doctor;


    //endregion

    //region Constructors
    public DoctorHospital()
    {
    }

    public DoctorHospital(Double income, LocalDate dateTo, LocalDate dateFrom) {
        this.income = income;
        this.dateTo = dateTo;
        this.dateFrom = dateFrom;
    }

    //endregion

    //region toString
    @Override
    public String toString()
    {
        return "DoctorHospital{" +
                "doctorHospitalId=" + doctorHospitalId +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", income=" + income +
                '}';
    }
    //endregion

    //region Getter and Setter
    public Integer getDoctorHospitalId()
    {
        return doctorHospitalId;
    }

    public void setDoctorHospitalId(Integer doctorHospitalId)
    {
        this.doctorHospitalId = doctorHospitalId;
    }

    public LocalDate getDateFrom()
    {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom)
    {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo()
    {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo)
    {
        this.dateTo = dateTo;
    }

    public Double getIncome()
    {
        return income;
    }

    public void setIncome(Double income)
    {
        this.income = income;
    }

    public Hospital getHospital()
    {
        return hospital;
    }

    public void setHospital(Hospital hospital)
    {
        this.hospital = hospital;
    }

    public Doctor getDoctor()
    {
        return doctor;
    }

    public void setDoctor(Doctor doctor)
    {
        this.doctor = doctor;
    }

    public double getTotalIncome()
    {
        double currentIncome = income == null ? 0.0 : income;
        return getMonths() * currentIncome;
    }

    public long getMonths()
    {
        LocalDate endDate;

        if (dateTo == null || dateTo.isAfter(LocalDate.now()))
        {
            endDate = LocalDate.now();
        }
        else
        {
            endDate = dateTo.plusDays(1);
        }

        return ChronoUnit.MONTHS.between(dateFrom, endDate);
    }
    //endregion

    //region Add and Remove


    //endregion

    //region HashCode and Equals
    @Override
    public int hashCode()
    {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        boolean equal;
        DoctorHospital doctorHospital1 = this;

        if (doctorHospital1 == obj)
        {
            equal = true;
        }
        else if (obj == null || !(obj instanceof DoctorHospital))
        {
            equal = false;
        }
        else
        {
            DoctorHospital doctorHospital2 = (DoctorHospital)obj;
            equal = doctorHospital1.doctorHospitalId != null
                    && Objects.equals(
                            doctorHospital1.doctorHospitalId,
                            doctorHospital2.getDoctorHospitalId()
                    );
        }

        return equal;
    }
    //endregion
}
