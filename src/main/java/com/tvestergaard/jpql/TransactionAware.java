package com.tvestergaard.jpql;

import javax.persistence.EntityTransaction;

public interface TransactionAware extends AutoCloseable
{

    /**
     * Checks if the transaction used in this object is currently active.
     *
     * @return {@code true} if the transaction used in this object is currently active.
     * @see EntityTransaction#isActive()
     */
    boolean isActive();

    /**
     * Begins the currently active transaction.
     *
     * @return {@code this}
     */
    TransactionAware begin();

    /**
     * Commits the currently active transaction.
     *
     * @return {@code this}
     */
    TransactionAware commit();

    /**
     * Rolls back changes made to the currently active transaction.
     *
     * @return {@code this}
     */
    TransactionAware rollback();

    /**
     * Closes the transaction.
     *
     * @throws Exception IOException
     */
    void close() throws Exception;
}
