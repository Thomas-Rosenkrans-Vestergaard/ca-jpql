package com.tvestergaard.jpql;

import com.tvestergaard.jpql.entities.Teacher;

import java.util.List;

public interface TeacherRepository extends TransactionAware
{

    /**
     * Finds the teacher with the provided id.
     *
     * @param id The id of the teacher to find.
     * @return The thecher with the provided id, {@code null} when no such entity exists.
     */
    Teacher find(long id);

    /**
     * Finds all the teachers in the system.
     *
     * @return A list of all the teachers in the system.
     */
    List<Teacher> all();

    /**
     * Returns the teacher who teaches the most semesters.
     *
     * @return The teacher who teaches the most semesters.
     */
    Teacher findWithMostSemesters();
}
