package com.tvestergaard.jpql;

import com.tvestergaard.jpql.entities.Teacher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static junit.framework.TestCase.assertEquals;

public class TransactionalTeacherRepositoryTest
{

    private EntityManagerFactory           factory = Persistence.createEntityManagerFactory("ca-jpql-test-pu");
    private EntityManager                  manager;
    private TransactionalTeacherRepository instance;

    @Before
    public void setUp() throws Exception
    {
        manager = factory.createEntityManager();
        instance = new TransactionalTeacherRepository(manager, TransactionStrategy.ROLLBACK);
    }

    @After
    public void tearDown() throws Exception
    {
        instance.close();
    }

    @Test
    public void findWithMostSemesters()
    {
        Teacher teacher = instance.findWithMostSemesters();

        assertEquals("Lars", teacher.getFirstName());
        assertEquals("Mortensen", teacher.getLastName());
    }

    @Test
    public void close()
    {

    }
}