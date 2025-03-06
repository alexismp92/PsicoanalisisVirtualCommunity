package com.psicovirtual.community.mapper;

import com.psicovirtual.community.dto.InterestDTO;
import com.psicovirtual.community.entities.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper
public interface InterestMapperI {

    InterestMapperI INSTANCE = Mappers.getMapper(InterestMapperI.class);

    Interest InterestDTOToEntity(InterestDTO interestDTO);


}
