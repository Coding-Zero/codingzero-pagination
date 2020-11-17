package com.codingzero.utilities.pagination;

import java.util.Objects;

/**
 * This class represents offset (index) based paging parameter.
 *
 * Consider to use when you need to use an index to be the start point for next page, like for MySQL.
 *
 */
public final class OffsetPaging extends Paging<Integer> {

    private static final int MINIMAL_START = 1;

    /**
     * Construct this class
     *
     * @param start must be start from 1, cannot be null.
     * @param size int
     */
    public OffsetPaging(Integer start, int size) {
        super(start, size);
        checkForIllegalStart();
    }

    private void checkForIllegalStart() {
        if (Objects.isNull(getStart())) {
            throw new IllegalArgumentException("Page start need to be not null");
        }
        if (getStart() < MINIMAL_START) {
            throw new IllegalArgumentException("Page start need to be larger than " + MINIMAL_START);
        }
    }

    /**
     * Calculate the next page start index with the existing page size.
     *
     * next page start index = current start index + page size
     *
     * eg:
     * 1 + 10 = 11, which is the next page start position.
     *
     * @return OffsetPaging
     */
    public OffsetPaging next() {
        return new OffsetPaging(getStart() + getSize(), getSize());
    }

}
