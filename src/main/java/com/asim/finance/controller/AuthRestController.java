package com.asim.finance.controller;

import com.asim.finance.dto.LoginRequest;
import com.asim.finance.dto.LoginResponse;
import com.asim.finance.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthRestController(AuthenticationManager authenticationManager,
                              JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request
    ) {

        Authentication authentication =
                authenticationManager.authenticate(

                        new UsernamePasswordAuthenticationToken(

                                request.getEmail(),

                                request.getPassword()

                        )
                );

        String token =
                jwtService.generateToken(

                        (org.springframework.security.core.userdetails.UserDetails)
                                authentication.getPrincipal()

                );

        return new LoginResponse(token);

    }

}