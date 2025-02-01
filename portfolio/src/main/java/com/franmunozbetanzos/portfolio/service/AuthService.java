package com.franmunozbetanzos.portfolio.service;

import com.franmunozbetanzos.portfolio.dto.LoginRequestDTO;
import com.franmunozbetanzos.portfolio.dto.LoginResponseDTO;
import com.franmunozbetanzos.portfolio.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import static com.franmunozbetanzos.portfolio.constant.ApiConstants.ROLE_;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponseDTO login(LoginRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserDetails user = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(user);

        String[] roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .map(role -> role.replace(ROLE_, ""))
                .toArray(String[]::new);

        return LoginResponseDTO.builder()
                .token(token)
                .username(user.getUsername())
                .roles(roles)
                .build();
    }
}
