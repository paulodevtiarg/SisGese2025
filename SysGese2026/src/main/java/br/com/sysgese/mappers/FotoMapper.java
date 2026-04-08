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

    @Mapping(target = "adolescenteId", source = "adolescente.id")
    FotoDTO toDTO(Foto entity);

    List<FotoDTO> toDTOList(List<Foto> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "adolescente", ignore = true)
    Foto toEntity(FotoDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "adolescente", ignore = true)
    void updateEntityFromDTO(FotoDTO dto, @MappingTarget Foto entity);
}
