package br.com.sysgese.mappers;

import java.util.Base64;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import br.com.sysgese.dtos.CicatrizDTO;
import br.com.sysgese.models.Cicatriz;

@Mapper(componentModel = "spring")
public interface CicatrizMapper {

	    @Mapping(target = "adolescente", ignore = true)
	    @Mapping(target = "foto", source = "fotoBase64", qualifiedByName = "base64ToByteArray")
	    Cicatriz toEntity(CicatrizDTO dto);
	    
	    @Mapping(target = "fotoBase64", source = "foto", qualifiedByName = "byteArrayToBase64")
	    CicatrizDTO toDTO(Cicatriz entity);
	    
	    @Named("base64ToByteArray")
	    default byte[] base64ToByteArray(String base64) {
	        if (base64 == null || base64.isEmpty()) {
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
	    
	    // Método para atualizar entidade existente
	    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	    @Mapping(target = "id", ignore = true)
	    @Mapping(target = "adolescente", ignore = true)
	    @Mapping(target = "foto", source = "fotoBase64", qualifiedByName = "base64ToByteArray")
	    void updateEntityFromDTO(CicatrizDTO dto, @MappingTarget Cicatriz entity);
}
