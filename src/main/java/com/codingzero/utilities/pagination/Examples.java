package com.codingzero.utilities.pagination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Some examples you can check out.
 */
public class Examples {

    private static final List<String> LINES = new ArrayList<>(100);
    static {
        for (int i = 0; i < 100; i ++) {
            LINES.add("Line #" + i);
        }
    }

    public static void main(String[] args) {

        OffsetPaginatedResult<List<String>> result = new OffsetPaginatedResult<>(
                request -> {
                    OffsetPaging paging = request.getPage();
                    int fromIndex = paging.getStart() - 1;
                    int toIndex = paging.getStart() + paging.getSize() - 1;
                    if (toIndex > LINES.size()) {
                        toIndex = LINES.size();
                    }
                    return LINES.subList(fromIndex, toIndex);
                },
                request -> LINES.size()
        );

        //Accessing data page by page
        List<String> lines;
        result = result.start(new OffsetPaging(1, 25));
        do {
            System.out.println(result.getCurrentPage());
            lines = result.getData();
            System.out.println(lines);
            result = result.next();
        } while (lines.size() > 0);


        //Accessing data page by page
        int total = result.getTotalCount();
        result = result.start(new OffsetPaging(1, 31));
        while (result.getCurrentPage().getStart() <= total) {
            System.out.println(result.getCurrentPage() + " <= " + total);
            lines = result.getData();
            System.out.println(lines);
            result = result.next();
        }
    }
}
