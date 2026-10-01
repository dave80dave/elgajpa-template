package at.htlklu.elga.apps;

import at.htlklu.elga.model.MedicalCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.persistence.*;

public class MedicalCaseApp
{
    private static Logger logger = LogManager.getLogger(MedicalCaseApp.class);

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    private static int medicalCaseId = 1;

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
        MedicalCase medicalCase = entityManager.find(MedicalCase.class, medicalCaseId);
        logger.info(medicalCase);
        logger.info(medicalCase.getPatient());
        logger.info(medicalCase.getHospital());
        logger.info(medicalCase.getReferringDoctor());
        logger.info(medicalCase.getDiagnosis());
        logger.info(medicalCase.getMedicalVisits().size());
    }
}
