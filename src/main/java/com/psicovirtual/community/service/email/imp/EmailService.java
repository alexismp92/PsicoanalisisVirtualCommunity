package com.psicovirtual.community.service.email.imp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.psicovirtual.community.component.EmailProperties;

import com.psicovirtual.community.dao.imp.EmailConfigService;
import com.psicovirtual.community.dto.EmailDTO;
import com.psicovirtual.community.exception.NotFoundException;
import com.psicovirtual.community.service.email.IEmailOperations;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Set;

import static com.psicovirtual.community.utils.ConversionUtils.parseObjectToJson;


@Service
@AllArgsConstructor
@Slf4j
public class EmailService implements IEmailOperations {

    private final EmailProperties emailProperties;
    private final EmailConfigService emailConfigService;
    private final WebClient.Builder webClientBuilder;


    public void sendAdminEmail(String type) throws NotFoundException, JsonProcessingException {

        var emailConfig = emailConfigService.getEmailByType(type);

        log.info("Sending email to: " + emailConfig.getEmailTo());
        log.info("From: " + emailConfig.getEmailFrom());
        log.info("Subject: " + emailConfig.getSubject());
        log.info("Message: " + emailConfig.getMessage());

        var emailDTO = EmailDTO.builder().emailFrom(emailConfig.getEmailFrom()).
                emails(Set.of(emailConfig.getEmailTo())).subject(emailConfig.getSubject()).
                message(emailConfig.getMessage()).
                build();

       sendHttpRequest(emailDTO);

    }

    @Override
    public void sendEmail(String email, String type) throws NotFoundException, JsonProcessingException {
        var emailConfig = emailConfigService.getEmailByType(type);

        log.info("Sending email to: " + email);
        log.info("From: " + emailConfig.getEmailFrom());
        log.info("Subject: " + emailConfig.getSubject());
        log.info("Message: " + emailConfig.getMessage());

        var emailDTO = EmailDTO.builder().emailFrom(emailConfig.getEmailFrom()).
                emails(Set.of(email)).subject(emailConfig.getSubject()).
                message(emailConfig.getMessage()).
                build();

        sendHttpRequest(emailDTO);
    }

    /**
     * Method to send and httpRequest with the email details
     * @param emailDTO
     */
    private void sendHttpRequest(EmailDTO emailDTO) throws JsonProcessingException {
        log.info("Creating web client to send email");
        final var webClient = webClientBuilder.baseUrl(emailProperties.getBaseUrl()).build();

        var json = parseObjectToJson(emailDTO);

        webClient.post()
                .uri(emailProperties.getPath())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(json))
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> log.info("Email sent successfully"))
                .doOnError(error -> log.error("Error sending email: ", error))
                .subscribe();;
    }

}
