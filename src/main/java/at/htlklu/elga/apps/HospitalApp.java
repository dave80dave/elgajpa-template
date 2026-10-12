package at.htlklu.elga.apps;

import at.htlklu.elga.model.Hospital;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.persistence.*;

public class HospitalApp
{
	private static Logger logger = LogManager.getLogger(HospitalApp.class);

	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;
	private static int hospitalId = 1;
	
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
		Hospital hospital = entityManager.find(Hospital.class, hospitalId);


		logger.info(hospital.getRaitingStatistics());
		logger.info(hospital);

		logger.info(hospital.getSexFrequency());

//		hospital.getDepartments().stream()
//								 .forEach(System.out::println);

//		logger.info(HTLEntityManager.getPersistenceContextContent(entityManager));				// zeige den 1st-Level Cache
	}

}
