package br.com.sysgese.mappers;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import br.com.sysgese.dtos.PerfilDTO;
import br.com.sysgese.models.Perfil;

@Mapper(componentModel = "spring")
public interface PerfilMapper {

    PerfilDTO toDTO(Perfil entity);

    List<PerfilDTO> toDTOList(List<Perfil> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCad", ignore = true)
    @Mapping(target = "dataAlt", ignore = true)
    Perfil toEntity(PerfilDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCad", ignore = true)
    @Mapping(target = "dataAlt", ignore = true)
    void updateEntityFromDTO(PerfilDTO dto, @MappingTarget Perfil entity);
}
