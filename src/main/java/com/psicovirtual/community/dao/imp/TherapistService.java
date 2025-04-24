package com.psicovirtual.community.dao.imp;

import com.psicovirtual.community.dao.ITherapistRepo;
import com.psicovirtual.community.entities.Therapist;
import com.psicovirtual.community.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class TherapistService {

    private final ITherapistRepo iTherapistRepo;

    public Collection<Therapist> findAllById(Set<Long> ids){
        log.info("looking for therapist with ids " + ids);
        return iTherapistRepo.findAllById(ids);
    }

    /**
     * Method to get a therapist by email
     * @param email
     * @return Therapist
     * @throws NotFoundException
     */
    public Therapist findByEmail(String email) throws NotFoundException {
        log.info("looking for a therapist with email " + email);
        return iTherapistRepo.findByEmail(email).orElseThrow(()-> new NotFoundException("email " + email +" not registered"));
    }

    /**
     * Method to save a Therapist
     * @param Therapist
     * @return Therapist
     */
    public Therapist save(Therapist therapist){
        log.info("saving therapist request: " + therapist);
        return iTherapistRepo.save(therapist);
    }

}
