package util;

import data.enums.Language;
import org.apache.http.util.LangUtils;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleUtils {

    private LocaleUtils() {
    }

    public static String getMessageByKey(String key, Language language) {
        Locale locale = new Locale(language.getLanguageCode(), language.getCountryCode());
        ResourceBundle bundle = ResourceBundle.getBundle("language", locale);
        return bundle.getString(key);
    }
}
