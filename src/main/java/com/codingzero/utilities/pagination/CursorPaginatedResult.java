package com.codingzero.utilities.pagination;

import java.util.Collection;
import java.util.Objects;

/**
 * This class represents cursor based paging result.
 *
 * @param <T> type of data you're accessing
 */
public final class CursorPaginatedResult<T> extends PaginatedResult<T, CursorPaging> {

    public CursorPaginatedResult(PaginatedResultDelegate<T, CursorPaging> delegate,
                                 CursorPagingDelegate pagingDelegate,
                                 Object... arguments) {
        this(delegate, null, pagingDelegate, arguments);
    }

    public CursorPaginatedResult(PaginatedResultDelegate<T, CursorPaging> delegate,
                                 ResultCountDelegate resultCountDelegate,
                                 CursorPagingDelegate pagingDelegate,
                                 Object... arguments) {
        super(delegate, resultCountDelegate, pagingDelegate, arguments);
    }

    public CursorPaginatedResult(PaginatedResultDelegate<T, CursorPaging> delegate,
                                 PagingDelegate<CursorPaging> pagingDelegate,
                                 Object... arguments) {
        this(delegate, null, pagingDelegate, arguments);
    }

    public CursorPaginatedResult(PaginatedResultDelegate<T, CursorPaging> delegate,
                                 ResultCountDelegate resultCountDelegate,
                                 PagingDelegate<CursorPaging> pagingDelegate,
                                 Object... arguments) {
        super(delegate, resultCountDelegate, pagingDelegate, arguments);
    }

    public static abstract class CursorPagingDelegate implements PagingDelegate<CursorPaging> {

        @Override
        public CursorPaging nextPage(ResultFetchRequest<CursorPaging> request) {
            CursorPaging paging = request.getPage();
            String cursor = paging.getStart();
            int size = paging.getSize();
            int index = 0;
            boolean foundCursor = false;
            String nextCursor = null;
            for (String key: getResult()){
                if (paging.isFirstPage()
                        || compare(cursor, key)) {
                    foundCursor = true;
                }
                if (foundCursor) {
                    if ((index ++) == size) {
                        nextCursor = key;
                        break;
                    }
                }
            }
            if (Objects.isNull(nextCursor)) {
                return CursorPaging.lastPage(size);
            }
            return new CursorPaging(nextCursor, size);
        }

        protected abstract Collection<String> getResult();

        protected abstract boolean compare(String cursor, String key);

    }

}
