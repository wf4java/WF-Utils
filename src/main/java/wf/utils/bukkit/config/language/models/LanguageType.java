package wf.utils.bukkit.config.language.models;

import wf.utils.bukkit.config.language.GeneralLanguage;
import wf.utils.bukkit.config.language.PersonalLanguage;

public enum LanguageType {

    GENERAL,
    PERSONAL,
    UNKNOWN;

    public static LanguageType getLanguageType(Language language){
        if(language instanceof GeneralLanguage) return GENERAL;
        if(language instanceof PersonalLanguage) return PERSONAL;
        return UNKNOWN;
    }

}
