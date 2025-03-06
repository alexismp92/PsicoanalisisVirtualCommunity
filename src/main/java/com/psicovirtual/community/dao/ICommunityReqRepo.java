package com.psicovirtual.community.dao;

import com.psicovirtual.community.entities.CommunityReq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommunityReqRepo extends JpaRepository<CommunityReq, Long> {
}
