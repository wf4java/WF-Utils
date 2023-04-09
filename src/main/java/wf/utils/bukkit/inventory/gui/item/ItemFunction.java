package wf.utils.bukkit.inventory.gui.item;

import java.util.HashMap;

public class ItemFunction {

    private String name;
    private Function function;
    private HashMap<String, String> arguments;

    public ItemFunction() {
    }

    public ItemFunction(String name, Function function, HashMap<String, String> arguments) {
        this.name = name;
        this.function = function;
        this.arguments = arguments;
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

    public HashMap<String, String> getArguments() {
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
