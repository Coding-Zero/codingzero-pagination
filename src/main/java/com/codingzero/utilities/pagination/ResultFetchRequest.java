package com.codingzero.utilities.pagination;

public class ResultFetchRequest {

    private Object[] arguments;
    private ResultPage page;
    private ResultSorting sorting;

    public ResultFetchRequest(Object[] arguments, ResultPage page, ResultSorting sorting) {
        this.arguments = arguments;
        this.page = page;
        this.sorting = sorting;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public <T extends ResultPage> T getPage() {
        return (T) page;
    }

    public ResultSorting getSorting() {
        return sorting;
    }
}
