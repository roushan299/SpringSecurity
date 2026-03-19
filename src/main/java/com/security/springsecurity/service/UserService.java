package com.security.springsecurity.service;

import com.security.springsecurity.dto.UserResponse;
import com.security.springsecurity.entity.User;
import com.security.springsecurity.enums.Role;
import com.security.springsecurity.exception.UserAlreadyExits;
import com.security.springsecurity.exception.UserDoesNotExists;
import com.security.springsecurity.mapper.UserMapper;
import com.security.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User doesn't exits with this email: "+email);
        }
        User user = optionalUser.get();
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(getAuthorities(user))
                .build();
        return userDetails;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return user.getRole().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }

    public User registerUser(String email, String password) {
        User user = registerUser(email, password, Set.of(Role.USER), "");
        return user;
    }

    public User registerUser(String email, String password, Set<Role> roles, String username ) {
        if(userRepository.existsByEmail(email)) {
            throw new UserAlreadyExits("User already exits with this email: "+email);
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRole(roles);

        return userRepository.save(newUser);
    }

    public User findUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()) {
            throw new UserDoesNotExists("User doesn't exits with this email: "+email);
        }
        return optionalUser.get();
    }

    public UserResponse getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = findUserByEmail(email);
        UserResponse userResponse = UserMapper.getUserResponse(user);
        return userResponse;
    }

}
