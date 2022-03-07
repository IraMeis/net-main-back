package com.morena.netMain.auth.model;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority {

    system("system"),
    comment_viewer("comment_viewer"),
    comment_modifier("comment_modifier"),
    post_viewer("post_viewer"),
    post_modifier("post_modifier"),
    user_data_viewer("user_data_viewer"),
    user_data_modifier("user_data_modifier"),
    user_data_admin_viewer("user_data_admin_viewer"),
    user_data_admin_deleter("user_data_admin_deleter"),
    comment_admin_viewer("comment_admin_viewer"),
    comment_admin_deleter("comment_admin_deleter");

    private final String vale;

    @Override
    public String getAuthority() {
        return vale;
    }
}
