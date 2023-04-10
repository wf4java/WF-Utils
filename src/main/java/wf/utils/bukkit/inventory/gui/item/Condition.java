package wf.utils.bukkit.inventory.gui.item;

import org.bukkit.entity.Player;

import java.util.Map;
import java.util.function.BiFunction;

public class Condition {

    private BiFunction<Player, Map<String, String>, Boolean> condition;

    public Condition() {
    }

    public Condition(BiFunction<Player, Map<String, String>, Boolean> condition) {
        this.condition = condition;
    }

    public boolean isCondited(Player player, Map<String, String> arguments){
        return condition.apply(player, arguments);
    }

    public BiFunction<Player, Map<String, String>, Boolean> getCondition() {
        return condition;
    }

    public void setCondition(BiFunction<Player, Map<String, String>, Boolean> condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "ItemCondition{" +
                "condition=" + condition +
                '}';
    }

}
