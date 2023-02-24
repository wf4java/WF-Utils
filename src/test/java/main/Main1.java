package main;

import java.util.Comparator;
import java.util.TreeMap;

public class Main1 {


    public static void main(String[] args) {


        TreeMap<String, String> map = new TreeMap<String, String>(new MapKeyComparator());

        map.put("aa", "1");
        map.put("bba", "4");
        map.put("baa", "2");
        map.put("aaasdasdasdasd2384823b", "3");


        map.forEach((k, v) -> {
            System.out.println(k + " | " + v);
        });


    }



    public static class MapKeyComparator implements Comparator<String> {
        @Override
        public int compare(String str1, String str2) {
            return str1.compareTo(str2) * -1;
        }
    }


}
