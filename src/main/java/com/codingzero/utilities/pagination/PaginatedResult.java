package com.codingzero.utilities.pagination;

public class PaginatedResult<T> {

    private PaginatedResultDelegate<T> delegate;
    private Object[] arguments;
    private ResultPage page; //current page.
    private ResultSorting sorting; //current sorting
    private T currentResult;

    public PaginatedResult(PaginatedResultDelegate<T> delegate, Object... arguments) {
        this.arguments = arguments;
        this.delegate = delegate;
        this.page = null;
        this.sorting = null;
        cleanCurrentResult();
    }

    private void cleanCurrentResult() {
        currentResult = null;
    }

    private T getCurrentResult() {
        if (null == currentResult) {
            currentResult = getResult();
        }
        return currentResult;
    }

    private PaginatedResultDelegate<T> getDelegate() {
        return delegate;
    }

    /**
     * Initialize the current page offset to the given one.
     * 
     * @param resultPage Object
     * @return PaginatedResult<T>
     */
    public PaginatedResult<T> start(ResultPage resultPage) {
        return start(resultPage, null);
    }

    public PaginatedResult<T> start(ResultPage resultPage, ResultSorting resultSorting) {
        setPage(resultPage);
        this.sorting = resultSorting;
        cleanCurrentResult();
        return this;
    }

    public PaginatedResult<T> next() {
        checkForNoPageOffset();
        checkForNullDelegate();
        ResultPage nextPage = getDelegate().nextPage(
                new ResultFetchRequest(arguments, page, sorting), getCurrentResult());
        setPage(nextPage);
        return this;
    }

    public T getResult() {
        checkForNoPageOffset();
        checkForNullDelegate();
        return delegate.fetchResult(new ResultFetchRequest(arguments, page, sorting));
    }

    private void setPage(ResultPage pageCursor) {
        if (null == pageCursor) {
            throw new IllegalArgumentException("ResultPage cannot be null value");
        }
        page = pageCursor;
    }
    
    public ResultPage getPage() {
        return page;
    }

    public ResultSorting getSorting() {
        return sorting;
    }

    private void checkForNullDelegate() {
        if (null == getDelegate()) {
            throw new NullPointerException("Need assign a delegate, "
                    + "" + PaginatedResultDelegate.class.getName() + " to this result first.");
        }
    }
    
    private void checkForNoPageOffset() {
        if (null == getPage()) {
            throw new IllegalArgumentException("Result page is required!");
        }
    }

}