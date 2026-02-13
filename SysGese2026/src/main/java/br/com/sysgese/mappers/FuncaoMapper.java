package br.com.sysgese.mappers;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import br.com.sysgese.dtos.FuncaoDTO;
import br.com.sysgese.models.Funcao;

@Mapper(componentModel = "spring")
public interface FuncaoMapper {

    FuncaoDTO toDTO(Funcao entity);

    List<FuncaoDTO> toDTOList(List<Funcao> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCad", ignore = true)
    @Mapping(target = "dataAlt", ignore = true)
    Funcao toEntity(FuncaoDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCad", ignore = true)
    @Mapping(target = "dataAlt", ignore = true)
    void updateEntityFromDTO(FuncaoDTO dto, @MappingTarget Funcao entity);
}