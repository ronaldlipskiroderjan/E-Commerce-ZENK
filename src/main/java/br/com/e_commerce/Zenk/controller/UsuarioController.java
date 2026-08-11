package br.com.e_commerce.Zenk.controller;

import br.com.e_commerce.Zenk.dtos.request.PasswordUpdateRequestDTO;
import br.com.e_commerce.Zenk.dtos.request.UpdateUsuarioRequestDTO;
import br.com.e_commerce.Zenk.dtos.response.TokenResponseDTO;
import br.com.e_commerce.Zenk.dtos.response.UsuarioResponseDTO;
import br.com.e_commerce.Zenk.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioResponseDTO findMe(Authentication authentication) {
        return usuarioService.findMe(authentication);
    }

    @PutMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public TokenResponseDTO updateMe(@Valid @RequestBody UpdateUsuarioRequestDTO dto,
                                     Authentication authentication) throws Exception {
        return usuarioService.update(dto, authentication);
    }

    @PatchMapping("/me/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public TokenResponseDTO updatePassword(@Valid @RequestBody PasswordUpdateRequestDTO dto,
                                           Authentication authentication) throws Exception {
        return usuarioService.updatePassword(dto, authentication);
    }

    @DeleteMapping("/me/deactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateMe(Authentication authentication) {
        usuarioService.deactivateMe(authentication);
    }

    @GetMapping("admin/usuarios")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UsuarioResponseDTO> findAll(@PageableDefault Pageable pageable) throws Exception {
        return usuarioService.findAllUsers(pageable);
    }

    @GetMapping("admin/usuarios/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public UsuarioResponseDTO findById(@PathVariable Integer id) throws Exception {
        return usuarioService.findById(id);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void deactivateUser() {
        usuarioService.deactivateUser();
    }
}

