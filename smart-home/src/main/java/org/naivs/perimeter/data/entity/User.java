package org.naivs.perimeter.data.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class User implements UserDetails {

    private List<Role> authorities;
    private String username;
    private String password;
    private boolean accountNotExpired;
    private boolean accountIsNotLocked;
    private boolean credentialsIsNotExpired;
    private boolean isEnabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNotExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountIsNotLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsIsNotExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setAuthorities(List<Role> authorities) {
        this.authorities = authorities;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountNotExpired(boolean accountNotExpired) {
        this.accountNotExpired = accountNotExpired;
    }

    public void setAccountIsNotLocked(boolean accountIsNotLocked) {
        this.accountIsNotLocked = accountIsNotLocked;
    }

    public void setCredentialsIsNotExpired(boolean credentialsIsNotExpired) {
        this.credentialsIsNotExpired = credentialsIsNotExpired;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
