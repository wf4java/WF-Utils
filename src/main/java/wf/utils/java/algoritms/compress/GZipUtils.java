package wf.utils.java.algoritms.compress;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZipUtils {


    public static byte[] decompress(byte[] bytes) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream)) {

            byte[] buffer = new byte[1024];
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int length;

            while ((length = gzipInputStream.read(buffer)) != -1) { outputStream.write(buffer, 0, length); }
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static byte[] compress(byte[] bytes) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
            gzipOutputStream.write(bytes);
            gzipOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static String toString(byte[] bytes, boolean compressed) {
        if (!compressed)
            return new String(bytes, StandardCharsets.UTF_8);

        return new String(decompress(bytes), StandardCharsets.UTF_8);
    }

    public static byte[] toBytes(String text, boolean compressed) {
        if (!compressed)
            return text.getBytes(StandardCharsets.UTF_8);

        return compress(text.getBytes(StandardCharsets.UTF_8));
    }


}
