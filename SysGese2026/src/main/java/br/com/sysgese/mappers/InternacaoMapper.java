package br.com.sysgese.mappers;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import br.com.sysgese.dtos.InternacaoDTO;
import br.com.sysgese.models.Internacao;

@Mapper(componentModel = "spring")
public interface InternacaoMapper {

    // ==============================
    // ENTITY -> DTO
    // ==============================
    @Mapping(source = "adolescente.id", target = "idAdolescente")
    @Mapping(source = "unidade.id", target = "idUnidade")
    InternacaoDTO toDTO(Internacao entity);

    List<InternacaoDTO> toDTOList(List<Internacao> entities);

    // ==============================
    // DTO -> ENTITY
    // ==============================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCad", ignore = true)
    @Mapping(target = "dataAlt", ignore = true)
    @Mapping(target = "adolescente.id", source = "idAdolescente")
    @Mapping(target = "unidade.id", source = "idUnidade")
    Internacao toEntity(InternacaoDTO dto);

    // ==============================
    // UPDATE parcial
    // ==============================
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCad", ignore = true)
    @Mapping(target = "dataAlt", ignore = true)
    @Mapping(target = "adolescente.id", source = "idAdolescente")
    @Mapping(target = "unidade.id", source = "idUnidade")
    void updateEntityFromDTO(InternacaoDTO dto, @MappingTarget Internacao entity);
}
