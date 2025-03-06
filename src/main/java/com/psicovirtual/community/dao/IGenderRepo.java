package com.psicovirtual.community.dao;

import com.psicovirtual.community.entities.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface IGenderRepo extends JpaRepository<Gender, Long> {

    @Transactional(readOnly = true)
    Optional<Gender> findByGenderName(String gernderName);

}
