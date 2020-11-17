package com.codingzero.utilities.pagination;

/**
 * This class represents offset (index) based paging result.
 * 
 * Also provide a helper PagingDelegate for supporting next page logic #{@link PagingDelegate#nextPage(Paging)}
 * 
 * @param <T> type of accessing data
 */
public final class OffsetPaginatedResult<T> extends PaginatedResult<T, OffsetPaging> {

    public OffsetPaginatedResult(PaginatedResultDelegate<T, OffsetPaging> delegate, Object... arguments) {
        this(delegate, null, arguments);
    }

    public OffsetPaginatedResult(PaginatedResultDelegate<T, OffsetPaging> delegate,
                                 ResultCountDelegate resultCountDelegate,
                                 Object... arguments) {
        super(delegate, resultCountDelegate, new OffsetPagingDelegate(), arguments);
    }

    private static class OffsetPagingDelegate implements PagingDelegate<OffsetPaging> {

        @Override
        public OffsetPaging nextPage(OffsetPaging current) {
            return current.next();

        }
    }

}
