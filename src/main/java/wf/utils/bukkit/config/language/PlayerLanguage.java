package wf.utils.bukkit.config.language;


import org.bukkit.plugin.Plugin;
import wf.utils.bukkit.config.BukkitConfig;

import java.util.List;

public class PlayerLanguage implements Language {

    public PlayerLanguage(Plugin plugin, String resourcePath, String toPath) {

    }

    public MessageReceiver getMessageReceiver(String player){
        return null;
    }

    @Override
    public BukkitConfig getConfig() {
        return null;
    }

    @Override
    public List<String> getAvailableLanguages() {
        return null;
    }
}
