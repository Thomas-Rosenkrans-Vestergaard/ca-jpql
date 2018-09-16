package com.tvestergaard.jpql;

public interface TransactionalRepository extends TransactionAware, AutoCloseable
{

    /**
     * Sets the operation to perform when the {@link TransactionalRepository} is closed. If the
     * {@link TransactionalRepository} has been committed or rolled back manually, no action is taken.
     *
     * @param strategy The action to perform when this object is closed, and the transaction is still active.
     * @return this
     * @see TransactionalRepository#onClose(TransactionStrategy)
     * @see TransactionalRepository#close()
     */
    TransactionalRepository onClose(TransactionStrategy strategy);
}
