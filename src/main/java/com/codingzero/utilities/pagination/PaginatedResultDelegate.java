package com.codingzero.utilities.pagination;

public interface PaginatedResultDelegate<T> {

    /**
     * Returns a T type of result by specified tag and the giving arguments.
     *
     * The returned result is located by the giving page cursor.
     *
     * @param request ResultFetchRequest
     * @return T
     */
    T fetchResult(ResultFetchRequest request);

    default ResultPage nextPage(ResultFetchRequest request, T currentResult){
        if (NumberBasedResultPage.class.isInstance(request.getPage())) {
            NumberBasedResultPage page = (NumberBasedResultPage) request.getPage();
            return page.next();
        }
        throw new UnsupportedOperationException("nextPage() need to be implemented first!");
    }
    
}
