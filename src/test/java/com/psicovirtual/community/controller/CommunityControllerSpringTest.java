package com.psicovirtual.community.controller;

import com.psicovirtual.community.dto.JoinRequest;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
class CommunityControllerSpringTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommunityController communityController;

    @Test
    void joinUs_Success() throws Exception {

        JoinRequest joinRequest = new JoinRequest();

        MockMultipartFile file = new MockMultipartFile("files", "test.pdf", "text/pdf", "content 1".getBytes());
        String json = "{\"firstName\":\"JOHN\",\"lastName\":\"DOE\",\"secLastName\":\"\",\"dateOfBirth\":\"1990-10-07\",\"email\":\"test@test.com\",\"confirmEmail\":\"test@test.com\",\"gender\":\"MALE\",\"country\":\"US\"," +
                "\"educations\":[{\"educationId\":0,\"institution\":\"MIT\",\"graduationYear\":2010,\"certificateFilename\":\"test.pdf\",\"country\":\"US\"}],\"yearsOfExperience\":5,\"experienceDesc\":\"experience description\",\"motivationDesc\":\"motivation description\"," +
                "\"rate\":20,\"interests\":{\"interestId\":0,\"isCourse\":true,\"isStudyGroups\":true,\"isSupervisions\":true,\"isSocialMediaPromotions\":true},\"isMigrationExperience\":true,\"isOpenToAdjustRate\":true}";

        joinRequest.setFiles(new HashSet<>(Collections.singletonList(file)));
        joinRequest.setTherapistDTO(json);

        mockMvc.perform(multipart("/community/join")
                        .file(file)
                        .param("therapistDTO", joinRequest.getTherapistDTO())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());

    }

    @Test
    void joinUs_InvalidFileExtension() throws Exception {

        JoinRequest joinRequest = new JoinRequest();

        MockMultipartFile file = new MockMultipartFile("files", "test.xml", "text/xml", "content 1".getBytes());
        String json = "{\"firstName\":\"JOHN\",\"lastName\":\"DOE\",\"secLastName\":\"\",\"dateOfBirth\":\"1990-10-07\",\"email\":\"test@test.com\",\"confirmEmail\":\"test@test.com\",\"gender\":\"MALE\",\"country\":\"US\"," +
                "\"educations\":[{\"educationId\":0,\"institution\":\"MIT\",\"graduationYear\":2010,\"certificateFilename\":\"test.pdf\",\"country\":\"US\"}],\"yearsOfExperience\":5,\"experienceDesc\":\"experience description\",\"motivationDesc\":\"motivation description\"," +
                "\"rate\":20,\"interests\":{\"interestId\":0,\"isCourse\":true,\"isStudyGroups\":true,\"isSupervisions\":true,\"isSocialMediaPromotions\":true},\"isMigrationExperience\":true,\"isOpenToAdjustRate\":true}";

        joinRequest.setFiles(new HashSet<>(Collections.singletonList(file)));
        joinRequest.setTherapistDTO(json);

        mockMvc.perform(multipart("/community/join")
                        .file(file)
                        .param("therapistDTO", joinRequest.getTherapistDTO())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest());

    }



}
