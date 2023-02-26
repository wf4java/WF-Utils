package wf.utils.bukkit.config.language;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import wf.utils.bukkit.config.BukkitConfig;
import wf.utils.java.file.utils.ResourceUtils;
import wf.utils.java.file.yamlconfiguration.configuration.ConfigDefaultValue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class GeneralLanguage implements Language {



    private List<String> availableLanguages;
    private BukkitConfig languageConfig;
    private BukkitConfig optionsConfig;
    private String path;
    private ConfigDefaultValue[] defaultValues;
    private String selectedLanguage;
    private MessageReceiver messageReceiver;



    public GeneralLanguage(Plugin plugin, String toPath, ConfigDefaultValue... defaultValues) {
        this(plugin, toPath, new String[0], defaultValues);
    }
    public GeneralLanguage(Plugin plugin, String toPath, String... defaultLanguages) {
        this(plugin, toPath, defaultLanguages, new ConfigDefaultValue[0]);
    }

    public GeneralLanguage(Plugin plugin, String toPath, String[] defaultLanguages, ConfigDefaultValue... defaultValues) {
        this.path = toPath;
        this.defaultValues = defaultValues;
        optionsConfig = new BukkitConfig(plugin,toPath + File.separator + "options",false);
        availableLanguages = copyAllConfigs(plugin, path, defaultLanguages);

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

   private List<String> copyAllConfigs(Plugin plugin, String path, String[] defaultLanguages){
       List<String> files = new ArrayList<>();
       for(String l : defaultLanguages){
           plugin.saveResource(path + File.separator + l + ".yml",false);
           files.add(l + ".yml");
       }
       List<String> existingFiles = getExistingConfigs(plugin, path);
       if(files.isEmpty() && existingFiles.isEmpty()){
           languageConfig = new BukkitConfig(plugin, path + File.separator + "en",false, defaultValues);
           return Arrays.asList("en");
       }else{
           for(String name : files){
               if(existingFiles.contains(name)) continue;
               ResourceUtils.copyFromResource(path + File.separator + name,path + File.separator + name,false);
           }
       }
       files.addAll(existingFiles);
       return files;
    }

    private List<String> getExistingConfigs(Plugin plugin, String toPath){
        ArrayList<String> fileNames = new ArrayList<>(Arrays.asList(Objects.requireNonNull(new File(plugin.getDataFolder(), toPath).list())));
        fileNames.remove("options.yml");
        return fileNames.stream().map((f) -> {return f.split("\\.")[0];}).collect(Collectors.toList());
    }

    public void selectLanguage(Plugin plugin, String lang){
        languageConfig = new BukkitConfig(plugin, path + File.separator + lang);
        optionsConfig.set("general_language", lang);
        optionsConfig.save();
        selectedLanguage = lang;
        messageReceiver = MessageReceiverBuilder.create(languageConfig.getConfig(), selectedLanguage);
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


    @Override
    public BukkitConfig getConfig() {
        return null;
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

    public ConfigDefaultValue[] getDefaultValues() {
        return defaultValues;
    }

    public void setDefaultValues(ConfigDefaultValue[] defaultValues) {
        this.defaultValues = defaultValues;
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
