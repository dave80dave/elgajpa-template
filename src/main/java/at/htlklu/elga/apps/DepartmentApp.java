package at.htlklu.elga.apps;

import at.htlklu.elga.model.Department;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.persistence.*;

public class DepartmentApp
{
	private static Logger logger = LogManager.getLogger(DepartmentApp.class);

	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;
	private static int departmentId = 2;
	
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

        Department department = entityManager.find(Department.class, departmentId);
        logger.info(department);
        logger.info(department.getHospital());
        logger.info(department.getMedicalVisits().size());

//		logger.info(HTLEntityManager.getPersistenceContextContent(entityManager));				// zeige den 1st-Level Cache
    }

}
