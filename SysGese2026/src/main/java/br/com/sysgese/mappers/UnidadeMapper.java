package br.com.sysgese.mappers;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import br.com.sysgese.dtos.UnidadeDTO;
import br.com.sysgese.models.Unidade;
@Mapper(componentModel = "spring")
public interface UnidadeMapper {

    UnidadeDTO toDTO(Unidade entity);

    List<UnidadeDTO> toDTOList(List<Unidade> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCad", ignore = true)
    @Mapping(target = "dataAlt", ignore = true)
    Unidade toEntity(UnidadeDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCad", ignore = true)
    @Mapping(target = "dataAlt", ignore = true)
    void updateEntityFromDTO(UnidadeDTO dto, @MappingTarget Unidade entity);
}