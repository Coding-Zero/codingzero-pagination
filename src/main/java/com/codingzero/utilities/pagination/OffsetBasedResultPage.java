package com.codingzero.utilities.pagination;

public class OffsetBasedResultPage extends ResultPage<Integer> {

    private static final int MINIMAL_START = 1;

    public OffsetBasedResultPage(Integer start, int size) {
        super(start, size);
        checkForInvalidStart();
    }

    private void checkForInvalidStart() {
        if (this.getStart() < MINIMAL_START) {
            throw new IllegalArgumentException("Page start need to be larger than " + MINIMAL_START);
        }
    }

    public OffsetBasedResultPage next() {
        return new OffsetBasedResultPage(getStart() + getSize(), getSize());
    }

}
