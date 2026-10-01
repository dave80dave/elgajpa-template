package at.htlklu.elga.apps;

import at.htlklu.elga.model.Address;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.persistence.*;

public class AddressApp
{
    private static Logger logger = LogManager.getLogger(AddressApp.class);

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    private static int addressId = 1;

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
        Address address = entityManager.find(Address.class, addressId);
        logger.info(address);
        logger.info(address.getPatient());
        logger.info(address.getRegion());
    }
}
