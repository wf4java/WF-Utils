package wf.utils.bukkit.command.handler.subcommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import wf.utils.bukkit.command.handler.subcommand.executor.Argument;
import wf.utils.bukkit.command.handler.subcommand.executor.SubCommandExecutor;
import wf.utils.java.functions.TriConsumer;

public class SubCommandBuilder {

    private String command;
    private String permission;
    private SubCommandExecutor subCommandExecutor;
    private TriConsumer<CommandSender, Command, Object[]> runnable;
    private boolean onlyPlayer = false;


    public SubCommandBuilder setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public SubCommandExecutor subCommandExecutor(SubCommandExecutor subCommandExecutor) {
        this.subCommandExecutor = subCommandExecutor;
        return this.subCommandExecutor;
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
        subCommandExecutor.setArguments(arguments);
        return this;
    }

    public SubCommandBuilder setCommand(String command) {
        subCommandExecutor.setCommand(command);
        this.command = command;
        return this;
    }

    public SubCommand build() {
        if(this.subCommandExecutor == null) this.subCommandExecutor = new SubCommandExecutor();

        return new SubCommand(command, permission, subCommandExecutor, runnable, onlyPlayer);
    }

}
