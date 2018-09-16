package com.tvestergaard.jpql;

import com.tvestergaard.jpql.entities.Semester;
import com.tvestergaard.jpql.entities.Student;

import java.util.List;

public interface StudentRepository
{

    /**
     * Finds the student with the provided id.
     *
     * @param id The id of the student to find.
     * @return The student with the provided id, {@code null} when no such student exists.
     */
    Student find(long id);

    /**
     * Finds the complete list of students in the system.
     *
     * @return The complete list of students in the system.
     */
    List<Student> all();

    /**
     * Finds the students with the provided first name.
     *
     * @param firstName The first name that returned students must have.
     * @return The students with the provided first name.
     */
    List<Student> findByFirstName(String firstName);

    /**
     * Finds the students with the provided last name.
     *
     * @param lastName The last name that returned students must have.
     * @return The students with the provided last name.
     */
    List<Student> findByLastName(String lastName);

    /**
     * Creates (inserts) a new student.
     *
     * @param firstName The first name of the student.
     * @param lastName  The last name of the student.
     * @return The newly created student entity.
     */
    Student createStudent(String firstName, String lastName);

    /**
     * Creates (inserts) a new student, and assigns the student to the provided semester.
     *
     * @param firstName The first name of the student.
     * @param lastName  The last name of the student.
     * @param semester  The semester to assign the student to.
     * @return The newly created student entity.
     */
    Student createStudent(String firstName, String lastName, Semester semester);

    /**
     * Assigns the provided student to the provided semester.
     *
     * @param student The student to assign to the provided semester.
     * @param to      The semester to assign the provided student to.
     * @return The updated student entity.
     */
    Student assign(Student student, Semester to);

    /**
     * Returns the number of students in the semester with the provided name.
     *
     * @param semesterName The name of the semester to return the number of enrolled students from.
     * @return The number of students in the semester with the provided name.
     */
    long countEnrolledIn(String semesterName);

    /**
     * Returns the total number of students in all semesters.
     *
     * @return The total number of students in all semesters.
     */
    long countEnrolled();

    /**
     * Returns a {@link StudentInfo} instance containing information about the student with the provided id.
     *
     * @param id The id of the student to return the info of.
     * @return The information about the student with the provided id, {@code null} when no such student exists.
     */
    StudentInfo getStudentInfo(long id);

    /**
     * Returns a list of {@link StudentInfo} instances containing information about the students in the system.
     *
     * @return The list of information.
     */
    List<StudentInfo> getStudentInfo();
}
