package com.codingzero.utilities.pagination;

/**
 * This interface define the protocol of how to fetch data.
 *
 * @param <T> type of data
 * @param <P> type of paging
 */
public interface PaginatedResultDelegate<T, P extends Paging> {

    /**
     * Return data based on the given fetching request
     *
     * @param request ResultFetchRequest%3C? extends P%3E
     * @return T
     */
    T fetchResult(ResultFetchRequest<? extends P> request);

}
