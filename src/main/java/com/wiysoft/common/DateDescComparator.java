package com.wiysoft.common;

import java.util.*;

/**
 * Created by weiliyang on 1/13/16.
 */
public class DateDescComparator implements Comparator<Date> {

    @Override
    public int compare(final Date left, final Date right) {
        if (left == null) {
            return 1;
        } else if (right == null) {
            return -1;
        } else {
            if (right.after(left)) {
                return 1;
            } else if (right.before(left)) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
