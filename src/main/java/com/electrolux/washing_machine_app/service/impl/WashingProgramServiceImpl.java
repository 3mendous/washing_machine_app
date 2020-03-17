package com.electrolux.washing_machine_app.service.impl;

import com.electrolux.washing_machine_app.entity.WashingProgram;
import com.electrolux.washing_machine_app.mapper.WashingProgramMapper;
import com.electrolux.washing_machine_app.rest.dto.WashingProgramDTO;
import com.electrolux.washing_machine_app.repository.WashingProgramRepository;
import com.electrolux.washing_machine_app.service.WashingProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WashingProgramServiceImpl implements WashingProgramService {

    @Autowired
    private WashingProgramRepository washingProgramRepository;

    @Autowired
    private WashingProgramMapper washingProgramMapper;

    @Override
    public WashingProgramDTO getWashingProgramByName(String name) {
        WashingProgram washingProgram = washingProgramRepository.findByName(name);
        return washingProgram == null
                ? null
                : washingProgramMapper.toDto(washingProgram);
    }

    @Override
    public List<WashingProgramDTO> getAllWashingPrograms() {
        return washingProgramRepository.findAll().stream()
                .map(washingProgramMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<WashingProgramDTO> getAllWashingProgramsByMaterial(String material) {
        return washingProgramRepository.findAllByMaterial(material).stream()
                .map(washingProgramMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void saveWashingProgram(WashingProgramDTO washingProgramDTO) {
        washingProgramRepository.save(washingProgramMapper.toEntity(washingProgramDTO));
    }

    @Override
    public void deleteWashingProgram(String name) {
        washingProgramRepository.deleteByName(name);
    }

    @Override
    public void updateWashingProgram(String name, WashingProgramDTO washingProgramDTO) {
        washingProgramRepository.deleteByName(name);
        washingProgramRepository.save(washingProgramMapper.toEntity(washingProgramDTO));
    }
}
