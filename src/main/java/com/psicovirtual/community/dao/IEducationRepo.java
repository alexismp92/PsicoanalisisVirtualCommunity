package com.psicovirtual.community.dao;

import com.psicovirtual.community.entities.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEducationRepo extends JpaRepository<Education, Long> {
}
