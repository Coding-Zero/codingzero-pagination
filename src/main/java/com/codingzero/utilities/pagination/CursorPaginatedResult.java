package com.codingzero.utilities.pagination;

/**
 * This class represents cursor based paging result.
 *
 * @param <T> type of data you're accessing
 */
public final class CursorPaginatedResult<T> extends PaginatedResult<T, CursorPaging> {

    public CursorPaginatedResult(PaginatedResultDelegate<T, CursorPaging> delegate,
                                 PagingDelegate<CursorPaging> pagingDelegate,
                                 Object... arguments) {
        this(delegate, null,pagingDelegate, arguments);
    }

    public CursorPaginatedResult(PaginatedResultDelegate<T, CursorPaging> delegate,
                                 ResultCountDelegate resultCountDelegate,
                                 PagingDelegate<CursorPaging> pagingDelegate,
                                 Object... arguments) {
        super(delegate, resultCountDelegate, pagingDelegate, arguments);
    }
}
