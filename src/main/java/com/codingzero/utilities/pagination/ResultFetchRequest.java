package com.codingzero.utilities.pagination;

import java.util.Collections;
import java.util.List;

/**
 * This class encapsulate all parameters which are required to fetch data
 * from persistence systems, like database, file system etc.
 *
 * @param <P> type of paging
 */
public class ResultFetchRequest<P extends Paging> {

    private List<Object> arguments;
    private P page;
    private List<FieldSort> fieldSorts;

    public ResultFetchRequest(List<Object> arguments, P page, List<FieldSort> fieldSorts) {
        this.arguments = Collections.unmodifiableList(arguments);
        this.page = page;
        this.fieldSorts = Collections.unmodifiableList(fieldSorts);
    }

    public Object[] getArguments() {
        return arguments.toArray(new Object[arguments.size()]);
    }

    public <T> T getArgument(int index) {
        return (T) arguments.get(index);
    }

    public P getPage() {
        return page;
    }

    public List<FieldSort> getFieldSorts() {
        return fieldSorts;
    }
}
