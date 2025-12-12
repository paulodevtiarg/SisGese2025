package br.com.sysgese.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import br.com.sysgese.dtos.TatuagemDTO;
import br.com.sysgese.models.Tatuagem;

@Mapper(componentModel = "spring")
public interface TatuagemMapper {
	
	    @Mapping(target = "adolescente", ignore = true)
	    Tatuagem toEntity(TatuagemDTO dto);
	    
	    TatuagemDTO toDTO(Tatuagem entity);
	    
	    // Método para atualizar entidade existente
	    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	    @Mapping(target = "id", ignore = true)
	    @Mapping(target = "adolescente", ignore = true)
	    void updateEntityFromDTO(TatuagemDTO dto, @MappingTarget Tatuagem entity);

}
