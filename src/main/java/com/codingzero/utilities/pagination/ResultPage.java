package com.codingzero.utilities.pagination;

import java.util.Objects;

public abstract class ResultPage<S> {

    private S start;
    private int size;

    public ResultPage(S start, int size) {
        this.start = start;
        this.size = size;
        checkIfValidSize();
    }

    private void checkIfValidSize() {
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
        ResultPage<?> that = (ResultPage<?>) o;
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
