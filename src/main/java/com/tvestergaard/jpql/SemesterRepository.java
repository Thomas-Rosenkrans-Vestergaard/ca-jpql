package com.tvestergaard.jpql;

import com.tvestergaard.jpql.entities.Semester;

import java.util.List;

public interface SemesterRepository extends TransactionAware
{

    /**
     * Finds the semester with the provided id.
     *
     * @param id The id of the semester to find.
     * @return The semester with the provided id, {@code null} when no such entity exists.
     */
    Semester find(long id);

    /**
     * Returns a complete list of the semesters in the system.
     *
     * @return The complete list of the semesters in the system.
     */
    List<Semester> all();
}
