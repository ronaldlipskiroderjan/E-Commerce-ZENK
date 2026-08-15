package br.com.e_commerce.Zenk.controller;

import br.com.e_commerce.Zenk.dtos.request.AuthLoginRequestDTO;
import br.com.e_commerce.Zenk.dtos.request.AuthRequestDTO;
import br.com.e_commerce.Zenk.dtos.request.TokenRefreshRequestDTO;
import br.com.e_commerce.Zenk.dtos.response.TokenResponseDTO;
import br.com.e_commerce.Zenk.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // OK
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody AuthRequestDTO dto) throws Exception {
        authService.register(dto);
    }

    // OK
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TokenResponseDTO login(@Valid @RequestBody AuthLoginRequestDTO dto) throws Exception {
        return authService.login(dto);
    }

    // OK
    @PostMapping("/refresh-token")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TokenResponseDTO refreshToken(Authentication authentication) {
        return authService.refreshToken(authentication);
    }
}
