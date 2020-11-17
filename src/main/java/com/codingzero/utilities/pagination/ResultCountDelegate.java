package com.codingzero.utilities.pagination;

/**
 * This interface define the protocol of counting of the total number of result.
 *
 */
public interface ResultCountDelegate {

    int countTotal(ResultCountTotalRequest request);

}
