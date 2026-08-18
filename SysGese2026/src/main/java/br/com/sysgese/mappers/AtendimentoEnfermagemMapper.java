package br.com.sysgese.mappers;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import br.com.sysgese.dtos.AtendimentoEnfermagemDTO;
import br.com.sysgese.models.AtendimentoEnfermagem;

@Mapper(componentModel = "spring")
public interface AtendimentoEnfermagemMapper {

    // ==========================================================
    // ENTITY -> DTO
    // ==========================================================

    @Mapping(source = "adolescente.id", target = "idAdolescente")
    @Mapping(source = "unidade.id", target = "idUnidade")
    @Mapping(source = "servidor.id", target = "idServidor")

    @Mapping(source = "adolescente.nome", target = "nomeAdolescente")
    @Mapping(source = "adolescente.cidadeNascimento", target = "cidadeAdolescente")
    @Mapping(source = "adolescente.ufNascimento", target = "ufAdolescente")
    @Mapping(source = "adolescente.fotoRegistro", target = "fotoAdolescente")
    @Mapping(source = "adolescente.mae", target = "maeDoAdolescente")
    @Mapping(source = "adolescente.idade", target = "idadeAdolescente")

    @Mapping(source = "unidade.nome", target = "nomeUnidade")
    @Mapping(source = "unidade.nome", target = "nomeUnidadeInternacao")
    @Mapping(source = "adolescente.unidadeCadastro.nome",
            target = "nomeUnidadeCadastro")

    AtendimentoEnfermagemDTO toDTO(
            AtendimentoEnfermagem entity
    );

    List<AtendimentoEnfermagemDTO> toDTOList(
            List<AtendimentoEnfermagem> entities
    );


    // ==========================================================
    // DTO -> ENTITY
    // ==========================================================

    @Mapping(target = "id", ignore = true)

    @Mapping(target = "adolescente.id", source = "idAdolescente")
    @Mapping(target = "unidade.id", source = "idUnidade")
    @Mapping(target = "servidor.id", source = "idServidor")

    AtendimentoEnfermagem toEntity(
            AtendimentoEnfermagemDTO dto
    );


    // ==========================================================
    // UPDATE PARCIAL
    // ==========================================================

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)

    /*
     * Os vínculos não serão alterados durante uma edição.
     *
     * O adolescente, unidade e servidor pertencem
     * ao atendimento original.
     */

    @Mapping(target = "adolescente", ignore = true)
    @Mapping(target = "unidade", ignore = true)
    @Mapping(target = "servidor", ignore = true)

    void updateEntityFromDTO(
            AtendimentoEnfermagemDTO dto,
            @MappingTarget AtendimentoEnfermagem entity
    );
}