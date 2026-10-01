package at.htlklu.elga.apps;

import at.htlklu.elga.model.Doctor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DoctorApp {
    private static Logger logger = LogManager.getLogger(DoctorApp.class);

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    private static int doctorId = 2;

    public static void main(String[] args)
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("elga");
        entityManager = entityManagerFactory.createEntityManager();

        selectA();

        entityManager.close();
        entityManagerFactory.close();
    }


    private static void selectA() {
        Doctor doctor = entityManager.find(Doctor.class, doctorId);
//        logger.info(doctor);
//        logger.info(doctor.toString());
//        logger.info(doctor.getDoctorHospitals().size());
//        System.out.println(doctor.getDoctorHospitals().size());

        doctor.getDoctorHospitals()
                .forEach(doctorHospital -> System.out.printf("%s | %s | %s%n",
                        doctorHospital.getDateFrom(),
                        doctorHospital.getDateTo(),
                        doctorHospital.getHospital().getNameShort()));




        System.out.println(doctor.getIncomeMin());
        System.out.println(doctor.getIncomeMax());
        System.out.println(doctor.getIncomeMin() + doctor.getIncomeMax());
//        System.out.println(doctor.getMonthsStatistics());
//        System.out.println(doctor.getIncomeStatistics());
//        System.out.println(doctor.getTotalIncomeStatistics());
        //		hospital.getDepartments().stream()
//								 .forEach(System.out::println);

//		logger.info(HTLEntityManager.getPersistenceContextContent(entityManager));				// zeige den 1st-Level Cache
    }

}

