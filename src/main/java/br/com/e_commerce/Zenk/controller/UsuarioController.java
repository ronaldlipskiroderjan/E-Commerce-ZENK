package br.com.e_commerce.Zenk.controller;

import br.com.e_commerce.Zenk.dtos.request.PasswordUpdateRequestDTO;
import br.com.e_commerce.Zenk.dtos.request.UpdateUsuarioRequestDTO;
import br.com.e_commerce.Zenk.dtos.response.UsuarioResponseDTO;
import br.com.e_commerce.Zenk.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // OK
    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioResponseDTO findMe(Authentication authentication) {
        return usuarioService.findMe(authentication);
    }

    // OK
    @PutMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMe(@Valid @RequestBody UpdateUsuarioRequestDTO dto,
                                     Authentication authentication) throws Exception {
        usuarioService.update(dto, authentication);
    }

    // OK
    @PatchMapping("/me/password")
    @ResponseStatus(HttpStatus.OK)
    public void updatePassword(@Valid @RequestBody PasswordUpdateRequestDTO dto,
                                           Authentication authentication) {
        usuarioService.updatePassword(dto, authentication);
    }

    // OK
    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMe(Authentication authentication) {
        usuarioService.deleteMe(authentication);
    }

    // OK
    @GetMapping("/admin")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UsuarioResponseDTO> findAll(@PageableDefault Pageable pageable) {
        return usuarioService.findAllUsers(pageable);
    }

    // OK
    @GetMapping("/admin/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public UsuarioResponseDTO findById(@PathVariable Integer id) throws Exception {
        return usuarioService.findById(id);
    }

    // OK
    @DeleteMapping("/admin/{id}/status")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void deactivateUser(@PathVariable Integer id) throws Exception{
        usuarioService.deleteUser(id);
    }
}
