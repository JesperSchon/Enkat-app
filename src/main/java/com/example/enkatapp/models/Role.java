package com.example.enkatapp.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Role {

    ADMIN(
            Set.of(
                    Permission.ADMIN_READ,
                    Permission.ADMIN_CREATE,
                    Permission.ADMIN_DELETE,
                    Permission.ADMIN_UPDATE,
                    Permission.CREATOR_READ,
                    Permission.CREATOR_CREATE,
                    Permission.CREATOR_DELETE,
                    Permission.CREATOR_UPDATE
            )
    ),
    CREATOR(
            Set.of(
                    Permission.CREATOR_READ,
                    Permission.CREATOR_CREATE,
                    Permission.CREATOR_DELETE,
                    Permission.CREATOR_UPDATE
            )
    ),
    RESPONDENT(
            (Collections.emptySet()));

    @Getter
    private final Set<Permission> permission;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermission()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .toList();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }


}

