package wf.utils.bukkit.config.language.models;

public interface MessageReceiver {

    public String get(String path);
    public String get(String path, boolean colorTranslate);
    public String get(String path, char colorChar);
    public String getLanguage();

}
