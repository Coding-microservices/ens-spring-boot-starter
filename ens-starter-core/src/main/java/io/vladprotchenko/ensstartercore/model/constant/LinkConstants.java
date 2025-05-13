package io.vladprotchenko.ensstartercore.model.constant;

public final class LinkConstants {

    public static final String VERIFY_OTP_LOGIN = "http://localhost:8085/api/v1/auth/login/verify-otp";
    public static final String RESET_PASSWORD_CONFIRM = "http://localhost:8085/api/v1/account/password/reset-confirm";
    public static final String RESET_PASSWORD_EMAIL = "http://localhost:8085/api/v1/account/password/reset?%s";
    public static final String LOGIN = "http://localhost:8085/api/v1/auth/login";
    public static final String CHANGE_EMAIL = "http://localhost:8085/api/v1/account/email-confirm";

    private LinkConstants() {
        throw new IllegalStateException("Utility class");
    }
}
