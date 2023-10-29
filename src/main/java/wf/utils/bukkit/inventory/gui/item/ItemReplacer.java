package wf.utils.bukkit.inventory.gui.item;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class ItemReplacer {

    private String from;
    private BiFunction<Player, Map<String, String>, String> to;

    public ItemReplacer() {

    }

    public ItemReplacer(String from, BiFunction<Player, Map<String, String>, String> to) {
        this.from = from;
        this.to = to;
    }

    public String get(Player player, Map<String, String> arguments){
        return to.apply(player, arguments);
    }

    public String replace(String s, Player player, Map<String, String> arguments){
        if(!s.contains(from)) return s;
        return s.replace(from, get(player, arguments));
    }

    public List<String> replace(List<String> list, Player player, Map<String, String> arguments){
        list.replaceAll(s -> replace(s, player, arguments));
        return list;
    }

    public <T> Map<T, String> replace(Map<T, String> map, Player player, Map<String, String> arguments){
        map.replaceAll((k, v) -> replace(v, player, arguments));
        return map;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public BiFunction<Player, Map<String, String>, String> getTo() {
        return to;
    }

    public void setTo(BiFunction<Player, Map<String, String>, String> to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "ItemReplacer{" +
                "from='" + from + '\'' +
                ", to=" + to +
                '}';
    }
}





