package io.vladprotchenko.ensstartercore.security.dto;

import io.vladprotchenko.ensstartercore.security.model.constant.UserRole;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminDetailsDto {
    UUID accountId;
    String email;
    UserRole role;
    boolean isSuperAdmin;
}
