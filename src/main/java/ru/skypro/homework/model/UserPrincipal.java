package ru.skypro.homework.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.skypro.homework.dto.UserAuthDTO;

import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {
    private final UserAuthDTO userAuthDTO;

    public UserPrincipal(UserAuthDTO userAuthDTO) {
        this.userAuthDTO = userAuthDTO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_" + userAuthDTO.getRole().name());
        return List.of(simpleGrantedAuthority);
    }

    @Override
    public String getPassword() {
        return userAuthDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return userAuthDTO.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
