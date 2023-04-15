package wf.utils.bukkit.command.handler.subcommand.executor;


import org.bukkit.command.CommandSender;
import wf.utils.bukkit.command.handler.subcommand.executor.types.ArgumentType;

public class Argument {

    private String name = null;
    private ArgumentType type = ArgumentType.STRING;
    private Object def;
    private boolean obligatorily = true;


    public Argument(String name) {
        this.name = name;
    }

    public Argument(String name, ArgumentType type) {
        this.name = name;
        this.type = type;
    }

    public Argument(String name, ArgumentType type, boolean obl) {
        this.name = name;
        this.type = type;
        this.obligatorily = obl;
    }


    public Argument(String name, ArgumentType type, Object def) {
        this.name = name;
        this.type = type;
        this.def = def;
    }

    public Argument(String name, ArgumentType type, boolean obl, Object def) {
        this.name = name;
        this.type = type;
        this.def = def;
        this.obligatorily = obl;
    }

    public Argument(ArgumentType type, boolean obl, Object def) {
        this.type = type;
        this.def = def;
        this.obligatorily = obl;
    }


    public Argument(ArgumentType type, Object def) {
        this.type = type;
        this.def = def;
    }

    public Argument(ArgumentType type) {
        this.type = type;
    }




    public boolean typeIsRight(CommandSender sender, String argument){
        return type.isIt(sender, argument);
    }

    public Object get(CommandSender sender, String argument){
        return type.get(sender, argument);
    }

    public String getName() {
        if(name == null){
            if(type.getName() == null) return "arg";
            else return type.getName();
        }

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArgumentType getType() {
        return type;
    }

    public void setType(ArgumentType type) {
        this.type = type;
    }

    public boolean isObligatorily() {
        return obligatorily;
    }

    public Object getDefault() {
        return def;
    }

    public void setDefault(Object def) {
        this.def = def;
    }


    @Override
    public String toString() {
        return "Argument{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", def=" + def +
                ", obligatorily=" + obligatorily +
                '}';
    }

}
