package io.vladprotchenko.ensstartercore.security.filter;

import io.vladprotchenko.ensstartercore.exception.EnsServiceException;
import io.vladprotchenko.ensstartercore.security.model.UserDetailsImpl;
import io.vladprotchenko.ensstartercore.security.service.JwtTokenValidator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    private static final int BEARER_PREFIX_LENGTH = 7;

    private final JwtTokenValidator jwtTokenValidator;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            log.debug("AuthTokenFilter started for: {}", request.getRequestURI());
            String jwt = parseJwt(request);

            if (jwt == null) {
                filterChain.doFilter(request, response);
                return;
            }

            if (jwtTokenValidator.isValid(jwt)) {
                authenticateUser(jwt, request);
                filterChain.doFilter(request, response);
                return;
            }

        } catch (EnsServiceException e) {
            log.error("Cannot set user authentication: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (Exception e) {
            log.error("Something went wrong: {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    private void authenticateUser(String jwt, HttpServletRequest request) {
        String username = jwtTokenValidator.getUsernameFromToken(jwt);
        String firstName = jwtTokenValidator.getFirstNameFromToken(jwt);
        UUID userId = UUID.fromString(jwtTokenValidator.getUserIdFromToken(jwt));
        Boolean isSuperAdmin = jwtTokenValidator.isSuperAdmin(jwt);
        Collection<? extends GrantedAuthority> authorities = jwtTokenValidator.getAuthorityFromToken(jwt);

        UserDetailsImpl userDetails =
                UserDetailsImpl.builder()
                        .email(username)
                        .firstName(firstName)
                        .accountId(userId)
                        .authorities(authorities)
                        .isSuperAdmin(isSuperAdmin)
                        .build();

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, jwt, authorities);

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.debug("Set authentication for user: {}", userDetails.getEmail());
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(AUTHORIZATION);
        return headerAuth != null && headerAuth.startsWith(BEARER)
                ? headerAuth.substring(BEARER_PREFIX_LENGTH)
                : null;
    }
}
