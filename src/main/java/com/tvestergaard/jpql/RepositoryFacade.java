package com.tvestergaard.jpql;

import javax.persistence.EntityManager;

public class RepositoryFacade extends AbstractTransactionalRepository
{

    private final EntityManager      entityManager;
    public final  SemesterRepository semesters;
    public final  StudentRepository  students;
    public final  TeacherRepository  teachers;

    /**
     * Creates a new {@link RepositoryFacade}.
     *
     * @param manager The entity manager to perform operation upon.
     * @param onClose The action to take, when the {@link RepositoryFacade} closes.
     */
    public RepositoryFacade(EntityManager manager, TransactionStrategy onClose)
    {
        super(manager.getTransaction(), onClose);

        this.entityManager = manager;
        this.semesters = new TransactionalSemesterRepository(this.entityManager, onClose);
        this.students = new TransactionalStudentRepository(this.entityManager, onClose);
        this.teachers = new TransactionalTeacherRepository(this.entityManager, onClose);

        this.entityTransaction.begin();
    }

    /**
     * Performs the provided operations within a single transaction. When no exception occurs, the results are committed.
     * When an exception occurs, the results are rolled back. The transaction is begun again whether or not an
     * exception was thrown.
     *
     * @param triConsumer The operations to perform.
     * @return {@code this}
     */
    public RepositoryFacade transaction(TriConsumer triConsumer) throws Exception
    {
        try {
            triConsumer.consume(this.semesters, this.students, this.teachers);
            this.entityTransaction.commit();
        } catch (Exception e) {
            this.entityTransaction.rollback();
            throw e;
        } finally {
            if (!this.entityTransaction.isActive())
                this.entityTransaction.begin();
        }

        return this;
    }
}
