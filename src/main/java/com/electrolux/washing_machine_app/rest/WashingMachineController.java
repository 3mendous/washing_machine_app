package com.electrolux.washing_machine_app.rest;

import com.electrolux.washing_machine_app.exception.RestException;
import com.electrolux.washing_machine_app.rest.dto.CurrentStateDTO;
import com.electrolux.washing_machine_app.rest.dto.StateTransitionDTO;
import com.electrolux.washing_machine_app.rest.dto.WashingProgramDTO;
import com.electrolux.washing_machine_app.service.StateTransitionService;
import com.electrolux.washing_machine_app.service.WashingProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WashingMachineController {

    @Autowired
    private StateTransitionService stateTransitionService;

    @Autowired
    private WashingProgramService washingProgramService;


    @GetMapping("/start")
    @ResponseBody
    public StateTransitionDTO startProgram(@RequestParam(name = "name") String name) {
        return stateTransitionService.startProgram(washingProgramService.getWashingProgramByName(name));
    }

    @GetMapping("/pause")
    @ResponseBody
    public StateTransitionDTO pauseProgram() {
        return stateTransitionService.pauseProgram();
    }

    @GetMapping("/continue")
    @ResponseBody
    public StateTransitionDTO continueProgram() {
        return stateTransitionService.continueProgram();
    }

    @GetMapping("/stop")
    @ResponseBody
    public StateTransitionDTO stopProgram() {
        return stateTransitionService.stopProgram();
    }

    @GetMapping("/getState")
    @ResponseBody
    public CurrentStateDTO getState() {
        return stateTransitionService.getState();
    }

    @GetMapping("/getAll")
    @ResponseBody
    public List<WashingProgramDTO> getAllWashingPrograms() {
        return washingProgramService.getAllWashingPrograms();
    }

    @GetMapping("/getByName")
    @ResponseBody
    public WashingProgramDTO getWashingProgramByName(@RequestParam(name = "name") String name) throws RestException {
        WashingProgramDTO washingProgramDTO = washingProgramService.getWashingProgramByName(name);
        if (washingProgramDTO != null) {
            return washingProgramDTO;
        } else {
            throw new RestException("No program with such name");
        }
    }

    @GetMapping("/getByMaterial")
    @ResponseBody
    public List<WashingProgramDTO> getAllWashingProgramsByMaterial(@RequestParam(name = "material") String material) {
        return washingProgramService.getAllWashingProgramsByMaterial(material);
    }

    @PostMapping("/save")
    public void saveWashingProgram(@RequestBody WashingProgramDTO washingProgramDTO) throws RestException {
        if (washingProgramService.getWashingProgramByName(washingProgramDTO.getName()) == null) {
            washingProgramService.saveWashingProgram(washingProgramDTO);
        } else {
            throw new RestException("There is program with such name");
        }
    }

    @DeleteMapping("/delete")
    public void deleteWashingProgram(@RequestParam(name = "name") String name) {
        washingProgramService.deleteWashingProgram(name);
    }

    @PostMapping("/update")
    public void updateWashingProgram(@RequestParam(name = "name") String name,
                                     @RequestBody WashingProgramDTO washingProgramDTO) {
        washingProgramService.updateWashingProgram(name, washingProgramDTO);
    }

}
