package at.htlklu.elga.apps;

import at.htlklu.elga.model.Doctor;
import at.htlklu.elga.model.DoctorHospital;
import at.htlklu.elga.model.Hospital;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class DoctorHospitalApp {

    private static Logger logger = LogManager.getLogger(DoctorHospitalApp.class);

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    private static int doctorHospitalId = 3;

    public static void main(String[] args)
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("elga");
        entityManager = entityManagerFactory.createEntityManager();

        //selectA();
        createDoctorHospital();

        entityManager.close();
        entityManagerFactory.close();
    }


    private static void selectA()
    {
        DoctorHospital doctorHospital = entityManager.find(DoctorHospital.class, doctorHospitalId);

        logger.info(doctorHospital);
        logger.info(doctorHospital.getHospital().getName());
        logger.info(doctorHospital.getDoctor());
        logger.info(doctorHospital.toString());

//		hospital.getDepartments().stream()
//								 .forEach(System.out::println);

//		logger.info(HTLEntityManager.getPersistenceContextContent(entityManager));				// zeige den 1st-Level Cache
    }

    private static void createDoctorHospital() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            // Hospital
            // Doctor

            Hospital hospital = entityManager.find(Hospital.class, 1);
            Doctor doctor = entityManager.find(Doctor.class, 1);

            DoctorHospital doctorHospital = new DoctorHospital(1000.0, LocalDate.of(2024, 1, 1), LocalDate.of(2022, 12, 31));
            // add1
            hospital.addDoctorHospital(doctorHospital);
            doctor.addDoctorHospital(doctorHospital);

            // add2
            entityManager.persist(doctorHospital);
            transaction.commit();
        }
        catch (Exception e) {
            transaction.rollback();
            logger.error(e.getMessage());

        }
    }

}
