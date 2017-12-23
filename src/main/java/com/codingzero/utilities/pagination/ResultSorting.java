package com.codingzero.utilities.pagination;

public class ResultSorting {

    private String field;
    private Order order;

    public ResultSorting(String field, Order order) {
        this.field = field;
        this.order = order;
    }

    public String getField() {
        return field;
    }

    public Order getOrder() {
        return order;
    }

    public enum Order {
        ASC,
        DESC
    }

}
