package by.bsuir.entity.enums;

public enum LANGUAGES {

    RUSSIAN("ru"),
    ENGLISH("en");

    private String message;

    LANGUAGES(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
