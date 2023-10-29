package wf.utils.bukkit.config.language;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import wf.utils.bukkit.config.BukkitConfig;
import wf.utils.bukkit.config.language.models.Language;
import wf.utils.bukkit.config.language.models.MessageReceiver;
import wf.utils.java.file.yamlconfiguration.configuration.ConfigDefaultValue;


import java.io.File;
import java.util.*;
import java.util.stream.Collectors;


public class GeneralLanguage implements Language {



    private List<String> availableLanguages;
    private BukkitConfig languageConfig;
    private BukkitConfig optionsConfig;
    private String path;
    private final Collection<ConfigDefaultValue> defaultValues;
    private String selectedLanguage;
    private MessageReceiver messageReceiver;



    public GeneralLanguage(Plugin plugin, String path, Collection<ConfigDefaultValue> defaultValues) {
        this(plugin, path, new String[0], defaultValues);
    }

    public GeneralLanguage(Plugin plugin, String path) {
        this(plugin, path, new String[0], null);
    }

    public GeneralLanguage(Plugin plugin, String path, String... dl) {
        this(plugin, path, dl, null);
    }

    public GeneralLanguage(Plugin plugin, String path, String[] dl, Collection<ConfigDefaultValue> defaultValues) {
        this.path = path;
        this.defaultValues = defaultValues;
        optionsConfig = new BukkitConfig(plugin,path + File.separator + "options",false);
        availableLanguages = copyAllConfigs(plugin, dl);

        if(optionsConfig.contains("general_language")){
            String generalLanguage = optionsConfig.getString("general_language");
            if(availableLanguages.contains(generalLanguage)){
                selectLanguage(plugin, generalLanguage);
            }else{
                generalLanguage = availableLanguages.contains("en") ? "en" : availableLanguages.get(0);
                selectLanguage(plugin, generalLanguage);
                optionsConfig.set("general_language", generalLanguage);
                optionsConfig.save();
            }
            selectedLanguage = generalLanguage;
        }else{
            String generalLanguage = availableLanguages.contains("en") ? "en" : availableLanguages.get(0);
            selectLanguage(plugin, generalLanguage);
            selectedLanguage = generalLanguage;
            optionsConfig.set("general_language", generalLanguage);
            optionsConfig.save();
        }
    }

   private List<String> copyAllConfigs(Plugin plugin, String[] dl){
       List<String> files = new ArrayList<>();
       for(String l : dl){
           copyFromResource(plugin,path + File.separator + l + ".yml");
           files.add(l);
       }
       List<String> existingFiles = getExistingConfigs(plugin, path);
       if(files.isEmpty() && existingFiles.isEmpty()) return Arrays.asList("en");
       for(String s : existingFiles){
           if(files.contains(s)) continue;
           files.add(s);
       }
       return files;
    }

    public static List<String> getExistingConfigs(Plugin plugin, String path){
        ArrayList<String> fileNames = new ArrayList<>(Arrays.asList(Objects.requireNonNull(new File(plugin.getDataFolder(), path).list())));
        fileNames.remove("options.yml");
        return fileNames.stream().map((f) -> {return f.split("\\.")[0];}).collect(Collectors.toList());
    }

    public static void copyFromResource(Plugin plugin, String path){
        File file = new File(plugin.getDataFolder(), path);
        if (!file.exists()) {plugin.saveResource(path, true);}
    }

    public void selectLanguage(Plugin plugin, String lang){
        languageConfig = new BukkitConfig(plugin,path + File.separator + lang,false, defaultValues);
        optionsConfig.set("general_language", lang);
        optionsConfig.save();
        selectedLanguage = lang;
        loadMessageReceiver();
    }

    public void loadMessageReceiver(){
        messageReceiver = new MessageReceiver() {
            @Override
            public String get(String path) {
                return mess(path);
            }

            @Override
            public String get(String path, boolean colorTranslate) {
                return mess(path, colorTranslate);
            }

            @Override
            public String get(String path, char colorChar) {
                return mess(path, colorChar);
            }

            @Override
            public String getLanguage() {
                return selectedLanguage;
            }
        };
    }
    public MessageReceiver getMessageReceiver(){
        return messageReceiver;
    }


    public String mess(String path) {
        String mess = languageConfig.getString(path);
        return mess == null ? ChatColor.WHITE + "Message \"" + path + "\" not found" :
                ChatColor.translateAlternateColorCodes('&', mess);
    }

    public String mess(String path, boolean colorTranslate) {
        String mess = languageConfig.getString(path);
        return mess == null ? ChatColor.WHITE + "Message \"" + path + "\" not found" :
                (colorTranslate ? ChatColor.translateAlternateColorCodes('&', mess) : mess);
    }

    public String mess(String path, char colorChar) {
        String mess = languageConfig.getString(path);
        return mess == null ? ChatColor.WHITE + "Message \"" + path + "\" not found" :
                ChatColor.translateAlternateColorCodes(colorChar, mess);
    }


    public List<String> getAvailableLanguages() {
        return availableLanguages;
    }

    public void setAvailableLanguages(List<String> availableLanguages) {
        this.availableLanguages = availableLanguages;
    }

    public BukkitConfig getLanguageConfig() {
        return languageConfig;
    }

    public void setLanguageConfig(BukkitConfig languageConfig) {
        this.languageConfig = languageConfig;
    }

    public BukkitConfig getOptionsConfig() {
        return optionsConfig;
    }

    public void setOptionsConfig(BukkitConfig optionsConfig) {
        this.optionsConfig = optionsConfig;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSelectedLanguage() {
        return selectedLanguage;
    }

    public void setSelectedLanguage(String selectedLanguage) {
        this.selectedLanguage = selectedLanguage;
    }

    public void setMessageReceiver(MessageReceiver messageReceiver) {
        this.messageReceiver = messageReceiver;
    }
}
