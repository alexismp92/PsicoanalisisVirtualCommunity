package com.psicovirtual.community.dao.imp;

import com.psicovirtual.community.dao.ICountryRepo;
import com.psicovirtual.community.entities.Country;
import com.psicovirtual.community.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CountryService {

    private final ICountryRepo iCountryRepo;

    /**
     * Method to get a Country based on the iso2
     * @param iso2
     * @return
     * @throws NotFoundException
     */
    public Country getCountry(String iso2) throws NotFoundException {
        log.info("looking country iso2: " + iso2);
        return this.iCountryRepo.findByIso2(iso2).orElseThrow(() -> new NotFoundException("Country iso2 " + iso2 + " not found"));
    }


}
