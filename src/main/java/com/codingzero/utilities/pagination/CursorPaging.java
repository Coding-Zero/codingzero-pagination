package com.codingzero.utilities.pagination;

/**
 * This class represents cursor based paging parameter.
 *
 * Consider to use when you need to use a key to be the start point for next page, like for MongoDB or Cassandra.
 *
 */
public final class CursorPaging extends Paging<String> {

    public static final String START_CURSOR = "__START_CURSOR__";
    public static final String END_CURSOR = "__END_CURSOR__";

    public CursorPaging(String start, int size) {
        super(start, size);
        checkForInvalidStart();
    }

    private void checkForInvalidStart() {
        if (this.getStart() == null) {
            throw new IllegalArgumentException("Page start cursor cannot be null value");
        }
    }

    public boolean isFirstPage() {
        return START_CURSOR.equalsIgnoreCase(getStart());
    }

    public boolean isLastPage() {
        return END_CURSOR.equalsIgnoreCase(getStart());
    }

    public static CursorPaging firstPage(int size) {
        return new CursorPaging(START_CURSOR, size);
    }

    public static CursorPaging lastPage(int size) {
        return new CursorPaging(END_CURSOR, size);
    }
}
