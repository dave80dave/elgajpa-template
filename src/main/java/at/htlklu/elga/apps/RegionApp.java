package at.htlklu.elga.apps;

import at.htlklu.elga.model.Region;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.persistence.*;

public class RegionApp
{
    private static Logger logger = LogManager.getLogger(RegionApp.class);

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    private static int regionId = 1;

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
        Region region = entityManager.find(Region.class, regionId);
        logger.info(region);
    }
}
