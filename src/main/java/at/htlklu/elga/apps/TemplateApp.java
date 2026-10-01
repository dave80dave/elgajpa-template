package at.htlklu.elga.apps;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.persistence.*;

public class TemplateApp
{
    private static Logger logger = LogManager.getLogger(TemplateApp.class);

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    public static void main(String[] args)
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("elga");
        entityManager = entityManagerFactory.createEntityManager();

        test();

        entityManager.close();
        entityManagerFactory.close();
    }

    private static void test()
    {
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try
        {
            entityTransaction.begin();
            // to do
            entityTransaction.commit();
        }
        catch (Exception e)
        {
            entityTransaction.rollback();
            e.printStackTrace();
        }

    }

}