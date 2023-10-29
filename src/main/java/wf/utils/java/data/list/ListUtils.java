package wf.utils.java.data.list;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {

    public static <T> List<T> copyOfRange(List<T> list, int from, int to) {
        if (from < 0 || from >= list.size() || to < 0 || to >= list.size() || from > to)
            throw new IllegalArgumentException("Illegal extraction bounds");

        List<T> result = new ArrayList<>(to - from + 1);

        for (int i = from; i <= to; i++)
            result.add(list.get(i));

        return result;
    }

}
