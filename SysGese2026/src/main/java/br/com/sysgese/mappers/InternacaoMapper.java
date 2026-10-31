package br.com.sysgese.mappers;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import br.com.sysgese.dtos.InternacaoDTO;
import br.com.sysgese.enumerators.ParentescoEnum;
import br.com.sysgese.models.Adolescente;
import br.com.sysgese.models.Familiar;
import br.com.sysgese.models.Internacao;

@Mapper(componentModel = "spring")
public interface InternacaoMapper {

    // ==============================
    // ENTITY -> DTO
    // ==============================

    @Mapping(source = "adolescente.id", target = "idAdolescente")
    @Mapping(source = "unidade.id", target = "idUnidade")
    @Mapping(source = "unidade.nome", target = "nomeUnidade")
    @Mapping(source = "adolescente.nome", target = "nomeAdolescente")
    @Mapping(source = "adolescente.cidadeNascimento", target = "cidadeAdolescente")
    @Mapping(source = "infracao.id", target = "idInfracao")
    @Mapping(source = "infracao.artigo", target = "artigoInfracao")
    @Mapping(source = "infracao.descricao", target = "descricaoInfracao")
    @Mapping(source = "adolescente.ufNascimento", target = "ufAdolescente")
    @Mapping(source = "adolescente.fotoRegistro", target = "fotoAdolescente")
    @Mapping(source = "unidade.nome", target = "nomeUnidadeInternacao")
    @Mapping(source = "adolescente.unidadeCadastro.nome", target = "nomeUnidadeCadastro")
    @Mapping(source = "adolescente.idade", target = "idadeAdolescente")

    // 👇 MÃE agora vem da lista de familiares
    @Mapping(target = "maeDoAdolescente", source = "adolescente", qualifiedByName = "nomeDaMae")

    InternacaoDTO toDTO(Internacao entity);

    List<InternacaoDTO> toDTOList(List<Internacao> entities);

    // ==============================
    // DTO -> ENTITY
    // ==============================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCad", ignore = true)
    @Mapping(target = "dataAlt", ignore = true)
    @Mapping(target = "adolescente.id", source = "idAdolescente")
    @Mapping(target = "unidade.id", source = "idUnidade")
    @Mapping(target = "infracao.id", source = "idInfracao")
    Internacao toEntity(InternacaoDTO dto);

    // ==============================
    // UPDATE parcial
    // ==============================
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCad", ignore = true)
    @Mapping(target = "dataAlt", ignore = true)
    @Mapping(target = "adolescente.id", source = "idAdolescente")
    @Mapping(target = "unidade.id", source = "idUnidade")
    @Mapping(target = "infracao.id", source = "idInfracao")
    void updateEntityFromDTO(InternacaoDTO dto, @MappingTarget Internacao entity);

    /**
     * Retorna o nome da mãe a partir da lista de familiares do adolescente.
     */
    @Named("nomeDaMae")
    default String nomeDaMae(Adolescente adolescente) {
        if (adolescente == null || adolescente.getFamiliares() == null) {
            return null;
        }
        return adolescente.getFamiliares().stream()
                .filter(f -> f.getParentesco() == ParentescoEnum.MAE)
                .map(Familiar::getNomeFamiliar)
                .findFirst()
                .orElse(null);
    }
}