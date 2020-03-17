package com.electrolux.washing_machine_app.service;

import com.electrolux.washing_machine_app.rest.dto.WashingProgramDTO;
import com.electrolux.washing_machine_app.rest.dto.CurrentStateDTO;
import com.electrolux.washing_machine_app.rest.dto.StateTransitionDTO;
import org.springframework.stereotype.Service;

@Service
public interface StateTransitionService {
    StateTransitionDTO startProgram(WashingProgramDTO washingProgramDTO);
    StateTransitionDTO pauseProgram();
    StateTransitionDTO continueProgram();
    StateTransitionDTO stopProgram();
    CurrentStateDTO getState();
}
