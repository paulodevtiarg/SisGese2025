package br.com.sysgese.mappers;


import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import br.com.sysgese.dtos.TatuagemDTO;
import br.com.sysgese.models.Tatuagem;
@Mapper(componentModel = "spring")
public interface TatuagemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "adolescente", ignore = true)
    Tatuagem toEntity(TatuagemDTO dto);

    List<TatuagemDTO> toDTOList(List<Tatuagem> entities);
    
    
    @Mapping(target = "adolescenteId", source = "adolescente.id")
    TatuagemDTO toDTO(Tatuagem entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "adolescente", ignore = true)
    void updateEntityFromDTO(TatuagemDTO dto, @MappingTarget Tatuagem entity);
}
