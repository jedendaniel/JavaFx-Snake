package dd.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CollectionsUtil<E> {

    @SafeVarargs
    public static <E> List<E> mergeLists(List<E>... lists) {
        List<E> resultList = new ArrayList<>();
        Arrays.stream(lists).forEach(resultList::addAll);
        return resultList;
    }
}
