package br.com.sysgese.mappers;

import java.util.Base64;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import br.com.sysgese.dtos.CicatrizDTO;
import br.com.sysgese.dtos.TatuagemDTO;
import br.com.sysgese.models.Cicatriz;
import br.com.sysgese.models.Tatuagem;

@Mapper(componentModel = "spring")
public interface TatuagemMapper {
	
		@Mapping(target = "id", ignore = true)
	    @Mapping(target = "adolescente.id", source = "adolescenteId")
	    @Mapping(target = "foto", source = "fotoBase64", qualifiedByName = "base64ToByteArray")
	    Tatuagem toEntity(TatuagemDTO dto);
	    
		@Mapping(target = "adolescenteId", source = "adolescente.id")
		@Mapping(target = "fotoBase64", source = "foto", qualifiedByName = "byteArrayToBase64")
	    TatuagemDTO toDTO(Tatuagem entity);
	    
	    // Método para atualizar entidade existente
	    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	    @Mapping(target = "id", ignore = true)
	    @Mapping(target = "adolescente", ignore = true)
	    @Mapping(target = "foto", source = "fotoBase64", qualifiedByName = "base64ToByteArray")
	    void updateEntityFromDTO(TatuagemDTO dto, @MappingTarget Tatuagem entity);
	    
	 // ==============================
	    // Conversores Base64
	    // ==============================

	    @Named("base64ToByteArray")
	    default byte[] base64ToByteArray(String base64) {
	        if (base64 == null || base64.isBlank()) {
	            return null;
	        }
	        return Base64.getDecoder().decode(base64);
	    }

	    @Named("byteArrayToBase64")
	    default String byteArrayToBase64(byte[] bytes) {
	        if (bytes == null || bytes.length == 0) {
	            return null;
	        }
	        return Base64.getEncoder().encodeToString(bytes);
	    }
	    
	    
	    

}
