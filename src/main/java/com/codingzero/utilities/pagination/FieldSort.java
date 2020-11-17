package com.codingzero.utilities.pagination;

/**
 * This class represents the sorting parameter related to one field.
 */
public class FieldSort {

    private String fieldName;
    private Order order;

    public FieldSort(String fieldName, Order order) {
        this.fieldName = fieldName;
        this.order = order;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Order getOrder() {
        return order;
    }

    public enum Order {
        ASC,
        DESC
    }

}
