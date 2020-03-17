package com.electrolux.washing_machine_app.service.impl;

import com.electrolux.washing_machine_app.rest.dto.WashingProgramDTO;
import com.electrolux.washing_machine_app.rest.dto.CurrentStateDTO;
import com.electrolux.washing_machine_app.rest.dto.StateTransitionDTO;
import com.electrolux.washing_machine_app.service.StateTransitionService;
import com.electrolux.washing_machine_app.state_mashine.enums.Events;
import com.electrolux.washing_machine_app.state_mashine.enums.States;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static com.electrolux.washing_machine_app.state_mashine.enums.Events.*;

@Service
public class StateTransitionServiceImpl implements StateTransitionService {

    static Timer timer;
    long endTime;
    long duration;

    @Autowired
    private StateMachine<States, Events> stateMachine;

    @Override
    public StateTransitionDTO startProgram(WashingProgramDTO washingProgramDTO) {
        try {
            return sendEventAndBuildStateDTO(START_PROGRAM);
        } finally {
            duration = washingProgramDTO.getDuration() * 60000;
            endTime = new Date().getTime() + duration;
            executeTask();
        }
    }

    @Override
    public StateTransitionDTO pauseProgram() {
        try {
            return sendEventAndBuildStateDTO(PAUSE_PROGRAM);
        } finally {
            timer.cancel();
            duration = endTime - new Date().getTime();
        }
    }

    @Override
    public StateTransitionDTO continueProgram() {
        try {
            return sendEventAndBuildStateDTO(CONTINUE_PROGRAM);
        } finally {
            executeTask();
        }
    }

    @Override
    public StateTransitionDTO stopProgram() {
        return sendEventAndBuildStateDTO(FINISH_PROGRAM);
    }

    @Override
    public CurrentStateDTO getState() {
        return CurrentStateDTO.builder()
        .currentState(stateMachine.getState().getId().toString())
                .build();
    }

    private void executeTask() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                stateMachine.sendEvent(FINISH_PROGRAM);
            }
        };
        timer = new Timer();
        timer.schedule(task, duration);
    }

    private StateTransitionDTO sendEventAndBuildStateDTO(Events events) {
        String initialState = stateMachine.getState().getId().toString();
        stateMachine.sendEvent(events);
        return StateTransitionDTO.builder()
                .initialState(initialState)
                .nextState(stateMachine.getState().getId().toString())
                .build();
    }
}

