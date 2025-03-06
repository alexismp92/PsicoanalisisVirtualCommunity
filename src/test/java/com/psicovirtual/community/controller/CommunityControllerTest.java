package com.psicovirtual.community.controller;

import com.psicovirtual.community.dto.JoinRequest;
import com.psicovirtual.community.dto.TherapistDTO;
import com.psicovirtual.community.exception.CommunityException;
import com.psicovirtual.community.service.CommunityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommunityControllerTest {

    @Mock
    private CommunityService communityService;

    @InjectMocks
    private CommunityController communityController;


    @Test
    void joinUs_Success() throws Exception {
        JoinRequest joinRequest = new JoinRequest();

        MockMultipartFile file = new MockMultipartFile("test", "test.pdf", "text/pdf", "content 1".getBytes());
        String json = "{\"firstName\":\"JOHN\",\"lastName\":\"DOE\",\"secLastName\":\"\",\"dateOfBirth\":\"1990-10-07\",\"email\":\"test@test.com\",\"confirmEmail\":\"test@test.com\",\"gender\":\"MALE\",\"country\":\"US\"," +
                "\"educations\":[{\"educationId\":0,\"institution\":\"MIT\",\"graduationYear\":2010,\"certificateFilename\":\"test.pdf\",\"country\":\"US\"}],\"yearsOfExperience\":5,\"experienceDesc\":\"experience description\",\"motivationDesc\":\"motivation description\"," +
                "\"rate\":20,\"interests\":{\"interestId\":0,\"isCourse\":true,\"isStudyGroups\":true,\"isSupervisions\":true,\"isSocialMediaPromotions\":true},\"isMigrationExperience\":true,\"isOpenToAdjustRate\":true}";

        joinRequest.setFiles(new HashSet<>(Collections.singletonList(file)));
        joinRequest.setTherapistDTO(json);

        when(communityService.sendJoinRequest(any(TherapistDTO.class), any(Set.class))).thenReturn(true);
        ResponseEntity<Void> response = communityController.joinUs(joinRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(communityService, times(1)).sendJoinRequest(any(TherapistDTO.class), any(Set.class));
    }

    @Test
    void joinUs_InvalidJsonFormat(){
        JoinRequest joinRequest = new JoinRequest();
        MockMultipartFile file = new MockMultipartFile("test", "test.pdf", "text/pdf", "content 1".getBytes());
        joinRequest.setFiles(new HashSet<>(Collections.singletonList(file)));
        joinRequest.setTherapistDTO("invalid");

        assertThrows(CommunityException.class, () -> communityController.joinUs(joinRequest));
    }

    @Test
    void joinUs_NoFilesProvided() {
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setFiles(null);
        joinRequest.setTherapistDTO("{}");
        String msg = null;
        try {
            communityController.joinUs(joinRequest);
        } catch (CommunityException e) {
            msg = e.getMessage();
            assertEquals(e.getMessage(),"Files not found");
        }
        assertNotNull(msg);
    }

    @Test
    void joinUs_TooManyFiles() {
        Set<MultipartFile> files = new HashSet<>();
        for (int i = 0; i < 6; i++) {
            MockMultipartFile file = new MockMultipartFile("test"+i, "test"+1+".pdf", "text/pdf", "content 1".getBytes());
            files.add(file);
        }
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setFiles(files);
        joinRequest.setTherapistDTO("{}");
        String msg = null;


        try {
            communityController.joinUs(joinRequest);
        } catch (CommunityException e) {
            msg = e.getMessage();
            assertEquals(e.getMessage(),"Number of files is invalid");
        }
        assertNotNull(msg);

    }

    @Test
    void joinUs_FileSizeExceeded() {
        MockMultipartFile file = new MockMultipartFile("test", "test.pdf", "text/pdf", new byte[1024 * 1024 * 11]);
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setFiles(new HashSet<>(Collections.singletonList(file)));
        joinRequest.setTherapistDTO("{}");
        String msg = null;

        try {
            communityController.joinUs(joinRequest);
        } catch (CommunityException e) {
            msg = e.getMessage();
            assertEquals(e.getMessage(),"File size not allowed");
        }
        assertNotNull(msg);


    }

    @Test
    void joinUs_InvalidFileExtension() {
        MockMultipartFile file = new MockMultipartFile("test", "test", "text/pdf", new byte[1024 * 1024 * 1]);
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setFiles(new HashSet<>(Collections.singletonList(file)));
        joinRequest.setTherapistDTO("{}");

        String msg = null;

        try {
            communityController.joinUs(joinRequest);
        } catch (CommunityException e) {
            msg = e.getMessage();
            assertEquals(e.getMessage(),"Invalid file extension");
        }
        assertNotNull(msg);
    }

    @Test
    void joinUs_FileExtensionNotAllowed() {
        MockMultipartFile file = new MockMultipartFile("test", "test.xml", "text/xml", new byte[1024 * 1024 * 1]);
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setFiles(new HashSet<>(Collections.singletonList(file)));
        joinRequest.setTherapistDTO("{}");

        String msg = null;

        try {
            communityController.joinUs(joinRequest);
        } catch (CommunityException e) {
            msg = e.getMessage();
            assertEquals(e.getMessage(),"File extension is not allowed");
        }
        assertNotNull(msg);
    }
}