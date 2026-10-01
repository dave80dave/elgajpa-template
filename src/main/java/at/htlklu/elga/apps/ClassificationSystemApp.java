package at.htlklu.elga.apps;

import at.htlklu.elga.model.ClassificationSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.persistence.*;

public class ClassificationSystemApp
{
    private static Logger logger = LogManager.getLogger(ClassificationSystemApp.class);

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    private static int classificationSystemId = 1;

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
        ClassificationSystem classificationSystem = entityManager.find(
                ClassificationSystem.class,
                classificationSystemId
        );
        logger.info(classificationSystem);
        logger.info(classificationSystem.getParent());
    }
}
