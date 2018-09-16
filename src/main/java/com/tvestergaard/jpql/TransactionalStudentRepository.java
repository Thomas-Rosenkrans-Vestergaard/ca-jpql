package com.tvestergaard.jpql;

import com.tvestergaard.jpql.entities.Semester;
import com.tvestergaard.jpql.entities.Student;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public class TransactionalStudentRepository extends AbstractTransactionalRepository implements StudentRepository
{

    /**
     * The {@code EntityManager} that operations are performed upon.
     */
    private EntityManager entityManager;

    /**
     * Creates a new {@link TransactionalStudentRepository}.
     *
     * @param entity  The {@code EntityManager} that operations are to be performed upon.
     * @param onClose The transactional operation taken when this object closes.
     */
    public TransactionalStudentRepository(EntityManager entity, TransactionStrategy onClose)
    {
        super(null, onClose);
        this.entityManager = entity;
        this.entityTransaction = this.entityManager.getTransaction();
        begin();
    }

    /**
     * Finds the student with the provided id.
     *
     * @param id The id of the student to find.
     * @return The student with the provided id, {@code null} when no such student exists.
     */
    @Override public Student find(long id)
    {
        try {
            return entityManager.createNamedQuery("Student.findById", Student.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Finds the complete list of students in the system.
     *
     * @return The complete list of students in the system.
     */
    @Override public List<Student> all()
    {
        return entityManager.createNamedQuery("Student.findAll", Student.class).getResultList();
    }

    /**
     * Finds the students with the provided first name.
     *
     * @param firstName The first name that returned students must have.
     * @return The students with the provided first name.
     */
    @Override public List<Student> findByFirstName(String firstName)
    {
        return entityManager.createNamedQuery("Student.findByFirstName", Student.class)
                .setParameter("firstName", firstName)
                .getResultList();
    }

    /**
     * Finds the students with the provided last name.
     *
     * @param lastName The last name that returned students must have.
     * @return The students with the provided last name.
     */
    @Override public List<Student> findByLastName(String lastName)
    {
        return entityManager.createNamedQuery("Student.findByLastName", Student.class)
                .setParameter("lastName", lastName)
                .getResultList();
    }

    /**
     * Creates (inserts) a new student.
     *
     * @param firstName The first name of the student.
     * @param lastName  The last name of the student.
     * @return The newly created student entity.
     */
    @Override public Student createStudent(String firstName, String lastName)
    {
        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        entityManager.persist(student);

        return student;
    }

    /**
     * Creates (inserts) a new student, and assigns the student to the provided semester.
     *
     * @param firstName The first name of the student.
     * @param lastName  The last name of the student.
     * @param semester  The semester to assign the student to.
     * @return The newly created student entity.
     */
    @Override public Student createStudent(String firstName, String lastName, Semester semester)
    {
        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setSemester(semester);
        entityManager.persist(student);

        return student;
    }

    /**
     * Assigns the provided student to the provided semester.
     *
     * @param student The student to assign to the provided semester.
     * @param to      The semester to assign the provided student to.
     * @return The updated student entity.
     */
    @Override public Student assign(Student student, Semester to)
    {
        student.setSemester(to);
        entityManager.persist(student);

        return student;
    }

    /**
     * Returns the number of students in the semester with the provided name.
     *
     * @param semesterName The name of the semester to return the number of enrolled students from.
     * @return The number of students in the semester with the provided name.
     */
    @Override public long countEnrolledIn(String semesterName)
    {
        return (Long) entityManager.createQuery("SELECT count(s) FROM Student s WHERE s.semester.name = :name")
                .setParameter("name", semesterName)
                .getSingleResult();
    }

    /**
     * Returns the total number of students in all semesters.
     *
     * @return The total number of students in all semesters.
     */
    @Override public long countEnrolled()
    {
        return (Long) entityManager.createQuery("SELECT count(s) FROM Student s WHERE s.semester IS NOT NULL")
                .getSingleResult();
    }

    /**
     * Returns a {@link StudentInfo} instance containing information about the student with the provided id.
     *
     * @param id The id of the student to return the info of.
     * @return The information about the student with the provided id, {@code null} when no such student exists.
     */
    @Override public StudentInfo getStudentInfo(long id)
    {
        try {
            return entityManager.createQuery(
                    "SELECT NEW com.tvestergaard.jpql.StudentInfo(s.firstName, s.lastName, s.id, s.semester.name, s.semester.description) " +
                    "FROM Student s WHERE s.id = :id", StudentInfo.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Returns a list of {@link StudentInfo} instances containing information about the students in the system.
     *
     * @return The list of information.
     */
    @Override public List<StudentInfo> getStudentInfo()
    {
        return entityManager.createQuery(
                "SELECT NEW com.tvestergaard.jpql.StudentInfo(s.firstName, s.lastName, s.id, s.semester.name, s.semester.description) " +
                "FROM Student s", StudentInfo.class)
                .getResultList();
    }

    /**
     * Closes the {@code EntityManager}.
     *
     * @throws Exception IOException
     */
    @Override public void close() throws Exception
    {
        super.close();
        entityManager.close();
    }
}
