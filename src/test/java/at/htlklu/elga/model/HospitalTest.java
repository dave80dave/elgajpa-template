package at.htlklu.elga.model;

import org.junit.jupiter.api.*;
import jakarta.persistence.*;
import static junit.framework.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Quellen
// https://www.baeldung.com/junit-assertions
// https://www.baeldung.com/junit-before-beforeclass-beforeeach-beforeall

@DisplayName("Unit-Tests for Hospital")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HospitalTest
{
    //region static Properties
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    //endregion


    //region @BeforeAll and @BeforeEach
    @BeforeAll
    static void beforeAll()
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("elga");
    }

    @AfterAll
    static void afterAll()
    {
        entityManagerFactory.close();
    }

    @BeforeEach
    public void beforeEach()
    {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterEach
    public void afterEach()
    {
        entityManager.close();
    }
    //endregion


    //region Simple
    @Test
    @DisplayName("findById")
    @Order(10)
    // [UnitOfWork_StateUnderTest_ExpectedBehavior]
    void testHospitalLoading_NotNull()
    {
        Hospital hospital = entityManager.find(Hospital.class, 1);
        assertNotNull(hospital);
        assertEquals(1, hospital.getHospitalId().intValue());
    }

    @Test
    @DisplayName("findByWrongId")
    @Order(11)
    void testHospitalLoading_Null()
    {
        Hospital hospital = entityManager.find(Hospital.class, -1);
        assertNull(hospital);
    }

    @Test
    @DisplayName("getHospitalId")
    @Order(12)
    void testHospitalLoading_Id()
    {
        Hospital hospital = entityManager.find(Hospital.class, 1);
        assertNotNull(hospital);            // assertNull
        assertNotNull(hospital.getHospitalId());
        assertEquals(1, hospital.getHospitalId().intValue());
    }

    @Test
    @DisplayName("Departments1")
    @Order(20)
    void testHospitalLoading_DepartmentsSize1()
    {
        Hospital hospital = entityManager.find(Hospital.class, 1);
        assertNotNull(hospital);
        assertEquals(5, hospital.getDepartments().size());
    }

    @Test
    @DisplayName("Departments2")
    @Order(30)
    void testHospitalLoading_DepartmentsSize2()
    {
        Hospital hospital = entityManager.find(Hospital.class, 1);
        assertNotNull(hospital);
        assertTrue(hospital.getDepartments().size() > 0, "Departments are available");      // assertFalse
    }

    @Test
    @DisplayName("Departments3")
    @Order(40)
    void testHospitalLoading_DepartmentsSize3()
    {
        Hospital hospital = entityManager.find(Hospital.class, 1);
        assertNotNull(hospital);
        assertThat(hospital.getDepartments().size(), greaterThan(0));
    }
    //endregion

}
