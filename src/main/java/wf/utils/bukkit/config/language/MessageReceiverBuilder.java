package wf.utils.bukkit.config.language;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class MessageReceiverBuilder {



    public static MessageReceiver create(FileConfiguration fileConfiguration, String lang){

        return new MessageReceiver() {
            @Override
            public String get(String path) {
                return mess(fileConfiguration, path);
            }

            @Override
            public String get(String path, boolean colorTranslate) {
                return mess(fileConfiguration, path, colorTranslate);
            }

            @Override
            public String get(String path, char colorChar) {
                return mess(fileConfiguration, path, colorChar);
            }

            @Override
            public String getLanguage() {
                return lang;
            }
        };

    }

    public static String mess(FileConfiguration config, String path) {
        String mess = config.getString(path);
        return mess == null ? ChatColor.WHITE + "Message \"" + path + "\" not found" :
                ChatColor.translateAlternateColorCodes('&', mess);
    }

    public static String mess(FileConfiguration config,String path, boolean colorTranslate) {
        String mess = config.getString(path);
        return mess == null ? ChatColor.WHITE + "Message \"" + path + "\" not found" :
                (colorTranslate ? ChatColor.translateAlternateColorCodes('&', mess) : mess);
    }

    public static String mess(FileConfiguration config, String path, char colorChar) {
        String mess = config.getString(path);
        return mess == null ? ChatColor.WHITE + "Message \"" + path + "\" not found" :
                ChatColor.translateAlternateColorCodes(colorChar, mess);
    }

}
