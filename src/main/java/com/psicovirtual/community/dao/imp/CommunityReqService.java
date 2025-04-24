package com.psicovirtual.community.dao.imp;

import com.psicovirtual.community.dao.ICommunityReqRepo;
import com.psicovirtual.community.entities.CommunityReq;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class CommunityReqService{

    private final ICommunityReqRepo iCommunityReqRepo;

    /**
     * Method to get all the CommunityRequest based on its ID
     * @param communityRequestIds
     * @return
     */
    public Collection<CommunityReq> findAllById(Collection<Long> communityRequestIds) {
        return iCommunityReqRepo.findAllById(communityRequestIds);
    }

    /**
     * Method to save multiple CommunityRequests
     * @param communityRequests
     * @return
     */
    public Collection<CommunityReq> save(Collection<CommunityReq> communityRequests) {
        return iCommunityReqRepo.saveAll(communityRequests);
    }


}
