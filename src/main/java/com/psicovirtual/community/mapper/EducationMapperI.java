package com.psicovirtual.community.mapper;

import com.psicovirtual.community.dto.EducationDTO;
import com.psicovirtual.community.entities.Country;
import com.psicovirtual.community.entities.Education;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EducationMapperI {

    EducationMapperI INSTANCE = Mappers.getMapper(EducationMapperI.class);

    @Mapping(source = "country", target = "country")
    Education EducationDTOToEntity(EducationDTO educationDTO, Country country);

}
