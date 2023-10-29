package wf.utils.bukkit.config.language;


import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import wf.utils.bukkit.config.BukkitConfig;
import wf.utils.bukkit.config.language.models.Language;
import wf.utils.bukkit.config.language.models.MessageReceiver;
import wf.utils.java.file.yamlconfiguration.configuration.ConfigDefaultValue;
import wf.utils.java.file.yamlconfiguration.configuration.ConfigDefaultValues;

import java.io.File;
import java.util.*;

public class PersonalLanguage implements Language {


    private List<String> availableLanguages;
    private ArrayList<BukkitConfig> allLanguages = new ArrayList<>();
    private HashMap<String, MessageReceiver> allReceivers = new HashMap<>();
    private String path;
    private BukkitConfig optionsConfig;




    public PersonalLanguage(Plugin plugin, String path, Collection<ConfigDefaultValue> defaultValues) {
        this(plugin, path, new String[0], defaultValues);
    }

    public PersonalLanguage(Plugin plugin, String path) {
        this(plugin, path, new String[0], null);
    }
    public PersonalLanguage(Plugin plugin, String path, String[] dl) {
        this(plugin, path, dl, null);
    }

    public PersonalLanguage(Plugin plugin, String path, String[] dl, Collection<ConfigDefaultValue> defaultValues) {
        this.path = path;
        optionsConfig = new BukkitConfig(plugin,path + File.separator + "options",false);


        availableLanguages = copyAllConfigs(plugin, dl);
        loadAllLanguages(plugin, availableLanguages, defaultValues);
    }


    private List<String> copyAllConfigs(Plugin plugin, String[] dl){
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

    private void loadAllLanguages(Plugin plugin, List<String> languages, Collection<ConfigDefaultValue> defaultValues){
        for(String lang : languages){
            BukkitConfig config = new BukkitConfig(plugin,path + File.separator + lang,false, defaultValues);
            allLanguages.add(config);
            allReceivers.put(lang, createMessageReceiver(config.getConfig(), lang));
        }
    }

    private static MessageReceiver getLastElement(MessageReceiver[] array){
        return array[array.length - 1];
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
        String language = optionsConfig.getString("players." + player + ".language");

        if(language == null) return getLastElement(allReceivers.values().toArray(new MessageReceiver[0]));
        MessageReceiver mr = allReceivers.get(language);
        return mr != null ? mr : getLastElement(allReceivers.values().toArray(new MessageReceiver[0]));
    }

    public void setPlayerLanguage(String player, String language){
        optionsConfig.set("players." + player + ".language", language);
        optionsConfig.save();
    }

    @Override
    public List<String> getAvailableLanguages() {
        return availableLanguages;
    }

    public void setAvailableLanguages(List<String> availableLanguages) {
        this.availableLanguages = availableLanguages;
    }

    public ArrayList<BukkitConfig> getAllLanguages() {
        return allLanguages;
    }

    public void setAllLanguages(ArrayList<BukkitConfig> allLanguages) {
        this.allLanguages = allLanguages;
    }

    public HashMap<String, MessageReceiver> getAllReceivers() {
        return allReceivers;
    }

    public void setAllReceivers(HashMap<String, MessageReceiver> allReceivers) {
        this.allReceivers = allReceivers;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public BukkitConfig getOptionsConfig() {
        return optionsConfig;
    }

    public void setOptionsConfig(BukkitConfig optionsConfig) {
        this.optionsConfig = optionsConfig;
    }
}
