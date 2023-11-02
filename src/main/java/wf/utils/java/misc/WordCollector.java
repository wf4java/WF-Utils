package wf.utils.java.misc;


//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class WordCollector {

//    private static final String collectorUrl = "https://makeword.ru/combo/";
//
//
//    public static HashMap<Integer, List<String>> collectWordsMap(String letters){
//        try {
//            return toMap(Jsoup.connect(collectorUrl + letters).get()
//                    .select("body > div.content_wrapper > div.content_left > ul > li > a")
//                    .stream().map(Element::wholeText).collect(Collectors.toList()));
//        } catch (IOException e) {throw new RuntimeException(e);}
//    }
//
//    public static ArrayList<String> collectWords(String letters){
//        HashMap<Integer, List<String>> wordsMap = collectWordsMap(letters);
//        return (ArrayList<String>) wordsMap.get(letters.length());
//    }
//
//    public static String collectWord(String letters){
//        ArrayList<String> words = collectWords(letters);
//        if(words == null || words.size() == 0) return null;
//        return words.get(0);
//    }
//
//    private static HashMap<Integer, List<String>> toMap(Collection<String> words){
//        HashMap<Integer, List<String>> sorted = new HashMap<>();
//        words.forEach((w) ->{
//          if(sorted.containsKey(w.length())) { sorted.get(w.length()).add(w); }
//          else {
//              ArrayList<String> newList = new ArrayList<>();
//              newList.add(w);
//              sorted.put(w.length(), newList);
//          }
//        });
//        return sorted;
//    }



}
