package data.enums;

public enum Language {
    EN("en", "EN"),
    RU("ru", "RU"),
    DEFAULT("en", "EN");

    private final String languageCode;
    private final String countryCode;

    Language(String languageCode, String countryCode) {
        this.languageCode = languageCode;
        this.countryCode = countryCode;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public String getCountryCode() {
        return countryCode;
    }
}
