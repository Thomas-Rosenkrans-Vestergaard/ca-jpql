package com.tvestergaard.jpql;

import com.tvestergaard.jpql.entities.Teacher;

import javax.persistence.EntityManager;

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
