package com.codingzero.utilities.pagination;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * This class define a protocol for you to access the data page by page.
 *
 * Remember that you don't actually access data until you invoke #{@link #getData()} method.
 *
 * NOTE:
 * 1) this is stateful object
 * 2) Only serialize the result data returned from #{@link #getData()} method, but this one.
 *
 * @param <T> type of data you're accessing
 * @param <P> type of paging, like #{@link OffsetPaging} or #{@link CursorPaging}
 */
public class PaginatedResult<T, P extends Paging> {

    private PaginatedResultDelegate<T, P> delegate;
    private ResultCountDelegate resultCountDelegate;
    private PagingDelegate<P> pagingDelegate;
    private List<Object> arguments;
    private P currentPage;
    private List<FieldSort> fieldSorts;

    public PaginatedResult(PaginatedResultDelegate<T, P> delegate,
                           ResultCountDelegate resultCountDelegate,
                           PagingDelegate<P> pagingDelegate,
                           Object... arguments) {
        this.resultCountDelegate = resultCountDelegate;
        this.pagingDelegate = pagingDelegate;
        this.arguments = Collections.unmodifiableList(Arrays.asList(arguments));
        this.delegate = delegate;
        this.currentPage = null;
        this.fieldSorts = null;
        checkForNullDelegate();
        checkForNullPagingDelegate();
    }

    /**
     * Initial the current page.
     *
     * @param paging P
     * @param <R> R extends PaginatedResult%3CT, P%3E
     * @return R extends PaginatedResult%3CT, P%3E
     */
    public <R extends PaginatedResult<T, P>> R start(P paging) {
        return start(paging, new FieldSort[0]);
    }

    /**
     * Initial the current page and sorting conditions.
     *
     * @param paging P
     * @param fieldSort array of FieldSort
     * @param <R> R extends PaginatedResult%3CT, P%3E
     * @return R extends PaginatedResult%3CT, P%3E
     */
    public <R extends PaginatedResult<T, P>> R start(P paging, FieldSort... fieldSort) {
        setCurrentPage(paging);
        this.fieldSorts = Collections.unmodifiableList(Arrays.asList(fieldSort));
        return (R) this;
    }

    /**
     * Move to next page based on the current page
     *
     * @param <R> PaginatedResult
     * @return R extends PaginatedResult%3CT, P%3E
     */
    public <R extends PaginatedResult<T, P>> R next() {
        checkForNoPage();
        checkForNullDelegate();
        P nextPage = pagingDelegate.nextPage(currentPage);
        setCurrentPage(nextPage);
        return (R) this;
    }

    /**
     * Returns the data with the current page.
     *
     * @return T type of accessing data
     */
    public T getData() {
        checkForNoPage();
        checkForNullDelegate();
        return (T) delegate.fetchResult(new ResultFetchRequest(arguments, currentPage, fieldSorts));
    }

    /**
     * Returns the total numbers of data is going to return.
     *
     * @return long
     */
    public int getTotalCount() {
        if (!isTotalCountAvailable()) {
            throw new UnsupportedOperationException("ResultCountDelegate is not assigned, try to implement one");
        }
        return resultCountDelegate.countTotal(new ResultCountTotalRequest(arguments));
    }

    /**
     * Can tell the total numbers of data.
     *
     * If you need this function, try to implement #{@link ResultCountDelegate} interface.
     *
     * @return boolean
     */
    public boolean isTotalCountAvailable() {
        return !Objects.isNull(resultCountDelegate);
    }

    /**
     * Returns the current page.
     *
     * @return P Paging
     */
    public P getCurrentPage() {
        return currentPage;
    }

    /**
     * Returns the current sorting conditions.
     *
     * @return List%3CFieldSort%3E
     */
    public List<FieldSort> getFieldSorts() {
        return fieldSorts;
    }

    private void setCurrentPage(P pageCursor) {
        if (null == pageCursor) {
            throw new IllegalArgumentException("ResultPage cannot be null value");
        }
        currentPage = pageCursor;
    }

    private void checkForNullDelegate() {
        if (Objects.isNull(delegate)) {
            throw new NullPointerException("Need assign a delegate, "
                    + "" + PaginatedResultDelegate.class.getName() + " to this result first.");
        }
    }

    private void checkForNullPagingDelegate() {
        if (Objects.isNull(pagingDelegate)) {
            throw new NullPointerException("Need assign a paging delegate, "
                    + "" + PaginatedResultDelegate.class.getName() + " to this result first.");
        }
    }
    
    private void checkForNoPage() {
        if (null == getCurrentPage()) {
            throw new IllegalArgumentException("Result page is required!");
        }
    }

}