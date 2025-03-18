package com.psicovirtual.community.dao.imp;

import com.psicovirtual.community.dao.IEmailConfigRepo;
import com.psicovirtual.community.entities.EmailConfig;
import com.psicovirtual.community.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailConfigService {

    private final IEmailConfigRepo iEmailConfigRepo;

    /**
     * Method to get email by type
     * @param type
     * @return EmailConfig
     * @throws NotFoundException
     */
    public EmailConfig getEmailByType(String type) throws NotFoundException {
        log.info("looking for email type: " + type);
        return this.iEmailConfigRepo.findByType(type).orElseThrow(() -> new NotFoundException("Email type " + type + " not found"));
    }

}
