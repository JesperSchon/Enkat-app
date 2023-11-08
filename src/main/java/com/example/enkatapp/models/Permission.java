package com.example.enkatapp.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    ADMIN_UPDATE("admin:update"),
    CREATOR_READ("creator:read"),
    CREATOR_CREATE("creator:create"),
    CREATOR_DELETE("creator:delete"),
    CREATOR_UPDATE("creator:update")

    ;

    @Getter
    private final String permission;

}
