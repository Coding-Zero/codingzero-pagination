package com.codingzero.utilities.pagination;

/**
 * This class represents cursor based paging parameter.
 *
 * Consider to use when you need to use a key to be the start point for next page, like for MongoDB or Cassandra.
 *
 */
public final class CursorPaging extends Paging<String> {

    public CursorPaging(String start, int size) {
        super(start, size);
        checkForInvalidStart();
    }

    private void checkForInvalidStart() {
        if (this.getStart() == null) {
            throw new IllegalArgumentException("Page start cursor cannot be null value");
        }
    }



}
