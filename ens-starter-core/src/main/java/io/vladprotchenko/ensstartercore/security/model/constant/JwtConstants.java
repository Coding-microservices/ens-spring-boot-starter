package io.vladprotchenko.ensstartercore.security.model.constant;

public class JwtConstants {

    public static final String CLAIM_ACCOUNT_ID = "accountId";
    public static final String CLAIM_FIRST_NAME = "firstName";
    public static final String IS_SUPER_ADMIN_CLAIM = "isSuperAdmin";
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String CLAIM_ROLE = "role";
    public static final int ROLE_LENGTH = 5;

    private JwtConstants() {
        throw new IllegalStateException("Utility class");
    }

}
