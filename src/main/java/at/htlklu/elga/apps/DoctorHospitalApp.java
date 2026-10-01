package at.htlklu.elga.apps;

import at.htlklu.elga.model.DoctorHospital;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DoctorHospitalApp {

    private static Logger logger = LogManager.getLogger(DoctorHospitalApp.class);

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    private static int doctorHospitalId = 3;

    public static void main(String[] args)
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("elga");
        entityManager = entityManagerFactory.createEntityManager();

        selectA();

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

}
