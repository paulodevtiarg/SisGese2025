package br.com.sysgese.mappers;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import br.com.sysgese.dtos.CargoDTO;

import br.com.sysgese.models.Cargo;


@Mapper(componentModel = "spring")
public interface CargoMapper {
	
	 CargoDTO toDTO(Cargo entity);

	    List<CargoDTO> toDTOList(List<Cargo> entities);
	    
	    @Mapping(target = "id", ignore = true)
	    @Mapping(target = "dataCad", ignore = true)
	    @Mapping(target = "dataAlt", ignore = true)
	    Cargo toEntity(CargoDTO dto);

	    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	    @Mapping(target = "id", ignore = true)
	    @Mapping(target = "dataCad", ignore = true)
	    @Mapping(target = "dataAlt", ignore = true)
	    void updateEntityFromDTO(CargoDTO dto, @MappingTarget Cargo entity);

}
