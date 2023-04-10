package wf.utils.bukkit.inventory.gui.item;

import org.bukkit.entity.Player;

import java.util.Map;
import java.util.function.BiConsumer;


public class Function {

    private BiConsumer<Player, Map<String, String>> runnable;

    public Function(BiConsumer<Player, Map<String, String>> runnable) {
        this.runnable = runnable;
    }

    public void apply(Player player, Map<String, String> map){
        runnable.accept(player, map);
    }

    public BiConsumer<Player, Map<String, String>> getRunnable() {
        return runnable;
    }

    public void setRunnable(BiConsumer<Player, Map<String, String>> runnable) {
        this.runnable = runnable;
    }
}
