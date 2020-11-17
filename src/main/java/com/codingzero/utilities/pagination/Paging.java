package com.codingzero.utilities.pagination;

import java.util.Objects;

/**
 * This abstract class encapsulates paging related parameters.
 *
 * @param <S> the start position
 */
public abstract class Paging<S> {

    private S start;
    private int size;

    /**
     * Sub classes need to overwrite
     *
     * @param start based on subclass's validation logic
     * @param size cannot be negative number.
     */
    protected Paging(S start, int size) {
        this.start = start;
        this.size = size;
        checkForIllegalSize();
    }

    private void checkForIllegalSize() {
        if (this.getSize() < 0) {
            throw new IllegalArgumentException("Page size need to be larger than or equal to 0.");
        }
    }

    public S getStart() {
        return start;
    }
    
    public int getSize() {
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paging<?> that = (Paging<?>) o;
        return getSize() == that.getSize() &&
                Objects.equals(getStart(), that.getStart());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStart(), getSize());
    }

    @Override
    public String toString() {
        return "ResultPage{" +
                "start=" + start +
                ", size=" + size +
                '}';
    }
}
