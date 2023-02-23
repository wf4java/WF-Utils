package wf.utils.bukkit.config.language;

import com.google.common.io.Files;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;


public class Language {

    private static ArrayList<String> availableLanguages;
    private static String language;
    private static FileConfiguration config;
    private static File file;


    public static void loadLanguage(Plugin plugin, String language){

        Language.language = language;

        file = new File(plugin.getDataFolder(),"languages" + File.separator + language + ".yml");
        if (!file.exists()) {
            plugin.saveResource("languages" + File.separator + language + ".yml", true);
        }
        config = YamlConfiguration.loadConfiguration(file);

    }

    public MessageReceiver getMessageReceiver(){
        return MessageReceiverBuilder.create(config, language);
    }

    public static ArrayList<String> getAvailableLanguages(){
        return availableLanguages;
    }

    public static void copyAll(Plugin plugin){
        try {
            for(String lang : new File(plugin.getClass().getResource("/languages").toURI()).list()){
                File fileInFolder = new File(plugin.getDataFolder(), "languages" + File.separator + lang);
                if(!fileInFolder.exists()) Files.copy(new File(plugin.getClass().getResource("/languages" + File.separator + lang).toURI()), fileInFolder);
            }

            availableLanguages = getLanguages(plugin);

        } catch (URISyntaxException | IOException e) {throw new RuntimeException(e);}
    }

    public static ArrayList<String> getLanguages(Plugin plugin){
        ArrayList<String> ret = (ArrayList<String>) Arrays.asList(new File(plugin.getDataFolder(),"languages").list());
        if(ret.contains("player_languages.yml")) ret.remove("player_languages.yml");
        return ret;
    }


    public static void setAvailableLanguages(ArrayList<String> availableLanguages) {
        Language.availableLanguages = availableLanguages;
    }

    public static String getLanguage() {
        return language;
    }

    public static void setLanguage(String language) {
        Language.language = language;
    }

    public static FileConfiguration getConfig() {
        return config;
    }

    public static void setConfig(FileConfiguration config) {
        Language.config = config;
    }

    public static File getFile() {
        return file;
    }

    public static void setFile(File file) {
        Language.file = file;
    }

    public static void save(){
        try { config.save(file); } catch (IOException e) { throw new RuntimeException(e); }
    }


    public static String mess(String path) {
        String mess = config.getString(path);
        return mess == null ? ChatColor.WHITE + "Message \"" + path + "\" not found" :
                ChatColor.translateAlternateColorCodes('&', mess);
    }

    public static String mess(String path, boolean colorTranslate) {
        String mess = config.getString(path);
        return mess == null ? ChatColor.WHITE + "Message \"" + path + "\" not found" :
                (colorTranslate ? ChatColor.translateAlternateColorCodes('&', mess) : mess);
    }

    public static String mess(String path, char colorChar) {
        String mess = config.getString(path);
        return mess == null ? ChatColor.WHITE + "Message \"" + path + "\" not found" :
                ChatColor.translateAlternateColorCodes(colorChar, mess);
    }
}
