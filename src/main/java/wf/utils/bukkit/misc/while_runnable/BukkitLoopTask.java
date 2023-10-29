package wf.utils.bukkit.misc.while_runnable;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import wf.utils.java.thread.loop.LoopTask;

public class BukkitLoopTask implements LoopTask {


    private BukkitTask task;
    private Runnable runnable;
    private final Plugin plugin;
    private final long period;
    private final long delay;
    private final boolean sync;


    public BukkitLoopTask(Runnable runnable, Plugin plugin, long period, long delay, boolean sync){
        this.plugin = plugin;
        this.period = period;
        this.delay = delay;
        this.sync = sync;
        this.runnable = runnable;
    }

    public void initTask() {
        BukkitRunnable bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        };

        if(sync) task = bukkitRunnable.runTaskTimer(plugin, delay, period);
        else task = bukkitRunnable.runTaskTimerAsynchronously(plugin, delay, period);
    }

    @Override
    public void stop() {
        if(!task.isCancelled()) task.cancel();
    }

    @Override
    public void start() {
        initTask();
    }

    protected void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public long getPeriod() {
        return period;
    }

    public long getDelay() {
        return delay;
    }

    public boolean isSync() {
        return sync;
    }
}
