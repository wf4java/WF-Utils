package main.main50;

import org.bukkit.Location;
import wf.utils.java.file.yamlconfiguration.configuration.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Main28 {


    public static void main(String[] args) {

        HashMap<String, Word> map = new HashMap<>();

        Word word = new Word("yes");

        map.put("test", word);

        HashMap<String, Word> copy = deepClone(map);

        word.name = "no";

        copy.get("test").print();
        map.get("test").print();
        word.print();

    }
    public static <K, V> HashMap<K, V> deepClone(HashMap<K, V> original) {
        HashMap<K, V> copy = new HashMap<>();
        for (Map.Entry<K, V> entry : original.entrySet()) {
            copy.put(entry.getKey(), entry.getValue());
        }
        return copy;
    }

}
