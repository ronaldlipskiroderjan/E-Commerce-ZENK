package br.com.e_commerce.Zenk.service;

import br.com.e_commerce.Zenk.database.model.EnderecoEntity;
import br.com.e_commerce.Zenk.database.model.UsuarioEntity;
import br.com.e_commerce.Zenk.database.repository.IEnderecoRepository;
import br.com.e_commerce.Zenk.database.repository.IUsuarioRepository;
import br.com.e_commerce.Zenk.dtos.request.EnderecoRequestDTO;
import br.com.e_commerce.Zenk.dtos.response.EnderecoResponseDTO;
import br.com.e_commerce.Zenk.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final IEnderecoRepository enderecoRepository;
    private final IUsuarioRepository usuarioRepository;

    public void createEndereco(Authentication authentication, EnderecoRequestDTO dto) throws Exception {
        if (enderecoRepository.existsByLogradouroAndNumero(dto.logradouro(), dto.numero())) {
            throw new BadRequestException("Endereço já cadastrado!");
        }
        UsuarioEntity usuarioEntity = usuarioRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado!"));
        enderecoRepository.save(EnderecoEntity.builder()
                .logradouro(dto.logradouro())
                .numero(dto.numero())
                .complemento(dto.complemento())
                .bairro(dto.bairro())
                .cidade(dto.cidade())
                .estado(dto.estado())
                .cep(dto.cep())
                .usuario(usuarioEntity)
                .build()
        );
    }

    public List<EnderecoResponseDTO> findAll(Authentication authentication) {
        return enderecoRepository.findAllByUsuarioEmail(authentication.getName())
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public EnderecoResponseDTO findById(Authentication authentication, Integer enderecoId) throws Exception {
        return enderecoRepository.findByIdAndUsuarioEmail(enderecoId, authentication.getName())
                .map(this::toDTO)
                .orElseThrow(() -> new NotFoundException("Endereço não encontrado!"));
    }

    public void update(Authentication authentication, Integer enderecoId, EnderecoRequestDTO dto) throws Exception{
        EnderecoEntity enderecoEntity = enderecoRepository.findByIdAndUsuarioEmail(enderecoId, authentication.getName())
                .orElseThrow(() -> new NotFoundException("Endereço não encontrado! "));
        enderecoEntity.setLogradouro(dto.logradouro());
        enderecoEntity.setNumero(dto.numero());
        enderecoEntity.setComplemento(dto.complemento());
        enderecoEntity.setBairro(dto.bairro());
        enderecoEntity.setCidade(dto.cidade());
        enderecoEntity.setEstado(dto.estado());
        enderecoEntity.setCep(dto.cep());
        enderecoRepository.save(enderecoEntity);
    }

    public void deleteById(Authentication authentication, Integer enderecoId) throws Exception{
        EnderecoEntity enderecoEntity = enderecoRepository.findByIdAndUsuarioEmail(enderecoId, authentication.getName())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado!"));
        enderecoRepository.delete(enderecoEntity);
    }

    private EnderecoResponseDTO toDTO(EnderecoEntity e) {
        return new EnderecoResponseDTO(
                e.getId(),
                e.getLogradouro(),
                e.getNumero(),
                e.getComplemento(),
                e.getBairro(),
                e.getCidade(),
                e.getEstado(),
                e.getCep()
        );
    }


}
