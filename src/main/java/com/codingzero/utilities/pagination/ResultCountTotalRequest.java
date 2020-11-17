package com.codingzero.utilities.pagination;

import java.util.Collections;
import java.util.List;

/**
 * This class encapsulate all parameters which can be used to calculate total number of result
 * from persistence systems, like database, file system etc.
 */
public class ResultCountTotalRequest {

    private List<Object> arguments;

    public ResultCountTotalRequest(List<Object> arguments) {
        this.arguments = Collections.unmodifiableList(arguments);
    }

    public Object[] getArguments() {
        return arguments.toArray(new Object[arguments.size()]);
    }

    public <T> T getArgument(int index) {
        return (T) arguments.get(index);
    }

}
