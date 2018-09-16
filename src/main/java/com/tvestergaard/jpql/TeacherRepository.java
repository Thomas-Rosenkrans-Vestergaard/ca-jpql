package com.tvestergaard.jpql;

import com.tvestergaard.jpql.entities.Teacher;

public interface TeacherRepository extends TransactionAware
{

    /**
     * Returns the teacher who teaches the most semesters.
     *
     * @return The teacher who teaches the most semesters.
     */
    Teacher findWithMostSemesters();
}
