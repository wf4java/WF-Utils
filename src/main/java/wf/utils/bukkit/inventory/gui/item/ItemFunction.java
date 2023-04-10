package wf.utils.bukkit.inventory.gui.item;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ItemFunction {

    private String name;
    private Function function;
    private Map<String, String> arguments;

    public ItemFunction() {
    }

    public ItemFunction(String name, Function function, HashMap<String, String> arguments) {
        this.name = name;
        this.function = function;
        this.arguments = arguments;
    }

    public void apply(Player player){
        function.apply(player, arguments);
    }

    public void apply(Player player, HashMap<String, String> arguments){
        arguments = ((HashMap<String, String>) arguments.clone());
        arguments.putAll(this.arguments);
        function.apply(player, arguments);
    }

    public void replaceAndApply(Player player, ItemReplacer[] replaces, HashMap<String, String> arguments){
        arguments = ((HashMap<String, String>) arguments.clone());
        arguments.putAll(this.arguments);
        function.apply(player, ItemReplacerUtils.replace(replaces, arguments, player, (HashMap<String, String>) arguments.clone()));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public Map<String, String> getArguments() {
        return arguments;
    }

    public void setArguments(HashMap<String, String> arguments) {
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return "ItemFunction{" +
                "name='" + name + '\'' +
                ", function=" + function +
                ", arguments=" + arguments +
                '}';
    }
}
