package com.tvestergaard.jpql;

import com.tvestergaard.jpql.entities.Semester;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public class TransactionalSemesterRepository extends AbstractTransactionalRepository implements SemesterRepository
{

    /**
     * The {@code EntityManager} that operations are performed upon.
     */
    private EntityManager entityManager;

    /**
     * Creates a new {@link TransactionalSemesterRepository}.
     *
     * @param manager The {@code EntityManager} that operations are to be performed upon.
     * @param onClose The transactional operation taken when this object closes.
     */
    public TransactionalSemesterRepository(EntityManager manager, TransactionStrategy onClose)
    {
        super(null, onClose);
        this.entityManager = manager;
        this.entityTransaction = this.entityManager.getTransaction();
        begin();
    }

    /**
     * Finds the semester with the provided id.
     *
     * @param id The id of the semester to find.
     * @return The semester with the provided id, {@code null} when no such entity exists.
     */
    @Override public Semester find(long id)
    {
        try {
            return entityManager.createNamedQuery("Semester.findById", Semester.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Returns a complete list of the semesters in the system.
     *
     * @return The complete list of the semesters in the system.
     */
    @Override public List<Semester> all()
    {
        return entityManager.createNamedQuery("Semester.findAll", Semester.class)
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
