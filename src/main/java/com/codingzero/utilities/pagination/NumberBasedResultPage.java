package com.codingzero.utilities.pagination;

public class NumberBasedResultPage extends ResultPage<Integer> {

    private static final int MINIMAL_START = 1;

    public NumberBasedResultPage(Integer start, int size) {
        super(start, size);
        checkForInvalidStart();
    }

    private void checkForInvalidStart() {
        if (this.getStart() < MINIMAL_START) {
            throw new IllegalArgumentException("Page start need to be larger than " + MINIMAL_START);
        }
    }

}
