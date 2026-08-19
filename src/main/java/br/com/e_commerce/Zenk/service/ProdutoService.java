package br.com.e_commerce.Zenk.service;

import br.com.e_commerce.Zenk.database.model.CategoriaEntity;
import br.com.e_commerce.Zenk.database.model.ProdutoEntity;
import br.com.e_commerce.Zenk.database.repository.ICategoriaRepository;
import br.com.e_commerce.Zenk.database.repository.IProdutoRepository;
import br.com.e_commerce.Zenk.dtos.request.ProdutoRequestDTO;
import br.com.e_commerce.Zenk.dtos.response.ProdutoResponseCategoriaResponseDTO;
import br.com.e_commerce.Zenk.dtos.response.ProdutoResponseDTO;
import br.com.e_commerce.Zenk.exception.AlreadyExistsException;
import br.com.e_commerce.Zenk.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final IProdutoRepository produtoRepository;
    private final ICategoriaRepository categoriaRepository;

    public void createProduto(ProdutoRequestDTO dto, Integer categoriaId) throws Exception {
        if (produtoRepository.existsByNomeIgnoreCase(dto.nome())) {
            throw new AlreadyExistsException("Produto já cadastrado!");
        }
        CategoriaEntity categoriaEntity = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new NotFoundException("Categoria não Encontrada!"));
        produtoRepository.save(ProdutoEntity.builder()
                        .nome(dto.nome())
                        .descricao(dto.descricao())
                        .preco(dto.preco())
                        .quantidade(dto.quantidade())
                        .categoria(categoriaEntity)
                .build());
    }

    public Page<ProdutoResponseDTO> findAllByCategoriaId(Integer categoriaId, Pageable pageable) throws Exception{
        if (!(categoriaRepository.existsById(categoriaId))) {
            throw new NotFoundException("Categoria não Encontrada!");
        }
        return produtoRepository.findAllByCategoriaId(categoriaId, pageable)
                .map(p-> new ProdutoResponseDTO(
                        p.getId(),
                        p.getNome(),
                        p.getDescricao(),
                        p.getPreco(),
                        p.getQuantidade()
                ));
    }

    public Page<ProdutoResponseCategoriaResponseDTO> findAll(Pageable pageable) {
        return produtoRepository.findAll(pageable)
                .map(p-> new ProdutoResponseCategoriaResponseDTO(
                        p.getId(),
                        p.getNome(),
                        p.getDescricao(),
                        p.getPreco(),
                        p.getQuantidade(),
                        p.getCategoria().getNome()
                ));
    }

    public ProdutoResponseDTO findById(Integer id) throws Exception{
        return produtoRepository.findById(id)
                .map(p-> new ProdutoResponseDTO(
                    p.getId(),
                    p.getNome(),
                    p.getDescricao(),
                    p.getPreco(),
                    p.getQuantidade() ))
                .orElseThrow(() -> new NotFoundException("Produto Não Encontrado!"));
    }

    public void update(Integer id, ProdutoRequestDTO dto) throws Exception{
        ProdutoEntity produto = produtoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado!"));
        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPreco(dto.preco());
        produto.setQuantidade(dto.quantidade());
        produtoRepository.save(produto);
    }

    public void updateCategoria(Integer produtoId, Integer categoriaId) throws Exception {
        ProdutoEntity produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado!"));
        CategoriaEntity categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrado!"));
        produto.setCategoria(categoria);
        produtoRepository.save(produto);
    }

    public void delete(Integer categoriaId) throws Exception {
        CategoriaEntity categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrado!"));
        categoriaRepository.delete(categoria);
    }
}
