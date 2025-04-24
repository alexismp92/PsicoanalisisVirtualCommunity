package com.psicovirtual.community.service.email;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.psicovirtual.community.exception.NotFoundException;


public interface IEmailOperations {

    /**
     * Email the administrators
     *
     * @throws NotFoundException
     */
    void sendAdminEmail(String type) throws NotFoundException, JsonProcessingException;

    /**
     * Email to the specified email address with the specified email type configured on the DB
     *
     * @param email
     * @param type
     * @throws NotFoundException
     */
    void sendEmail(String email, String type) throws NotFoundException, JsonProcessingException;
}
