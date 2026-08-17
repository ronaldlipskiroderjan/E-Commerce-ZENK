package br.com.e_commerce.Zenk.controller;

import br.com.e_commerce.Zenk.dtos.request.EnderecoRequestDTO;
import br.com.e_commerce.Zenk.dtos.response.EnderecoResponseDTO;
import br.com.e_commerce.Zenk.service.EnderecoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/enderecos")
@RequiredArgsConstructor
public class EnderecoController {

    private final EnderecoService enderecoService;

    // OK
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createEndereco(Authentication authentication,
                               @Valid @RequestBody EnderecoRequestDTO dto) throws Exception {
        enderecoService.createEndereco(authentication, dto);
    }

    // OK
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EnderecoResponseDTO> findAll(Authentication authentication) {
        return enderecoService.findAll(authentication);
    }

    // OK
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EnderecoResponseDTO findById(@PathVariable Integer id, Authentication authentication) throws Exception{
        return enderecoService.findById(id, authentication);
    }

    // OK
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Integer id,
                       Authentication authentication,
                       @Valid @RequestBody EnderecoRequestDTO dto) throws Exception{
        enderecoService.update(authentication, id, dto);
    }

    // OK
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id,
                           Authentication authentication) throws Exception {
        enderecoService.deleteById(authentication, id);
    }
}
