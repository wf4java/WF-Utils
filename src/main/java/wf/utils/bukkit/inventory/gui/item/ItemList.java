package wf.utils.bukkit.inventory.gui.item;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public interface ItemList {

    public int getCount(Player player, Map<String, String> arguments);

    public String get(Player player, Map<String, String> arguments, int index);

    public List<String> get(Player player, Map<String, String> arguments, int start, int end);


}

