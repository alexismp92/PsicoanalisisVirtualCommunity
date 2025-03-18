package com.psicovirtual.community.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.psicovirtual.community.dto.JoinRequest;
import com.psicovirtual.community.dto.TherapistDTO;
import com.psicovirtual.community.exception.CommunityException;
import com.psicovirtual.community.service.CommunityService;
import com.psicovirtual.community.utils.ConversionUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
import java.util.stream.Collectors;

import static com.psicovirtual.community.utils.Constants.*;
import static com.psicovirtual.community.utils.Utils.getExtension;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/community")
@Validated
@Tag(name = "Community Service")
public class CommunityController {

    private final CommunityService communityService;

    @PostMapping(value = "/join", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Send a request to join to to the community",
            description = "Method which registers the user request to join to the community and save the therapist data",
            responses = {
                @ApiResponse(responseCode = "200", description = "Community join request sent successfully"),
                @ApiResponse(responseCode = "400", description = "Bad request"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
            })
       public ResponseEntity<Void> joinUs(@ModelAttribute JoinRequest request) throws CommunityException {

        fileValidator(request.getFiles());

        log.info("files received " + request.getFiles().size());
        log.info("json received " + request.getTherapistDTO());

        try{
            var therapistDTO = ConversionUtils.parseJsonToObject(request.getTherapistDTO(), TherapistDTO.class);

            this.communityService.sendJoinRequest(therapistDTO, request.getFiles());

        }catch (JsonProcessingException ex){
            throw new CommunityException(ex.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Method to validate the files received in the request
     * @param files
     * @throws CommunityException
     */
    private void fileValidator(Set<MultipartFile> files) throws CommunityException{

        if (files == null){
            throw new CommunityException("Files not found");
        }
        if (files.size() > 5){
            throw new CommunityException("Number of files is invalid");
        }
        if(files.stream().filter(file -> file.getSize() > MAX_FILE_SIZE).findAny().isPresent()){
            throw new CommunityException("File size not allowed");
        }
        var filenames = files.stream().map(file -> file.getOriginalFilename()).collect(Collectors.toSet());

        for(String name : filenames){

            final var extension = getExtension(name);

            if(!extension.equalsIgnoreCase(EXT_PDF) && !extension.equalsIgnoreCase((EXT_JPEG)) &&
                    !extension.equalsIgnoreCase(EXT_JPG) && !extension.equalsIgnoreCase(EXT_PNG)){
                throw new CommunityException("File extension is not allowed");
            }
        }

    }

}
