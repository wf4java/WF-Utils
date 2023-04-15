package wf.utils.bukkit.command.handler.subcommand.executor;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import wf.utils.bukkit.config.language.models.MessageReceiver;

import java.util.Arrays;

public class SubCommandExecutor {

    private Argument[] arguments;
    private String command = "/command";


    public SubCommandExecutor(Argument... arguments) {
        this.arguments = arguments;
    }

    public SubCommandExecutor() {
        this.arguments = new Argument[0];
    }

    public SubCommandExecutor(String command, Argument... arguments) {
        this.command = command;
        this.arguments = arguments;
    }


    public Object[] calculate(CommandSender sender, MessageReceiver messages, String[] args, int argsOffSet){
        return calculate(sender, messages, argsOffSet == 0 ? args : Arrays.copyOfRange(args, argsOffSet, args.length));
    }


    public Object[] calculate(CommandSender sender, MessageReceiver messages, String[] args){

        if(getMinObligatorilyArgs() > args.length){
            sender.sendMessage("\n" + ChatColor.RED + (messages == null ? "Write all arguments!" :
                    messages.get("COMMAND.DEFAULT.WRITE_ALL_ARGUMENTS")));
            sender.sendMessage(getArgumentsText());
            return null;
        }

        Object[] objects = new Object[arguments.length];

        for(int i = 0; i < arguments.length; i++){
            Argument argument = arguments[i];
            if(args.length > i){
                if(!argument.typeIsRight(sender, args[i])){
                    sender.sendMessage(ChatColor.RED + "\n" + ((messages == null || argument.getType().getMessageCode() == null) ? argument.getType().getMessage() :
                            messages.get(argument.getType().getMessageCode())));
                    sender.sendMessage(getWrongArgumentText(i));
                    return null;
                }
                objects[i] = argument.get(sender, args[i]);
            }else {
                objects[i] = argument.getDefault();
            }
        }

        return objects;

    }


    public String getWrongArgumentText(int num){
        StringBuilder builder = new StringBuilder(ChatColor.GOLD + command);
        for(int i = 0; i < arguments.length; i++){
            builder.append(" " + ChatColor.AQUA + "{").append(i == num ? ChatColor.DARK_RED :
                    ( i > num ? ChatColor.YELLOW : ChatColor.GREEN )).append(!arguments[i].isObligatorily() ? "@" : "").
                    append(arguments[i].getName()).append(ChatColor.AQUA).append("}");
        }
        return builder.toString();
    }

    public String getArgumentsText(){
        StringBuilder text = new StringBuilder(ChatColor.GOLD + command);
        for(Argument argument : arguments) {
            text.append(ChatColor.AQUA + " {" + ChatColor.RED).append(!argument.isObligatorily() ? "@" : "").append(argument.getName()).append(ChatColor.AQUA).append("}");
        }
        return text.toString();
    }

    public int getMinObligatorilyArgs(){
        return getMinObligatorilyArgs(arguments);
    }

    public static int getMinObligatorilyArgs(Argument[] arguments){
        int min = 0;

        for(int i = 0; i < arguments.length; i++){
            if(!arguments[i].isObligatorily()) break;
            min = i + 1;
        }

        return min;
    }


    public Argument[] getArguments() {
        return arguments;
    }

    public void setArguments(Argument[] arguments) {
        this.arguments = arguments;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "CommandBuilder{" +
                "arguments=" + Arrays.toString(arguments) +
                ", command='" + command + '\'' +
                '}';
    }
}

