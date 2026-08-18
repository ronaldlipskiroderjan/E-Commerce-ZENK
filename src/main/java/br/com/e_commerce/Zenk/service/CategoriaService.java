package br.com.e_commerce.Zenk.service;

import br.com.e_commerce.Zenk.database.model.CategoriaEntity;
import br.com.e_commerce.Zenk.database.repository.ICategoriaRepository;
import br.com.e_commerce.Zenk.dtos.request.CategoriaRequestDTO;
import br.com.e_commerce.Zenk.dtos.response.CategoriaResponseDTO;
import br.com.e_commerce.Zenk.exception.AlreadyExistsException;
import br.com.e_commerce.Zenk.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final ICategoriaRepository categoriaRepository;

    public void create(CategoriaRequestDTO dto) throws Exception{
        if (categoriaRepository.existsByNomeIgnoreCase(dto.nome())) {
            throw new AlreadyExistsException("Categoria já cadastrada!");
        }
        categoriaRepository.save(CategoriaEntity.builder()
                    .nome(dto.nome())
                    .descricao(dto.descricao())
                    .build()
        );
    }

    public Page<CategoriaResponseDTO> findAll(Pageable pageable) {
        return categoriaRepository.findAll(pageable)
                .map(this::toDTO);
    }

    public CategoriaResponseDTO findById(Integer id) throws Exception {
        return categoriaRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada!!"));
    }

    public void update(Integer id, CategoriaRequestDTO dto) throws Exception {
        CategoriaEntity categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada!"));
        categoria.setNome(dto.nome());
        categoria.setDescricao(dto.descricao());
        categoriaRepository.save(categoria);
    }

    public void delete(Integer id) throws Exception{
        CategoriaEntity categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada!"));
        categoriaRepository.delete(categoria);
    }

    private CategoriaResponseDTO toDTO(CategoriaEntity c) {
        return new CategoriaResponseDTO(
                c.getId(),
                c.getNome(),
                c.getDescricao()
        );
    }
}
