package br.com.sysgese.mappers;

import java.util.List;

import br.com.sysgese.dtos.FamiliarDTO;
import br.com.sysgese.models.Familiar;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import br.com.sysgese.dtos.AdolescenteDTO;
import br.com.sysgese.dtos.EnderecoDTO;
import br.com.sysgese.models.Adolescente;
import br.com.sysgese.models.Endereco;

@Mapper(
        componentModel = "spring",
        uses = { CicatrizMapper.class, TatuagemMapper.class, FotoMapper.class, EnderecoMapper.class }
)
public interface AdolescenteMapper {

    // ==============================
    // ENTITY -> DTO
    // ==============================
    @Mapping(target = "cicatrizes", source = "cicatrizes")
    @Mapping(target = "tatuagens", source = "tatuagens")
    @Mapping(target = "fotos", source = "fotos")
    @Mapping(target = "familiares", source = "familiares") // 🔥 Adicionado
    AdolescenteDTO toDTO(Adolescente entity);

    List<AdolescenteDTO> toDTOList(List<Adolescente> entities);

    // ==============================
    // DTO -> ENTITY
    // ==============================
    @Mapping(target = "dataCad", ignore = true)
    @Mapping(target = "dataAlt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "internacoes", ignore = true)
    @Mapping(target = "familiares", source = "familiares") // 🔥 Adicionado
    Adolescente toEntity(AdolescenteDTO dto);

    // ==============================
    // UPDATE parcial
    // ==============================
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCad", ignore = true)
    @Mapping(target = "internacoes", ignore = true)
    @Mapping(target = "familiares", ignore = true) // 🔥 Ignorado aqui (faremos manual no service)
    void updateEntityFromDTO(AdolescenteDTO dto, @MappingTarget Adolescente entity);

    // ... (os @AfterMapping de cicatriz, tatuagem, foto, endereco continuam iguais) ...

    // ==============================
    // MAPPER DA PRESCRIÇÃO/FAMILIAR
    // ==============================
    List<Familiar> toFamiliarEntityList(List<FamiliarDTO> dtos);
    List<FamiliarDTO> toFamiliarDTOList(List<Familiar> entities);

    Familiar toFamiliarEntity(FamiliarDTO dto);
    FamiliarDTO toFamiliarDTO(Familiar entity);

    @AfterMapping
    default void linkFamiliares(@MappingTarget Adolescente adolescente) {
        if (adolescente.getFamiliares() != null) {
            adolescente.getFamiliares().forEach(familiar -> familiar.setAdolescente(adolescente));
        }
    }
}