package com.electrolux.washing_machine_app.service;

import com.electrolux.washing_machine_app.rest.dto.WashingProgramDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WashingProgramService {
    WashingProgramDTO getWashingProgramByName (String name);
    List<WashingProgramDTO> getAllWashingPrograms();
    List<WashingProgramDTO> getAllWashingProgramsByMaterial(String material);
    void saveWashingProgram(WashingProgramDTO washingProgram);
    void deleteWashingProgram (String name);
    void updateWashingProgram (String name, WashingProgramDTO washingProgram);
}
