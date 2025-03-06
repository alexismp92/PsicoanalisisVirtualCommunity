package com.psicovirtual.community.dao;

import com.psicovirtual.community.entities.CommunitySts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICommunityStsRepo extends JpaRepository<CommunitySts, Long> {
    Optional<CommunitySts> findByCommStatusName(String commStatusName);
}
