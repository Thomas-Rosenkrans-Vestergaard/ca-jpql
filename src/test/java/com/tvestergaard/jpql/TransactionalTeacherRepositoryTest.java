package com.tvestergaard.jpql;

import com.tvestergaard.jpql.entities.Teacher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

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
        instance.begin();
    }

    @After
    public void tearDown() throws Exception
    {
        instance.close();
    }

    @Test
    public void find()
    {
        Teacher find = instance.find(2);

        assertEquals("Thomas", find.getFirstName());
    }

    @Test
    public void findReturnsNull()
    {
        assertNull(instance.find(-1));
    }

    @Test
    public void all()
    {
        List<Teacher> teachers = instance.all();

        assertEquals(3, teachers.size());
        assertEquals(1, (long) teachers.get(0).getId());
        assertEquals(3, (long) teachers.get(2).getId());
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