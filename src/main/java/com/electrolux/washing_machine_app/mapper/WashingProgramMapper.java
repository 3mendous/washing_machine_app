package com.electrolux.washing_machine_app.mapper;

import com.electrolux.washing_machine_app.entity.WashingProgram;
import com.electrolux.washing_machine_app.rest.dto.WashingProgramDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class WashingProgramMapper {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Autowired
    private ModelMapper modelMapper;

    public WashingProgram toEntity(WashingProgramDTO washingProgramDTO) {
        return modelMapper.map(washingProgramDTO, WashingProgram.class);
    }

    public WashingProgramDTO toDto(WashingProgram washingProgram) {
        return modelMapper.map(washingProgram, WashingProgramDTO.class);
    }
}
