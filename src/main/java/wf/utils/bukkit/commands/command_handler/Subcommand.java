package wf.utils.bukkit.commands.command_handler;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import wf.utils.bukkit.commands.command_builder.SubCommandExecutor;
import wf.utils.bukkit.config.language.MessageReceiver;
import wf.utils.java.functions.TriConsumer;



public class Subcommand {

    private String command;
    private String permission;
    private SubCommandExecutor commandBuilder;
    private TriConsumer<CommandSender, Command, Object[]> runnable;
    private boolean onlyPlayer = false;


    public Subcommand(){

    }

    public Subcommand(String command, String permission, SubCommandExecutor commandBuilder, TriConsumer<CommandSender, Command, Object[]> runnable, boolean onlyPlayer) {
        this.command = command;
        this.permission = permission;
        this.commandBuilder = commandBuilder;
        this.runnable = runnable;
        this.onlyPlayer = onlyPlayer;
    }

    public Subcommand(String permission, SubCommandExecutor commandBuilder, TriConsumer<CommandSender, Command, Object[]> runnable, boolean onlyPlayer) {
        this.permission = permission;
        this.commandBuilder = commandBuilder;
        this.runnable = runnable;
        this.onlyPlayer = onlyPlayer;
    }

    public Subcommand(SubCommandExecutor commandBuilder, TriConsumer<CommandSender, Command, Object[]> runnable) {
        this.commandBuilder = commandBuilder;
        this.runnable = runnable;
    }

    public Subcommand(String permission, SubCommandExecutor commandBuilder, TriConsumer<CommandSender, Command, Object[]> runnable) {
        this.permission = permission;
        this.commandBuilder = commandBuilder;
        this.runnable = runnable;
    }

    public Subcommand(SubCommandExecutor commandBuilder, TriConsumer<CommandSender, Command, Object[]> runnable, boolean onlyPlayer) {
        this.commandBuilder = commandBuilder;
        this.runnable = runnable;
        this.onlyPlayer = onlyPlayer;
    }


    public void onCommand(CommandSender sender, Command command, String[] args, int argsPosition, MessageReceiver msg){

        if(!checkPermission(sender)){
            sender.sendMessage(ChatColor.RED + ("\n" + (msg == null ? "You not have permission!" : msg.get("YOU_NOT_HAVE_PERMISSION"))));
            return;
        }

        Object[] output = commandBuilder.calculate(sender, msg, args, argsPosition);
        if(output == null) return;
        runnable.accept(sender, command, output);
    }


    public boolean checkPermission(CommandSender sender){
        if(sender.isOp() || sender.hasPermission("*")) return true;
        return sender.hasPermission(permission);
    }


    public SubCommandBuilder builder(){
        return new SubCommandBuilder();
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public SubCommandExecutor getCommandBuilder() {
        return commandBuilder;
    }

    public void setCommandBuilder(SubCommandExecutor commandBuilder) {
        this.commandBuilder = commandBuilder;
    }

    public TriConsumer<CommandSender, Command, Object[]> getRunnable() {
        return runnable;
    }

    public void setRunnable(TriConsumer<CommandSender, Command, Object[]> runnable) {
        this.runnable = runnable;
    }

    public boolean isOnlyPlayer() {
        return onlyPlayer;
    }

    public void setOnlyPlayer(boolean onlyPlayer) {
        this.onlyPlayer = onlyPlayer;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "Subcommand{" +
                "permission=" + permission +
                ", commandBuilder=" + commandBuilder +
                ", runnable=" + runnable +
                ", onlyPlayer=" + onlyPlayer +
                '}';
    }
}
