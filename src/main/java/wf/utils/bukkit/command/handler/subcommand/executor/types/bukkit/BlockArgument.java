package wf.utils.bukkit.command.handler.subcommand.executor.types.bukkit;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wf.utils.bukkit.command.handler.subcommand.executor.types.ArgumentType;

import java.util.ArrayList;
import java.util.List;

public class BlockArgument implements ArgumentType {

    @Override
    public String getMessage() {
        return "This argument is not valid, enter minecraft block name!";
    }

    @Override
    public String getMessageCode() {
        return "COMMAND.DEFAULT.ARGUMENT.BLOCK_ARGUMENT_WRONG";
    }

    @Override
    public String getName() {
        return "block";
    }

    @Override
    public boolean isIt(CommandSender sender, String argument) {
        Material material = Material.getMaterial(argument.toUpperCase());
        if(material == null || !material.isBlock()) return false;
        return true;
    }

    @Override
    public Object get(CommandSender sender,String argument) {
        return Material.getMaterial(argument.toUpperCase());
    }

    @Override
    public List<String> tabulation(CommandSender sender, String arg) {
        return getContainedMaterials(arg);
    }


    private List<String> getAllMaterials() {
        List<String> list = new ArrayList<String>();
        for(Material mat : Material.values()) {
            if(mat.isBlock()) list.add(mat.name().toLowerCase());
        }

        return list;
    }

    private List<String> getContainedMaterials(String material) {
        if(material.isEmpty()) return getAllMaterials();
        List<String> list = new ArrayList<String>();
        for(String mat : getAllMaterials()) {
            if(mat.contains(material.toLowerCase())) {
                list.add(mat);
            }
        }
        return list;
    }


}
