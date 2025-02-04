package com.franmunozbetanzos.portfolio.service;

import com.franmunozbetanzos.portfolio.dto.LoginRequest;
import com.franmunozbetanzos.portfolio.dto.LoginResponse;
import com.franmunozbetanzos.portfolio.dto.RegisterRequest;
import com.franmunozbetanzos.portfolio.exception.BadRegisterRequestException;
import com.franmunozbetanzos.portfolio.model.Role;
import com.franmunozbetanzos.portfolio.model.User;
import com.franmunozbetanzos.portfolio.repository.RoleRepository;
import com.franmunozbetanzos.portfolio.repository.UserRepository;
import com.franmunozbetanzos.portfolio.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.franmunozbetanzos.portfolio.constant.ApiConstants.ROLE_;
import static com.franmunozbetanzos.portfolio.constant.ApiConstants.ROLE_USER;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserDetails user = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(user);

        String[] roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .map(role -> role.replace(ROLE_, ""))
                .toArray(String[]::new);

        return LoginResponse.builder()
                .token(token)
                .username(user.getUsername())
                .roles(roles)
                .build();
    }

    public void register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRegisterRequestException("${exception.register.username.exists}");
        }

        Role userRole = roleRepository.findByName(ROLE_USER)
                .orElseThrow(() -> new BadRegisterRequestException("${exception.register.role.exists"));

        User newUser = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(userRole))
                .build();

        userRepository.save(newUser);
    }
}
