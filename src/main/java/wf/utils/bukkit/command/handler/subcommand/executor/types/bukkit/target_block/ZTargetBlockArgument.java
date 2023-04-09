package wf.utils.bukkit.command.handler.subcommand.executor.types.bukkit.target_block;

import org.bukkit.entity.Player;
import wf.utils.bukkit.command.handler.subcommand.executor.types.standart.IntegerArgument;

import java.util.Arrays;
import java.util.List;

public class ZTargetBlockArgument extends IntegerArgument {

    private int maxDistance = 25;
    private boolean full = true;

    public ZTargetBlockArgument() {}

    public ZTargetBlockArgument(int maxDistance, boolean full) {
        this.maxDistance = maxDistance;
        this.full = full;
    }

    public ZTargetBlockArgument(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    public ZTargetBlockArgument(boolean full) {
        this.full = full;
    }

    @Override
    public List<String> tabulation(Player player, String arg) {
        return Arrays.asList(String.valueOf(player.getTargetBlock(maxDistance).getZ()));
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
