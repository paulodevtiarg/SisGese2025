package br.com.sysgese.mappers;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import br.com.sysgese.dtos.FotoDTO;
import br.com.sysgese.models.Foto;

@Mapper(componentModel = "spring")
public interface FotoMapper {

    // ==============================
    // ENTITY -> DTO
    // ==============================
    FotoDTO toDTO(Foto entity);

    List<FotoDTO> toDTOList(List<Foto> entities);

    // ==============================
    // DTO -> ENTITY
    // ==============================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "adolescente", ignore = true) // será setado no AfterMapping do AdolescenteMapper
    Foto toEntity(FotoDTO dto);

    // ==============================
    // UPDATE parcial
    // ==============================
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "adolescente", ignore = true)
    void updateEntityFromDTO(FotoDTO dto, @MappingTarget Foto entity);
}
