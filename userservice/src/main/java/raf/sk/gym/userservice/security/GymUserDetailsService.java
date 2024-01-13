package raf.sk.gym.userservice.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import raf.sk.gym.userservice.details.GymUserDetails;
import raf.sk.gym.userservice.repository.UserRepository;

@Service
public class GymUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    public GymUserDetailsService(UserRepository repository) {this.repository = repository;}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new GymUserDetails(user);
    }
}
