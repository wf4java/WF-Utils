package wf.utils.java.file.yamlconfiguration.configuration;

public class ConfigBuilder {

    private String path;
    private String resourcePath;
    private ConfigDefaultValue[] defaultValues;
    private boolean autoSave;
    private long autoSaveTime;

    public ConfigBuilder(String path, String resourcePath, ConfigDefaultValue[] defaultValues, boolean autoSave, long autoSaveTime) {
        this.path = path;
        this.resourcePath = resourcePath;
        this.defaultValues = defaultValues;
        this.autoSave = autoSave;
        this.autoSaveTime = autoSaveTime;
    }

    public ConfigBuilder setPath(String path) {
        this.path = path;
        return this;
    }

    public ConfigBuilder setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
        return this;
    }

    public ConfigBuilder setDefaultValues(ConfigDefaultValue[] defaultValues) {
        this.defaultValues = defaultValues;
        return this;
    }

    public ConfigBuilder setAutoSave(boolean autoSave) {
        this.autoSave = autoSave;
        return this;
    }

    public ConfigBuilder setAutoSaveTime(long autoSaveTime) {
        this.autoSaveTime = autoSaveTime;
        return this;
    }

    public Config Build(){
        return null;
        //return new Config(path, resourcePath, defaultValues, autoSave, autoSaveTime);
    }
}
