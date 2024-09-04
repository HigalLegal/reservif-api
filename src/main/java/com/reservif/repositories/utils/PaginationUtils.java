package com.reservif.repositories.utils;

public class PaginationUtils {

    public static int idealLimitReturn(int limit, int offset, int max) {
        return offset >= limit ? max : limit;
    }

}
