package com.tvestergaard.jpql;

import com.tvestergaard.jpql.entities.Semester;

import java.util.List;

public interface SemesterRepository
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

    /**
     * Creates (inserts) a new semester.
     *
     * @param description The description of the new semester.
     * @param name        The name of the new semester.
     * @return The newly created semester.
     */
    Semester create(String description, String name);
}
