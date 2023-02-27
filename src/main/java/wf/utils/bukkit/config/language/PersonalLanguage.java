package wf.utils.bukkit.config.language;


import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import wf.utils.bukkit.config.BukkitConfig;
import wf.utils.java.file.yamlconfiguration.configuration.ConfigDefaultValue;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class PersonalLanguage implements Language {


    private List<String> availableLanguages;
    private ArrayList<BukkitConfig> allLanguages = new ArrayList<>();
    private HashMap<String, MessageReceiver> allReceivers = new HashMap<>();
    private String path;
    private BukkitConfig optionsConfig;


    public PersonalLanguage(Plugin plugin, String resourcePath, String path) {

    }

    public PersonalLanguage(Plugin plugin, String path, ConfigDefaultValue... defaultValues) {

    }

    public PersonalLanguage(Plugin plugin, String path, String[] dl, ConfigDefaultValue... defaultValues) {

    }


    private List<String> copyAllConfigs(Plugin plugin, String[] dl, ConfigDefaultValue... defaultValues){
        List<String> files = new ArrayList<>();
        for(String l : dl){
            GeneralLanguage.copyFromResource(plugin,path + File.separator + l + ".yml");
            files.add(l);
        }
        List<String> existingFiles = GeneralLanguage.getExistingConfigs(plugin, path);
        if(files.isEmpty() && existingFiles.isEmpty()) return Arrays.asList("en");

        for(String s : existingFiles){
            if(files.contains(s)) continue;
            files.add(s);
        }
        return files;
    }

    private void loadAllLanguages(Plugin plugin, List<String> languages, ConfigDefaultValue... defaultValues){
        for(String lang : languages){
            BukkitConfig config = new BukkitConfig(plugin,path + File.separator + lang,false, defaultValues);
            allLanguages.add(config);
            allReceivers.put(lang, createMessageReceiver(config.getConfig(), lang));
        }
    }



    public static MessageReceiver createMessageReceiver(FileConfiguration config, String language){
        return new MessageReceiver() {
            @Override
            public String get(String path) {
                String mess = config.getString(path);
                return mess == null ? ChatColor.WHITE + "Message \"" + path + "\" not found" :
                        ChatColor.translateAlternateColorCodes('&', mess);
            }
            @Override
            public String get(String path, boolean colorTranslate) {
                String mess = config.getString(path);
                return mess == null ? ChatColor.WHITE + "Message \"" + path + "\" not found" :
                        (colorTranslate ? ChatColor.translateAlternateColorCodes('&', mess) : mess);
            }
            @Override
            public String get(String path, char colorChar) {
                String mess = config.getString(path);
                return mess == null ? ChatColor.WHITE + "Message \"" + path + "\" not found" :
                        ChatColor.translateAlternateColorCodes(colorChar, mess);
            }

            @Override
            public String getLanguage() {
                return language;
            }
        };
    }

    public MessageReceiver getMessageReceiver(String player){
        MessageReceiver mr = allReceivers.get(optionsConfig.getString("players." + player + ".language"));
        return mr != null ? mr : allReceivers.values().toArray(new MessageReceiver[0])[0];
    }

    public void setPlayerLanguage(String player, String language){
        optionsConfig.set("players." + player + ".language", language);
    }

    @Override
    public List<String> getAvailableLanguages() {
        return availableLanguages;
    }

}
