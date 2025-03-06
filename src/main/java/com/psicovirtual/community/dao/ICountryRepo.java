package com.psicovirtual.community.dao;

import com.psicovirtual.community.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ICountryRepo extends JpaRepository<Country, Long> {

    @Transactional(readOnly = true)
    Optional<Country> findByIso2(String iso2);

}
