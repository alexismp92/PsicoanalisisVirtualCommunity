package com.psicovirtual.community.service.email.imp;

import com.psicovirtual.community.component.EmailProperties;

import com.psicovirtual.community.dao.imp.EmailConfigService;
import com.psicovirtual.community.dto.EmailDTO;
import com.psicovirtual.community.exception.NotFoundException;
import com.psicovirtual.community.service.email.IEmailOperations;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Set;

import static com.psicovirtual.community.utils.Constants.REG_ADMIN;


@Service
@AllArgsConstructor
@Slf4j
@Profile({"local"})
public class EmailService implements IEmailOperations {

    private final EmailProperties emailProperties;
    private final EmailConfigService emailConfigService;
    private final WebClient.Builder webClientBuilder;


    public void sendEmail() throws NotFoundException {

        var emailConfig = emailConfigService.getEmailByType(REG_ADMIN);

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
    public void sendEmail(String email, String type) throws NotFoundException {
        var emailConfig = emailConfigService.getEmailByType(type);

        log.info("Sending email to: " + email);
        log.info("From: " + email);
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
    private void sendHttpRequest(EmailDTO emailDTO){
        log.info("Creating web client to send email");
        final var webClient = webClientBuilder.baseUrl(emailProperties.getBaseUrl()).build();

        webClient.post()
                .uri(emailProperties.getPath())
                .body(Mono.just(emailDTO), String.class)
                .retrieve()
                .bodyToMono(String.class);

        log.info("email sent");
    }

}
