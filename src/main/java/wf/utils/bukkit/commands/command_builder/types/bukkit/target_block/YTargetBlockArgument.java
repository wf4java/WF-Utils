package wf.utils.bukkit.commands.command_builder.types.bukkit.target_block;

import org.bukkit.entity.Player;
import wf.utils.bukkit.commands.command_builder.types.standart.IntegerArgument;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class YTargetBlockArgument extends IntegerArgument {

    private int maxDistance = 25;
    private boolean full = true;

    public YTargetBlockArgument() {}

    public YTargetBlockArgument(int maxDistance, boolean full) {
        this.maxDistance = maxDistance;
        this.full = full;
    }

    public YTargetBlockArgument(boolean full) {
        this.full = full;
    }

    public YTargetBlockArgument(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    @Override
    public List<String> tabulation(Player player, String arg) {
        if(full) return Arrays.asList(
                String.valueOf(player.getTargetBlock(maxDistance).getY()),

                String.valueOf(player.getTargetBlock(maxDistance).getY()) + " " +
                        String.valueOf(player.getTargetBlock(maxDistance).getZ())
        );
        else return Collections.singletonList(String.valueOf(player.getTargetBlock(maxDistance).getY()));
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
