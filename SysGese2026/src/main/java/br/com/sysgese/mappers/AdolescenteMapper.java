package br.com.sysgese.mappers;
import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import br.com.sysgese.dtos.AdolescenteDTO;
import br.com.sysgese.dtos.EnderecoDTO;
import br.com.sysgese.models.Adolescente;
import br.com.sysgese.models.Endereco;

@Mapper(
        componentModel = "spring",
        uses = { CicatrizMapper.class, TatuagemMapper.class, FotoMapper.class, EnderecoMapper.class }
)
public interface AdolescenteMapper {
    
    // ==============================
    // ENTITY -> DTO
    // ==============================
    @Mapping(target = "cicatrizes", source = "cicatrizes")
    @Mapping(target = "tatuagens", source = "tatuagens")
    @Mapping(target = "fotos", source = "fotos")
    AdolescenteDTO toDTO(Adolescente entity);

    List<AdolescenteDTO> toDTOList(List<Adolescente> entities);

    // ==============================
    // DTO -> ENTITY
    // ==============================
    @Mapping(target = "dataCad", ignore = true) // backend controla
    @Mapping(target = "dataAlt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "internacoes", ignore = true) // evitar problemas de referência cíclica
    Adolescente toEntity(AdolescenteDTO dto);

    // ==============================
    // UPDATE parcial
    // ==============================
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCad", ignore = true)
    @Mapping(target = "internacoes", ignore = true)
    void updateEntityFromDTO(AdolescenteDTO dto, @MappingTarget Adolescente entity);

    // ==============================
    // Ajuste de relacionamento bidirecional
    // ==============================
    @AfterMapping
    default void linkCicatrizes(@MappingTarget Adolescente adolescente) {
        if (adolescente.getCicatrizes() != null) {
            adolescente.getCicatrizes()
                .forEach(c -> c.setAdolescente(adolescente));
        }
    }

    @AfterMapping
    default void linkTatuagens(@MappingTarget Adolescente adolescente) {
        if (adolescente.getTatuagens() != null) {
            adolescente.getTatuagens()
                .forEach(t -> t.setAdolescente(adolescente));
        }
    }

    @AfterMapping
    default void linkFotos(@MappingTarget Adolescente adolescente) {
        if (adolescente.getFotos() != null) {
            adolescente.getFotos()
                .forEach(f -> f.setAdolescente(adolescente));
        }
    }
    @AfterMapping
    default void preencherEnderecoAtual(
            @MappingTarget AdolescenteDTO dto,
            Adolescente entity) {

        if (entity.getEnderecos() == null) return;

        entity.getEnderecos().stream()
            .filter(e -> Boolean.TRUE.equals(e.getAtivo()))
            .findFirst()
            .ifPresent(endereco -> 
                dto.setEnderecoAtual(toEnderecoDTO(endereco))
            );
    }
    
    default EnderecoDTO toEnderecoDTO(Endereco endereco) {
        return endereco == null ? null : new EnderecoDTO() {{
            setCidade(endereco.getCidade());
            setBairro(endereco.getBairro());
            setUf(endereco.getUf());
            setLogradouro(endereco.getLogradouro());
            setNumero(endereco.getNumero());
            setCep(endereco.getCep());
            setReferencia(endereco.getReferencia());
        }};
    }
    
}
