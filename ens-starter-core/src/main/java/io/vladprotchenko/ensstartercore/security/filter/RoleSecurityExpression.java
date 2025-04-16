package io.vladprotchenko.ensstartercore.security.filter;

import io.vladprotchenko.ensstartercore.exception.custom.AuthorizationException;
import io.vladprotchenko.ensstartercore.security.model.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

@Slf4j
public class RoleSecurityExpression {

    public static final String ANONYMOUS_USER = "anonymousUser";

    public boolean isSuperAdmin(Authentication authentication) {
        if (authentication == null || ANONYMOUS_USER.equals(authentication.getPrincipal().toString())) {
            log.error("Attempt to access Super Admin functionalities by unauthenticated user");
            throw new AuthorizationException("User is not authenticated. Please log in");
        }

        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        if (Boolean.TRUE.equals(user.getIsSuperAdmin())) {
            return true;
        }

        log.warn("User {} attempted to access Super Admin functionalities without permission", user.getEmail());
        return false;
    }
}
