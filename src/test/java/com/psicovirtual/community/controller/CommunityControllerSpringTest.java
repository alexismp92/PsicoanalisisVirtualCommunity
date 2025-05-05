package com.psicovirtual.community.controller;

import com.psicovirtual.community.dto.JoinRequest;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
class CommunityControllerSpringTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void joinUsSuccess() throws Exception {

        JoinRequest joinRequest = new JoinRequest();

        MockMultipartFile file = new MockMultipartFile("files", "test.pdf", "text/pdf", "content 1".getBytes());
        String json = "{\"firstName\":\"JOHN\",\"lastName\":\"DOE\",\"secLastName\":\"\",\"dateOfBirth\":\"1990-10-07\",\"email\":\"test@test.com\",\"confirmEmail\":\"test@test.com\",\"gender\":\"MALE\",\"country\":\"US\"," +
                "\"educations\":[{\"educationId\":0,\"institution\":\"MIT\",\"graduationYear\":2010,\"certificateFilename\":\"test.pdf\",\"country\":\"US\"}],\"yearsOfExperience\":5,\"experienceDesc\":\"experience description\",\"motivationDesc\":\"motivation description\"," +
                "\"rate\":20,\"interests\":{\"interestId\":0,\"isCourse\":true,\"isStudyGroups\":true,\"isSupervisions\":true,\"isSocialMediaPromotions\":true},\"isMigrationExperience\":true,\"isOpenToAdjustRate\":true}";

        joinRequest.setFiles(new HashSet<>(Collections.singletonList(file)));
        joinRequest.setTherapistDTO(json);

        mockMvc.perform(multipart("/community/join-request")
                        .file(file)
                        .param("therapistDTO", joinRequest.getTherapistDTO())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());

    }

    @Test
    @Order(2)
    void joinUsInvalidFileExtension() throws Exception {

        JoinRequest joinRequest = new JoinRequest();

        MockMultipartFile file = new MockMultipartFile("files", "test.xml", "text/xml", "content 1".getBytes());
        String json = "{\"firstName\":\"JOHN\",\"lastName\":\"DOE\",\"secLastName\":\"\",\"dateOfBirth\":\"1990-10-07\",\"email\":\"test@test.com\",\"confirmEmail\":\"test@test.com\",\"gender\":\"MALE\",\"country\":\"US\"," +
                "\"educations\":[{\"educationId\":0,\"institution\":\"MIT\",\"graduationYear\":2010,\"certificateFilename\":\"test.pdf\",\"country\":\"US\"}],\"yearsOfExperience\":5,\"experienceDesc\":\"experience description\",\"motivationDesc\":\"motivation description\"," +
                "\"rate\":20,\"interests\":{\"interestId\":0,\"isCourse\":true,\"isStudyGroups\":true,\"isSupervisions\":true,\"isSocialMediaPromotions\":true},\"isMigrationExperience\":true,\"isOpenToAdjustRate\":true}";

        joinRequest.setFiles(new HashSet<>(Collections.singletonList(file)));
        joinRequest.setTherapistDTO(json);

        mockMvc.perform(multipart("/community/join-request")
                        .file(file)
                        .param("therapistDTO", joinRequest.getTherapistDTO())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest());

    }

    @Test
    @Order(3)
    void updateCommunityStatusAcceptedTest() throws Exception {

        String json = "[{\"therapistId\":1,\"firstName\":\"JOHN\",\"lastName\":\"DOE\",\"secLastName\":\"\",\"dateOfBirth\":\"1990-10-07\",\"email\":\"test@test.com\",\"confirmEmail\":\"test@test.com\",\"gender\":\"MALE\",\"country\":\"US\"," +
                "\"educations\":[{\"educationId\":0,\"institution\":\"MIT\",\"graduationYear\":2010,\"certificateFilename\":\"test.pdf\",\"country\":\"US\"}],\"yearsOfExperience\":5,\"experienceDesc\":\"experience description\",\"motivationDesc\":\"motivation description\"," +
                "\"rate\":20,\"interests\":{\"interestId\":0,\"isCourse\":true,\"isStudyGroups\":true,\"isSupervisions\":true,\"isSocialMediaPromotions\":true}, \"communityRequest\":{\"communityReqId\":1,\"communityStatus\":\"APPROVED\"}," +
                "\"isMigrationExperience\":true,\"isOpenToAdjustRate\":true}]";


        mockMvc.perform(patch("/community/join-request/status")
                        .contentType(MediaType.APPLICATION_JSON).
                        content(json))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    void updateCommunityStatusRejectedTest() throws Exception {

        String json = "[{\"therapistId\":1,\"firstName\":\"JOHN\",\"lastName\":\"DOE\",\"secLastName\":\"\",\"dateOfBirth\":\"1990-10-07\",\"email\":\"test@test.com\",\"confirmEmail\":\"test@test.com\",\"gender\":\"MALE\",\"country\":\"US\"," +
                "\"educations\":[{\"educationId\":0,\"institution\":\"MIT\",\"graduationYear\":2010,\"certificateFilename\":\"test.pdf\",\"country\":\"US\"}],\"yearsOfExperience\":5,\"experienceDesc\":\"experience description\",\"motivationDesc\":\"motivation description\"," +
                "\"rate\":20,\"interests\":{\"interestId\":0,\"isCourse\":true,\"isStudyGroups\":true,\"isSupervisions\":true,\"isSocialMediaPromotions\":true}, \"communityRequest\":{\"communityReqId\":1,\"communityStatus\":\"REJECTED\",\"rejectedReason\":\"therapist does not have a bachelors degree\"}," +
                "\"isMigrationExperience\":true,\"isOpenToAdjustRate\":true}]";

        mockMvc.perform(patch("/community/join-request/status")
                        .contentType(MediaType.APPLICATION_JSON).
                        content(json))
                .andExpect(status().isOk());
    }

    @Test
    @Order(5)
    void updateEmptyCommunityStatusTest() throws Exception {
        String json = "[]";
        mockMvc.perform(patch("/community/join-request/status")
                        .contentType(MediaType.APPLICATION_JSON).
                        content(json))
                .andExpect(status().isBadRequest());
    }


}
