package wf.utils.bukkit.config.language;


import org.bukkit.plugin.Plugin;

public class PlayerLanguage extends GeneralLanguage {

    public PlayerLanguage(Plugin plugin, String resourcePath, String toPath) {
        super(plugin, resourcePath, toPath);
    }

    public MessageReceiver getMessageReceiver(String player){
        return null;
    }
}
