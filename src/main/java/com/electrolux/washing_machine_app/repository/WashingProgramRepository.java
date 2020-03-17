package com.electrolux.washing_machine_app.repository;

import com.electrolux.washing_machine_app.entity.WashingProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface WashingProgramRepository extends JpaRepository<WashingProgram, UUID> {
    WashingProgram findByName (String name);
    List<WashingProgram> findAllByMaterial(String material);
    List<WashingProgram> findAllByMaterialAndDurationBetween(String material,
                                                             int minDuration,
                                                             int maxDuration);
    @Transactional
    void deleteByName(String name);
}
