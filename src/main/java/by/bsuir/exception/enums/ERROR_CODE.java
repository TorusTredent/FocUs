package by.bsuir.exception.enums;

public enum ERROR_CODE {

    INVALID_REQUEST("Invalid request"),
    NOT_ENOUGH_RIGHTS("Not enough right"),
    PATH_NOT_FOUND("Path not found"),
    USER_IS_ALREADY_EXIST("User is already exist"),
    USER_NOT_FOUND("User not found"),
    CATEGORY_NOT_FOUND("Category not found"),
    TASK_NOT_FOUND ("Task not found"),
    PACK_NOT_FOUND("Pack not found"),
    RANK_NOT_FOUND("Rank not found");

    private String message;

    ERROR_CODE(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
