package com.tvestergaard.jpql;

import com.tvestergaard.jpql.entities.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public class TransactionalTeacherRepository extends AbstractTransactionalRepository implements TeacherRepository
{

    /**
     * The {@code EntityManager} that operations are performed upon.
     */
    private EntityManager entityManager;

    /**
     * Creates a new {@link TransactionalTeacherRepository}.
     *
     * @param manager The {@code EntityManager} that operations are to be performed upon.
     * @param onClose The transactional operation taken when this object closes.
     */
    public TransactionalTeacherRepository(EntityManager manager, TransactionStrategy onClose)
    {
        super(null, onClose);
        this.entityManager = manager;
        this.entityTransaction = this.entityManager.getTransaction();
        begin();
    }

    /**
     * Finds the teacher with the provided id.
     *
     * @param id The id of the teacher to find.
     * @return The thecher with the provided id, {@code null} when no such entity exists.
     */
    @Override public Teacher find(long id)
    {
        try {
            return entityManager.createNamedQuery("Teacher.findById", Teacher.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Finds all the teachers in the system.
     *
     * @return A list of all the teachers in the system.
     */
    @Override public List<Teacher> all()
    {
        return entityManager.createNamedQuery("Teacher.findAll", Teacher.class).getResultList();
    }

    /**
     * Returns the teacher who teaches the most semesters.
     *
     * @return The teacher who teaches the most semesters.
     */
    @Override public Teacher findWithMostSemesters()
    {
        return entityManager.createQuery(
                "SELECT t " +
                "FROM Teacher t " +
                "ORDER BY t.semesters.size DESC", Teacher.class)
                .setMaxResults(1)
                .getSingleResult();
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
