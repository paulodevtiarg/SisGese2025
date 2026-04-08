package br.com.sysgese.mappers;



import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import org.mapstruct.NullValuePropertyMappingStrategy;

import br.com.sysgese.dtos.CicatrizDTO;
import br.com.sysgese.models.Cicatriz;
@Mapper(componentModel = "spring")
public interface CicatrizMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "adolescente", ignore = true) // será setado manual
    Cicatriz toEntity(CicatrizDTO dto);

    @Mapping(target = "adolescenteId", source = "adolescente.id")
    CicatrizDTO toDTO(Cicatriz entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "adolescente", ignore = true)
    void updateEntityFromDTO(CicatrizDTO dto, @MappingTarget Cicatriz entity);
}

