package wf.utils.java.file.utils;


import org.bukkit.plugin.java.JavaPlugin;
import wf.utils.java.file.yamlconfiguration.configuration.Config;
import wf.utils.java.file.yamlconfiguration.file.YamlConfiguration;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResourceUtils {

    public static void copyFromResource(String resourcePath, String toPath, boolean replace){
        try {
            File file = new File(toPath);
            if (replace || !file.exists()) {
                InputStream link = (Config.class.getResourceAsStream(resourcePath));
                Files.copy(link, file.getAbsoluteFile().toPath());
            }
        }catch (IOException e) { e.printStackTrace(); }
    }
//    public static List<String> getResourceFiles(String path) {
//        List<String> filenames = new ArrayList<>();
//
//        try(InputStream in = getResourceAsStream(path);
//            BufferedReader br = new BufferedReader(new InputStreamReader(in)))
//        {
//            String resource;
//
//            while ((resource = br.readLine()) != null) {
//                filenames.add(resource);
//            }
//        } catch (Exception ignored) {}
//
//        return filenames;
//    }

    public static List<String> getResourceFiles(String path){
        return Arrays.asList(new File(getContextClassLoader().getResource(path).getFile()).list());
    }

    public static List<String> getResourceFiles(Class c, String path){
        return Arrays.asList(new File(c.getResource(path).getFile()).list());
    }

    private static InputStream getResourceAsStream(String resource) {
        final InputStream in = getContextClassLoader().getResourceAsStream(resource);
        return in == null ? ResourceUtils.class.getResourceAsStream(resource) : in;
    }

    private static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

}
