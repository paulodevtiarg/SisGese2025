package br.com.sysgese.mappers;

import java.util.ArrayList;
import java.util.List;

import br.com.sysgese.enumerators.ParentescoEnum;
import br.com.sysgese.models.Adolescente;
import br.com.sysgese.models.Familiar;
import org.mapstruct.*;

import br.com.sysgese.dtos.AtendimentoEnfermagemDTO;
import br.com.sysgese.models.AtendimentoEnfermagem;

import br.com.sysgese.dtos.PrescricaoMedicamentoDTO;
import br.com.sysgese.models.PrescricaoMedicamento;

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
    @Mapping(
            target = "maeDoAdolescente",
            expression = "java(nomeDaMae(entity.getAdolescente()))"
    )
    @Mapping(source = "adolescente.idade", target = "idadeAdolescente")
    @Mapping(source = "unidade.nome", target = "nomeUnidade")
    @Mapping(source = "unidade.nome", target = "nomeUnidadeInternacao")
    @Mapping(source = "adolescente.unidadeCadastro.nome", target = "nomeUnidadeCadastro")
    AtendimentoEnfermagemDTO toDTO(AtendimentoEnfermagem entity);

    List<AtendimentoEnfermagemDTO> toDTOList(List<AtendimentoEnfermagem> entities);

    // ==========================================================
    // MAPPER DA PRESCRIÇÃO
    // ==========================================================
    PrescricaoMedicamento toEntity(PrescricaoMedicamentoDTO dto);
    PrescricaoMedicamentoDTO toDTO(PrescricaoMedicamento entity);

    // ==========================================================
    // DTO -> ENTITY (NOVO)
    // ==========================================================

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "adolescente.id", source = "idAdolescente")
    @Mapping(target = "unidade.id", source = "idUnidade")
    @Mapping(target = "servidor.id", source = "idServidor")
    @Mapping(target = "prescricoes", ignore = true) // 🔥 Ignora para fazer manualmente
    AtendimentoEnfermagem toEntity(AtendimentoEnfermagemDTO dto);


    // 🔥 O PULO DO GATO: Monta a lista de prescrições manualmente
    @AfterMapping
    default void linkPrescricoes(AtendimentoEnfermagemDTO dto, @MappingTarget AtendimentoEnfermagem entity) {
        if (dto.getPrescricoes() != null && !dto.getPrescricoes().isEmpty()) {

            List<PrescricaoMedicamento> listaEntity = new ArrayList<>();

            for (PrescricaoMedicamentoDTO pDto : dto.getPrescricoes()) {
                PrescricaoMedicamento pEntity = toEntity(pDto); // Converte usando o método acima
                pEntity.setAtendimento(entity); // Amarra o pai
                listaEntity.add(pEntity);
            }

            entity.setPrescricoes(listaEntity);
        }
    }
    List<PrescricaoMedicamento> toEntityList(List<PrescricaoMedicamentoDTO> dtos);

    // ==========================================================
    // UPDATE PARCIAL
    // ==========================================================

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "adolescente", ignore = true)
    @Mapping(target = "unidade", ignore = true)
    @Mapping(target = "servidor", ignore = true)
    @Mapping(target = "prescricoes", ignore = true) // 🔥 Ignora para fazer manualmente
    void updateEntityFromDTO(AtendimentoEnfermagemDTO dto, @MappingTarget AtendimentoEnfermagem entity);

    @AfterMapping
    default void linkPrescricoesUpdate(AtendimentoEnfermagemDTO dto, @MappingTarget AtendimentoEnfermagem entity) {
        if (dto.getPrescricoes() != null && !dto.getPrescricoes().isEmpty()) {

            List<PrescricaoMedicamento> listaEntity = new ArrayList<>();

            for (PrescricaoMedicamentoDTO pDto : dto.getPrescricoes()) {
                PrescricaoMedicamento pEntity = toEntity(pDto);
                pEntity.setAtendimento(entity);
                listaEntity.add(pEntity);
            }

            // Limpa a lista antiga e adiciona a nova
            entity.getPrescricoes().clear();
            entity.getPrescricoes().addAll(listaEntity);
        }
    }

    /**
     * Retorna o nome da mãe a partir da lista de familiares do adolescente.
     */
    default String nomeDaMae(Adolescente adolescente) {
        if (adolescente == null || adolescente.getFamiliares() == null) {
            return null;
        }
        return adolescente.getFamiliares().stream()
                .filter(f -> f.getParentesco() == ParentescoEnum.MAE)  // ajuste o enum
                .map(Familiar::getNomeFamiliar)
                .findFirst()
                .orElse(null);
    }
}