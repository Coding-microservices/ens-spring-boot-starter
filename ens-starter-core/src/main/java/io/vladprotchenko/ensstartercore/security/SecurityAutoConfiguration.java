package io.vladprotchenko.ensstartercore.security;

import io.vladprotchenko.ensstartercore.security.filter.AuthEntryPointJwt;
import io.vladprotchenko.ensstartercore.security.filter.CustomAccessDeniedHandler;
import io.vladprotchenko.ensstartercore.security.filter.RoleSecurityExpression;
import io.vladprotchenko.ensstartercore.security.service.AuthenticationFacade;
import io.vladprotchenko.ensstartercore.security.service.JwtTokenValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SecurityAutoConfiguration {

    @Bean
    public JwtTokenValidator jwtTokenValidator(
        @Value("${app.jwtSecret}") String jwtSecret) {
        return new JwtTokenValidator(jwtSecret);
    }

    @Bean
    public AuthEntryPointJwt authEntryPointJwt() {
        return new AuthEntryPointJwt();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean(name = "roleSecurity")
    public RoleSecurityExpression roleSecurityExpression() {
        return new RoleSecurityExpression();
    }

    @Bean
    public AuthenticationFacade authenticationFacade() {
        return new AuthenticationFacade();
    }

}
