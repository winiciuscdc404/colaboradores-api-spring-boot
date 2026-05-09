package com.sistema01.backend.repository;

import com.sistema01.backend.model.BackendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackendRepository extends JpaRepository<BackendEntity, Integer> {
}

