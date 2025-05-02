package io.vladprotchenko.ensstartercore.security.service;

import io.vladprotchenko.ensstartercore.exception.custom.AuthorizationException;
import io.vladprotchenko.ensstartercore.security.dto.AdminDetailsDto;
import io.vladprotchenko.ensstartercore.security.model.UserDetailsImpl;
import io.vladprotchenko.ensstartercore.security.model.constant.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.UUID;

import static io.vladprotchenko.ensstartercore.security.model.constant.JwtConstants.ROLE_LENGTH;
import static io.vladprotchenko.ensstartercore.security.filter.RoleSecurityExpression.ANONYMOUS_USER;

@Slf4j
public class AuthenticationFacade {

    private UserDetailsImpl validateAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (ANONYMOUS_USER.equals(principal.toString())) {
            log.error("Attempt to get user details by anonymous or unauthenticated user.");
            throw new AuthorizationException("User is not authenticated. Please log in.");
        }
        return (UserDetailsImpl) principal;
    }

    public String getUserEmailFromAuthentication() {
        UserDetailsImpl principal = validateAuthenticatedUser();
        return principal.getEmail();
    }

    public String getUserFirstNameFromAuthentication() {
        UserDetailsImpl principal = validateAuthenticatedUser();
        return principal.getFirstName();
    }
    public UUID getAccountIdFromAuthentication() {
        UserDetailsImpl principal = validateAuthenticatedUser();
        return principal.getAccountId();
    }

    public AdminDetailsDto getAdminDetailsFromAuthentication() {
        UserDetailsImpl principal = validateAuthenticatedUser();
        List<UserRole> roles = principal.getAuthorities().stream()
                .map(authority ->
                        UserRole.valueOf(authority.getAuthority().substring(ROLE_LENGTH)))
                .toList();
        UserRole roleType = null;
        if (!roles.isEmpty()) {
            roleType = roles.getFirst();
        }
        return new AdminDetailsDto(principal.getAccountId(),
                principal.getEmail(), roleType, principal.getIsSuperAdmin());
    }
}
