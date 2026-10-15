package at.htlklu.elga.apps;

import at.htlklu.elga.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.persistence.*;

import java.time.LocalDate;

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

        //selectA();
        createMedicalCase();

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

    public static void createMedicalCase() {

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {

            ClassificationSystem classificationSystem = entityManager.find(ClassificationSystem.class, 10_000_001);
            Patient patient = entityManager.find(Patient.class, 1);
            Hospital hospital = entityManager.find(Hospital.class, 2);
            Doctor doctor = entityManager.find(Doctor.class, 1);
            MedicalCase medicalCase = new MedicalCase(patient,hospital,doctor,classificationSystem,"s","3", LocalDate.of(2026, 10, 1),LocalDate.now());

            patient.addMedicalCase(medicalCase);
            //hospital.addMedicalCase(medicalCase);
            medicalCase.setHospital(hospital); // das ist das gleiche wie oben nur schneller
            doctor.addMedicalCase(medicalCase);
            classificationSystem.addMedicalCase(medicalCase);


            entityManager.persist(medicalCase);
            transaction.commit();
        }
        catch (Exception e) {
            transaction.rollback();
            logger.error(e.getMessage());
        }
    }
}
