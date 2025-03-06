package com.psicovirtual.community.dao.imp;

import com.psicovirtual.community.dao.IGenderRepo;
import com.psicovirtual.community.entities.Gender;
import com.psicovirtual.community.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenderService {

    private final IGenderRepo iGenderRepo;

    /**
     * Method to get a Gender based on the Gender Name
     * @param genderName
     * @return
     * @throws NotFoundException
     */
    public Gender getGender(String genderName) throws NotFoundException {
        log.info("looking gender: " + genderName);
        return this.iGenderRepo.findByGenderName(genderName).orElseThrow(() -> new NotFoundException("Gender " + genderName + " not found"));
    }


}
