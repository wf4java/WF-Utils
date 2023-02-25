package wf.utils.bukkit.config.language;

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



    private static List<String> availableLanguages;
    private BukkitConfig languageConfig;
    private BukkitConfig optionsConfig;
    private String toPath;
    private ConfigDefaultValue[] defaultValues;
    private String selectedLanguage;
    private MessageReceiver messageReceiver;

    public GeneralLanguage(Plugin plugin, String resourcePath, String toPath, ConfigDefaultValue... defaultValues) {
        this.toPath = toPath;
        this.defaultValues = defaultValues;
        optionsConfig = new BukkitConfig(plugin,toPath + File.separator + "options",false);
        availableLanguages = copyAllConfigs(plugin, resourcePath, toPath);

        if(optionsConfig.contains("general_language")){
            String generalLanguage = optionsConfig.getString("general_language");
            if(availableLanguages.contains(generalLanguage)){
                selectLanguage(plugin, generalLanguage);
            }else{
                generalLanguage = availableLanguages.contains("en") ? "en" : availableLanguages.get(0);
                selectLanguage(plugin, generalLanguage);
                optionsConfig.set("general_language", generalLanguage);
            }
            selectedLanguage = generalLanguage;
        }else{
            String generalLanguage = availableLanguages.contains("en") ? "en" : availableLanguages.get(0);
            selectLanguage(plugin, generalLanguage);
            selectedLanguage = generalLanguage;
            optionsConfig.set("general_language", generalLanguage);
        }


    }

   private List<String> copyAllConfigs(Plugin plugin, String resourcePath, String toPath){
       List<String> files = ResourceUtils.getResourceFiles(resourcePath);
       List<String> existingFiles = getExistingConfigs(plugin, toPath);
       if(files.isEmpty() && existingFiles.isEmpty()){
           languageConfig = new BukkitConfig(plugin, toPath + File.separator + "en.yml",false, defaultValues);
           return Arrays.asList("en");
       }else{
           for(String name : files){
               if(existingFiles.contains(name)) continue;
               ResourceUtils.copyFromResource(resourcePath + File.separator + name,toPath + File.separator + name,false);
           }
       }
       files.addAll(existingFiles);
       return files;
    }

    private List<String> getExistingConfigs(Plugin plugin, String toPath){
        List<String> fileNames = Arrays.asList(Objects.requireNonNull(new File(plugin.getDataFolder(), toPath).list()));
        fileNames.remove("options.yml");
        return fileNames.stream().map((f) -> {return f.split("\\.")[0];}).collect(Collectors.toList());
    }

    public void selectLanguage(Plugin plugin, String lang){
        languageConfig = new BukkitConfig(plugin, toPath + File.separator + lang);
        optionsConfig.set("general_language", lang);
        selectedLanguage = lang;
        messageReceiver = MessageReceiverBuilder.create(languageConfig.getConfig(), selectedLanguage);
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

    public static List<String> getAvailableLanguages() {
        return availableLanguages;
    }

    public static void setAvailableLanguages(List<String> availableLanguages) {
        GeneralLanguage.availableLanguages = availableLanguages;
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

    public String getToPath() {
        return toPath;
    }

    public void setToPath(String toPath) {
        this.toPath = toPath;
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
