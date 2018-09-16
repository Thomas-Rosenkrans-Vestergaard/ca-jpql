package com.tvestergaard.jpql;

import com.tvestergaard.jpql.entities.Semester;
import com.tvestergaard.jpql.entities.Student;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.fail;

public class RepositoryFacadeTest
{

    private final EntityManagerFactory factory = Persistence.createEntityManagerFactory("ca-jpql-test-pu");
    private       EntityManager        entityManager;
    private       RepositoryFacade     instance;

    @Before
    public void setUp() throws Exception
    {
        entityManager = factory.createEntityManager();
        instance = new RepositoryFacade(entityManager, TransactionStrategy.ROLLBACK);
    }

    @Test
    public void transactionCommit() throws Exception
    {
        instance.transaction((semesters, students, teachers) -> {
            Semester createdSemester = semesters.create("TransactionalSemA", "TransactionalSemB");
            Student  createdStudent  = students.createStudent("TransactionalStuA", "TransactionalStuB", createdSemester);
        });

        assertNotNull(entityManager.createNamedQuery("Semester.findByDescription")
                              .setParameter("description", "TransactionalSemA")
                              .getSingleResult());

        assertNotNull(entityManager.createNamedQuery("Student.findByFirstName")
                              .setParameter("firstName", "TransactionalStuA")
                              .getSingleResult());
    }

    @Test
    public void transactionRollback() throws Exception
    {
        try {
            instance.transaction((semesters, students, teachers) -> {
                Semester createdSemester = semesters.create("TransactionalSemA", "TransactionalSemB");
                Student  createdStudent  = students.createStudent("TransactionalStuA", "TransactionalStuB", createdSemester);
                throw new Exception(); // Something went wrong
            });
            fail("Should not reach.");
        } catch (Exception e) {

        }

        try {
            entityManager.createNamedQuery("Semester.findByDescription", Semester.class)
                    .setParameter("description", "TransactionalSemA")
                    .getSingleResult();
            fail("Should not exist");
        } catch (Exception e) {

        }

        try {
            entityManager.createNamedQuery("Student.findByFirstName", Student.class)
                    .setParameter("firstName", "TransactionalStuA")
                    .getSingleResult();
            fail("Should not exist");
        } catch (Exception e) {

        }
    }
}