package com.reservif.repositories.utils;

public class PaginationUtils {

    public static int treatNullInteger(Integer possiblyNullInteger, int defaultValue) {
        return possiblyNullInteger == null ? defaultValue : possiblyNullInteger;
    }

}
