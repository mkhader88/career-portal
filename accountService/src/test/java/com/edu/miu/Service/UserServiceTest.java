package com.edu.miu.Service;



import com.edu.miu.Repo.UserRepoTest;
import com.edu.miu.security.CustomUserDetailsService;
import com.edu.miu.security.JwtTokenProvider;
import com.edu.miu.security.UserPrincipal;
import com.edu.miu.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.verify;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)

public class UserServiceTest {

    @Mock private UserRepoTest userRepo;
    @Mock private AuthenticationManager authenticationManager;
    @Mock private UserPrincipal userPrincipal;
    @Mock private UserService userService;

    @Mock private JwtTokenProvider tokenProvider;
    @Mock private CustomUserDetailsService customUserDetailsService;

    @BeforeEach void setUp() {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        "admin@admin.com",
                        "1"
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
    }

    @Test void login() throws Exception {
        customUserDetailsService.loadUserByUsername("admin@admin.com");
        verify(userRepo).("admin@admin.com");
    }
}
