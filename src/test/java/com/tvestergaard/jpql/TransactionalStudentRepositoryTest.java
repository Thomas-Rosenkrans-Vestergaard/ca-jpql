package com.tvestergaard.jpql;

import com.tvestergaard.jpql.entities.Semester;
import com.tvestergaard.jpql.entities.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TransactionalStudentRepositoryTest
{

    private EntityManagerFactory           factory = Persistence.createEntityManagerFactory("ca-jpql-test-pu");
    private EntityManager                  manager;
    private TransactionalStudentRepository instance;

    @Before
    public void setUp() throws Exception
    {
        manager = factory.createEntityManager();
        instance = new TransactionalStudentRepository(manager, TransactionStrategy.ROLLBACK);
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
        Student student = instance.find(1);

        assertEquals(1, (long) student.getId());
        assertEquals("Jens", student.getFirstName());
        assertEquals("Jensen", student.getLastName());
        assertEquals(1, (long) student.getSemester().getId());
    }

    @Test
    public void findReturnsNull()
    {
        assertNull(instance.find(-1));
    }

    @Test
    public void all()
    {
        List<Student> students = instance.all();

        assertEquals(6, students.size());
        assertEquals(1, (long) students.get(0).getId());
        assertEquals(6, (long) students.get(5).getId());
    }

    @Test
    public void findByFirstName()
    {
        List<Student> students = instance.findByFirstName("Anders");

        assertEquals(1, students.size());
        assertEquals(6, (long) students.get(0).getId());
    }

    @Test
    public void findByLastName()
    {
        List<Student> students = instance.findByLastName("And");

        assertEquals(2, students.size());
        assertEquals(5, (long) students.get(0).getId());
        assertEquals(6, (long) students.get(1).getId());
    }

    @Test
    public void createStudent()
    {
        Student student = instance.createStudent("Thomas", "Vestergaard");

        assertNotNull(student.getId());
        assertEquals(student, instance.find(student.getId()));
        assertNull(student.getSemester());
    }

    @Test
    public void createStudentAssign()
    {
        Semester semester = manager.find(Semester.class, 1l);
        Student  student  = instance.createStudent("Thomas", "Vestergaard", semester);


        assertNotNull(student.getId());
        assertEquals(student, instance.find(student.getId()));
        assertEquals(semester, student.getSemester());
    }

    @Test
    public void assign()
    {
        Semester semester = manager.find(Semester.class, 1l);
        Student  student  = instance.createStudent("Thomas", "Vestergaard");

        assertNull(student.getSemester());
        instance.assign(student, semester);
        assertEquals(semester, student.getSemester());
    }

    @Test
    public void countEnrolledIn()
    {
        assertEquals(2, instance.countEnrolledIn("CLcos-v14e"));
    }

    @Test
    public void countEnrolled()
    {
        assertEquals(6, instance.countEnrolled());
    }

    @Test
    public void getStudentInfo()
    {
        StudentInfo studentInfo = instance.getStudentInfo(1);

        assertEquals(1, (long) studentInfo.studentId);
        assertEquals("Jens Jensen", studentInfo.fullName);
        assertEquals("Computer Science 3. sem", studentInfo.classDescription);
        assertEquals("CLcos-v14e", studentInfo.classNameThisSemester);
    }

    @Test
    public void getStudentInfoReturnsNull()
    {
        assertNull(instance.getStudentInfo(-1));
    }

    @Test
    public void getStudentInfoList()
    {

    }

    @Test
    public void close()
    {
    }
}