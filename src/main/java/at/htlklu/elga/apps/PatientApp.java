package at.htlklu.elga.apps;

import at.htlklu.elga.model.Patient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.persistence.*;

public class PatientApp
{
    private static Logger logger = LogManager.getLogger(PatientApp.class);

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    private static int patientId = 1;

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
        Patient patient = entityManager.find(Patient.class, patientId);
        logger.info(patient);
//        logger.info(patient.getCarrier());
        logger.info(patient.getAddresses().size());
    }
}
