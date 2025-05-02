package com.psicovirtual.community.service.email.imp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.psicovirtual.community.component.EmailProperties;

import com.psicovirtual.community.dto.EmailRequestDTO;
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
    private final WebClient.Builder webClientBuilder;


    public void sendAdminEmail(String emailType) throws JsonProcessingException {

        log.info("Sending email type: " + emailType);
        var emailRequestDTO = EmailRequestDTO.builder().
                emailType(emailType).build();

       sendHttpRequest(emailRequestDTO);

    }

    @Override
    public void sendEmail(String email, String emailType) throws NotFoundException, JsonProcessingException {

        log.info("Sending email type %s to : %s", emailType, email);

        var emailDTO = EmailRequestDTO.builder().emailType(emailType).
                emails(Set.of(email)).build();

        sendHttpRequest(emailDTO);
    }

    /**
     * Method to send and httpRequest with the email details
     * @param emailRequestDTO
     */
    private void sendHttpRequest(EmailRequestDTO emailRequestDTO) throws JsonProcessingException {
        log.info("Creating web client to send email");
        final var webClient = webClientBuilder.baseUrl(emailProperties.getBaseUrl()).build();

        var json = parseObjectToJson(emailRequestDTO);

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
