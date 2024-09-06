package com.example.bookshop.utils;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class SortUtils {
    public static Sort parseSortParams(List<String> sortParams) {
        if (sortParams == null || sortParams.isEmpty()) {
            return Sort.by(Sort.Direction.DESC, "createDate");
        }
        List<Sort.Order> orders = new ArrayList<>();
        for (String param : sortParams) {
            String[] sortPair = param.split(",");
            if (sortPair.length == 2) {
                Sort.Direction direction = Sort.Direction.fromString(sortPair[1]);
                orders.add(new Sort.Order(direction, sortPair[0]));
            }
        }

        return Sort.by(orders);
    }
}
