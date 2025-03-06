package com.psicovirtual.community.dao.imp;

import com.psicovirtual.community.dao.ICommunityStsRepo;
import com.psicovirtual.community.entities.CommunitySts;
import com.psicovirtual.community.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommunityStatusService {

    private final ICommunityStsRepo iCommunityStsRepo;

    /**
     * Method to get a CommunityStatus based on the status name
     * @param statusName
     * @return
     * @throws NotFoundException
     */
    public CommunitySts getCommunityStatus(String statusName) throws NotFoundException {
        log.info("get communityStatus " + statusName);
        return iCommunityStsRepo.findByCommStatusName(statusName).
                orElseThrow(()-> new NotFoundException("status name " + statusName + " not found"));
    }


}
