package ar.edu.itba.paw.models;

public enum UserRole {
    USER_ROLE("ROLE_USER"),
    DRIVER_ROLE("ROLE_DRIVER"),
    ADMIN_ROLE("ROLE_ADMIN"),
    USER("USER"),
    DRIVER("DRIVER"),
    ADMIN("ADMIN");

    private final String text;

    UserRole(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
