package at.htlklu.elga.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "DOCTOR")

    public class Doctor implements Serializable
    {

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

        @NotBlank
        @Column(name = "SEX")
        private Character sex;

        @NotBlank
        @Column(name = "TITLE")
        private String title;

        @NotBlank
        @Column(name = "DATEOFBIRTH")
        private Date dateOfBirth;

        @OneToMany(mappedBy = "doctor",
                cascade = CascadeType.ALL,
                fetch = FetchType.LAZY)
        private Set<DoctorHospital> doctorHospitals = new HashSet<>();
        //endregion

        //region Constructor
        public Doctor() {
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

        public Date getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(Date dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public Set<DoctorHospital> getDoctorHospitals() {
            return doctorHospitals;
        }
        //endregion

        // 1. Ziel: Kennzahl Income

        public LongSummaryStatistics getMonthsStatistics() {
            return this.getDoctorHospitals().stream()
                    .mapToLong(DoctorHospital::getMonths)
                    .summaryStatistics();
        }
        // 1. Ziel: Kennzahl Month

        public DoubleSummaryStatistics getIncomeStatistics() {
            return this.getDoctorHospitals().stream()
                    .mapToDouble(DoctorHospital::getIncome)
                    .summaryStatistics();
        }

        public DoubleSummaryStatistics getTotalIncomeStatistics() {
            return this.getDoctorHospitals().stream()
                    .mapToDouble(DoctorHospital::getTotalIncome)
                    .summaryStatistics();
        }

        //region Description
        @Override
        public String toString() {
            return String.format("%s (%d)", this.surname, this.doctorId);
        }
        //endregion
    }