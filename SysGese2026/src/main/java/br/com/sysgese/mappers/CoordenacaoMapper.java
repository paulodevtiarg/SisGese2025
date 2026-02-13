package br.com.sysgese.mappers;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import br.com.sysgese.dtos.CoordenacaoDTO;
import br.com.sysgese.models.Coordenacao;

@Mapper(componentModel = "spring")
public interface CoordenacaoMapper {

    @Mapping(source = "servidor.id", target = "servidorId")
    @Mapping(source = "unidade.id", target = "unidadeId")
    CoordenacaoDTO toDTO(Coordenacao entity);

    List<CoordenacaoDTO> toDTOList(List<Coordenacao> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCad", ignore = true)
    @Mapping(target = "dataAlt", ignore = true)
    @Mapping(target = "servidor", ignore = true)
    @Mapping(target = "unidade", ignore = true)
    Coordenacao toEntity(CoordenacaoDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "servidor", ignore = true)
    @Mapping(target = "unidade", ignore = true)
    void updateEntityFromDTO(CoordenacaoDTO dto, @MappingTarget Coordenacao entity);
}
