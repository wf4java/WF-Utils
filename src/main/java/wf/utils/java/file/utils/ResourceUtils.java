package wf.utils.java.file.utils;




import wf.utils.java.data.string.StringUtils;
import wf.utils.java.misc.Assert;
import wf.utils.java.misc.annotation.Nullable;
import wf.utils.java.object.ClassUtils;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class ResourceUtils {




    public static boolean isUrl(@Nullable String resourceLocation) {
        if (resourceLocation == null) {
            return false;
        } else if (resourceLocation.startsWith("classpath:")) {
            return true;
        } else {
            try {
                toURL(resourceLocation);
                return true;
            } catch (MalformedURLException var2) {
                return false;
            }
        }
    }

    public static URL getURL(String resourceLocation) throws FileNotFoundException {
        Assert.notNull(resourceLocation, "Resource location must not be null");
        if (resourceLocation.startsWith("classpath:")) {
            String path = resourceLocation.substring("classpath:".length());
            ClassLoader cl = ClassUtils.getDefaultClassLoader();
            URL url = cl != null ? cl.getResource(path) : ClassLoader.getSystemResource(path);
            if (url == null) {
                String description = "class path resource [" + path + "]";
                throw new FileNotFoundException(description + " cannot be resolved to URL because it does not exist");
            } else {
                return url;
            }
        } else {
            try {
                return toURL(resourceLocation);
            } catch (MalformedURLException var6) {
                try {
                    return (new File(resourceLocation)).toURI().toURL();
                } catch (MalformedURLException var5) {
                    throw new FileNotFoundException("Resource location [" + resourceLocation + "] is neither a URL not a well-formed file path");
                }
            }
        }
    }

    public static File getFile(String resourceLocation) throws FileNotFoundException {
        Assert.notNull(resourceLocation, "Resource location must not be null");
        if (resourceLocation.startsWith("classpath:")) {
            String path = resourceLocation.substring("classpath:".length());
            String description = "class path resource [" + path + "]";
            ClassLoader cl = ClassUtils.getDefaultClassLoader();
            URL url = cl != null ? cl.getResource(path) : ClassLoader.getSystemResource(path);
            if (url == null) {
                throw new FileNotFoundException(description + " cannot be resolved to absolute file path because it does not exist");
            } else {
                return getFile(url, description);
            }
        } else {
            try {
                return getFile(toURL(resourceLocation));
            } catch (MalformedURLException var5) {
                return new File(resourceLocation);
            }
        }
    }

    public static File getFile(URL resourceUrl) throws FileNotFoundException {
        return getFile(resourceUrl, "URL");
    }

    public static File getFile(URL resourceUrl, String description) throws FileNotFoundException {
        Assert.notNull(resourceUrl, "Resource URL must not be null");
        if (!"file".equals(resourceUrl.getProtocol())) {
            throw new FileNotFoundException(description + " cannot be resolved to absolute file path because it does not reside in the file system: " + resourceUrl);
        } else {
            try {
                return new File(toURI(resourceUrl).getSchemeSpecificPart());
            } catch (URISyntaxException var3) {
                return new File(resourceUrl.getFile());
            }
        }
    }

    public static File getFile(URI resourceUri) throws FileNotFoundException {
        return getFile(resourceUri, "URI");
    }

    public static File getFile(URI resourceUri, String description) throws FileNotFoundException {
        Assert.notNull(resourceUri, "Resource URI must not be null");
        if (!"file".equals(resourceUri.getScheme())) {
            throw new FileNotFoundException(description + " cannot be resolved to absolute file path because it does not reside in the file system: " + resourceUri);
        } else {
            return new File(resourceUri.getSchemeSpecificPart());
        }
    }

    public static boolean isFileURL(URL url) {
        String protocol = url.getProtocol();
        return "file".equals(protocol) || "vfsfile".equals(protocol) || "vfs".equals(protocol);
    }

    public static boolean isJarURL(URL url) {
        String protocol = url.getProtocol();
        return "jar".equals(protocol) || "war".equals(protocol) || "zip".equals(protocol) || "vfszip".equals(protocol) || "wsjar".equals(protocol);
    }

    public static boolean isJarFileURL(URL url) {
        return "file".equals(url.getProtocol()) && url.getPath().toLowerCase().endsWith(".jar");
    }

    public static URL extractJarFileURL(URL jarUrl) throws MalformedURLException {
        String urlFile = jarUrl.getFile();
        int separatorIndex = urlFile.indexOf("!/");
        if (separatorIndex != -1) {
            String jarFile = urlFile.substring(0, separatorIndex);

            try {
                return toURL(jarFile);
            } catch (MalformedURLException var5) {
                if (!jarFile.startsWith("/")) {
                    jarFile = "/" + jarFile;
                }

                return toURL("file:" + jarFile);
            }
        } else {
            return jarUrl;
        }
    }

    public static URL extractArchiveURL(URL jarUrl) throws MalformedURLException {
        String urlFile = jarUrl.getFile();
        int endIndex = urlFile.indexOf("*/");
        if (endIndex != -1) {
            String warFile = urlFile.substring(0, endIndex);
            if ("war".equals(jarUrl.getProtocol())) {
                return toURL(warFile);
            }

            int startIndex = warFile.indexOf("war:");
            if (startIndex != -1) {
                return toURL(warFile.substring(startIndex + "war:".length()));
            }
        }

        return extractJarFileURL(jarUrl);
    }

    public static URI toURI(URL url) throws URISyntaxException {
        return toURI(url.toString());
    }

    public static URI toURI(String location) throws URISyntaxException {
        return new URI(StringUtils.replace(location, " ", "%20"));
    }

    public static URL toURL(String location) throws MalformedURLException {
        return new URL(location);
    }

    public static URL toRelativeURL(URL root, String relativePath) throws MalformedURLException {
        relativePath = StringUtils.replace(relativePath, "#", "%23");
        return new URL(root, relativePath);
    }

    public static void useCachesIfNecessary(URLConnection con) {
        con.setUseCaches(con.getClass().getSimpleName().startsWith("JNLP"));
    }


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
