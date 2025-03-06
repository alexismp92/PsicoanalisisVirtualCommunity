package com.psicovirtual.community.mapper;

import com.psicovirtual.community.dto.EducationDTO;
import com.psicovirtual.community.dto.InterestDTO;
import com.psicovirtual.community.entities.*;
import com.psicovirtual.community.dto.TherapistDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper
public interface TherapistMapperI {

    TherapistMapperI INSTANCE = Mappers.getMapper(TherapistMapperI.class);

    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "country", target = "country")
    @Mapping(source = "educations", target = "educations")
    @Mapping(source = "interests", target = "interests")
    @Mapping(source = "communityRequest", target = "communityRequest")
    Therapist TherapistDTOToEntity(TherapistDTO therapistDTO,
                                   Gender gender,
                                   Country country,
                                   Set<Education> educations,
                                   Interest interests,
                                   CommunityReq communityRequest);

    @Mapping(source = "country", target = "country")
    Education EducationDTOToEntity(EducationDTO educationDTO, Country country);

    Interest InterestDTOToEntity(InterestDTO interestDTO);


    @AfterMapping
    default void linkPersonToEducation(@MappingTarget Therapist therapist) {
        if (therapist.getEducations() != null) {
            therapist.getEducations().forEach(education -> education.setTherapist(therapist));
        }
        if (therapist.getEducations() != null) {
            therapist.getInterests().setTherapist(therapist);
        }
        if (therapist.getCommunityRequest() != null) {
            therapist.getCommunityRequest().setTherapist(therapist);
        }
    }


}
