package br.com.sysgese.mappers;

import br.com.sysgese.dtos.InfracaoDTO;
import br.com.sysgese.models.Infracao;
import org.mapstruct.*;

import java.util.List;
@Mapper(componentModel = "spring")
public interface InfracaoMapper {
    // ==============================
    // ENTITY -> DTO
    // ==============================
    InfracaoDTO toDTO(Infracao entity);

    List<InfracaoDTO> toDTOList(List<Infracao> entities);

    Infracao toEntity(InfracaoDTO dto);

    // ==============================
    // UPDATE parcial
    // ==============================
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(InfracaoDTO dto, @MappingTarget Infracao entity);
}
