package wf.utils.bukkit.commands.command_handler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import wf.utils.bukkit.commands.command_builder.Argument;
import wf.utils.bukkit.commands.command_builder.SubCommandExecutor;
import wf.utils.java.functions.TriConsumer;

public class SubCommandBuilder {

    private String command;
    private String permission;
    private SubCommandExecutor commandBuilder = new SubCommandExecutor();
    private TriConsumer<CommandSender, Command, Object[]> runnable;
    private boolean onlyPlayer = false;


    public SubCommandBuilder setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public SubCommandBuilder setCommandBuilder(SubCommandExecutor commandBuilder) {
        this.commandBuilder = commandBuilder;
        return this;
    }

    public SubCommandBuilder setRunnable(TriConsumer<CommandSender, Command, Object[]> runnable) {
        this.runnable = runnable;
        return this;
    }

    public SubCommandBuilder setOnlyPlayer(boolean onlyPlayer) {
        this.onlyPlayer = onlyPlayer;
        return this;
    }

    public SubCommandBuilder setArguments(Argument... arguments) {
        commandBuilder.setArguments(arguments);
        return this;
    }

    public SubCommandBuilder setCommand(String command) {
        commandBuilder.setCommand(command);
        this.command = command;
        return this;
    }

    public Subcommand build(){
        return new Subcommand(command, permission, commandBuilder, runnable, onlyPlayer);
    }

}
