package com.tvestergaard.jpql;

import javax.persistence.EntityManager;

public class RepositoryFacade extends AbstractTransactionalRepository
{

    private final EntityManager                   entityManager;
    private final TransactionalSemesterRepository semesterRepository;
    private final TransactionalStudentRepository  studentRepository;
    private final TransactionalTeacherRepository  teacherRepository;

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
        this.semesterRepository = new TransactionalSemesterRepository(this.entityManager, onClose);
        this.studentRepository = new TransactionalStudentRepository(this.entityManager, onClose);
        this.teacherRepository = new TransactionalTeacherRepository(this.entityManager, onClose);

        this.entityTransaction.begin();
    }

    /**
     * Performs the provided operations within a single transaction. When no exception occurs, the results are committed.
     * When an exception occurs, the results are rolled back.
     *
     * @param triConsumer The operations to perform.
     * @return {@code this}
     */
    public RepositoryFacade transaction(TriConsumer triConsumer) throws Exception
    {
        try {
            triConsumer.consume(this.semesterRepository, this.studentRepository, this.teacherRepository);
            this.entityTransaction.commit();
        } catch (Exception e) {
            this.entityTransaction.rollback();
            throw e;
        }

        return this;
    }
}
