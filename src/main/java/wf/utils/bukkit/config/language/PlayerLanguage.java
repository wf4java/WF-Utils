package wf.utils.bukkit.config.language;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import wf.utils.bukkit.commands.command_builder.DefaultMessages;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class PlayerLanguage extends Language{

    private static FileConfiguration playerLanguagesConfig;
    private static File playerLanguagesFile;

    private static HashMap<String, MessageReceiver> loadedLanguages;

    public PlayerLanguage(Plugin plugin){
        copyAll(plugin);

        if(playerLanguagesConfig != null) return;

        playerLanguagesFile = new File(plugin.getDataFolder(),"languages" + File.separator + "player_languages.yml");
        if (!playerLanguagesFile.exists()) {
            plugin.saveResource("languages" + File.separator + "player_languages.yml",true);
        }
        playerLanguagesConfig = YamlConfiguration.loadConfiguration(playerLanguagesFile);

        loadedLanguages = new HashMap<>();

        for(String lang : getLanguages(plugin)){
            lang = lang.split("\\.")[0];

            File file = new File(plugin.getDataFolder(),"languages" + File.separator + lang + ".yml");
            FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);

            if(DefaultMessages.load(fileConfiguration, !lang.equalsIgnoreCase("en"))){
                save(file, fileConfiguration);
            }

            loadedLanguages.put(lang, MessageReceiverBuilder.create(fileConfiguration, lang));
        }

    }

    public static void setPlayerLanguage(String player, String lang){
        playerLanguagesConfig.set("player_selected_language" + player, lang);
        save(playerLanguagesFile, playerLanguagesConfig);
    }

    public static String getPlayerLanguage(String player){
        return playerLanguagesConfig.getString("player_selected_language" + player);
    }

    public static MessageReceiver getMessageReceiver(String player){
        String language = playerLanguagesConfig.getString("player_selected_language" + player);
        if(language == null) language = "en";

        MessageReceiver receiver = loadedLanguages.get(language);
        return receiver == null ? loadedLanguages.values().stream().findFirst().get() : receiver;
    }

    private static void save(File file, FileConfiguration fileConfiguration){
        try { fileConfiguration.save(file); } catch (IOException e) { throw new RuntimeException(e); }
    }

    public static FileConfiguration getPlayerLanguagesConfig() {
        return playerLanguagesConfig;
    }

    public static void setPlayerLanguagesConfig(FileConfiguration playerLanguagesConfig) {
        PlayerLanguage.playerLanguagesConfig = playerLanguagesConfig;
    }

    public static File getPlayerLanguagesFile() {
        return playerLanguagesFile;
    }

    public static void setPlayerLanguagesFile(File playerLanguagesFile) {
        PlayerLanguage.playerLanguagesFile = playerLanguagesFile;
    }

    public static HashMap<String, MessageReceiver> getLoadedLanguages() {
        return loadedLanguages;
    }

    public static void setLoadedLanguages(HashMap<String, MessageReceiver> loadedLanguages) {
        PlayerLanguage.loadedLanguages = loadedLanguages;
    }
}
