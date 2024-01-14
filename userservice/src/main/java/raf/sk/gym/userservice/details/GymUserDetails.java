package raf.sk.gym.userservice.details;

import io.jsonwebtoken.lang.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import raf.sk.gym.userservice.model.User;

import java.util.Collection;

/**
 * A class defining user details for use by Spring Security.
 */
public class GymUserDetails implements UserDetails {
    private final User user;

    public GymUserDetails(User user) {this.user = user;}

    /**
     * Returns the userType of the current user.
     * Used in setting JWT claims.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.of(new SimpleGrantedAuthority(user.getUserType()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.getIsBanned();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getIsActivated();
    }

    @Override
    public boolean isEnabled() {
        return !user.getIsBanned() && user.getIsActivated();
    }
}
