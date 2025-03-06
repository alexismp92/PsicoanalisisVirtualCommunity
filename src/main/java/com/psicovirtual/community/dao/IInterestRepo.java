package com.psicovirtual.community.dao;

import com.psicovirtual.community.entities.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInterestRepo extends JpaRepository<Interest, Long> {
}
