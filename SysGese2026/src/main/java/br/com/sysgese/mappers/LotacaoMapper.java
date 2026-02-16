package br.com.sysgese.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.sysgese.dtos.LotacaoDTO;
import br.com.sysgese.models.Lotacao;


@Mapper(componentModel = "spring")
public interface LotacaoMapper {
	
	@Mapping(source = "servidor.id", target = "servidorId")
    @Mapping(source = "unidade.id", target = "unidadeId")
    @Mapping(source = "cargo.id", target = "cargoId")
	  @Mapping(source = "funcao.id", target = "funcaoId")
    @Mapping(source = "servidor.nome", target = "servidorNome")
    @Mapping(source = "unidade.nome", target = "unidadeNome")
    @Mapping(source = "cargo.descricao", target = "cargoNome")
	  @Mapping(source = "funcao.descricao", target = "funcaoNome")
    LotacaoDTO toDTO(Lotacao entity);

    List<LotacaoDTO> toDTOList(List<Lotacao> entities);

    @Mapping(source = "servidorId", target = "servidor.id")
    @Mapping(source = "unidadeId", target = "unidade.id")
    @Mapping(source = "cargoId", target = "cargo.id")
    @Mapping(source = "funcaoId", target = "funcao.id")
    Lotacao toEntity(LotacaoDTO dto);
}
