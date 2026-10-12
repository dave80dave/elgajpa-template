package at.htlklu.elga.apps;

import at.htlklu.elga.model.Doctor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.Map;

public class DoctorApp {
    private static Logger logger = LogManager.getLogger(DoctorApp.class);

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    private static int doctorId = 2;

    public static void main(String[] args)
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("elga");
        entityManager = entityManagerFactory.createEntityManager();

        //selectA();
        createDoctor();
        entityManager.close();
        entityManagerFactory.close();
    }


    private static void selectA() {
        Doctor doctor = entityManager.find(Doctor.class, doctorId);
        logger.info(doctor);
        logger.info(doctor.toString());
        logger.info(doctor.getDoctorHospitals().size());
        System.out.println(doctor.getDoctorHospitals().size());

        doctor.getDoctorHospitals()
                .forEach(doctorHospital -> System.out.printf("%s | %s | %s%n",
                        doctorHospital.getDateFrom(),
                        doctorHospital.getDateTo(),
                        doctorHospital.getHospital().getNameShort()));
//        System.out.println(doctor.getMonthsStatistics());
//        System.out.println(doctor.getIncomeStatistics());
//        System.out.println(doctor.getTotalIncomeStatistics());
//        System.out.println(doctor.getTotalIncomeStatistics());
//        		hospital.getDepartments().stream()
//								 .forEach(System.out::println);

//		logger.info(HTLEntityManager.getPersistenceContextContent(entityManager));
//      zeige den 1st-Level Cache

        System.out.println("Kosten pro Tag:" + doctor.getCostsByDayStatistics());
        System.out.println(doctor.getDaysStatistics());
        System.out.println(doctor.getCostsStatistics());
        System.out.println(doctor.getRatingsStatistics());
        System.out.println(doctor.getHospitalFrequencies());

        // Eine Map besteht aus beliebigen vielen Entries.
        // Ein Entry besteht aus einem Key und einem Value Paar.
        // der Key muss in der Map eindeutig sein.
        // Wie nutzt man eine Map mittels Streams?
        // Zuerst muss die Map in ein Set umgewandelt werden, dann das Set in einen Stream und dann entsteht ein Stream  von Entries

        doctor.getHospitalFrequencies().entrySet().stream()
                .sorted(Map.Entry.comparingByValue())// Stream von Entries
                .forEach(entry -> System.out.printf("%s: %d%n", entry.getKey(), entry.getValue()));
    }

    //region Create Doctor auch zum Lernen

    // Müssen eine Transaktion starten

    public static void createDoctor() {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        try {
            Doctor doctor = new Doctor("Max", "Mustermann", 'm', "Dr.",
                    LocalDate.of(1991, 1, 1));

            entityManager.persist(doctor);

            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Fehler beim Erstellen des Doktors");
        }



        //endregion
    }
}

