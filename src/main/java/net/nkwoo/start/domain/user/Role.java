package net.nkwoo.start.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "Guest User"),
    USER("ROLE_USER", "Normal User");

    private final String key;
    private final String title;
}
