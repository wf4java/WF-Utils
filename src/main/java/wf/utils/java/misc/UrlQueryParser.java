package wf.utils.java.misc;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class UrlQueryParser {


    public Map<String, String> parseQueryString(String query) {
        try {
            Map<String, String> params = new HashMap<>();

            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=", 2);
                if (keyValue.length == 2) {
                    String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8.name());
                    String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8.name());
                    params.put(key, value);
                } else if (keyValue.length == 1) {
                    String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8.name());
                    params.put(key, "");
                }
            }

            return params;
        }
        catch (UnsupportedEncodingException e) { throw new RuntimeException(e); }
    }

    public String buildQueryString(Map<String, String> params) {
        try {
            StringBuilder query = new StringBuilder();

            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.name());
                String value = URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.name());

                if (query.length() != 0)
                    query.append("&");

                query.append(key).append("=").append(value);
            }

            return query.toString();
        }
        catch (UnsupportedEncodingException e) { throw new RuntimeException(e); }
    }


}
