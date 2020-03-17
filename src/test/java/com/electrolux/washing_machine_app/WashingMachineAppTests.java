package com.electrolux.washing_machine_app;

import com.electrolux.washing_machine_app.entity.WashingProgram;
import com.electrolux.washing_machine_app.exception.RestException;
import com.electrolux.washing_machine_app.mapper.WashingProgramMapper;
import com.electrolux.washing_machine_app.rest.WashingMachineController;
import com.electrolux.washing_machine_app.rest.dto.WashingProgramDTO;
import com.electrolux.washing_machine_app.service.WashingProgramService;
import com.electrolux.washing_machine_app.state_mashine.enums.Events;
import com.electrolux.washing_machine_app.state_mashine.enums.States;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;

import java.util.List;
import java.util.stream.Stream;

import static com.electrolux.washing_machine_app.state_mashine.enums.Events.*;
import static com.electrolux.washing_machine_app.state_mashine.enums.States.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class WashingMachineAppTests {

    @Autowired
    private StateMachine<States, Events> stateMachine;

    @Autowired
    private WashingProgramMapper mapper;

    @Autowired
    private WashingProgramService washingProgramService;

    @Autowired
    private WashingMachineController washingMachineController;

    @Test
    void checkInitialState() {
        stateMachine.start();
        assertEquals(READY, stateMachine.getState().getId());
        stateMachine.stop();
    }

    @Test
    void checkReadyStateTransitions() {
        stateMachine.start();
        stateMachine.sendEvent(START_PROGRAM);
        assertEquals(WORKING, stateMachine.getState().getId());
        stateMachine.stop();
    }

    @ParameterizedTest
    @MethodSource
    void checkWorkingStateTransitions(Events event, States state) {
        stateMachine.start();
        stateMachine.sendEvent(START_PROGRAM);
        stateMachine.sendEvent(event);
        assertEquals(state, stateMachine.getState().getId());
        stateMachine.stop();
    }

    @Test
    void checkPausedStateTransitions() {
        stateMachine.start();
        stateMachine.sendEvent(START_PROGRAM);
        stateMachine.sendEvent(PAUSE_PROGRAM);
        stateMachine.sendEvent(CONTINUE_PROGRAM);
        assertEquals(WORKING, stateMachine.getState().getId());
        stateMachine.stop();
    }

    @Test
    void checkDtoToEntityMapping() {
        WashingProgramDTO washingProgramDTO = buildDTO();
        WashingProgram washingProgram = mapper.toEntity(washingProgramDTO);
        assertThat(washingProgramDTO).isEqualToIgnoringGivenFields(washingProgram, "id");
    }

    @Test
    void checkEntityToDtoMapping() {
        WashingProgram washingProgram = buildEntity();
        WashingProgramDTO washingProgramDTO = mapper.toDto(washingProgram);
        assertThat(washingProgramDTO).isEqualToIgnoringGivenFields(washingProgram, "id");
    }

    @Test
    public void checkEntitySavingAndGettingByName() {
        WashingProgramDTO washingProgram = getDtoAndSaveProgram();
        WashingProgramDTO savedWashingProgram =
                washingProgramService.getWashingProgramByName(washingProgram.getName());
        assertThat(washingProgram).isEqualToComparingFieldByField(savedWashingProgram);
        washingProgramService.deleteWashingProgram(washingProgram.getName());
    }

    @Test
    public void checkEntityDeleting() {
        WashingProgramDTO washingProgram = getDtoAndSaveProgram();
        washingProgramService.deleteWashingProgram(washingProgram.getName());
        assertNull(washingProgramService.getWashingProgramByName(washingProgram.getName()));
        washingProgramService.deleteWashingProgram(washingProgram.getName());
    }

    @Test
    public void checkEntityGettingByMaterial() {
        WashingProgramDTO washingProgram = getDtoAndSaveProgram();
        List<WashingProgramDTO> savedWashingPrograms =
                washingProgramService.getAllWashingProgramsByMaterial(washingProgram.getMaterial());
        assertThat(washingProgram).isEqualToComparingFieldByField(savedWashingPrograms.get(0));
        washingProgramService.deleteWashingProgram(washingProgram.getName());
    }

    @Test
    public void checkEntityUpdating() {
        WashingProgramDTO washingProgram = getDtoAndSaveProgram();
        washingProgram.setSpinningSpeed(1400);
        washingProgramService.updateWashingProgram(washingProgram.getName(), washingProgram);
        WashingProgramDTO updatedWashingProgram =
                washingProgramService.getWashingProgramByName(washingProgram.getName());
        assertThat(washingProgram).isEqualToComparingFieldByField(updatedWashingProgram);
        washingProgramService.deleteWashingProgram(washingProgram.getName());
    }

    @Test
    public void checkSaveException() {
        WashingProgramDTO washingProgram = getDtoAndSaveProgram();
        assertThrows(RestException.class, () -> washingMachineController.saveWashingProgram(washingProgram));
        washingProgramService.deleteWashingProgram(washingProgram.getName());
    }

    @Test
    public void checkGetByNameException() {
        WashingProgramDTO washingProgram = getDtoAndSaveProgram();
        washingProgram.setName("anotherName");
        assertThrows(RestException.class,
                () -> washingMachineController.getWashingProgramByName(washingProgram.getName()));
        washingProgramService.deleteWashingProgram(washingProgram.getName());
    }

    private WashingProgramDTO getDtoAndSaveProgram() {
        WashingProgramDTO washingProgram = buildDTO();
        washingProgramService.saveWashingProgram(washingProgram);
        return washingProgram;
    }

    private static Stream<Arguments> checkWorkingStateTransitions() {
        return Stream.of(
                Arguments.of(PAUSE_PROGRAM, PAUSED),
                Arguments.of(FINISH_PROGRAM, FINISHED)
        );
    }

    private WashingProgramDTO buildDTO() {
        return WashingProgramDTO.builder()
                .name("name")
                .material("material")
                .temperature(90)
                .duration(120)
                .spinningSpeed(800)
                .build();
    }

    private WashingProgram buildEntity() {
        return WashingProgram.builder()
                .name("name")
                .material("material")
                .temperature(90)
                .duration(120)
                .spinningSpeed(800)
                .build();
    }

}
