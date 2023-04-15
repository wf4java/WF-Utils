package wf.utils.bukkit.command.handler.subcommand.executor.types.bukkit.target_block;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import wf.utils.bukkit.command.handler.subcommand.executor.types.standart.IntegerArgument;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class XTargetBlockArgument extends IntegerArgument {

    private int maxDistance = 25;
    private boolean full = true;

    public XTargetBlockArgument() {}

    public XTargetBlockArgument(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    public XTargetBlockArgument(int maxDistance, boolean full) {
        this.maxDistance = maxDistance;
        this.full = full;
    }

    public XTargetBlockArgument(boolean full) {
        this.full = full;
    }

    @Override
    public List<String> tabulation(CommandSender sender, String arg) {
        if(!(sender instanceof LivingEntity)) return null;
        LivingEntity le = (LivingEntity) sender;
        if(full) return Arrays.asList(
                String.valueOf(le.getTargetBlock(maxDistance).getX()),

                String.valueOf(le.getTargetBlock(maxDistance).getX()) + " " +
                String.valueOf(le.getTargetBlock(maxDistance).getY()),

                String.valueOf(le.getTargetBlock(maxDistance).getX()) + " " +
                String.valueOf(le.getTargetBlock(maxDistance).getY()) + " " +
                String.valueOf(le.getTargetBlock(maxDistance).getZ()));
        else return Collections.singletonList(String.valueOf(le.getTargetBlock(maxDistance).getX()));
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }
}
