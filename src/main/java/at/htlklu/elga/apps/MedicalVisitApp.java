package at.htlklu.elga.apps;

import at.htlklu.elga.model.MedicalVisit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.persistence.*;

public class MedicalVisitApp
{
    private static Logger logger = LogManager.getLogger(MedicalVisitApp.class);

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    private static int medicalVisitId = 1;

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
        MedicalVisit medicalVisit = entityManager.find(MedicalVisit.class, medicalVisitId);
        logger.info(medicalVisit);
        logger.info(medicalVisit.getMedicalCase());
        logger.info(medicalVisit.getDepartment());
        logger.info(medicalVisit.getAttendingDoctor());
    }
}
