package br.com.sysgese.mappers;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import br.com.sysgese.dtos.EnderecoDTO;
import br.com.sysgese.models.Adolescente;
import br.com.sysgese.models.Endereco;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    // ==============================
    // ENTITY -> DTO
    // ==============================
    @Mapping(target = "idAdolescente", source = "adolescente.id")
    EnderecoDTO toDTO(Endereco entity);

    List<EnderecoDTO> toDTOList(List<Endereco> entities);

    // ==============================
    // DTO -> ENTITY
    // ==============================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "adolescente", ignore = true) // será setado no service
    Endereco toEntity(EnderecoDTO dto);

    // ==============================
    // UPDATE parcial
    // ==============================
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "adolescente", ignore = true)
    void updateEntityFromDTO(EnderecoDTO dto, @MappingTarget Endereco entity);

    // ==============================
    // AJUSTE DE RELACIONAMENTO
    // ==============================
    @AfterMapping
    default void linkAdolescente(EnderecoDTO dto, @MappingTarget Endereco entity) {
        if (dto.getIdAdolescente() != null) {
            Adolescente adolescente = new Adolescente();
            adolescente.setId(dto.getIdAdolescente());
            entity.setAdolescente(adolescente);
        }
    }
}
