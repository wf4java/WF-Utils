package wf.utils.bukkit.inventory.gui.item;

import org.bukkit.entity.Player;

import java.util.Map;
import java.util.function.BiFunction;

public class ItemCondition {

    private String name;
    private Condition condition;


    public ItemCondition() {
    }

    public ItemCondition(String name, Condition condition) {
        this.name = name;
        this.condition = condition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "ItemCondition{" +
                "name='" + name + '\'' +
                ", condition=" + condition +
                '}';
    }
}
