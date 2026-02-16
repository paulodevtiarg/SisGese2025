package br.com.sysgese.mappers;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import br.com.sysgese.dtos.ServidorDTO;
import br.com.sysgese.models.Perfil;
import br.com.sysgese.models.Servidor;

@Mapper(componentModel = "spring")
public interface ServidorMapper {

    // ==============================
    // ENTITY -> DTO
    // ==============================

    @Mapping(source = "perfil.id", target = "perfilId")
    ServidorDTO toDTO(Servidor entity);

    List<ServidorDTO> toDTOList(List<Servidor> entities);

    // ==============================
    // DTO -> ENTITY
    // ==============================

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "perfil", ignore = true) // será setado manualmente
    @Mapping(target = "dataCad", ignore = true)
    @Mapping(target = "dataAlt", ignore = true)
    Servidor toEntity(ServidorDTO dto);

    // ==============================
    // UPDATE parcial
    // ==============================

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "perfil", ignore = true)
    void updateEntityFromDTO(ServidorDTO dto, @MappingTarget Servidor entity);

    // ==============================
    // RELACIONAMENTO PERFIL
    // ==============================

    @AfterMapping
    default void linkPerfil(ServidorDTO dto, @MappingTarget Servidor entity) {
        if (dto.getPerfilId() != null) {
            Perfil perfil = new Perfil();
            perfil.setId(dto.getPerfilId());
            entity.setPerfil(perfil);
        }
    }
}
