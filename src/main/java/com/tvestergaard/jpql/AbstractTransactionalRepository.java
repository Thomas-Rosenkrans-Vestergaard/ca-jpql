package com.tvestergaard.jpql;

import javax.persistence.EntityTransaction;

public class AbstractTransactionalRepository implements TransactionalRepository
{

    /**
     * The operation to perform when the {@link TransactionalRepository} is closed. If the {@link TransactionalRepository}
     * has been committed or rolled back manually, no action is taken.
     *
     * @see TransactionalRepository#onClose(TransactionStrategy)
     * @see TransactionalRepository#close()
     */
    protected TransactionStrategy onClose;

    /**
     * The transaction to perform actions upon.
     */
    protected EntityTransaction entityTransaction;

    /**
     * Sets the {@code onClose} {@code TransactionStrategy}.
     *
     * @param transaction The transaction to perform actions upon.
     * @param onClose     The operation to perform when the {@link TransactionalRepository} closes.
     *                    If the {@link TransactionalRepository} has been committed or rolled back manually, no action is
     *                    taken.
     */
    public AbstractTransactionalRepository(EntityTransaction transaction, TransactionStrategy onClose)
    {
        this.entityTransaction = transaction;
        this.onClose = onClose;
    }

    /**
     * Returns the transaction that operations are performed upon.
     *
     * @return The transaction that operations are performed upon.
     */
    protected EntityTransaction getTransaction()
    {
        return entityTransaction;
    }

    /**
     * Sets the operation to perform when the {@link TransactionalRepository} is closed. If the
     * {@link TransactionalRepository} has been committed or rolled back manually, no action is taken.
     *
     * @param strategy The action to perform when this object is closed, and the transaction is still active.
     * @return this
     * @see TransactionalRepository#onClose(TransactionStrategy)
     * @see TransactionalRepository#close()
     */
    @Override public TransactionalRepository onClose(TransactionStrategy strategy)
    {
        this.onClose = onClose;

        return this;
    }

    /**
     * Checks if the transaction used in this object is currently active.
     *
     * @return {@code true} if the transaction used in this object is currently active.
     * @see EntityTransaction#isActive()
     */
    @Override public boolean isActive()
    {
        return entityTransaction != null && entityTransaction.isActive();
    }

    /**
     * Begins the currently active transaction.
     *
     * @return {@code this}
     */
    @Override public TransactionalRepository begin()
    {
        this.entityTransaction.begin();

        return this;
    }

    /**
     * Commits the currently active transaction.
     *
     * @return {@code this}
     */
    @Override public TransactionalRepository commit()
    {
        this.entityTransaction.commit();

        return this;
    }

    /**
     * Rolls back changes made to the currently active transaction.
     *
     * @return {@code this}
     */
    @Override public TransactionalRepository rollback()
    {
        this.entityTransaction.rollback();

        return this;
    }

    /**
     * Closes the transaction.
     *
     * @throws Exception IOException
     */
    @Override public void close() throws Exception
    {
        if (entityTransaction.isActive())
            if (onClose == TransactionStrategy.COMMIT)
                entityTransaction.commit();
            else if (onClose == TransactionStrategy.ROLLBACK)
                entityTransaction.rollback();
            else
                throw new UnsupportedOperationException("Unsupported TransactionStrategy " + onClose.name());
    }
}
