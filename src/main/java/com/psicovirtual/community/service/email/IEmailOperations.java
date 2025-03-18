package com.psicovirtual.community.service.email;

import com.psicovirtual.community.exception.NotFoundException;


public interface IEmailOperations {

    /**
     * Email the administrators
     *
     * @throws NotFoundException
     */
    void sendEmail() throws NotFoundException;

    /**
     * Email to the specified email address with the specified email type configured on the DB
     *
     * @param email
     * @param type
     * @throws NotFoundException
     */
    void sendEmail(String email, String type) throws NotFoundException;
}
