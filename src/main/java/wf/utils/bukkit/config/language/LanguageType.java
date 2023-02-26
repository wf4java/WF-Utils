package wf.utils.bukkit.config.language;

public enum LanguageType {

    GENERAL,
    PERSONAL,
    UNKNOWN;

    public static LanguageType getLanguageType(Language language){
        if(language instanceof GeneralLanguage) return GENERAL;
        if(language instanceof PlayerLanguage) return PERSONAL;
        return UNKNOWN;
    }

}
