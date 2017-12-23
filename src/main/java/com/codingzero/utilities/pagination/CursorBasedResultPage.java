package com.codingzero.utilities.pagination;

public class CursorBasedResultPage extends ResultPage<String> {

    public CursorBasedResultPage(String start, int size) {
        super(start, size);
        checkForInvalidStart();
    }

    private void checkForInvalidStart() {
        if (this.getStart() == null) {
            throw new IllegalArgumentException("Page start cursor cannot be null value");
        }
    }

}
