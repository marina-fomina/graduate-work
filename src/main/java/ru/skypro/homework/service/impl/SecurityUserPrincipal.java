package ru.skypro.homework.service.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.skypro.homework.dto.UserDetailsDTO;
import ru.skypro.homework.entity.User;

import java.util.Collection;
import java.util.List;


public class SecurityUserPrincipal implements org.springframework.security.core.userdetails.UserDetails {
    private final UserDetailsDTO userDetailsDTO;

    private static final String ROLE_PREFIX = "ROLE_";

    public SecurityUserPrincipal(UserDetailsDTO userDetailsDTO) {
        this.userDetailsDTO = userDetailsDTO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(ROLE_PREFIX + userDetailsDTO.getRole().name());
        return List.of(grantedAuthority);
    }

    @Override
    public String getPassword() {
        return userDetailsDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return userDetailsDTO.getUsername();
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

    private UserDetailsDTO mapToUserDetailsDTOFromUser(User user) {
        UserDetailsDTO userDetailsDTO1 = new UserDetailsDTO();

        userDetailsDTO1.setUsername(user.getUsername());
        userDetailsDTO1.setPassword(user.getPassword());
        userDetailsDTO1.setRole(user.getRole());

        return userDetailsDTO1;
    }
}
