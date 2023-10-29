package wf.utils.bukkit.misc.while_runnable;

import org.bukkit.plugin.Plugin;
import wf.utils.java.thread.loop.MultipleLoopTask;

import java.util.ArrayList;
import java.util.HashMap;

public class BukkitMultipleLoopTask extends BukkitLoopTask implements MultipleLoopTask {

    private final HashMap<String, Runnable> runnableMap = new HashMap<>();
    private final ArrayList<Runnable> runnables = new ArrayList<>();


    public BukkitMultipleLoopTask(Plugin plugin, long delay, long period, boolean sync) {
        super(null, plugin, period, delay, sync);
        setRunnable(() -> {
            for(Runnable runnable : runnables)
                runnable.run();
        });
    }

    @Override
    public void addRunnable(String name, Runnable runnable) {
        Runnable oldRunnable = runnableMap.put(name, runnable);
        if(oldRunnable != null && oldRunnable != runnable) runnables.remove(oldRunnable);
        runnables.add(runnable);
    }

    @Override
    public boolean removeRunnable(String name) {
        Runnable deletedRunnable = runnableMap.remove(name);
        if(deletedRunnable != null){
            runnables.remove(deletedRunnable);
            return true;
        }

        return false;
    }
}
