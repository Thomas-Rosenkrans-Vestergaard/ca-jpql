package com.tvestergaard.jpql;

import com.tvestergaard.jpql.entities.Semester;
import com.tvestergaard.jpql.entities.Teacher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.*;

public class TransactionalSemesterRepositoryTest
{

    private EntityManagerFactory            emf = Persistence.createEntityManagerFactory("ca-jpql-test-pu");
    private TransactionalSemesterRepository instance;

    @Before
    public void setUp() throws Exception
    {
        instance = new TransactionalSemesterRepository(emf.createEntityManager(), TransactionStrategy.ROLLBACK);
        instance.begin();
    }

    @After
    public void tearDown() throws Exception
    {
        if (instance.isActive())
            instance.close();
    }

    @Test
    public void create()
    {
        Semester created = instance.create("Description", "Name");
        assertEquals(instance.find(created.getId()), created);
    }

    @Test
    public void find()
    {
        Semester find = instance.find(1);

        assertNotNull(find);
        assertEquals(1, (long) find.getId());
        assertEquals("Computer Science 3. sem", find.getDescription());
        assertEquals("CLcos-v14e", find.getName());

        assertTrue(find.getTeachers()
                           .stream()
                           .map(Teacher::getId)
                           .collect(Collectors.toSet())
                           .containsAll(Arrays.asList(1l, 2l, 3l)));
    }

    @Test
    public void findReturnsNull()
    {
        assertNull(instance.find(-1));
    }


    @Test
    public void all()
    {
        List<Semester> semesters = instance.all();

        assertEquals("CLcos-v14e", semesters.get(0).getName());
        assertEquals("CLdat-a14e", semesters.get(1).getName());
        assertEquals("CLdat-b14e", semesters.get(2).getName());
    }

    @Test
    public void close()
    {
    }
}