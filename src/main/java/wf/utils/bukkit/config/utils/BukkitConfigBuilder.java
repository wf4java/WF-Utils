package wf.utils.bukkit.config.utils;

import org.bukkit.plugin.Plugin;
import wf.utils.bukkit.config.BukkitConfig;
import wf.utils.java.file.yamlconfiguration.configuration.ConfigDefaultValue;

import java.util.Collection;

public class BukkitConfigBuilder {

    private Plugin plugin;

    private String configName;

    private boolean autoCopy = true;

    private boolean autoSave = true;

    private BukkitConfig.SaveType saveType = BukkitConfig.SaveType.BUKKIT_ASYNC;

    private int autoSaveSeconds = 300;

    private boolean autoSaveUnique = false;

    private Collection<ConfigDefaultValue> defaultValues;

    public BukkitConfigBuilder setPlugin(Plugin plugin) {
        this.plugin = plugin;
        return this;
    }

    public BukkitConfigBuilder setConfigName(String configName) {
        this.configName = configName;
        return this;
    }

    public BukkitConfigBuilder setAutoCopy(boolean autoCopy) {
        this.autoCopy = autoCopy;
        return this;
    }

    public BukkitConfigBuilder setAutoSave(boolean autoSave) {
        this.autoSave = autoSave;
        return this;
    }

    public BukkitConfigBuilder setSaveType(BukkitConfig.SaveType saveType) {
        this.saveType = saveType;
        return this;
    }

    public BukkitConfigBuilder setAutoSaveSeconds(int autoSaveSeconds) {
        this.autoSaveSeconds = autoSaveSeconds;
        return this;
    }

    public BukkitConfigBuilder setAutoSaveUnique(boolean autoSaveUnique) {
        this.autoSaveUnique = autoSaveUnique;
        return this;
    }

    public BukkitConfigBuilder setDefaultValues(Collection<ConfigDefaultValue> defaultValues) {
        this.defaultValues = defaultValues;
        return this;
    }

    public BukkitConfig build() {
        if(!autoSave) return new BukkitConfig(plugin, configName, autoCopy, defaultValues);

        return new BukkitConfig(plugin, configName, autoCopy, defaultValues, saveType, autoSaveSeconds, autoSaveUnique);
    }

}
