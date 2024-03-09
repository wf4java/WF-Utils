package wf.utils.java.file.utils;




import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class ResourceUtils {

    public static void copyFromResource(String resourcePath, String toPath, boolean replace){
        try {
            File file = new File(toPath);
            if (replace || !file.exists()) {
                InputStream link = (ResourceUtils.class.getResourceAsStream(resourcePath));
                Files.copy(link, file.getAbsoluteFile().toPath());
            }
        }catch (IOException e) { e.printStackTrace(); }
    }

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
