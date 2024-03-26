package wf.utils.java.data.string;


import wf.utils.java.data.list.CollectionUtils;
import wf.utils.java.misc.Assert;
import wf.utils.java.misc.annotation.Nullable;
import wf.utils.java.object.ObjectUtils;


import java.util.*;
import java.util.stream.Collectors;

public class StringUtils {


    private static final String[] EMPTY_STRING_ARRAY = new String[0];


    public StringUtils() {
    }

    /** @deprecated */
    @Deprecated
    public static boolean isEmpty(@Nullable Object str) {
        return str == null || "".equals(str);
    }

    public static boolean hasLength(@Nullable CharSequence str) {
        return str != null && str.length() > 0;
    }

    public static boolean hasLength(@Nullable String str) {
        return str != null && !str.isEmpty();
    }

    public static boolean hasText(@Nullable CharSequence str) {
        if (str == null) {
            return false;
        } else {
            int strLen = str.length();
            if (strLen == 0) {
                return false;
            } else {
                for(int i = 0; i < strLen; ++i) {
                    if (!Character.isWhitespace(str.charAt(i))) {
                        return true;
                    }
                }

                return false;
            }
        }
    }

    public static boolean hasText(@Nullable String str) {
        return str != null && !str.isEmpty();
    }

    public static boolean containsWhitespace(@Nullable CharSequence str) {
        if (!hasLength(str)) {
            return false;
        } else {
            int strLen = str.length();

            for(int i = 0; i < strLen; ++i) {
                if (Character.isWhitespace(str.charAt(i))) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean containsWhitespace(@Nullable String str) {
        return containsWhitespace((CharSequence)str);
    }

    /** @deprecated */


    public static CharSequence trimAllWhitespace(CharSequence str) {
        if (!hasLength(str)) {
            return str;
        } else {
            int len = str.length();
            StringBuilder sb = new StringBuilder(str.length());

            for(int i = 0; i < len; ++i) {
                char c = str.charAt(i);
                if (!Character.isWhitespace(c)) {
                    sb.append(c);
                }
            }

            return sb;
        }
    }

    public static String trimAllWhitespace(String str) {
        return !hasLength(str) ? str : trimAllWhitespace((CharSequence)str).toString();
    }


    public static String trimLeadingCharacter(String str, char leadingCharacter) {
        if (!hasLength(str)) {
            return str;
        } else {
            int beginIdx;
            for(beginIdx = 0; beginIdx < str.length() && leadingCharacter == str.charAt(beginIdx); ++beginIdx) {
            }

            return str.substring(beginIdx);
        }
    }

    public static String trimTrailingCharacter(String str, char trailingCharacter) {
        if (!hasLength(str)) {
            return str;
        } else {
            int endIdx;
            for(endIdx = str.length() - 1; endIdx >= 0 && trailingCharacter == str.charAt(endIdx); --endIdx) {
            }

            return str.substring(0, endIdx + 1);
        }
    }

    public static boolean matchesCharacter(@Nullable String str, char singleCharacter) {
        return str != null && str.length() == 1 && str.charAt(0) == singleCharacter;
    }

    public static boolean startsWithIgnoreCase(@Nullable String str, @Nullable String prefix) {
        return str != null && prefix != null && str.length() >= prefix.length() && str.regionMatches(true, 0, prefix, 0, prefix.length());
    }

    public static boolean endsWithIgnoreCase(@Nullable String str, @Nullable String suffix) {
        return str != null && suffix != null && str.length() >= suffix.length() && str.regionMatches(true, str.length() - suffix.length(), suffix, 0, suffix.length());
    }

    public static boolean substringMatch(CharSequence str, int index, CharSequence substring) {
        if (index + substring.length() > str.length()) {
            return false;
        } else {
            for(int i = 0; i < substring.length(); ++i) {
                if (str.charAt(index + i) != substring.charAt(i)) {
                    return false;
                }
            }

            return true;
        }
    }

    public static int countOccurrencesOf(String str, String sub) {
        if (hasLength(str) && hasLength(sub)) {
            int count = 0;

            int idx;
            for(int pos = 0; (idx = str.indexOf(sub, pos)) != -1; pos = idx + sub.length()) {
                ++count;
            }

            return count;
        } else {
            return 0;
        }
    }

    public static String replace(String inString, String oldPattern, @Nullable String newPattern) {
        if (hasLength(inString) && hasLength(oldPattern) && newPattern != null) {
            int index = inString.indexOf(oldPattern);
            if (index == -1) {
                return inString;
            } else {
                int capacity = inString.length();
                if (newPattern.length() > oldPattern.length()) {
                    capacity += 16;
                }

                StringBuilder sb = new StringBuilder(capacity);
                int pos = 0;

                for(int patLen = oldPattern.length(); index >= 0; index = inString.indexOf(oldPattern, pos)) {
                    sb.append(inString, pos, index);
                    sb.append(newPattern);
                    pos = index + patLen;
                }

                sb.append(inString, pos, inString.length());
                return sb.toString();
            }
        } else {
            return inString;
        }
    }

    public static String delete(String inString, String pattern) {
        return replace(inString, pattern, "");
    }

    public static String deleteAny(String inString, @Nullable String charsToDelete) {
        if (hasLength(inString) && hasLength(charsToDelete)) {
            int lastCharIndex = 0;
            char[] result = new char[inString.length()];

            for(int i = 0; i < inString.length(); ++i) {
                char c = inString.charAt(i);
                if (charsToDelete.indexOf(c) == -1) {
                    result[lastCharIndex++] = c;
                }
            }

            if (lastCharIndex == inString.length()) {
                return inString;
            } else {
                return new String(result, 0, lastCharIndex);
            }
        } else {
            return inString;
        }
    }

    @Nullable
    public static String quote(@Nullable String str) {
        return str != null ? "'" + str + "'" : null;
    }

    @Nullable
    public static Object quoteIfString(@Nullable Object obj) {
        Object var10000;
        if (obj instanceof String) {
            var10000 = quote((String) obj);
        } else {
            var10000 = obj;
        }

        return var10000;
    }

    public static String unqualify(String qualifiedName) {
        return unqualify(qualifiedName, '.');
    }

    public static String unqualify(String qualifiedName, char separator) {
        return qualifiedName.substring(qualifiedName.lastIndexOf(separator) + 1);
    }

    public static String capitalize(String str) {
        return changeFirstCharacterCase(str, true);
    }

    public static String uncapitalize(String str) {
        return changeFirstCharacterCase(str, false);
    }

    public static String uncapitalizeAsProperty(String str) {
        return hasLength(str) && (str.length() <= 1 || !Character.isUpperCase(str.charAt(0)) || !Character.isUpperCase(str.charAt(1))) ? changeFirstCharacterCase(str, false) : str;
    }

    private static String changeFirstCharacterCase(String str, boolean capitalize) {
        if (!hasLength(str)) {
            return str;
        } else {
            char baseChar = str.charAt(0);
            char updatedChar;
            if (capitalize) {
                updatedChar = Character.toUpperCase(baseChar);
            } else {
                updatedChar = Character.toLowerCase(baseChar);
            }

            if (baseChar == updatedChar) {
                return str;
            } else {
                char[] chars = str.toCharArray();
                chars[0] = updatedChar;
                return new String(chars);
            }
        }
    }

    @Nullable
    public static String getFilename(@Nullable String path) {
        if (path == null) {
            return null;
        } else {
            int separatorIndex = path.lastIndexOf(47);
            return separatorIndex != -1 ? path.substring(separatorIndex + 1) : path;
        }
    }

    @Nullable
    public static String getFilenameExtension(@Nullable String path) {
        if (path == null) {
            return null;
        } else {
            int extIndex = path.lastIndexOf(46);
            if (extIndex == -1) {
                return null;
            } else {
                int folderIndex = path.lastIndexOf(47);
                return folderIndex > extIndex ? null : path.substring(extIndex + 1);
            }
        }
    }

    public static String stripFilenameExtension(String path) {
        int extIndex = path.lastIndexOf(46);
        if (extIndex == -1) {
            return path;
        } else {
            int folderIndex = path.lastIndexOf(47);
            return folderIndex > extIndex ? path : path.substring(0, extIndex);
        }
    }

    public static String applyRelativePath(String path, String relativePath) {
        int separatorIndex = path.lastIndexOf(47);
        if (separatorIndex != -1) {
            String newPath = path.substring(0, separatorIndex);
            if (!relativePath.startsWith("/")) {
                newPath = newPath + "/";
            }

            return newPath + relativePath;
        } else {
            return relativePath;
        }
    }

    public static String cleanPath(String path) {
        if (!hasLength(path)) {
            return path;
        } else {
            String normalizedPath = replace(path, "\\", "/");
            String pathToUse = normalizedPath;
            if (normalizedPath.indexOf(46) == -1) {
                return normalizedPath;
            } else {
                int prefixIndex = normalizedPath.indexOf(58);
                String prefix = "";
                if (prefixIndex != -1) {
                    prefix = normalizedPath.substring(0, prefixIndex + 1);
                    if (prefix.contains("/")) {
                        prefix = "";
                    } else {
                        pathToUse = normalizedPath.substring(prefixIndex + 1);
                    }
                }

                if (pathToUse.startsWith("/")) {
                    prefix = prefix + "/";
                    pathToUse = pathToUse.substring(1);
                }

                String[] pathArray = delimitedListToStringArray(pathToUse, "/");
                Deque<String> pathElements = new ArrayDeque(pathArray.length);
                int tops = 0;

                int i;
                for(i = pathArray.length - 1; i >= 0; --i) {
                    String element = pathArray[i];
                    if (!".".equals(element)) {
                        if ("..".equals(element)) {
                            ++tops;
                        } else if (tops > 0) {
                            --tops;
                        } else {
                            pathElements.addFirst(element);
                        }
                    }
                }

                if (pathArray.length == pathElements.size()) {
                    return normalizedPath;
                } else {
                    for(i = 0; i < tops; ++i) {
                        pathElements.addFirst("..");
                    }

                    if (pathElements.size() == 1 && ((String)pathElements.getLast()).isEmpty() && !prefix.endsWith("/")) {
                        pathElements.addFirst(".");
                    }

                    String joined = collectionToDelimitedString(pathElements, "/");
                    return prefix.isEmpty() ? joined : prefix + joined;
                }
            }
        }
    }

    public static boolean pathEquals(String path1, String path2) {
        return cleanPath(path1).equals(cleanPath(path2));
    }



    @Nullable
    public static Locale parseLocale(String localeValue) {
        if (!localeValue.contains("_") && !localeValue.contains(" ")) {
            validateLocalePart(localeValue);
            Locale resolved = Locale.forLanguageTag(localeValue);
            if (!resolved.getLanguage().isEmpty()) {
                return resolved;
            }
        }

        return parseLocaleString(localeValue);
    }

    @Nullable
    public static Locale parseLocaleString(String localeString) {
        if (localeString.equals("")) {
            return null;
        } else {
            String delimiter = "_";
            if (!localeString.contains("_") && localeString.contains(" ")) {
                delimiter = " ";
            }

            String[] tokens = localeString.split(delimiter, -1);
            String language;
            if (tokens.length == 1) {
                language = tokens[0];
                validateLocalePart(language);
                return new Locale(language);
            } else {
                String country;
                if (tokens.length == 2) {
                    language = tokens[0];
                    validateLocalePart(language);
                    country = tokens[1];
                    validateLocalePart(country);
                    return new Locale(language, country);
                } else if (tokens.length > 2) {
                    language = tokens[0];
                    validateLocalePart(language);
                    country = tokens[1];
                    validateLocalePart(country);
                    String variant = (String)Arrays.stream(tokens).skip(2L).collect(Collectors.joining(delimiter));
                    return new Locale(language, country, variant);
                } else {
                    throw new IllegalArgumentException("Invalid locale format: '" + localeString + "'");
                }
            }
        }
    }

    private static void validateLocalePart(String localePart) {
        for(int i = 0; i < localePart.length(); ++i) {
            char ch = localePart.charAt(i);
            if (ch != ' ' && ch != '_' && ch != '-' && ch != '#' && !Character.isLetterOrDigit(ch)) {
                throw new IllegalArgumentException("Locale part \"" + localePart + "\" contains invalid characters");
            }
        }

    }

    public static TimeZone parseTimeZoneString(String timeZoneString) {
        TimeZone timeZone = TimeZone.getTimeZone(timeZoneString);
        if ("GMT".equals(timeZone.getID()) && !timeZoneString.startsWith("GMT")) {
            throw new IllegalArgumentException("Invalid time zone specification '" + timeZoneString + "'");
        } else {
            return timeZone;
        }
    }

    public static String[] toStringArray(@Nullable Collection<String> collection) {
        return !CollectionUtils.isEmpty(collection) ? (String[])collection.toArray(EMPTY_STRING_ARRAY) : EMPTY_STRING_ARRAY;
    }

    public static String[] toStringArray(@Nullable Enumeration<String> enumeration) {
        return enumeration != null ? toStringArray((Collection)Collections.list(enumeration)) : EMPTY_STRING_ARRAY;
    }

    public static String[] addStringToArray(@Nullable String[] array, String str) {
        if (ObjectUtils.isEmpty(array)) {
            return new String[]{str};
        } else {
            String[] newArr = new String[array.length + 1];
            System.arraycopy(array, 0, newArr, 0, array.length);
            newArr[array.length] = str;
            return newArr;
        }
    }

    @Nullable
    public static String[] concatenateStringArrays(@Nullable String[] array1, @Nullable String[] array2) {
        if (ObjectUtils.isEmpty(array1)) {
            return array2;
        } else if (ObjectUtils.isEmpty(array2)) {
            return array1;
        } else {
            String[] newArr = new String[array1.length + array2.length];
            System.arraycopy(array1, 0, newArr, 0, array1.length);
            System.arraycopy(array2, 0, newArr, array1.length, array2.length);
            return newArr;
        }
    }

    public static String[] sortStringArray(String[] array) {
        if (ObjectUtils.isEmpty(array)) {
            return array;
        } else {
            Arrays.sort(array);
            return array;
        }
    }

    public static String[] trimArrayElements(String[] array) {
        if (ObjectUtils.isEmpty(array)) {
            return array;
        } else {
            String[] result = new String[array.length];

            for(int i = 0; i < array.length; ++i) {
                String element = array[i];
                result[i] = element != null ? element.trim() : null;
            }

            return result;
        }
    }

    public static String[] removeDuplicateStrings(String[] array) {
        if (ObjectUtils.isEmpty(array)) {
            return array;
        } else {
            Set<String> set = new LinkedHashSet(Arrays.asList(array));
            return toStringArray((Collection)set);
        }
    }

    @Nullable
    public static String[] split(@Nullable String toSplit, @Nullable String delimiter) {
        if (hasLength(toSplit) && hasLength(delimiter)) {
            int offset = toSplit.indexOf(delimiter);
            if (offset < 0) {
                return null;
            } else {
                String beforeDelimiter = toSplit.substring(0, offset);
                String afterDelimiter = toSplit.substring(offset + delimiter.length());
                return new String[]{beforeDelimiter, afterDelimiter};
            }
        } else {
            return null;
        }
    }

    @Nullable
    public static Properties splitArrayElementsIntoProperties(String[] array, String delimiter) {
        return splitArrayElementsIntoProperties(array, delimiter, (String)null);
    }

    @Nullable
    public static Properties splitArrayElementsIntoProperties(String[] array, String delimiter, @Nullable String charsToDelete) {
        if (ObjectUtils.isEmpty(array)) {
            return null;
        } else {
            Properties result = new Properties();
            String[] var4 = array;
            int var5 = array.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String element = var4[var6];
                if (charsToDelete != null) {
                    element = deleteAny(element, charsToDelete);
                }

                String[] splittedElement = split(element, delimiter);
                if (splittedElement != null) {
                    result.setProperty(splittedElement[0].trim(), splittedElement[1].trim());
                }
            }

            return result;
        }
    }

    public static String[] tokenizeToStringArray(@Nullable String str, String delimiters) {
        return tokenizeToStringArray(str, delimiters, true, true);
    }

    public static String[] tokenizeToStringArray(@Nullable String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {
        if (str == null) {
            return EMPTY_STRING_ARRAY;
        } else {
            StringTokenizer st = new StringTokenizer(str, delimiters);
            List<String> tokens = new ArrayList();

            while(true) {
                String token;
                do {
                    if (!st.hasMoreTokens()) {
                        return toStringArray((Collection)tokens);
                    }

                    token = st.nextToken();
                    if (trimTokens) {
                        token = token.trim();
                    }
                } while(ignoreEmptyTokens && token.length() <= 0);

                tokens.add(token);
            }
        }
    }

    public static String[] delimitedListToStringArray(@Nullable String str, @Nullable String delimiter) {
        return delimitedListToStringArray(str, delimiter, (String)null);
    }

    public static String[] delimitedListToStringArray(@Nullable String str, @Nullable String delimiter, @Nullable String charsToDelete) {
        if (str == null) {
            return EMPTY_STRING_ARRAY;
        } else if (delimiter == null) {
            return new String[]{str};
        } else {
            List<String> result = new ArrayList();
            int pos;
            if (delimiter.isEmpty()) {
                for(pos = 0; pos < str.length(); ++pos) {
                    result.add(deleteAny(str.substring(pos, pos + 1), charsToDelete));
                }
            } else {
                int delPos;
                for(pos = 0; (delPos = str.indexOf(delimiter, pos)) != -1; pos = delPos + delimiter.length()) {
                    result.add(deleteAny(str.substring(pos, delPos), charsToDelete));
                }

                if (str.length() > 0 && pos <= str.length()) {
                    result.add(deleteAny(str.substring(pos), charsToDelete));
                }
            }

            return toStringArray((Collection)result);
        }
    }

    public static String[] commaDelimitedListToStringArray(@Nullable String str) {
        return delimitedListToStringArray(str, ",");
    }

    public static Set<String> commaDelimitedListToSet(@Nullable String str) {
        String[] tokens = commaDelimitedListToStringArray(str);
        return new LinkedHashSet(Arrays.asList(tokens));
    }

    public static String collectionToDelimitedString(@Nullable Collection<?> coll, String delim, String prefix, String suffix) {
        if (CollectionUtils.isEmpty(coll)) {
            return "";
        } else {
            int totalLength = coll.size() * (prefix.length() + suffix.length()) + (coll.size() - 1) * delim.length();

            Object element;
            for(Iterator var5 = coll.iterator(); var5.hasNext(); totalLength += String.valueOf(element).length()) {
                element = var5.next();
            }

            StringBuilder sb = new StringBuilder(totalLength);
            Iterator<?> it = coll.iterator();

            while(it.hasNext()) {
                sb.append(prefix).append(it.next()).append(suffix);
                if (it.hasNext()) {
                    sb.append(delim);
                }
            }

            return sb.toString();
        }
    }

    public static String collectionToDelimitedString(@Nullable Collection<?> coll, String delim) {
        return collectionToDelimitedString(coll, delim, "", "");
    }

    public static String collectionToCommaDelimitedString(@Nullable Collection<?> coll) {
        return collectionToDelimitedString(coll, ",");
    }

    public static String arrayToDelimitedString(@Nullable Object[] arr, String delim) {
        if (ObjectUtils.isEmpty(arr)) {
            return "";
        } else if (arr.length == 1) {
            return ObjectUtils.nullSafeToString(arr[0]);
        } else {
            StringJoiner sj = new StringJoiner(delim);
            Object[] var3 = arr;
            int var4 = arr.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Object elem = var3[var5];
                sj.add(String.valueOf(elem));
            }

            return sj.toString();
        }
    }

    public static String arrayToCommaDelimitedString(@Nullable Object[] arr) {
        return arrayToDelimitedString(arr, ",");
    }

    public static String truncate(CharSequence charSequence) {
        return truncate(charSequence, 100);
    }

    public static String truncate(CharSequence charSequence, int threshold) {
        Assert.isTrue(threshold > 0, () -> {
            return "Truncation threshold must be a positive number: " + threshold;
        });
        if (charSequence.length() > threshold) {
            CharSequence var10000 = charSequence.subSequence(0, threshold);
            return "" + var10000 + " (truncated)...";
        } else {
            return charSequence.toString();
        }
    }


    public static boolean isAllCharactersSame(String s) {
        for (int i = 1, length = s.length(); i < length; ++i) {
            if (s.charAt(i) != s.charAt(0)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAnagrams(String s1, String s2) {
        int l1 = s1.length();
        int l2 = s2.length();
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
        Map<Character, Integer> charAppearances = new HashMap<>();

        for (int i = 0; i < l1; i++) {
            char c = s1.charAt(i);
            int numOfAppearances = charAppearances.getOrDefault(c, 0);
            charAppearances.put(c, numOfAppearances + 1);
        }

        for (int i = 0; i < l2; i++) {
            char c = s2.charAt(i);
            if (!charAppearances.containsKey(c)) {
                return false;
            }
            charAppearances.put(c, charAppearances.get(c) - 1);
        }

        for (int cnt : charAppearances.values()) {
            if (cnt != 0) {
                return false;
            }
        }
        return true;
    }

    public static int calculateHammingDistance(String s1, String s2)
            throws Exception {
        if (s1.length() != s2.length()) {
            throw new Exception("String lengths must be equal");
        }

        int stringLength = s1.length();
        int counter = 0;

        for (int i = 0; i < stringLength; i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                counter++;
            }
        }
        return counter;
    }

    public static double stringSame(String str1, String str2){
        return (1 - ((double) stringDistance(str1, str2) / Math.max(str1.length(), str2.length())));
    }

    public static int stringDistance(String str1, String str2) {
        str1 = str1.toLowerCase();
        str2 = str2.toLowerCase();

        int m = str1.length();
        int n = str2.length();

        int[][] T = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            T[i][0] = i;
        }
        for (int j = 1; j <= n; j++) {
            T[0][j] = j;
        }

        int cost;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                cost = str1.charAt(i - 1) == str2.charAt(j - 1) ? 0: 1;
                T[i][j] = Integer.min(Integer.min(T[i - 1][j] + 1, T[i][j - 1] + 1),
                        T[i - 1][j - 1] + cost);
            }
        }

        return T[m][n];
    }

    public static double getSimilarity(String str1, String str2) {
        if (str1 == null || str2 == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
        double maxLength = Double.max(str1.length(), str2.length());
        if (maxLength > 0) {
            return (maxLength - stringSame(str1, str2)) / maxLength;
        }
        return 1.0;
    }

    public static String reverse(String str) {
        return new StringBuilder(str).reverse().toString();
    }

    public static boolean isValidParentheses(String s) {
        char[] stack = new char[s.length()];
        int head = 0;
        for(char c : s.toCharArray()) {
            switch(c) {
                case '{':
                case '[':
                case '(':
                    stack[head++] = c;
                    break;
                case '}':
                    if(head == 0 || stack[--head] != '{') return false;
                    break;
                case ')':
                    if(head == 0 || stack[--head] != '(') return false;
                    break;
                case ']':
                    if(head == 0 || stack[--head] != '[') return false;
                    break;
            }
        }
        return head == 0;
    }

}
