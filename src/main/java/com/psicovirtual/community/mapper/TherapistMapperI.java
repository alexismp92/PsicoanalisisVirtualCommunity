package com.psicovirtual.community.mapper;

import com.psicovirtual.community.dto.CommunityReqDTO;
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

    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "country", target = "country")
    @Mapping(source = "educations", target = "educations")
    @Mapping(source = "interests", target = "interests")
    @Mapping(source = "communityRequest", target = "communityRequest")
    TherapistDTO EntityToTherapistDTO(Therapist therapist);

    @AfterMapping
    default void mapTherapist(@MappingTarget Therapist therapist) {
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

    default String mapGenderToDTO(Gender gender) {
        return gender != null ? gender.getGenderName() : null;
    }

    default String mapCountryToDTO(Country country) {
        return country != null ? country.getIso2() : null;
    }

    default CommunityReqDTO mapCommunityStatusToDTO(CommunityReq communityReq) {
        if(communityReq != null){
            return CommunityReqDTO.builder()
                    .communityReqId(communityReq.getCommunityReqId())
                    .communityStatus(communityReq.getCommunityStatus().getCommStatusName())
                    .build();
        }else{
            return null;
        }
    }

}
