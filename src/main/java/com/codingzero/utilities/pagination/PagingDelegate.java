package com.codingzero.utilities.pagination;

/**
 * This interface defines the protocol related paging logics.
 *
 * @param <P> type of paging
 */
public interface PagingDelegate<P extends Paging> {

    /**
     * calculate the next page based on the passed in page.
     *
     * @param paging P
     * @return P
     */
    P nextPage(P paging);
}
