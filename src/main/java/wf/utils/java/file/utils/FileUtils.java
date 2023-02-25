package wf.utils.java.file.utils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;

public class FileUtils {

    private static int getFilesCount(File source){
        int count = 0;

        if (source.isDirectory()) {
            String files[] = source.list();
            for (String file : files) {
                count += getFilesCount(new File(source, file));
            }
        }else{
            count++;
        }
        return count;
    }

    private static void copyFolder(File source, File destination) {
        if (source.isDirectory()) {
            if (!destination.exists()) { destination.mkdirs(); }

            String files[] = source.list();

            for (String file : files) {
                File srcFile = new File(source, file);
                File destFile = new File(destination, file);

                copyFolder(srcFile, destFile);}
        }
        else {
            InputStream in = null;
            OutputStream out = null;

            try {
                in = new FileInputStream(source);
                out = new FileOutputStream(destination);

                byte[] buffer = new byte[1024];

                int length;
                while ((length = in.read(buffer)) > 0) { out.write(buffer, 0, length); }
            }
            catch (Exception e) {
                try { in.close(); }
                catch (IOException e1) { e1.printStackTrace(); }

                try { out.close(); }
                catch (IOException e1) { e1.printStackTrace(); }
            }
        }
    }

    public void copyFromJar(String source, final Path target) {
        URI resource = null;
        FileSystem fileSystem = null;
        try {
            resource = getClass().getResource("").toURI();
            fileSystem = FileSystems.newFileSystem(resource, Collections.<String, String>emptyMap());
        } catch (URISyntaxException | IOException e) {throw new RuntimeException(e);}



        final Path jarPath = fileSystem.getPath(source);

        try {
            Files.walkFileTree(jarPath, new SimpleFileVisitor<Path>() {

                private Path currentTarget;

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    currentTarget = target.resolve(jarPath.relativize(dir).toString());
                    Files.createDirectories(currentTarget);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.copy(file, target.resolve(jarPath.relativize(file).toString()), StandardCopyOption.REPLACE_EXISTING);
                    return FileVisitResult.CONTINUE;
                }

            });
        } catch (IOException e) {throw new RuntimeException(e);}
    }

}
