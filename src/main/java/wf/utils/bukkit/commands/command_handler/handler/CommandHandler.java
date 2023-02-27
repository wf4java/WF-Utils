package wf.utils.bukkit.commands.command_handler.handler;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import wf.utils.bukkit.commands.command_handler.subcommand.executor.Argument;
import wf.utils.bukkit.commands.command_handler.subcommand.executor.types.bukkit.BukkitArgumentType;
import wf.utils.bukkit.commands.command_handler.DefaultCommandHandlerMessages;
import wf.utils.bukkit.commands.command_handler.subcommand.SubCommand;
import wf.utils.bukkit.commands.command_handler.subcommand.SubCommandBuilder;
import wf.utils.bukkit.config.language.*;
import wf.utils.java.file.yamlconfiguration.configuration.ConfigDefaultValue;


import java.util.*;
import java.util.stream.Collectors;


public class CommandHandler implements CommandExecutor, TabExecutor {

    private JavaPlugin plugin;
    private String ownCommandName;
    private TreeMap<String, SubCommand> subcommands = new TreeMap<String, SubCommand>(new Comparator<String>() {@Override public int compare(String str1, String str2) {return str1.compareTo(str2) * -1;}});
    private Language language;
    private LanguageType languageType;



    public CommandHandler(JavaPlugin plugin, String[] commands, Language language, boolean addDefaultCommands) {
        this.plugin = plugin;
        this.language = language;
        this.languageType = LanguageType.getLanguageType(language);
        this.ownCommandName = commands[0];
        for(String command : commands){
            plugin.getCommand(command).setExecutor(this);
            plugin.getCommand(command).setTabCompleter(this);
        }
        if(addDefaultCommands) addDefaultCommands();
    }

    public CommandHandler(JavaPlugin plugin, String[] commands, Language language) {
        this(plugin, commands, language, true);
    }

    public CommandHandler(JavaPlugin plugin, String command, Language language) {
        this.plugin = plugin;
        this.language = language;
        this.languageType = LanguageType.getLanguageType(language);
        plugin.getCommand(command).setExecutor(this);
        plugin.getCommand(command).setTabCompleter(this);
        this.ownCommandName = command;
        addDefaultCommands();
    }


    public CommandHandler(JavaPlugin plugin, String[] commands) {
        this(plugin, commands,null, true);
    }

    public CommandHandler(JavaPlugin plugin, String command) {
        this.plugin = plugin;
        plugin.getCommand(command).setExecutor(this);
        plugin.getCommand(command).setTabCompleter(this);
        this.ownCommandName = command;
        addDefaultCommands();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 0){
            sender.sendMessage(ChatColor.GOLD + "/" + ownCommandName + ChatColor.AQUA + " <" + ChatColor.DARK_GREEN +
                    String.join(ChatColor.RED + ", " + ChatColor.DARK_GREEN, subcommands.keySet().stream().
                            map((s) -> s.split("\\.")[0]).distinct().collect(Collectors.toList())) + ChatColor.AQUA + ">");
            return true;
        }

        MessageReceiver msg = getMessageReceiver(sender.getName());

        for(Map.Entry<String, SubCommand> entry : subcommands.entrySet()){
            String[] subcommandArgs = entry.getKey().split("\\.");
            String full = String.join(".", Arrays.copyOfRange(args,0, subcommandArgs.length));
            if(full.equalsIgnoreCase(String.join(".", subcommandArgs))){
                entry.getValue().onCommand(sender, command, args, subcommandArgs.length, msg);
                return true;
            }
        }

        if(msg == null) sender.sendMessage("\n" + ChatColor.RED + "Command not found!");
        else sender.sendMessage(ChatColor.RED + msg.get("COMMAND_NOT_FOUND"));

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return null;
        List<String> tabulation = new ArrayList<>();

        MessageReceiver msg = getMessageReceiver(sender.getName());

        for(Map.Entry<String, SubCommand> entry : subcommands.entrySet()){
            if(!entry.getValue().checkPermission(sender)) continue;

            String[] subcommandArgs = entry.getKey().split("\\.");

            if(args.length < 2){
                tabulation.add(subcommandArgs[0]);
            }else {
                boolean isIt = true;
                int max = Math.min(args.length - 1, subcommandArgs.length);
                for(int i = 0; i < max; i++) if(!subcommandArgs[i].equalsIgnoreCase(args[i])) { isIt = false; break; }
                if(!isIt) continue;

                if(args.length <= subcommandArgs.length){
                    tabulation.add(subcommandArgs[args.length - 1]);
                }else if(args.length <= subcommandArgs.length + entry.getValue().getCommandBuilder().getArguments().length){
                    return entry.getValue().getCommandBuilder().getArguments()[args.length - subcommandArgs.length - 1].getType().
                            tabulation((Player)sender, args[args.length - 1]);
                }

            }
        }

        return tabulation;
    }

    public void addDefaultCommands(){
        addSubcommand(new SubCommandBuilder()
                .setCommand("allcommands")
                .setRunnable((sender, command, args) -> {
                    int availableCommandsCount = 0;

                    for(Map.Entry<String, SubCommand> entry : subcommands.entrySet()){
                        if(entry.getValue().checkPermission(sender)){
                            if(availableCommandsCount == 0) sender.sendMessage("\n");
                            sender.sendMessage(entry.getValue().getCommandBuilder().getArgumentsText());
                            availableCommandsCount++;
                        }
                    }

                    if(availableCommandsCount == 0){
                        if(language == null) sender.sendMessage("Not found available commands!");
                        else sender.sendMessage(getMess(sender,"NOT_FOUND_AVAILABLE_COMMANDS"));
                    }
                }).build());



        if(languageType == LanguageType.GENERAL) {
            addSubcommand(new SubCommandBuilder()
                    .setCommand("language")
                    .setArguments(new Argument(BukkitArgumentType.LANGUAGE(language)))
                    .setRunnable((sender, command, args) -> {((GeneralLanguage) language).selectLanguage(plugin, (String) args[0]);})
                    .build());
        }
        else if(languageType == LanguageType.PERSONAL){
            addSubcommand(new SubCommandBuilder()
                    .setCommand("language")
                    .setArguments(new Argument(BukkitArgumentType.LANGUAGE(language)))
                    .setRunnable((sender, command, args) -> {((PersonalLanguage) language).setPlayerLanguage(sender.getName(), (String) args[0]);})
                    .build());
        }



    }

    public void addSubcommand(String command, SubCommand subcommand) {
        subcommand.getCommandBuilder().setCommand("/" + ownCommandName + " " + String.join(" ", command.split("\\.")) );
        subcommands.put(command, subcommand);
    }

    public void addSubcommand(SubCommand subcommand) {
        subcommand.getCommandBuilder().setCommand("/" + ownCommandName + " " + String.join(" ", subcommand.getCommand().split("\\.")) );
        subcommands.put(subcommand.getCommand(), subcommand);
    }

    public MessageReceiver getMessageReceiver(String player){
        if(languageType == LanguageType.PERSONAL) return ((PersonalLanguage) language).getMessageReceiver(player);
        if(languageType == LanguageType.GENERAL) return ((GeneralLanguage) language).getMessageReceiver();
        return null;
    }



    public String getMess(CommandSender sender, String path){
        if(languageType == LanguageType.GENERAL) return ((GeneralLanguage) language).mess(path);
        if(languageType == LanguageType.PERSONAL) return ((PersonalLanguage) language).getMessageReceiver(sender.getName()).get(path);
        return path;
    }

    public TreeMap<String, SubCommand> getSubcommands() {
        return subcommands;
    }

    public void setSubcommands(TreeMap<String, SubCommand> subcommands) {
        this.subcommands = subcommands;
    }

    public String getOwnCommandName() {
        return ownCommandName;
    }

    public void setOwnCommandName(String ownCommandName) {
        this.ownCommandName = ownCommandName;
    }

    public Language getLanguage() {
        return language;
    }

    public static ConfigDefaultValue[] getLanguageDefaultValues(){
        return DefaultCommandHandlerMessages.getValues();
    }


    @Override
    public String toString() {
        return "CommandHandler{" +
                "plugin=" + plugin +
                ", ownCommandName='" + ownCommandName + '\'' +
                ", subcommands=" + subcommands +
                ", language=" + language +
                '}';
    }
}


// OLD, NOT RECOMMENDED FOR USE!!! Pls use builder!


/*
CommandHandler commandHandler = new CommandHandler(this,"wf", new PlayerLanguage(plugin));


        commandHandler.addSubcommand("mess", new Subcommand(new CommandBuilder(
                new Argument("player", ArgumentType.STRING)
        ), (sender, command, args) -> {

            String string = (String) args[0];

            sender.sendMessage(PlayerLanguage.getMessageReceiver(sender.getName()).get(string));
        } ));

        commandHandler.addSubcommand("give", new Subcommand(new CommandBuilder(
                new Argument("player", BukkitArgumentType.ONLINE_PLAYER),
                new Argument("item", BukkitArgumentType.ITEM),
                new Argument("count", ArgumentType.INTEGER,false,1)
        ), (sender, command, args) -> {
            Player player = (Player) args[0];
            Material material = (Material) args[1];
            int count = (Integer) args[2];

            player.getInventory().addItem(new ItemStack(material, count));
        } ));


        commandHandler.addSubcommand("home.removes", new Subcommand(new CommandBuilder(
                new Argument("inf", ArgumentType.INTEGER),
                new Argument("not", ArgumentType.BOOLEAN,false,true)
        ), (sender, command, args) -> {
                Bukkit.broadcastMessage(String.valueOf(args[1]) + " |3");

        }));

        commandHandler.addSubcommand("home.sets", new Subcommand(new CommandBuilder(
                new Argument("inf", ArgumentType.BOOLEAN),
                new Argument("not", ArgumentType.BOOLEAN,false,true)
        ), (sender, command, args) -> {
                Bukkit.broadcastMessage(String.valueOf(args[1]) + " |4");

        }));

        commandHandler.addSubcommand("set", new Subcommand(new CommandBuilder("wf",
                new Argument("inf", ArgumentType.STRING),
                new Argument("not", ArgumentType.BOOLEAN,false,true)
        ), (sender, command, args) -> {
                Bukkit.broadcastMessage(String.valueOf(args[1]) + " |1");

        }));

        commandHandler.addSubcommand("remove", new Subcommand(new CommandBuilder(
                new Argument("inf", ArgumentType.DOUBLE),
                new Argument("not", ArgumentType.BOOLEAN,false,true)
        ), (sender, command, args) -> {
                Bukkit.broadcastMessage(String.valueOf(args[1]) + " |2");

        }));

        commandHandler.addSubcommand("test.player", new Subcommand("test", new CommandBuilder(
                new Argument(new OnlinePlayerArgument(false)),
                new Argument(ArgumentType.BOOLEAN,false,true)
        ), (sender, command, args) -> {
                Bukkit.broadcastMessage(String.valueOf(((Player) args[0]).getHealth()));
        }));



        commandHandler.addSubcommand("test.home", new Subcommand("test", new CommandBuilder(
                new Argument(new OnlinePlayerArgument(false)),
                new Argument(ArgumentType.DOUBLE),
                new Argument(ArgumentType.DOUBLE),
                new Argument(ArgumentType.DOUBLE),
                new Argument("hp", ArgumentType.DOUBLE),
                new Argument(ArgumentType.BOOLEAN,false,true)
        ), (sender, command, args) -> {
                Bukkit.broadcastMessage(String.valueOf(((Player) args[0]).getHealth()));

        }));


        commandHandler.addSubcommand("player.give", new Subcommand("test", new CommandBuilder(
                new Argument(new OnlinePlayerArgument(false)),
                new Argument(BukkitArgumentType.ITEM),
                new Argument(ArgumentType.INTEGER, false, 1)
        ), (sender, command, args) -> {
                ((Player) args[0]).getInventory().addItem(new ItemStack((Material) args[1], (Integer) args[2]));

        }));

        commandHandler.addSubcommand("player.giveb", new Subcommand("test", new CommandBuilder(
                new Argument(new OnlinePlayerArgument(false)),
                new Argument(BukkitArgumentType.BLOCK),
                new Argument(ArgumentType.INTEGER,false,1)
        ), (sender, command, args) -> {
                ((Player) args[0]).getInventory().addItem(new ItemStack((Material) args[1], (Integer) args[2]));

        }));

        commandHandler.addSubcommand("player.setb", new Subcommand("test", new CommandBuilder(
                new Argument(new OnlinePlayerArgument(false)),
                new Argument(BukkitArgumentType.BLOCK),
                new Argument("x", ArgumentType.DOUBLE,false,0d),
                new Argument("y", ArgumentType.DOUBLE,false,0d),
                new Argument("z", ArgumentType.DOUBLE,false,0d)
        ), (sender, command, args) -> {
                ((Player) args[0]).getLocation().add((Double) args[2], (Double) args[3] - 1, (Double) args[4]).getBlock().
                        setType((Material) args[1]);

        }));




        commandHandler.addSubcommand("block", new Subcommand("test", new CommandBuilder(
                new Argument("x", BukkitArgumentType.X_TARGET_BLOCK),
                new Argument("y", BukkitArgumentType.Y_TARGET_BLOCK),
                new Argument("z", BukkitArgumentType.Z_TARGET_BLOCK),
                new Argument(BukkitArgumentType.BLOCK)
        ), (sender, command, args) -> {
                ((Player) sender).getWorld().getBlockAt((Integer) args[0], (Integer) args[1],(Integer) args[2]).setType((Material) args[3]);

        }));


        commandHandler.addSubcommand("line", new Subcommand("test", new CommandBuilder(
                new Argument("x1", BukkitArgumentType.X_TARGET_BLOCK),
                new Argument("y1", BukkitArgumentType.Y_TARGET_BLOCK),
                new Argument("z1", BukkitArgumentType.Z_TARGET_BLOCK),
                new Argument("x2", BukkitArgumentType.X_TARGET_BLOCK),
                new Argument("y2", BukkitArgumentType.Y_TARGET_BLOCK),
                new Argument("z2", BukkitArgumentType.Z_TARGET_BLOCK),
                new Argument(BukkitArgumentType.BLOCK),
                new Argument("step", ArgumentType.DOUBLE,false,1d)
        ), (sender, command, args) -> {

                double x1 = (Integer) args[0];
                double y1 = (Integer) args[1];
                double z1 = (Integer) args[2];

                double x2 = (Integer) args[3];
                double y2 = (Integer) args[4];
                double z2 = (Integer) args[5];

                double[] confusion = MathUtils.getConfusion(x1, y1, z1, x2, y2, z2);
                double distance = MathUtils.getDistance(x1, y1, z1, x2, y2, z2);


                for(double i = 0; i < distance; i += (Double) args[7]){
                    ((Player) sender).getWorld().getBlockAt((int) Math.round(x2 + confusion[0] * i), (int) Math.round(y2 + confusion[1] * i),
                            (int) Math.round(z2 + confusion[2] * i)).setType((Material) args[6]);
                }

        }));

 */




