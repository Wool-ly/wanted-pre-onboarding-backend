package com.preonboarding.wanted.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class SecurityUser extends User {

    private User user;

    public SecurityUser(User user) {
        super(user.getUsername().toString(), user.getPassword(),
                AuthorityUtils.createAuthorityList(user.toString()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }


}