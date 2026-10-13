package at.htlklu.elga.apps;

import at.htlklu.elga.model.Department;
import at.htlklu.elga.model.Doctor;
import at.htlklu.elga.model.Hospital;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.persistence.*;

import java.time.LocalDate;

public class DepartmentApp {
    private static Logger logger = LogManager.getLogger(DepartmentApp.class);

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    private static int departmentId = 2;

    public static void main(String[] args) {
        entityManagerFactory = Persistence.createEntityManagerFactory("elga");
        entityManager = entityManagerFactory.createEntityManager();

//		selectA();
        createDepartment();

        entityManager.close();
        entityManagerFactory.close();
    }

    private static void selectA() {

        Department department = entityManager.find(Department.class, departmentId);
        logger.info(department);
        logger.info(department.getHospital());
        logger.info(department.getMedicalVisits().size());

//		logger.info(HTLEntityManager.getPersistenceContextContent(entityManager));				// zeige den 1st-Level Cache
    }

    private static void createDepartment() {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        try {
//            Department department = new Department("Augenklinik");
//            Hospital hospital = entityManager.find(Hospital.class, 1);
//            department.setHospital(hospital);
            // Fall 1: ohne fK für Hospital
            // a) Modelfehler, wenn Annotation @NotNull bei der Property "hospital" vorhanden ist.
            // b) Datenbankfehler, wenn @NotNull nicht vorhanden ist.
            // Fall 2: mit FK für Hospital (unidirektional navigierbar)
			// Nachteil: Wenn wie vor dem Persist bzw. Commit die Liste der Departments laden,
			// dann wird diese nicht automatisch um die neue Apteilung erweitert.

			// Fall 3: mit FK für Hospital und Aufnahme der neuen Abteilung in die Liste

			Department department = new Department("Augenklinik"); // lege neue an
			Hospital hospital = entityManager.find(Hospital.class, 1); // muss ich haben, weil ich ein Hospital brauche
			department.setHospital(hospital);
			System.out.printf("Anzahl: %d%n", hospital.getDepartments().size());
			hospital.getDepartments().add(department); // jetzt wird es eingetragen also, wenn nicht im Constructor dann so anlegen
			// Fall 4:
            entityManager.persist(department);
            transaction.commit();
            System.out.printf("Anzahl: %d%n", hospital.getDepartments().size());
        } catch (Exception e) {
            transaction.rollback();
            System.err.printf("Fehler: %s%n", e.getMessage());
        }
    }
}
