package com.psicovirtual.community.service;

import com.psicovirtual.community.dao.imp.*;
import com.psicovirtual.community.dto.TherapistDTO;

import com.psicovirtual.community.entities.CommunityReq;
import com.psicovirtual.community.entities.Education;
import com.psicovirtual.community.enums.CommunityStatusEnum;
import com.psicovirtual.community.exception.CommunityException;
import com.psicovirtual.community.exception.NotFoundException;
import com.psicovirtual.community.mapper.TherapistMapperI;
import com.psicovirtual.community.service.bucket.IBucketOperations;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

import static com.psicovirtual.community.utils.UUIDUtils.generateUUUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommunityService {

    private final GenderService genderService;
    private final CountryService countryService;
    private final TherapistService therapistService;
    private final CommunityStatusService communityStatusService;
    private final IBucketOperations iBucketOperations;

    /**
     * Method to send a request to join to the community
     * @param therapistDTO
     * @param files
     * @return boolean
     * @throws CommunityException
     */
    @Transactional
    public boolean sendJoinRequest(TherapistDTO therapistDTO, Set<MultipartFile> files) throws CommunityException {
        log.info("therapist is sending a request to join: " + therapistDTO);

        try {

            if(!therapistDTO.getEmail().equalsIgnoreCase(therapistDTO.getConfirmEmail())){
                throw new NotFoundException("Emails does not match");
            }

            if(isTherapistExist((therapistDTO.getEmail()))){
                throw new CommunityException("Email was already "+ therapistDTO.getEmail()+ " registered");
            }

            var gender = genderService.getGender(therapistDTO.getGender());
            log.info("gender is valid");
            var therapistCountry = countryService.getCountry(therapistDTO.getCountry());
            log.info("therapist country is valid");

            Set<Education> educationEntity = new HashSet<>();
            final var uuid = generateUUUID();
            for(var educationDTO : therapistDTO.getEducations()){
                log.info("Validating documents");
                var opFile = files.stream().filter(filename -> filename.getOriginalFilename().equalsIgnoreCase(educationDTO.getCertificateFilename())).findFirst();
                if(!opFile.isPresent()){
                    throw new CommunityException("Filename not found in the therapist request. Please verify the name or files attached");
                }
                var educationCountry = countryService.getCountry(educationDTO.getCountry());
                log.info("country education for " + educationDTO.getInstitution() + " is valid");

                //SAVE FILES INTO S3
                var fileToUpload = opFile.get();
                var paths = this.iBucketOperations.upload(Set.of(fileToUpload), uuid);
                educationDTO.setCertificatePath(paths.stream().findFirst().get());

                var eduEntity = TherapistMapperI.INSTANCE.EducationDTOToEntity(educationDTO, educationCountry);
                eduEntity.setEducationId(null);
                educationEntity.add(eduEntity);
            }

            var interests = TherapistMapperI.INSTANCE.InterestDTOToEntity(therapistDTO.getInterests());
            interests.setInterestId(null);

            var communityRequest  = buildCommunityRequest();

            var therapist = TherapistMapperI.INSTANCE.TherapistDTOToEntity(
                    therapistDTO, gender, therapistCountry, educationEntity, interests, communityRequest);

            var savedTherapist = therapistService.save(therapist);


        } catch (NotFoundException ex) {
           log.error(ex.getMessage());
            throw new CommunityException(ex.getMessage());

        }

        //SEND EMAIL TO THE ADMINS

        //SEND EMAIL TO THE THERAPIST WITH THE REQUEST

        return true;
    }


    /**
     * Method to validate if the therapist already was registered
     * @param email
     * @return boolean
     */
    private boolean isTherapistExist(String email){
        log.info("validate if therapist already was registered " + email);
        boolean isExist = false;
        try {
            var therapist = therapistService.getByEmail(email);
            isExist = true;
        } catch (NotFoundException e) {
            log.info("Therapist not registered");
        }
        return isExist;
    }

    /**
     * Method to build a community request
     * @return CommunityReq
     * @throws CommunityException
     */
    private CommunityReq buildCommunityRequest() throws CommunityException {

        try {
            var communityStatus = communityStatusService.getCommunityStatus(CommunityStatusEnum.PENDING.name());
            log.info("community status found");
            return CommunityReq.builder().communityStatus(communityStatus).build();
        } catch (NotFoundException ex) {
            log.error(ex.getMessage());
            throw new CommunityException(ex.getMessage());
        }
    }

}
