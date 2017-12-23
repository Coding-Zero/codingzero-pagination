package com.codingzero.utilities.pagination;

public abstract class PaginatedResultMapper<T, S> implements PaginatedResultDelegate<T> {

    public final static int RESULT_SOURCE_INDEX = 0;

    @Override
    public T fetchResult(ResultFetchRequest request) {
        @SuppressWarnings("unchecked")
        PaginatedResult<S> result = (PaginatedResult<S>) request.getArguments()[RESULT_SOURCE_INDEX];
        result = result.start(request.getPage(), request.getSorting());
        return toResult(result.getResult(), request.getArguments());
    }

    @Override
    public ResultPage nextPage(ResultFetchRequest request, T currentResult) {
        @SuppressWarnings("unchecked")
        PaginatedResult<S> result = (PaginatedResult<S>) request.getArguments()[RESULT_SOURCE_INDEX];
        return result.next().getPage();
    }

    /**
     *
     * @param source S
     * @param arguments Object[]
     * @return
     */
    abstract protected T toResult(S source, Object[] arguments);

}
