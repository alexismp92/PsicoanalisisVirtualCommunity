package com.psicovirtual.community.dao;

import com.psicovirtual.community.entities.Therapist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ITherapistRepo extends JpaRepository<Therapist, Long> {

    @Transactional(readOnly = true)
    Optional<Therapist> findByEmail(String email);
}
